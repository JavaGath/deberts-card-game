package de.javagath.backend.game.service;

import de.javagath.backend.game.model.enums.Owner;
import java.util.LinkedList;
import java.util.List;

/**
 * Contains information about the {@code Party}: {@code Score} of the party, history of the round
 * score and round information.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.0
 * @since 1.0
 */
public class PartyInformation {
  private final Score score = new Score();
  private final List<Score> roundScoreHistory = new LinkedList<>();
  private RoundInformation roundInformation;

  PartyInformation(RoundInformation roundInformation) {
    this.roundInformation = roundInformation;
  }

  /**
   * Returns score of the current {@code Party}.
   *
   * @return current combinations score
   */
  Score getScore() {
    return score;
  }

  /**
   * Returns a score list of all rounds in the {@code Party}.
   *
   * @return current combinations score
   */
  List<Score> getRoundScoreHistory() {
    return roundScoreHistory;
  }

  /**
   * Returns information about the current {@code Round} in the {@code Party}.
   *
   * @return current combinations score
   */
  RoundInformation getRoundInformation() {
    return roundInformation;
  }

  /**
   * Sets information about the current {@code Round} in the {@code Party}.
   *
   * @param roundInformation information about actual {@code Round}
   */
  void setRoundInformation(RoundInformation roundInformation) {
    this.roundInformation = roundInformation;
  }

  /** Adds score of the current round to the party score and declares it the round history. */
  void addCurrentScore() {
    Score roundScore = roundInformation.getScore();
    roundScoreHistory.add(roundScore);
    score.addPoints(Owner.BOT, roundScore.getPoints(Owner.BOT));
    score.addPoints(Owner.PLAYER, roundScore.getPoints(Owner.PLAYER));
  }
}
