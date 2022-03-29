package de.javagath.backend.game.service;

import de.javagath.backend.game.model.enums.Owner;
import java.util.HashMap;
import java.util.Map;

/**
 * Contains information about Score of both players. This class is helpful to declare {@code Round}
 * and {@code Party} score.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.0
 * @since 1.0
 */
public class Score {

  Map<Owner, Integer> score = new HashMap<>();

  /** Creates new Score object for Player and Bot with 0 points for each one. */
  public Score() {
    setDefault();
  }

  /**
   * Returns actual points for special owner (Bot or Player).
   *
   * @param owner Player or Bot in the party
   * @return actual points
   */
  public Integer getPoints(Owner owner) {
    return score.get(owner);
  }

  /**
   * Adds new points to the owners score.
   *
   * @param owner Player or Bot in the party
   * @param points new points to add
   */
  public void addPoints(Owner owner, int points) {
    int newPoints = score.get(owner) + points;
    score.put(owner, newPoints);
  }

  /**
   * Subtracts new points from the owners score.
   *
   * @param owner Player or Bot in the party
   * @param points points to subtract
   */
  public void subtractPoints(Owner owner, int points) {
    int newPoints = score.get(owner) - points;
    score.put(owner, newPoints);
  }

  /** Resets score of all players to 0 points. */
  public void setDefault() {
    score.put(Owner.PLAYER, 0);
    score.put(Owner.BOT, 0);
  }
}
