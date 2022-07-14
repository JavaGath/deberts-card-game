package de.javagath.backend.game.model.deck;

import de.javagath.backend.game.model.enums.Owner;
import de.javagath.backend.game.model.enums.Suit;
import de.javagath.backend.game.model.enums.Value;
import java.lang.invoke.MethodHandles;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An abstract class to contain and manage cards. Cards are grouped by {@code SuitPack}. All suits
 * are inside of {@code ArrayList}.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.0
 * @since 1.0
 */
public abstract class AbstractDeck implements Deck {

  static final Logger LOG =
      LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());
  final LinkedList<Suit> fullSuits = new LinkedList<>();
  EnumMap<Suit, SuitPack> suitMap = new EnumMap<>(Suit.class);
  Owner owner;
  int containedCards;

  @Override
  public boolean contains(Card card) {
    return contains(card.getSuit(), card.getValue());
  }

  @Override
  public boolean contains(Suit suit, Value value) {
    var suitPack = suitMap.get(suit);
    return suitPack.contains(value);
  }

  @Override
  public int countCards() {
    var activeCards = 0;
    for (SuitPack suitPack : suitMap.values()) {
      activeCards += suitPack.getActiveCards();
    }
    return activeCards;
  }

  @Override
  public Card dealCard(Suit suit, Value value) {
    var suitPack = suitMap.get(suit);
    var dealtCard = suitPack.dealCard(value);
    if (suitPack.isEmpty()) {
      fullSuits.remove(suit);
    }
    containedCards--;
    return dealtCard;
  }

  @Override
  public Card dealRandomCard() {
    try {
      LOG.debug("FullSuits: {}", fullSuits);
      var suitIndex = new Random().nextInt(fullSuits.size());
      Suit suit = fullSuits.get(suitIndex);
      return handeRandomCardDeal(suit);
    } catch (IllegalArgumentException e) {
      throw new NoSuchElementException("Deck does not contain any cards");
    }
  }

  @Override
  public Card dealRandomCardFromSuit(Suit suit, boolean isTrumpPick) {
    if (!isTrumpPick) {
      return handeRandomCardDeal(suit);
    } else {
      Card cardToDeal;
      try {
        cardToDeal = handeRandomCardDeal(suit);
        if (cardToDeal == null) {
          cardToDeal = Card.newInstance(suit, Value.ACE);
          fullSuits.remove(suit);
        }
      } catch (Exception e) {
        cardToDeal = Card.newInstance(suit, Value.ACE);
        fullSuits.remove(suit);
      }
      return cardToDeal;
    }
  }

  @Override
  public void resetDeck() {
    for (Suit suit : Suit.values()) {
      var suitPack = suitMap.get(suit);
      suitPack.resetPack();
      if (!suitPack.isEmpty()) {
        fullSuits.add(suit);
      }
    }
    LOG.debug("Deck successfully reset!");
  }

  @Override
  public Owner getOwner() {
    return owner;
  }

  @Override
  public boolean isEmpty() {
    return fullSuits.isEmpty();
  }

  private Card handeRandomCardDeal(Suit suit) {
    var suitPack = suitMap.get(suit);
    Card cardToDeal = suitPack.dealRandomCard();
    containedCards--;
    if (suitPack.isEmpty()) {
      fullSuits.remove(suit);
    }
    return cardToDeal;
  }

  @Override
  public int hashCode() {
    int result = fullSuits.hashCode();
    result = 31 * result + (suitMap != null ? suitMap.hashCode() : 0);
    result = 31 * result + (getOwner() != null ? getOwner().hashCode() : 0);
    return result;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof AbstractDeck)) {
      return false;
    }

    AbstractDeck that = (AbstractDeck) o;

    if (!fullSuits.equals(that.fullSuits)) {
      return false;
    }
    if (suitMap != null ? !suitMap.equals(that.suitMap) : that.suitMap != null) {
      return false;
    }
    return getOwner() == that.getOwner();
  }

  @Override
  public String toString() {
    return "AbstractDeck{"
        + "fullSuits="
        + fullSuits
        + ", suitMap="
        + suitMap
        + ", owner="
        + owner
        + '}';
  }
}
