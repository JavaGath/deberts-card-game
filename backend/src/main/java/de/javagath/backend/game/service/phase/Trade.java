package de.javagath.backend.game.service.phase;

import de.javagath.backend.game.model.deck.Challenge;
import de.javagath.backend.game.model.enums.Owner;
import de.javagath.backend.game.model.enums.PhaseName;
import de.javagath.backend.game.model.enums.Suit;
import de.javagath.backend.game.service.RoundInformation;

/**
 * The state entity to help the class {@code Round} to manage different phases in it. Each phase has
 * Actions to switch it to the next one, decide a {@code Challenge} between the players, play trump,
 * get {@code Round Information} or play switch trump seven.
 *
 * <p>In the trade phase each player has 6 cards and trades to decide a trump. In the current phase
 * methods: decideChallenge() and switchTrumpSeven() are not available.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.0
 * @since 1.0
 */
public class Trade implements Phase {

  private RoundInformation information;

  Trade(RoundInformation information) {
    this.information = information;
  }

  @Override
  public Phase switchPhase() {
    if (information.isTrumpChangePossible()) {
      throw new IllegalStateException("Trump should be chosen to switch the phase");
    }
    information.setPhaseName(PhaseName.COMBO);
    return new Combo(information);
  }

  @Override
  public void decideChallenge(Challenge<?> challenge) {
    throw new IllegalStateException("Challenge in the trade phase is not possible");
  }

  @Override
  public void switchTrumpSeven() {
    throw new IllegalStateException("The trump can not be switched in the current phase");
  }

  @Override
  public void playTrump(Suit suit, Owner picker) {
    information.playTrump(suit, picker);
  }

  @Override
  public void setInformation(RoundInformation information) {
    this.information = information;
  }

  @Override
  public boolean isSevenSwitchable() {
    return false;
  }

  @Override
  public boolean isFourSevenResettable() {
    return information.isFourSevenResettable();
  }
}
