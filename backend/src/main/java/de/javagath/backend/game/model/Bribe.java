package de.javagath.backend.game.model;

import de.javagath.backend.game.model.deck.Card;
import de.javagath.backend.game.model.deck.Challenge;
import de.javagath.backend.game.model.enums.Owner;
import de.javagath.backend.game.model.enums.Suit;
import java.util.ArrayList;
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

  private final ArrayList<Challenge<?>> bribeList = new ArrayList<>();
  private final Owner owner;

  /**
   * Creates new bribe for the special Owner.
   *
   * @param owner owner
   */
  public Bribe(Owner owner) {
    this.owner = owner;
  }

  /**
   * Gets owner of the bribe.
   *
   * @return owner
   */
  public Owner getOwner() {
    return owner;
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

  /**
   * Returns the number of sweet brides (placed a wrong suit card).
   *
   * @param roundBribeList Round list of Bribes
   * @return number of wrong played cards.
   */
  public int countSweetBride(ArrayList<Challenge<?>> roundBribeList) {
    int counter = 0;
    for (Challenge<?> challenge : bribeList) {
      Card attackerCard = (Card) challenge.getAttackerValue();
      Card ownerCard = (Card) challenge.getDefenderValue();
      if (challenge.getDefender().equals(owner)
          && !attackerCard.getSuit().equals(ownerCard.getSuit())) {
        int roundIndex = roundBribeList.indexOf(challenge);
        for (int j = roundIndex; j < roundBribeList.size(); j++) {
          Card checkedCard = getCheckedCard(roundBribeList.get(j), owner);
          if (checkedCard.getSuit().equals(ownerCard.getSuit())) counter++;
        }
      }
    }
    return counter;
  }

  /**
   * Returns a list of sweet bride challenges of the owner and removes it by the user.
   *
   * @param counter how many sweet challenges are needed
   * @param trump trump of the round
   * @return List of challenges
   */
  public List<Challenge<?>> takeSweetBrideList(int counter, Suit trump) {
    List<Challenge<?>> result = new LinkedList<>();
    for (int i = 0; i < counter; i++) {
      int sweetPoints = 0;
      int sweetIndex = 0;
      for (int j = 0; j < bribeList.size(); j++) {
        Challenge<?> bribe = bribeList.get(j);
        if (sweetPoints < bribe.getPoints(trump)) {
          sweetPoints = bribe.getPoints(trump);
          sweetIndex = j;
        }
      }
      result.add(bribeList.get(sweetIndex));
      bribeList.remove(sweetIndex);
    }
    return result;
  }

  private Card getCheckedCard(Challenge<?> checkedChallenge, Owner owner) {
    if (checkedChallenge.getAttacker().equals(owner)) {
      return (Card) checkedChallenge.getAttackerValue();
    } else {
      return (Card) checkedChallenge.getDefenderValue();
    }
  }
}
