package de.javagath.backend.game.model;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SuppressWarnings("javadoc")
@SpringBootTest
public class HandDeckTest {

  Deck firstDeck;

  @BeforeEach
  void beforeEach() {
    firstDeck = DeckFactory.getDeck(Owner.PLAYER);
  }

  @Test
  void addCard_newHandDeckAdd5Cards_handDeckWith5Cards() {
    Card firstCard = Card.newInstance(Suit.HEARTS, Value.SEVEN);
    Card secondCard = Card.newInstance(Suit.DIAMONDS, Value.SEVEN);
    Card thirdCard = Card.newInstance(Suit.SPADES, Value.SEVEN);
    Card fourthCard = Card.newInstance(Suit.CLUBS, Value.SEVEN);
    Card fifthCard = Card.newInstance(Suit.HEARTS, Value.EIGHT);

    firstDeck.addCard(firstCard);
    firstDeck.addCard(secondCard);
    firstDeck.addCard(thirdCard);
    firstDeck.addCard(fourthCard);
    firstDeck.addCard(fifthCard);

    assertThat(firstDeck.countCards()).isEqualTo(5);
  }

  @Test
  void addCard_newHandDeckAdd10Cards_throwIllegalArgumentExceptionOn10thCard() {
    Card firstCard = Card.newInstance(Suit.HEARTS, Value.SEVEN);
    Card secondCard = Card.newInstance(Suit.HEARTS, Value.EIGHT);
    Card thirdCard = Card.newInstance(Suit.HEARTS, Value.NINE);
    Card fourthCard = Card.newInstance(Suit.HEARTS, Value.TEN);
    Card fifthCard = Card.newInstance(Suit.HEARTS, Value.JACK);
    Card sixthCard = Card.newInstance(Suit.HEARTS, Value.QUEEN);
    Card seventhCard = Card.newInstance(Suit.HEARTS, Value.KING);
    Card eightCard = Card.newInstance(Suit.HEARTS, Value.ACE);
    Card ninthCard = Card.newInstance(Suit.DIAMONDS, Value.SEVEN);
    Card tenthCard = Card.newInstance(Suit.DIAMONDS, Value.EIGHT);

    firstDeck.addCard(firstCard);
    firstDeck.addCard(secondCard);
    firstDeck.addCard(thirdCard);
    firstDeck.addCard(fourthCard);
    firstDeck.addCard(fifthCard);
    firstDeck.addCard(sixthCard);
    firstDeck.addCard(seventhCard);
    firstDeck.addCard(eightCard);
    firstDeck.addCard(ninthCard);

    assertThatThrownBy(() -> firstDeck.addCard(tenthCard))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void equals_twoNewHandDecks_trueByDefault() {
    Deck secondDeck = DeckFactory.getDeck(Owner.PLAYER);

    assertThat(firstDeck).isEqualTo(secondDeck);
  }

  @Test
  void equals_twoNewCHandDecksDifferentOwners_false() {
    Deck secondDeck = DeckFactory.getDeck(Owner.BOT);

    assertThat(firstDeck).isNotEqualTo(secondDeck);
  }

  @Test
  void hashcode_twoNewHandDecks_sameHashCode() {
    Deck secondDeck = DeckFactory.getDeck(Owner.PLAYER);

    assertThat(firstDeck).hasSameHashCodeAs(secondDeck);
  }

  @Test
  void hashcode_twoNewHandDecksDifferentOwners_differentHashCode() {
    Deck secondDeck = DeckFactory.getDeck(Owner.BOT);

    assertThat(firstDeck.hashCode()).isNotEqualTo(secondDeck.hashCode());
  }

  @Test
  void toString_newCardDeck_StringContainsSuitsAndFullSuits() {

    assertThat(firstDeck.toString())
        .contains("suitMap")
        .contains("fullSuits")
        .contains("owner")
        .contains("maxCardsNumber")
        .contains("containedCards");
  }
}
