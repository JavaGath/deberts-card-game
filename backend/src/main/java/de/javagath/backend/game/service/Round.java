package de.javagath.backend.game.service;

import de.javagath.backend.game.model.deck.Card;
import de.javagath.backend.game.model.deck.Challenge;
import de.javagath.backend.game.model.deck.Deck;
import de.javagath.backend.game.model.deck.DeckFactory;
import de.javagath.backend.game.model.deck.Trump;
import de.javagath.backend.game.model.enums.Owner;
import de.javagath.backend.game.model.enums.PhaseName;
import de.javagath.backend.game.model.enums.Suit;
import de.javagath.backend.game.service.phase.Phase;
import de.javagath.backend.game.service.phase.PhaseFactory;

/**
 * An entity that contains and manages all {@code Decks} for the round. Each round has a {@code
 * CardDeck} which contains all {@code Cards} at the beginning, trump deck with {@code Trump} and
 * two {@code HandDecks} for each player. This information contains inside of {@code
 * RoundInformation} object. Each {@code Round} has three phases: trade, combination and action.
 *
 * <p>In the trade-phase each player has 6 cards and decides about a trump to play, in the
 * combination-phase each player has 9 cards and starts a challenge with its possible combinations
 * the action-phase each player begins with 9 cards and challenges with it each turn.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.0
 * @since 1.0
 */
class Round {
  private RoundInformation information;
  private Phase phase;

  private Round(RoundInformation information) {
    this.information = information;
    phase = PhaseFactory.getPhase(information);
  }

  /**
   * Factory method to create a new {@code Round}. If beginner is not a bot or player it will be
   * decided randomly.
   *
   * @param beginner of the new Round.
   * @return new {@code Round} object
   */
  static Round newInstance(Owner beginner) {
    RoundInformation newInformation = createDefaultInformation();
    if (beginner.equals(Owner.BOT) || beginner.equals(Owner.PLAYER)) {
      newInformation.setTurn(beginner);
      newInformation.setBeginner(beginner);
    } else {
      int ownerValue = (Math.random() <= 0.5) ? 1 : 2;
      Owner newBeginner = Owner.getOwnerByValue(ownerValue);
      newInformation.setTurn(newBeginner);
      newInformation.setBeginner(newBeginner);
    }
    return new Round(newInformation);
  }

  /**
   * Factory method to create a new {@code Round} based on RoundInformation and wished phase.
   *
   * @param information Round information
   * @param phase wised phase for the round
   * @return new {@code Round} object
   */
  static Round newInstance(RoundInformation information, PhaseName phase) {
    Round round = newInstance(Owner.NOBODY);
    if (phase == PhaseName.COMBO) {
      round.switchPhase();
    } else if (phase == PhaseName.ACTION) {
      round.switchPhase();
      round.switchPhase();
    }
    round.setInformation(information);
    return round;
  }

  private static RoundInformation createDefaultInformation() {
    Deck cardDeck = DeckFactory.getDeck(Owner.NOBODY);
    Card trumpCard = cardDeck.dealRandomCard();
    Deck playerDeck = DeckFactory.getDeck(Owner.PLAYER);
    Deck botDeck = DeckFactory.getDeck(Owner.BOT);
    return RoundInformation.builder()
        .cardDeck(cardDeck)
        .playerDeck(playerDeck)
        .botDeck(botDeck)
        .trumpDeck(Trump.newInstance(trumpCard))
        .trumpChangePossible(true)
        .build();
  }

  /**
   * Returns information about the current state of the {@code Round}.
   *
   * @return current {@code Round} information
   */
  RoundInformation getInformation() {
    return information;
  }

  /**
   * Sets new {@code RoundInformation} for the round and its current phase. The main Idea of this
   * method is to improve unit-testing for the project.
   *
   * @param information new round information
   */
  void setInformation(RoundInformation information) {
    this.information = information;
    phase.setInformation(information);
  }

  /** Resets current round if one player has for sevens. */
  void resetCauseFourSevens() {
    if (phase.isFourSevenResettable()) {
      information = createDefaultInformation();
      phase = PhaseFactory.getPhase(information);
    } else {
      throw new IllegalStateException("Round could not be resetted in the current state");
    }
  }

  /**
   * Returns true if it is possible to switch a trump seven.
   *
   * @return true if possible
   */
  boolean isSevenSwitchable() {
    return phase.isSevenSwitchable();
  }

  /**
   * Returns true if it is possible to reset a round because of four sevens in the hand.
   *
   * @return true if player has four sevens in the hand
   */
  boolean isFourSevenResettable() {
    return phase.isFourSevenResettable();
  }

  /**
   * Returns a total number of cards in the hands of players.
   *
   * @return total card number
   */
  int countHandCards() {
    return information.countHandCards();
  }

  /**
   * Returns the trump suit.
   *
   * @return trump suit
   */
  Suit getTrumpSuit() {
    return information.getTrumpSuit();
  }

  /**
   * Switches current {@code Round} to the next {@code Phase}. Each {@code Round} has three
   * different {@code Phases}: trade, combination and action.
   *
   * <p>In the trade-phase each player has 6 cards and decides about a trump to play, in the
   * combination-phase each player has 9 cards and starts a challenge with its possible combinations
   * the action-phase each player begins with 9 cards and challenges with it each turn.
   */
  void switchPhase() {
    phase = phase.switchPhase();
    information.dealCards();
  }

  /**
   * Switches trump seven from the players hand with it and the current trump card. It is possible
   * only with the native trump and in the trade phase.
   */
  void switchTrumpSeven() {
    phase.switchTrumpSeven();
  }

  /**
   * Plays a chosen trump in the round.
   *
   * @param suit the chosen trump suit
   * @param picker player who chose the trump in the trade phase
   */
  void playTrump(Suit suit, Owner picker) {
    phase.playTrump(suit, picker);
  }

  /**
   * Decides a {@code Challenge} between two players in the current phase. It could be a
   * combination-challenge in the combination-phase or card-challenge in the card-challenge.
   *
   * @param challenge to compare
   */
  void decideChallenge(Challenge<?> challenge) {
    phase.decideChallenge(challenge);
  }

  /**
   * Returns true if both players don't have any cards, false otherwise.
   *
   * @return true if both players have zero cards
   */
  boolean isOver() {
    return information.countHandCards() == 0;
  }

  /**
   * Sums up the score of the current round. If this round has not the native trump and the trump
   * picker loses it, his opponent will get all points.
   */
  void sumUp() {
    information.sumUp();
    Owner winner = getWinner();
    if (!information.isNativeTrump() && !winner.equals(information.getTrumpPicker())) {
      Score score = information.getScore();
      score.setDefault();
      int totalPoints = score.getPoints(Owner.PLAYER) + score.getPoints(Owner.BOT);
      score.addPoints(winner, totalPoints);
    }
  }

  /**
   * Returns an owner who wins the round.
   *
   * @return winner
   */
  Owner getWinner() {
    Score score = information.getScore();
    int playerPoints = score.getPoints(Owner.PLAYER);
    int botPoints = score.getPoints(Owner.BOT);
    if (playerPoints > botPoints) {
      return Owner.PLAYER;
    } else if (playerPoints < botPoints) {
      return Owner.BOT;
    } else {
      return information.getTrumpPicker();
    }
  }
}
