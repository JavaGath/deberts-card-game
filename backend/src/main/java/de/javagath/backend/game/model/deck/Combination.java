package de.javagath.backend.game.model.deck;

import de.javagath.backend.game.model.enums.Owner;
import de.javagath.backend.game.model.enums.Suit;
import de.javagath.backend.game.model.enums.Value;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

/**
 * Combinations of each player for the beginning of the action phase. Each {@code PlayerCombination}
 * consists of player name and the List of his card combinations.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.0
 * @since 1.0
 */
public class Combination implements Challengable<Combination> {
  private final Owner player;
  private final List<CardCombination> combinationList = new LinkedList<>();

  private Combination(Owner player) {
    this.player = player;
  }

  /**
   * Factory method to create a new {@code PlayerCombination} entity.
   *
   * @param owner player name
   * @return PlayerCombination object for the player combination
   */
  public static Combination newInstance(Owner owner) {
    return new Combination(owner);
  }

  /**
   * Adds new {@code Combination} to the list using the {@code Set<Card>}.
   *
   * @param combination set of cards
   */
  public void add(Set<Card> combination) {
    TreeSet<Card> orderedCombination = new TreeSet<>(combination);
    if (CombinationName.isCombination(orderedCombination)) {
      Card combinationCard = orderedCombination.first();
      CardCombination newCombination =
          new CardCombination(orderedCombination, combinationCard.getSuit());
      combinationList.add(newCombination);
    } else {
      throw new IllegalArgumentException("The card combination is not valid.");
    }
  }

  /**
   * Returns the highest {@code Combination} of all combinations of the player in the form of Set.
   *
   * @param trump trump {@code Suit}
   * @return highest {@code Combination}
   */
  public Set<Card> getHighestCombination(Suit trump) {
    return getHighestCardCombination(trump).getCards();
  }

  private CardCombination getHighestCardCombination(Suit trump) {
    CardCombination result = Objects.requireNonNull(combinationList.get(0));
    for (CardCombination combination : combinationList) {
      int compareValue = result.compareTo(combination);

      if (compareValue == 0) {
        result = trump.equals(combination.getSuit()) ? combination : result;
      } else if (compareValue < 1) {
        result = combination;
      }
    }
    return result;
  }

  /**
   * Gets player name of the combination.
   *
   * @return player name
   */
  public Owner getPlayer() {
    return player;
  }

  @Override
  public int getPoints(Suit trump) {
    int points = 0;
    for (CardCombination combination : combinationList) {
      boolean isTrump = trump.equals(combination.getSuit());
      points += combination.getName().getPoints(isTrump);
    }
    return points;
  }

  @Override
  public int compareToSuit(Combination object, Suit trump) {
    if (this.isEmpty() && object.isEmpty()) {
      return 0;
    } else if (this.isEmpty() && !object.isEmpty()) {
      return -1;
    } else if (!this.isEmpty() && object.isEmpty()) {
      return 1;
    }
    CardCombination thisCombination = getHighestCardCombination(trump);
    CardCombination otherCombination = object.getHighestCardCombination(trump);
    int compareValue = thisCombination.compareTo(otherCombination);

    if (compareValue != 0) {
      return compareValue;
    }

    if (thisCombination.isSameSuit(trump)) {
      return 1;
    } else if (otherCombination.isSameSuit(trump)) {
      return -1;
    } else {
      return compareValue;
    }
  }

  /**
   * Returns true if combination list does not contain any card combinations.
   *
   * @return true if combination list is empty
   */
  public boolean isEmpty() {
    return combinationList.isEmpty();
  }

  @Override
  public int hashCode() {
    int result = getPlayer() != null ? getPlayer().hashCode() : 0;
    result = 31 * result + combinationList.hashCode();
    return result;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Combination)) {
      return false;
    }

    Combination that = (Combination) o;

    if (getPlayer() != that.getPlayer()) {
      return false;
    }
    return combinationList.equals(that.combinationList);
  }

  @Override
  public String toString() {
    return "Combination{" + "player=" + player + ", combinationList=" + combinationList + '}';
  }

  private enum CombinationName {
    NOTHING(0, 0, 0),
    BELLA(2, 0, 20),
    TERTZ(3, 20, 20),
    FIFTY(4, 50, 50);

    private final int cards;
    private final int standardPoints;
    private final int trumpPoints;

    CombinationName(int cards, int standardPoints, int trumpPoints) {
      this.cards = cards;
      this.standardPoints = standardPoints;
      this.trumpPoints = trumpPoints;
    }

    /**
     * Returns a {@code CombinationName} by the number of cards value.
     *
     * @param cards {@code int} number of cards in the combination
     * @return {@code CombinationName}
     */
    public static CombinationName getNameByCards(int cards) {
      return Arrays.stream(values())
          .filter(name -> name.cards == cards)
          .findFirst()
          .orElse(NOTHING);
    }

    /**
     * Checks if a set of Cards is a valid combination.
     *
     * @param combinationSet set of {@code Card}s
     * @return true if it is a valid {@code CombinationName}
     */
    public static boolean isCombination(TreeSet<Card> combinationSet) {
      CombinationName name = CombinationName.getNameByCards(combinationSet.size());
      if (name == CombinationName.NOTHING) {
        return false;
      }

      List<Card> combinationList = new ArrayList<>(combinationSet);
      for (int i = 1; i < combinationList.size(); i++) {
        Card previous = combinationList.get(i - 1);
        Card current = combinationList.get(i);
        if (!isValidSequence(previous, current)) {
          return false;
        }
        if (name.equals(CombinationName.BELLA)) {
          return isValidBella(previous.getValue(), current.getValue());
        }
      }
      return true;
    }

    private static boolean isValidSequence(Card previous, Card current) {
      int consistency = current.getPosition() - previous.getPosition();
      if (consistency != 1) {
        return false;
      }
      return current.getSuit().equals(previous.getSuit());
    }

    private static boolean isValidBella(Value previous, Value current) {
      return previous.equals(Value.QUEEN) && current.equals(Value.KING);
    }

    /**
     * Return how much point brings a combination. Trump is needed to decide if combination is BELLA
     * or just kind and queen.
     *
     * @param trump is this a trump combination
     * @return points of combination
     */
    public int getPoints(boolean trump) {
      return trump ? trumpPoints : standardPoints;
    }
  }

  private class CardCombination implements Comparable<CardCombination> {
    private final TreeSet<Card> combinationCards;
    private final Suit suit;

    private CardCombination(TreeSet<Card> combinationCards, Suit suit) {
      this.combinationCards = combinationCards;
      this.suit = suit;
    }

    public Suit getSuit() {
      return suit;
    }

    public boolean isSameSuit(Suit suit) {
      return this.suit.equals(suit);
    }

    public Set<Card> getCards() {
      return combinationCards;
    }

    @Override
    public int compareTo(CardCombination combination) {
      int comparedName = compareName(combination.getName());
      if (comparedName != 0) {
        return comparedName;
      }

      return compareHeight(combination.getHeight());
    }

    private int compareName(CombinationName combinationName) {
      return this.getName().compareTo(combinationName);
    }

    public CombinationName getName() {
      return CombinationName.getNameByCards(combinationCards.size());
    }

    private int compareHeight(Value height) {
      return this.getHeight().compareTo(height);
    }

    private Value getHeight() {
      return combinationCards.last().getValue();
    }

    @Override
    public int hashCode() {
      int result = combinationCards != null ? combinationCards.hashCode() : 0;
      result = 31 * result + (getSuit() != null ? getSuit().hashCode() : 0);
      return result;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof CardCombination)) {
        return false;
      }

      CardCombination that = (CardCombination) o;

      if (!Objects.equals(combinationCards, that.combinationCards)) {
        return false;
      }
      return getSuit() == that.getSuit();
    }

    @Override
    public String toString() {
      return "CardCombination{" + "combinationCards=" + combinationCards + ", suit=" + suit + '}';
    }
  }
}
