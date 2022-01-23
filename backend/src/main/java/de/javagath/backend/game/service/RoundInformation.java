package de.javagath.backend.game.service;

import de.javagath.backend.game.model.deck.Card;
import de.javagath.backend.game.model.deck.Deck;
import de.javagath.backend.game.model.deck.Trump;
import de.javagath.backend.game.model.enums.Owner;
import de.javagath.backend.game.model.enums.PhaseName;
import de.javagath.backend.game.model.enums.Suit;
import de.javagath.backend.game.model.enums.Value;
import java.util.NoSuchElementException;
import lombok.Builder;

/**
 * Contains information about current {@code Round}: {@code CardDeck} which contains all {@code
 * Cards} at the beginning, trump deck with {@code Trump}, two {@code HandDecks} for each player and
 * actual turn.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.0
 * @since 1.0
 */
@Builder
public class RoundInformation {
  private final Score score = new Score();
  private final Score combinationScore = new Score();
  private Deck cardDeck;
  private Trump trumpDeck;
  private Deck playerDeck;
  private Deck botDeck;
  private boolean trumpChangePossible;
  @Builder.Default private Owner turn = Owner.PLAYER;
  private Owner trumpPicker;
  @Builder.Default private PhaseName phaseName = PhaseName.TRADE;

  /**
   * Returns combination score of the current {@code Round}. Combination score will be count
   * separately, because it is no guarantee, that the player will get it in the End of the {@code
   * Round}.
   *
   * @return current combinations score
   */
  public Score getCombinationScore() {
    return combinationScore;
  }

  /**
   * Returns score of the current {@code Round}.
   *
   * @return current combinations score
   */
  public Score getScore() {
    return score;
  }

  /**
   * Adds combination points to the score for the player.
   *
   * @param owner player (deck owner)
   * @param points points to add
   */
  public void addCombinationPoints(Owner owner, Integer points) {
    combinationScore.addPoints(owner, points);
  }

  /**
   * Adds round points to the score for the player.
   *
   * @param owner player (deck owner)
   * @param points points to add
   */
  public void addPoints(Owner owner, Integer points) {
    score.addPoints(owner, points);
  }

  /**
   * Returns deck bot or player deck depended on owner.
   *
   * @param owner owner of the deck
   * @return owners deck
   */
  public Deck getDeckByOwner(Owner owner) {
    if (owner.equals(Owner.PLAYER)) {
      return playerDeck;
    } else if (owner.equals(Owner.BOT)) {
      return botDeck;
    } else {
      throw new IllegalArgumentException("This owner is not a player in this game!");
    }
  }

  /**
   * Returns {@code Phase}.
   *
   * @return {@code Phase}
   */
  public PhaseName getPhaseName() {
    return phaseName;
  }

  /**
   * Sets {@code Phase}.
   *
   * @param phaseName new {@code Phase}
   */
  public void setPhaseName(PhaseName phaseName) {
    this.phaseName = phaseName;
  }

  /**
   * Returns {@code CardDeck}.
   *
   * @return {@code CardDeck}
   */
  public Deck getCardDeck() {
    return cardDeck;
  }

  /**
   * Sets {@code CardDeck}.
   *
   * @param cardDeck new {@code Deck}
   */
  public void setCardDeck(Deck cardDeck) {
    this.cardDeck = cardDeck;
  }

  /**
   * Returns {@code HandDeck} of the player.
   *
   * @return players {@code HandDeck}
   */
  public Deck getPlayerDeck() {
    return playerDeck;
  }

  /**
   * Returns {@code HandDeck} of the bot.
   *
   * @return bots {@code HandDeck}
   */
  public Deck getBotDeck() {
    return botDeck;
  }

  /**
   * Returns the Deck of the {@code Trump}.
   *
   * @return {@code Trump}
   */
  public Trump getTrumpDeck() {
    return trumpDeck;
  }

  /**
   * Sets {@code Trump}.
   *
   * @param trumpDeck {@code Trump} to set
   */
  public void setTrumpDeck(Trump trumpDeck) {
    this.trumpDeck = trumpDeck;
  }

  /**
   * Returns actual turn in the round.
   *
   * @return actual turn
   */
  public Owner getTurn() {
    return turn;
  }

  /**
   * Sets actual turn in the round.
   *
   * @param turn whose turn is now
   */
  public void setTurn(Owner turn) {
    this.turn = turn;
  }

  /**
   * Returns the trump suit.
   *
   * @return trump suit
   */
  public Suit getTrumpSuit() {
    return trumpDeck.getSuit();
  }

  /**
   * Returns the trump picker.
   *
   * @return trump picker
   */
  public Owner getTrumpPicker() {
    return trumpPicker;
  }

  /**
   * Returns true if the trump is native, false otherwise.
   *
   * @return true if trump is native
   */
  public boolean isNativeTrump() {
    return trumpDeck.isNative();
  }

  /**
   * Returns a total number of cards in the hands of players.
   *
   * @return total card number
   */
  public int countHandCards() {
    return playerDeck.countCards() + botDeck.countCards();
  }

  /**
   * Plays a chosen trump in the roundInformation.
   *
   * @param suit the chosen trump suit
   * @param picker player who chose the trump in the trade phase
   */
  public void playTrump(Suit suit, Owner picker) {
    if (!trumpChangePossible) {
      throw new IllegalStateException("The trump can not be switched two times in the round");
    }

    if (!trumpDeck.getSuit().equals(suit)) {
      playOwnTrump(suit, picker);
    }

    trumpChangePossible = false;
  }

  private void playOwnTrump(Suit suit, Owner trumpPicker) {
    Card newTrumpCard;
    try {
      newTrumpCard = cardDeck.dealRandomCardFromSuit(suit);
    } catch (NoSuchElementException e) {
      newTrumpCard = Card.newInstance(suit, Value.ACE);
    }
    trumpDeck.setTradedTrump(newTrumpCard);
    this.trumpPicker = trumpPicker;
  }

  /**
   * Switches a trump seven from the players deck with the trump card. This switch will be declared
   * in the decks contained in this class. This switch is possible only by native trump.
   */
  public void switchTrumpSeven() {
    Suit suit = trumpDeck.getSuit();
    Value value = Value.SEVEN;
    Deck exchangeDeck = getDeckContainingCard(suit, value);
    Card trumpSeven = exchangeDeck.dealCard(suit, value);
    Card trumpCard = trumpDeck.switchTrumpSeven(trumpSeven);
    exchangeDeck.addCard(trumpCard);
  }

  private Deck getDeckContainingCard(Suit suit, Value value) {
    if (!contains(suit, value)) {
      throw new IllegalStateException("Players do not have any trump seven");
    }

    return playerDeck.contains(suit, value) ? playerDeck : botDeck;
  }

  private boolean contains(Suit suit, Value value) {
    return playerDeck.contains(suit, value) || botDeck.contains(suit, value);
  }

  /**
   * Sums up round score with the combination score. If a player does not have any points,
   * combination score will not be added.
   */
  public void sumUp() {
    addCombinationScore(Owner.PLAYER);
    addCombinationScore(Owner.BOT);
  }

  private void addCombinationScore(Owner owner) {
    if (score.getPoints(owner) != 0) {
      score.addPoints(owner, combinationScore.getPoints(owner));
    }
  }

  public void dealCards() {
    PhaseName name = phaseName;
    for (int i = 0; i < phaseName.getValue(); i++) {
      playerDeck.addCard(cardDeck.dealRandomCard());
      botDeck.addCard(cardDeck.dealRandomCard());
    }
  }
}