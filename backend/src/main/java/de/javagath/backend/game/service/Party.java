package de.javagath.backend.game.service;

import de.javagath.backend.game.model.deck.Challenge;
import de.javagath.backend.game.model.enums.Owner;
import de.javagath.backend.game.model.enums.Suit;

/**
 * An entity that contains and manages {@code Round} and {@code PartyInformation} for the party.
 * After each outplayed round party creates a new one and declares its score in the history. Total
 * score and its history are contained in the class {@code PartyInformation}
 *
 * @author Ievgenii Izrailtenko
 * @version 1.0
 * @since 1.0
 */
public class Party {

  final PartyInformation partyInformation;
  Round round;

  private Party() {
    round = Round.newInstance(Owner.NOBODY);
    this.partyInformation = new PartyInformation(round.getInformation());
  }

  /**
   * Factory method to create a new {@code Party}.
   *
   * @return new {@code Party} object
   */
  public static Party newInstance() {
    return new Party();
  }

  /**
   * Returns information about the Party.
   *
   * @return PartyInformation
   */
  public PartyInformation getPartyInformation() {
    return partyInformation;
  }

  /**
   * Switches current {@code Round} to the next {@code Phase}. Each {@code Round} has three
   * different {@code Phases}: trade, combination and action.
   *
   * <p>In the trade-phase each player has 6 cards and decides about a trump to play, in the
   * combination-phase each player has 9 cards and starts a challenge with its possible combinations
   * the action-phase each player begins with 9 cards and challenges with it each turn.
   */
  public void switchPhase() {
    round.switchPhase();
  }

  /**
   * Decides a {@code Challenge} between two players in the current round-phase. Ends the round if
   * the current round is over.
   *
   * @param challenge to compare
   */
  public void playTurn(Challenge<?> challenge) {
    round.decideChallenge(challenge);
    if (round.isOver()) {
      endRound();
    }
  }

  void endRound() {
    round.sumUp();
    Owner dealer = round.getWinner().equals(Owner.BOT) ? Owner.BOT : Owner.PLAYER;
    round = Round.newInstance(dealer);
    partyInformation.addCurrentScore();
    partyInformation.setRoundInformation(round.getInformation());
  }

  /**
   * Plays a chosen trump in the current party round.
   *
   * @param suit the chosen trump suit
   * @param picker player who chose the trump in the trade phase
   */
  public void playTrump(Suit suit, Owner picker) {
    round.playTrump(suit, picker);
  }

  /** Switches trump seven from the players hand with it and the current trump card. */
  public void switchTrumpSeven() {
    round.switchTrumpSeven();
  }

  Round getRound() {
    return round;
  }
}
