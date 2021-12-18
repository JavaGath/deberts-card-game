package de.javagath.backend.game.model.deck;

import de.javagath.backend.game.model.enums.Suit;
import de.javagath.backend.game.model.enums.Value;

/**
 * Represents a single card. Each card has a {@code Suit} and {@code Value}. Active parameter
 * defines if {@code Card} is ready to play or was already dealt.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.0
 * @since 1.0
 */
public class Card implements Challengable<Card> {

  private final Suit suit;
  private final Value value;
  private boolean active;

  private Card(Suit suit, Value value) {
    this.suit = suit;
    this.value = value;
    this.active = true;
  }

  private Card(Card card) {
    this.suit = card.getSuit();
    this.value = card.getValue();
    this.active = card.isActive();
  }

  /**
   * Factory method to create a new {@code Card} entity. Each new {@code Card} is by default active.
   *
   * @param suit {@code Suit} of the {@code Card}
   * @param value {@code Value} of the {@code Card}
   * @return new {@code Card} object
   */
  public static Card newInstance(Suit suit, Value value) {
    return new Card(suit, value);
  }

  /**
   * Factory method to copy a {@code Card}.
   *
   * @param card a {@code Card} to copy
   * @return a new {@code Card} object
   */
  public static Card newInstance(Card card) {
    return new Card(card);
  }

  /**
   * Returns {@code true} if the {@code Card} is ready to play, {@code false} otherwise.
   *
   * @return {@code true} if {@code Card} is active, {@code false} otherwise
   */
  public boolean isActive() {
    return active;
  }

  /**
   * Sets active status for the {@code Card}.
   *
   * @param active new active status
   */
  public void setActive(boolean active) {
    this.active = active;
  }

  /**
   * Returns a {@code Suit} of the {@code Card}.
   *
   * @return {@code Suit} name
   */
  public Suit getSuit() {
    return suit;
  }

  /**
   * Gets a {@code value} of the {@code Card}.
   *
   * @return {@code Card value}
   */
  public Value getValue() {
    return value;
  }

  /**
   * Returns {@code true} if this {@code Card} has the same {@code Suit}, {@code false} otherwise.
   *
   * @param suit {@code Suit} to check
   * @return {@code true} if the same {@code Suit}, {@code false} otherwise
   */
  public boolean hasSuit(Suit suit) {
    return this.suit.equals(suit);
  }

  /**
   * Returns {@code true} if this {@code Card} has the same {@code Value}, {@code false} otherwise.
   *
   * @param value {@code Value} to check
   * @return {@code true} if the same {@code Value}, {@code false} otherwise
   */
  public boolean hasValue(Value value) {
    return this.value.equals(value);
  }

  /**
   * Returns points of the {@code Card} depends on the trump suit.
   *
   * @param trump suit
   * @return points
   */
  public int getPoints(Suit trump) {
    return value.getPoints(suit.equals(trump));
  }

  @Override
  public int compareToSuit(Card object, Suit trump) {
    if (suit.equals(object.getSuit())) {
      int compareResult =
          Integer.compare(getPoints(trump), object.getPoints(trump));
      if (compareResult == 0) {
        compareResult = getValue().compareTo(object.getValue());
      }
      return compareResult;
    }
    return object.getSuit().equals(trump) ? -1 : 1;
  }


  @Override
  public int hashCode() {
    int result = getSuit().hashCode();
    result = 31 * result + value.hashCode();
    result = 31 * result + (isActive() ? 1 : 0);
    return result;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Card card)) {
      return false;
    }

    if (isActive() != card.isActive()) {
      return false;
    }
    if (getSuit() != card.getSuit()) {
      return false;
    }
    return value == card.value;
  }

  @Override
  public String toString() {
    return "Card{" + "suit=" + suit + ", value=" + value + ", active=" + active + '}';
  }

  /**
   * Returns position of the card {@code FaceValue}.
   *
   * @return position of the {@code FaceValue}
   */
  public int getPosition() {
    return value.getPosition();
  }
}
