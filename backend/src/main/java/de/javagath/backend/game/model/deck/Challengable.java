package de.javagath.backend.game.model.deck;

import de.javagath.backend.game.model.enums.Suit;

/**
 * The interface to implement challenge between players in the game.
 *
 * @param <T> object type for challenge
 * @author Ievgenii Izrailtenko
 * @version 1.0
 * @since 1.0
 */
public interface Challengable<T> {

  /**
   * Returns points of the game object. Trump information is needed to decide it.
   *
   * @param trump trump {@code Suit}
   * @return winner points
   */
  int getPoints(Suit trump);

  /**
   * Compares game object depended on trump. Returns 1 if current object is bigger, -1 if smaller
   * and 0 if equal.
   *
   * @param object to compare
   * @param trump trump suit
   * @return compare result.
   */
  int compareToSuit(T object, Suit trump);
}
