package de.javagath.backend.game.model.enums;

import de.javagath.backend.game.model.deck.Card;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

/**
 * Represents all possible combinations at the beginning of action {@code Phase}.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.0
 * @since 1.0
 */
public enum CombinationName {
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
    return Arrays.stream(values()).filter(name -> name.cards == cards).findFirst().orElse(NOTHING);
  }

  /**
   * Checks if a set of Cards is a valid combination.
   *
   * @param combinationSet set of {@code Card}s
   * @return true if it is a valid {@code CombinationName}
   */
  public static boolean isCombination(TreeSet<Card> combinationSet) {
    CombinationName name = CombinationName.getNameByCards(combinationSet.size());
    List<Card> combinationList = new ArrayList<>(combinationSet);
    if (name == CombinationName.NOTHING) {
      return false;
    }

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
