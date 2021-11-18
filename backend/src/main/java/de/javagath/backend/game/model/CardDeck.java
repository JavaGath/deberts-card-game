package de.javagath.backend.game.model;

/**
 * An entity that contains and manages cards. Cards are grouped by {@code SuitPack}. All suits are
 * inside of {@code ArrayList}.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.0
 * @since 1.0
 */
public class CardDeck extends AbstractDeck {

  /**
   * Creates new {@code CardDeck} entity. During initialisation creates cards using cartesian
   * product of {@code Suit} and {@code Value}. Visibility of constructor is package private in
   * order to create single creation point in {@code DeckFactory}.
   */
  CardDeck() {
    owner = Owner.NOBODY;
    for (Suit suit : Suit.values()) {
      suitMap.put(suit, SuitPack.newInstance());
      var suitPack = suitMap.get(suit);
      for (Value value : Value.values()) {
        var newCard = Card.newInstance(suit, value);
        suitPack.addCard(newCard);
        LOG.debug("Card successfully added: {}", newCard);
      }
      if (!suitPack.isEmpty()) {
        fullSuits.add(suit);
      }
    }
  }

  @Override
  public void addCard(Card card) {
    SuitPack suitCardList = suitMap.get(card.getSuit());
    suitCardList.addCard(card);
  }

  @Override
  public String toString() {
    return "CardDeck{"
        + "fullSuits="
        + fullSuits
        + ", suitMap="
        + suitMap
        + ", owner="
        + owner
        + '}';
  }
}
