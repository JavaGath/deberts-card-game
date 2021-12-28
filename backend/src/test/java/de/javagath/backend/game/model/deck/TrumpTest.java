package de.javagath.backend.game.model.deck;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import de.javagath.backend.game.model.enums.Suit;
import de.javagath.backend.game.model.enums.Value;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TrumpTest {

  @Test
  void newInstance_randomCard_newTrumpByDefault() {
    Card card = Card.newInstance(Suit.HEARTS, Value.SEVEN);
    Trump trump = Trump.newInstance(card);

    assertThat(trump.getSuit()).isEqualTo(card.getSuit());
    assertThat(trump.getValue()).isEqualTo(card.getValue());
  }

  @Test
  void isNative_newInstance_trueByDefault() {
    Card card = Card.newInstance(Suit.DIAMONDS, Value.SEVEN);
    Trump trump = Trump.newInstance(card);

    assertThat(trump.isNative()).isTrue();
  }

  @Test
  void isNative_newInstanceSetNewTrump_false() {
    Card card = Card.newInstance(Suit.SPADES, Value.ACE);
    Trump trump = Trump.newInstance(card);

    trump.setTradedTrump(Card.newInstance(Suit.CLUBS, Value.ACE));

    assertThat(trump.isNative()).isFalse();
  }

  @Test
  void isNative_newInstanceSwitchTrump_false() {
    Card card = Card.newInstance(Suit.SPADES, Value.KING);
    Trump trump = Trump.newInstance(card);
    Card newTrumpCard = Card.newInstance(Suit.SPADES, Value.SEVEN);

    trump.switchTrumpSeven(newTrumpCard);

    assertThat(trump.isNative()).isFalse();
  }

  @Test
  void switchTrump_newInstanceSwitchToTheOtherTrump_throwsIllegalArgumentException() {
    Card card = Card.newInstance(Suit.CLUBS, Value.QUEEN);
    Trump trump = Trump.newInstance(card);
    Card newTrumpCard = Card.newInstance(Suit.DIAMONDS, Value.SEVEN);

    assertThatThrownBy(() -> trump.switchTrumpSeven(newTrumpCard))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void switchTrump_newInstanceSetOtherTrumpSwitchTrump_throwsIllegalArgumentException() {
    Card card = Card.newInstance(Suit.CLUBS, Value.QUEEN);
    Trump trump = Trump.newInstance(card);
    Card newTrumpCardToSet = Card.newInstance(Suit.HEARTS, Value.KING);
    Card newTrumpCardToSwitch = Card.newInstance(Suit.DIAMONDS, Value.SEVEN);

    trump.setTradedTrump(newTrumpCardToSet);

    assertThatThrownBy(() -> trump.switchTrumpSeven(newTrumpCardToSwitch))
        .isInstanceOf(IllegalStateException.class);
  }

  @Test
  void switchTrump_newInstanceSwitchToTheTrumpSeven_cardIsSwitched() {
    Card card = Card.newInstance(Suit.CLUBS, Value.QUEEN);
    Trump trump = Trump.newInstance(card);
    Card newTrumpCard = Card.newInstance(Suit.CLUBS, Value.SEVEN);

    Card trumpCard = trump.switchTrumpSeven(newTrumpCard);

    assertThat(trumpCard).isEqualTo((card));
    assertThat(trump.getSuit()).isEqualTo(newTrumpCard.getSuit());
    assertThat(trump.getValue()).isEqualTo(newTrumpCard.getValue());
  }

  @Test
  void setTradeTrump_newInstanceSetNewTrump_trumpIsSet() {
    Card card = Card.newInstance(Suit.DIAMONDS, Value.JACK);
    Trump trump = Trump.newInstance(card);
    Card newTrumpCard = Card.newInstance(Suit.HEARTS, Value.NINE);

    trump.setTradedTrump(newTrumpCard);

    assertThat(trump.getSuit()).isEqualTo(newTrumpCard.getSuit());
  }

  @Test
  void setTradeTrump_newInstanceSetTrumpWithSameSuit_throwsIllegalArgumentException() {
    Card card = Card.newInstance(Suit.SPADES, Value.TEN);
    Trump trump = Trump.newInstance(card);
    Card newTrumpCard = Card.newInstance(Suit.SPADES, Value.EIGHT);

    assertThatThrownBy(() -> trump.setTradedTrump(newTrumpCard))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void setTradeTrump_newInstanceSetTrumpAfterSetTrump_throwsIllegalArgumentException() {
    Card card = Card.newInstance(Suit.SPADES, Value.TEN);
    Trump trump = Trump.newInstance(card);
    Card newTrumpCard = Card.newInstance(Suit.CLUBS, Value.EIGHT);
    Card secondNewTrumpCard = Card.newInstance(Suit.DIAMONDS, Value.EIGHT);

    trump.setTradedTrump(newTrumpCard);

    assertThatThrownBy(() -> trump.setTradedTrump(secondNewTrumpCard))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void hashCode_newInstanceWithSameCard_sameHashCode() {
    Card card = Card.newInstance(Suit.SPADES, Value.TEN);
    Trump trump = Trump.newInstance(card);
    Trump secondTrump = Trump.newInstance(card);

    assertThat(trump).hasSameHashCodeAs(secondTrump);
  }

  @Test
  void hashCode_newInstanceWithDifferentCards_differentHashCode() {
    Card card = Card.newInstance(Suit.SPADES, Value.TEN);
    Card secondCard = Card.newInstance(Suit.DIAMONDS, Value.TEN);
    Trump trump = Trump.newInstance(card);
    Trump secondTrump = Trump.newInstance(secondCard);

    assertThat(trump.hashCode()).isNotEqualTo(secondTrump.hashCode());
  }

  @Test
  void equals_newInstanceWithSameCard_true() {
    Card card = Card.newInstance(Suit.SPADES, Value.TEN);
    Trump trump = Trump.newInstance(card);
    Trump secondTrump = Trump.newInstance(card);

    assertThat(trump).isEqualTo(secondTrump);
  }

  @Test
  void equals_newInstanceWithDifferentCards_false() {
    Card card = Card.newInstance(Suit.SPADES, Value.TEN);
    Card secondCard = Card.newInstance(Suit.DIAMONDS, Value.TEN);
    Trump trump = Trump.newInstance(card);
    Trump secondTrump = Trump.newInstance(secondCard);

    assertThat(trump).isNotEqualTo(secondTrump);
  }

  @Test
  void toString_newCardDeck_StringContainsSuitsAndFullSuits() {
    Card card = Card.newInstance(Suit.CLUBS, Value.JACK);
    Trump trump = Trump.newInstance(card);

    assertThat(trump.toString()).contains("card").contains("nativeTrump");
  }

  @Test
  void getTrumpCard_createTrumpAndGetSameCard_equalCard() {
    Card card = Card.newInstance(Suit.CLUBS, Value.JACK);
    Trump trump = Trump.newInstance(card);

    assertThat(card).isEqualTo(trump.getTrumpCard());
  }
}
