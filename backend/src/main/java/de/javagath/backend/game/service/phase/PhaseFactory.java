package de.javagath.backend.game.service.phase;

import de.javagath.backend.game.service.RoundInformation;

/**
 * Factory class to create {@code Phase} implementations.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.0
 * @see Phase
 * @see Trade
 * @since 1.0
 */
public class PhaseFactory {

  /**
   * Returns new {@code Phase} entity of Trade using input parameter of {@code RoundInformation}.
   * Each round begins with the trade phase, because of this factory returns only this
   * implementation.
   *
   * @param information information about the current round
   * @return new {@code Phase} entity
   */
  public static Phase getPhase(RoundInformation information) {
    information.dealCards();
    return new Trade(information);
  }
}
