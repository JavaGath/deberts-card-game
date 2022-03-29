package de.javagath.backend.game.service;

import de.javagath.backend.game.model.enums.Owner;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Contains information about the players bytes. Each player has active and total bytes. If the
 * player has three bytes, he gets a penalty of 100 points. Total bytes are needed for the party
 * statistic.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.0
 * @since 1.0
 */
class ByteStatistic {

  private final Map<Owner, Integer> activeBytes;
  private final Map<Owner, List<Integer>> totalBytes;

  private ByteStatistic(Map<Owner, Integer> activeBytes, Map<Owner, List<Integer>> totalBytes) {
    this.activeBytes = activeBytes;
    this.totalBytes = totalBytes;
  }

  /**
   * Creates new Instance of the byte statistic.
   *
   * @param player player of the party
   * @param opponent player's opponent
   * @return new {@code ByteStatistic}
   */
  static ByteStatistic newInstance(Owner player, Owner opponent) {
    Map<Owner, Integer> active = new HashMap<>();
    active.put(player, 0);
    active.put(opponent, 0);
    Map<Owner, List<Integer>> total = new HashMap<>();
    total.put(player, new LinkedList<>());
    total.put(opponent, new LinkedList<>());

    return new ByteStatistic(active, total);
  }

  /**
   * Adds new byte to the statistic for the player at the current round.
   *
   * @param owner player who gets byte
   * @param roundNumber in which round player gets the byte
   */
  void addByte(Owner owner, Integer roundNumber) {
    Integer newActiveBytes = activeBytes.get(owner) + 1;
    activeBytes.put(owner, newActiveBytes);
    totalBytes.get(owner).add(roundNumber);
  }

  /**
   * Returns the owner who gets the byte. If nobody should get the penalty returns {@code
   * Owner.NOBODY}
   *
   * @return Owner or {Owner.NOBODY}
   */
  Owner getBytePenaltyOwner() {
    if (activeBytes.get(Owner.PLAYER) > 2) {
      return Owner.PLAYER;
    } else if (activeBytes.get(Owner.BOT) > 2) {
      return Owner.BOT;
    } else {
      return Owner.NOBODY;
    }
  }

  /**
   * Returns true if one player of the party should get the byte penalty, false otherwise.
   *
   * @return true if one player has 3 bytes
   */
  boolean isBytePenalty() {
    return activeBytes.get(Owner.PLAYER) > 2 || activeBytes.get(Owner.BOT) > 2;
  }

  /**
   * Resets the activeBytes of the picked player if he has 3 active bytes.
   *
   * @param owner player whose bytes should be reset
   */
  void playBytePenalty(Owner owner) {
    if (activeBytes.get(owner) > 2) activeBytes.put(owner, 0);
  }
}
