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
 * <p>In the combination phase each player has 9 cards and challenges with its possible
 * combinations. In the current phase method: playTrump() is not available.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.0
 * @since 1.0
 */
public class Combination implements Phase {

  private final RoundInformation information;

  Combination(RoundInformation information) {
    this.information = information;
  }

  @Override
  public Phase switchPhase() {
    information.setPhaseName(PhaseName.ACTION);
    return new Action(information);
  }

  @Override
  public void decideChallenge(Challenge<?> challenge) {

    Owner winner = challenge.getWinner(information.getTrumpSuit());
    Integer points = challenge.getPoints(information.getTrumpSuit());
    information.addCombinationPoints(winner, points);
  }

  @Override
  public void switchTrumpSeven() {
    information.switchTrumpSeven();
  }

  @Override
  public void playTrump(Suit suit, Owner picker) {
    throw new IllegalStateException("The trump can not be played in the current phase");
  }
}
