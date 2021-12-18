package de.javagath.backend.game.model.deck;

import de.javagath.backend.game.model.enums.Owner;
import de.javagath.backend.game.model.enums.Suit;

/**
 * An object to manage a challenge between attacker and defender. Each challenge contains an
 * attacker and defender with names of the players and values to compare.
 *
 * @param <T> value to challenge
 * @author Ievgenii Izrailtenko
 * @version 1.0
 * @since 1.0
 */
public class Challenge<T extends Challengable<T>> {
  Owner attacker;
  Owner defender;
  T attackerValue;
  T defenderValue;

  /**
   * Returns {@code Owner} of the attacker.
   *
   * @return {@code Owner}
   */
  public Owner getAttacker() {
    return attacker;
  }

  /**
   * Sets attackers {@code Owner}.
   *
   * @param attacker {@code Owner}
   * @throws IllegalArgumentException attacker and defender can't be the same user
   */
  public void setAttacker(Owner attacker) {
    if (defender == null || !defender.equals(attacker)) {
      this.attacker = attacker;
    } else {
      throw new IllegalArgumentException(
          String.format("Attacker can't be the same owner like defender: %s", attacker));
    }
  }

  /**
   * Returns {@code Owner} of the defender.
   *
   * @return {@code Owner}
   */
  public Owner getDefender() {
    return defender;
  }

  /**
   * Sets attackers {@code Owner}.
   *
   * @param defender {@code Owner}
   * @throws IllegalArgumentException attacker and defender can't be the same user
   */
  public void setDefender(Owner defender) {
    if (attacker == null || !attacker.equals(defender)) {
      this.defender = defender;
    } else {
      throw new IllegalArgumentException(
          String.format("Defender can't be the same owner like attacker: %s", defender));
    }
  }

  /**
   * Returns attackers value to compare.
   *
   * @return attackers value to compare
   */
  public T getAttackerValue() {
    return attackerValue;
  }

  /**
   * Sets attackers value to compare.
   *
   * @param value attackers value
   * @throws IllegalArgumentException attacker and defender can't use same value to challenge
   */
  public void setAttackerValue(T value) {
    if (defenderValue == null || !defenderValue.equals(value)) {
      attackerValue = value;
    } else {
      throw new IllegalArgumentException(
          String.format(
              "Attacker can't have same value: %s like defender: %s", value, defenderValue));
    }
  }

  /**
   * Returns defenders value to compare.
   *
   * @return defenders value to compare
   */
  public T getDefenderValue() {
    return defenderValue;
  }

  /**
   * Sets defenders value to compare.
   *
   * @param value defenders value
   * @throws IllegalArgumentException attacker and defender can't use same value to challenge
   */
  public void setDefenderValue(T value) {
    if (attackerValue == null || !attackerValue.equals(value)) {
      defenderValue = value;
    } else {
      throw new IllegalArgumentException(
          String.format(
              "Defender can't have same value: %s like attacker: %s", value, attackerValue));
    }
  }

  /**
   * Returns points of the decided challenge. To decide a winner trump information is needed.
   *
   * @param trump trump {@code Suit}
   * @return winner points
   */
  public int getPoints(Suit trump) {
    if (isComplete()) {
      return attackerValue.getPoints(trump) + defenderValue.getPoints(trump);
    } else {
      throw new IllegalStateException("Challenge elements are not complete");
    }
  }

  /**
   * Returns {@code Owner} enum of the winner. To decide a winner trump information is needed.
   *
   * @param trump trump {@code Suit}
   * @return winner
   */
  public Owner getWinner(Suit trump) {
    if (isComplete()) {
      int compareResult = attackerValue.compareToSuit(defenderValue, trump);
      return compareResult >= 0 ? attacker : defender;
    } else {
      throw new IllegalStateException("Challenge elements are not complete");
    }
  }

  private boolean isComplete() {
    return attacker != null && defender != null && attackerValue != null && defenderValue != null;
  }
}
