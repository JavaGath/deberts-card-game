package de.javagath.backend.game.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.boot.test.context.SpringBootTest;

@SuppressWarnings({"javadoc"})
@SpringBootTest
public class CardTest {

  @ParameterizedTest
  @CsvFileSource(resources = "/SuitAndValueCombination.csv", numLinesToSkip = 1, delimiter = ';')
  void newInstance_allSuitAndValueCombinations_byDefault(Suit suit, Value value) {
    Card card = Card.newInstance(suit, value);

    assertThat(card.getValue()).isEqualTo(value);
  }

  @Test
  void getPoints_valueNineTrumpSuit_14Points() {
    Suit trumpSuit = Suit.DIAMONDS;
    int expectedPoints = 14;
    Card card = Card.newInstance(trumpSuit, Value.NINE);

    assertThat(card.getPoints(trumpSuit)).isEqualTo(expectedPoints);
  }

  @Test
  void getPoints_valueNineNotTrumpSuit_ZeroPoints() {
    Suit trumpSuit = Suit.DIAMONDS;
    int expectedPoints = 0;
    Card card = Card.newInstance(Suit.HEARTS, Value.NINE);

    assertThat(card.getPoints(trumpSuit)).isEqualTo(expectedPoints);
  }

  @Test
  void newInstance_newInstanceFromCardDiamondsSeven_isEqualByDefault() {
    Card firstCard = Card.newInstance(Suit.DIAMONDS, Value.SEVEN);
    Card secondCard = Card.newInstance(firstCard);

    assertThat(firstCard).isEqualTo(secondCard);
  }

  @Test
  void equals_diamondsSevenNewInstanceFromCardDiamondsSevenNotActive_isNotEqual() {
    Card firstCard = Card.newInstance(Suit.DIAMONDS, Value.SEVEN);
    Card secondCard = Card.newInstance(firstCard);

    secondCard.setActive(false);

    assertThat(firstCard).isNotEqualTo(secondCard);
  }

  @Test
  void equals_diamondsSevenHeartsEight_isNotEqual() {
    Card firstCard = Card.newInstance(Suit.DIAMONDS, Value.SEVEN);
    Card secondCard = Card.newInstance(Suit.HEARTS, Value.EIGHT);

    assertThat(firstCard).isNotEqualTo(secondCard);
  }

  @Test
  void equals_diamondsSevenDiamondsEight_isNotEqual() {
    Card firstCard = Card.newInstance(Suit.DIAMONDS, Value.SEVEN);
    Card secondCard = Card.newInstance(Suit.DIAMONDS, Value.EIGHT);

    assertThat(firstCard).isNotEqualTo(secondCard);
  }

  @Test
  void hashCode_diamondsSevenNewInstanceFromCardDiamondsSeven_isEqual() {
    Card firstCard = Card.newInstance(Suit.DIAMONDS, Value.SEVEN);
    Card secondCard = Card.newInstance(firstCard);

    assertThat(firstCard).hasSameHashCodeAs(secondCard);
  }

  @Test
  void hashCode_diamondsSevenHeartsEight_isNotEqual() {
    Card firstCard = Card.newInstance(Suit.DIAMONDS, Value.SEVEN);
    Card secondCard = Card.newInstance(Suit.HEARTS, Value.EIGHT);

    assertThat(firstCard.hashCode()).isNotEqualTo(secondCard.hashCode());
  }

  @ParameterizedTest
  @EnumSource(
      value = Suit.class,
      names = {"DIAMONDS", "HEARTS", "SPADES", "CLUBS"})
  void getValue_valueEightEachSuit_suitValueByDefault(Suit suit) {
    Card card = Card.newInstance(suit, Value.SEVEN);

    assertThat(card.getSuit()).isEqualTo(suit);
  }

  @Test
  void isActive_newInstance_trueByDefault() {
    Card card = Card.newInstance(Suit.DIAMONDS, Value.ACE);

    assertThat(card.isActive()).isTrue();
  }

  @Test
  void isActive_newInstanceSetFalse_false() {
    Card card = Card.newInstance(Suit.DIAMONDS, Value.ACE);

    card.setActive(false);

    assertThat(card.isActive()).isFalse();
  }

  @Test
  void hasSuit_suitDiamondsCheckWithDiamondsSuit_true() {
    Card card = Card.newInstance(Suit.DIAMONDS, Value.ACE);

    assertThat(card.hasSuit(Suit.DIAMONDS)).isTrue();
  }

  @ParameterizedTest
  @EnumSource(
      value = Suit.class,
      names = {"HEARTS", "SPADES", "CLUBS"})
  void hasSuit_suitDiamondsCheckWithOtherSuits_false(Suit suit) {
    Card card = Card.newInstance(suit, Value.SEVEN);

    assertThat(card.hasSuit(suit)).isTrue();
  }

  @Test
  void hasValue_valueNineCheckWithNineValue_true() {
    Card card = Card.newInstance(Suit.DIAMONDS, Value.NINE);

    assertThat(card.hasValue(Value.NINE)).isTrue();
  }

  @ParameterizedTest
  @EnumSource(
      value = Value.class,
      names = {"SEVEN", "EIGHT", "TEN", "JACK", "QUEEN", "KING", "ACE"})
  void hasValue_suitDiamondsCheckWithOtherSuits_false(Value value) {
    Card card = Card.newInstance(Suit.DIAMONDS, value);

    assertThat(card.hasValue(value)).isTrue();
  }

  @Test
  void toString_suitDiamondValueNine_expectedToString() {
    Card card = Card.newInstance(Suit.DIAMONDS, Value.NINE);
    String expectedString =
        "Card{" + "suit=" + Suit.DIAMONDS + ", value=" + Value.NINE + ", active=" + true + '}';

    assertThat(card.toString()).hasToString(expectedString);
  }
}
