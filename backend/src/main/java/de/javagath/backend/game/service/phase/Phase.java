package de.javagath.backend.game.service.phase;

import de.javagath.backend.game.model.deck.Challenge;
import de.javagath.backend.game.model.enums.Owner;
import de.javagath.backend.game.model.enums.Suit;
import de.javagath.backend.game.service.RoundInformation;

/**
 * The interface for the state entity to help the class {@code Round} to manage different phases in
 * it. Each phase has Actions to switch it to the next one, decide a {@code Challenge} between the
 * players, play trump, get {@code Round Information} or play switch trump seven.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.0
 * @since 1.0
 */
public interface Phase {

  /**
   * Switches current phase to the next one.
   *
   * @return next phase
   */
  Phase switchPhase();

  /**
   * Decides a {@code Challenge} between two players and counts their score to the winner.
   *
   * @param challenge card or combination challenge
   */
  void decideChallenge(Challenge<?> challenge);

  /**
   * Switches trump seven from the players hand with it and current the trump card if it's possible.
   */
  void switchTrumpSeven();

  /**
   * Plays a chosen trump in the round.
   *
   * @param suit the chosen trump suit
   * @param picker player who chose the trump in the trade phase
   */
  void playTrump(Suit suit, Owner picker);

  /**
   * Sets new {@code RoundInformation} for the current phase.
   *
   * @param information new round information
   */
  void setInformation(RoundInformation information);

  /**
   * Returns true if it is possible to switch a trump seven.
   *
   * @return true if possible
   */
  boolean isSevenSwitchable();

  /**
   * Returns true if it is possible to reset a round because of four sevens in the hand.
   *
   * @return true if player has four sevens in the hand
   */
  boolean isFourSevenResettable();
}
