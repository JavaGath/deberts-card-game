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
  private final ByteStatistic byteStatistic;
  private RoundInformation roundInformation;
  private boolean over = false;
  private Owner winner = Owner.NOBODY;

  private PartyInformation(RoundInformation roundInformation, ByteStatistic byteStatistic) {
    this.byteStatistic = byteStatistic;
    this.roundInformation = roundInformation;
  }

  static PartyInformation newInstance(RoundInformation roundInformation) {
    ByteStatistic statistic = ByteStatistic.newInstance(Owner.PLAYER, Owner.BOT);
    return new PartyInformation(roundInformation, statistic);
  }

  /**
   * Adds a number of bytes to the owner.
   *
   * @param owner who gets the byte
   */
  void addByte(Owner owner) {
    if (!owner.equals(Owner.NOBODY)) {
      byteStatistic.addByte(owner, roundScoreHistory.size() + 1);
    }
  }

  /**
   * Returns true if one player gets 501 points.
   *
   * @return returns true if round is over
   */
  boolean isOver() {
    return over;
  }

  /**
   * Returns the winner of the party. While Party is not over returns {@code Owner.NOBODY}.
   *
   * @return winner of the current party
   */
  Owner getWinner() {
    return winner;
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

  /**
   * Adds score of the current round to the party score, declares it the round history and checks
   * the last game possibility.
   */
  void sumUp() {
    Score roundScore = roundInformation.getScore();
    if (byteStatistic.isBytePenalty()) {
      Owner penaltyOwner = byteStatistic.getBytePenaltyOwner();
      roundScore.addPoints(penaltyOwner, -100);
      byteStatistic.playBytePenalty(penaltyOwner);
    }

    roundScoreHistory.add(roundScore);
    score.addPoints(Owner.BOT, roundScore.getPoints(Owner.BOT));
    score.addPoints(Owner.PLAYER, roundScore.getPoints(Owner.PLAYER));

    if (isLastGame()) {
      over = true;
      winner = decideWinner();
    }
  }

  private Owner decideWinner() {
    return score.getPoints(Owner.PLAYER) > score.getPoints(Owner.BOT) ? Owner.PLAYER : Owner.BOT;
  }

  private boolean isLastGame() {
    return !score.getPoints(Owner.BOT).equals(score.getPoints(Owner.PLAYER))
        && (score.getPoints(Owner.PLAYER) > 501 || score.getPoints(Owner.BOT) > 501);
  }
}
