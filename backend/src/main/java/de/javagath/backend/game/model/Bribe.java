package de.javagath.backend.game.model;

import de.javagath.backend.game.model.deck.Challenge;
import de.javagath.backend.game.model.enums.Owner;
import java.util.LinkedList;
import java.util.List;

/**
 * Bribe class that contains all won challenges from the player.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.0
 * @since 1.0*
 */
public class Bribe {

  private final List<Challenge<?>> bribeList = new LinkedList<>();
  private Owner owner;

  /**
   * Creates new bribe for the special Owner.
   *
   * @param owner owner
   */
  public Bribe(Owner owner) {
    this.owner = owner;
  }

  /**
   * Returns true if bribe list is empty.
   *
   * @return true by empty bribe list
   */
  public boolean isEmpty() {
    return bribeList.isEmpty();
  }

  /**
   * Adds challenge to the bribe list.
   *
   * @param challenge won challenge
   */
  public void addChallenge(Challenge<?> challenge) {
    bribeList.add(challenge);
  }
}
