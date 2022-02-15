package de.javagath.backend.game.model.deck;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import de.javagath.backend.game.model.enums.Owner;
import de.javagath.backend.game.model.enums.Suit;
import de.javagath.backend.game.model.enums.Value;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SuppressWarnings({"javadoc"})
@SpringBootTest
public class CombinationTest {

  @Test
  void add_notValidCombination_throwIllegalArgumentException() {
    Owner player = Owner.PLAYER;
    Combination combination = Combination.newInstance(player);
    Set<Card> badCombination = new HashSet<>();
    badCombination.add(Card.newInstance(Suit.DIAMONDS, Value.EIGHT));
    badCombination.add(Card.newInstance(Suit.CLUBS, Value.SEVEN));

    assertThatThrownBy(() -> combination.add(badCombination))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void add_emptyCombination_throwIllegalArgumentException() {
    Owner player = Owner.PLAYER;
    Combination combination = Combination.newInstance(player);
    Set<Card> emptyCombination = new HashSet<>();

    assertThatThrownBy(() -> combination.add(emptyCombination))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void add_notBella_throwIllegalArgumentException() {
    Owner player = Owner.PLAYER;
    Combination combination = Combination.newInstance(player);
    Set<Card> notBellaCombination = new HashSet<>();
    notBellaCombination.add(Card.newInstance(Suit.DIAMONDS, Value.EIGHT));
    notBellaCombination.add(Card.newInstance(Suit.DIAMONDS, Value.SEVEN));

    assertThatThrownBy(() -> combination.add(notBellaCombination))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void add_notValidSequence_throwIllegalArgumentException() {
    Owner player = Owner.PLAYER;
    Combination combination = Combination.newInstance(player);
    Set<Card> notValidSequence = new HashSet<>();
    notValidSequence.add(Card.newInstance(Suit.DIAMONDS, Value.EIGHT));
    notValidSequence.add(Card.newInstance(Suit.DIAMONDS, Value.ACE));

    assertThatThrownBy(() -> combination.add(notValidSequence))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void getHighestCombination_nineAndAceCombinations_getAceCombination() {
    Owner player = Owner.PLAYER;
    Combination combination = Combination.newInstance(player);
    Set<Card> nineTertz = new TreeSet<>();
    nineTertz.add(Card.newInstance(Suit.SPADES, Value.SEVEN));
    nineTertz.add(Card.newInstance(Suit.SPADES, Value.EIGHT));
    nineTertz.add(Card.newInstance(Suit.SPADES, Value.NINE));
    Set<Card> aceTertz = new TreeSet<>();
    aceTertz.add(Card.newInstance(Suit.HEARTS, Value.ACE));
    aceTertz.add(Card.newInstance(Suit.HEARTS, Value.KING));
    aceTertz.add(Card.newInstance(Suit.HEARTS, Value.QUEEN));

    combination.add(nineTertz);
    combination.add(aceTertz);

    assertThat(combination.getHighestCombination(Suit.CLUBS)).isEqualTo(aceTertz);
  }

  @Test
  void compareToSuit_nineTrumpAndNineNonTrumpCombinations_trumpCombinationWins() {
    Owner player = Owner.PLAYER;
    Owner bot = Owner.BOT;
    Suit trump = Suit.SPADES;
    int expectedCompareResult = 1;
    Combination trumpCombination = Combination.newInstance(player);
    Set<Card> trumpTertz = new TreeSet<>();
    trumpTertz.add(Card.newInstance(trump, Value.SEVEN));
    trumpTertz.add(Card.newInstance(trump, Value.EIGHT));
    trumpTertz.add(Card.newInstance(trump, Value.NINE));
    Combination nonTrumpCombination = Combination.newInstance(bot);
    Set<Card> nonTrumpTertz = new TreeSet<>();
    nonTrumpTertz.add(Card.newInstance(Suit.HEARTS, Value.SEVEN));
    nonTrumpTertz.add(Card.newInstance(Suit.HEARTS, Value.EIGHT));
    nonTrumpTertz.add(Card.newInstance(Suit.HEARTS, Value.NINE));

    trumpCombination.add(trumpTertz);
    nonTrumpCombination.add(nonTrumpTertz);

    assertThat(trumpCombination.compareToSuit(nonTrumpCombination, trump))
        .isEqualTo(expectedCompareResult);
  }

  @Test
  void compareToSuit_nineNonTrumpAndNineTrumpCombinations_trumpCombinationWins() {
    Owner player = Owner.PLAYER;
    Owner bot = Owner.BOT;
    Suit trump = Suit.SPADES;
    int expectedCompareResult = -1;
    Combination trumpCombination = Combination.newInstance(player);
    Set<Card> trumpTertz = new TreeSet<>();
    trumpTertz.add(Card.newInstance(trump, Value.SEVEN));
    trumpTertz.add(Card.newInstance(trump, Value.EIGHT));
    trumpTertz.add(Card.newInstance(trump, Value.NINE));
    Combination nonTrumpCombination = Combination.newInstance(bot);
    Set<Card> nonTrumpTertz = new TreeSet<>();
    nonTrumpTertz.add(Card.newInstance(Suit.HEARTS, Value.SEVEN));
    nonTrumpTertz.add(Card.newInstance(Suit.HEARTS, Value.EIGHT));
    nonTrumpTertz.add(Card.newInstance(Suit.HEARTS, Value.NINE));

    trumpCombination.add(trumpTertz);
    nonTrumpCombination.add(nonTrumpTertz);

    assertThat(nonTrumpCombination.compareToSuit(trumpCombination, trump))
        .isEqualTo(expectedCompareResult);
  }

  @Test
  void compareToSuit_nineNonTrumpAndNineNonTrumpCombinations_sameValueByComparing() {
    Owner player = Owner.PLAYER;
    Owner bot = Owner.BOT;
    Suit trump = Suit.CLUBS;
    int expectedCompareResult = 0;
    Combination firstNonTrumpCombination = Combination.newInstance(player);
    Set<Card> firstNonTrumpTertz = new TreeSet<>();
    firstNonTrumpTertz.add(Card.newInstance(Suit.SPADES, Value.SEVEN));
    firstNonTrumpTertz.add(Card.newInstance(Suit.SPADES, Value.EIGHT));
    firstNonTrumpTertz.add(Card.newInstance(Suit.SPADES, Value.NINE));
    Combination secondNonTrumpCombination = Combination.newInstance(bot);
    Set<Card> secondNonTrumpTertz = new TreeSet<>();
    secondNonTrumpTertz.add(Card.newInstance(Suit.HEARTS, Value.SEVEN));
    secondNonTrumpTertz.add(Card.newInstance(Suit.HEARTS, Value.EIGHT));
    secondNonTrumpTertz.add(Card.newInstance(Suit.HEARTS, Value.NINE));

    firstNonTrumpCombination.add(firstNonTrumpTertz);
    secondNonTrumpCombination.add(secondNonTrumpTertz);

    assertThat(firstNonTrumpCombination.compareToSuit(secondNonTrumpCombination, trump))
        .isEqualTo(expectedCompareResult);
  }

  @Test
  void compareToSuit_emptyAttackerEmptyDefender_sameValueByComparing() {
    Owner player = Owner.PLAYER;
    Owner bot = Owner.BOT;
    Suit trump = Suit.CLUBS;
    int expectedCompareResult = 0;
    Combination playerCombination = Combination.newInstance(player);
    Combination botCombination = Combination.newInstance(bot);

    assertThat(playerCombination.compareToSuit(botCombination, trump))
        .isEqualTo(expectedCompareResult);
  }

  @Test
  void compareToSuit_emptyAttackerNotEmptyDefender_lowerValueByComparing() {
    Owner player = Owner.PLAYER;
    Owner bot = Owner.BOT;
    Suit trump = Suit.CLUBS;
    int expectedCompareResult = -1;
    Combination playerCombination = Combination.newInstance(player);
    Combination botCombination = Combination.newInstance(bot);

    Set<Card> nonTrumpTertz = new TreeSet<>();
    nonTrumpTertz.add(Card.newInstance(Suit.SPADES, Value.SEVEN));
    nonTrumpTertz.add(Card.newInstance(Suit.SPADES, Value.EIGHT));
    nonTrumpTertz.add(Card.newInstance(Suit.SPADES, Value.NINE));

    botCombination.add(nonTrumpTertz);

    assertThat(playerCombination.compareToSuit(botCombination, trump))
        .isEqualTo(expectedCompareResult);
  }

  @Test
  void toString_suitDiamondValueNine_expectedToString() {
    Owner player = Owner.PLAYER;
    Combination combination = Combination.newInstance(player);
    Set<Card> tertz = new TreeSet<>();
    tertz.add(Card.newInstance(Suit.SPADES, Value.SEVEN));
    tertz.add(Card.newInstance(Suit.SPADES, Value.EIGHT));
    tertz.add(Card.newInstance(Suit.SPADES, Value.NINE));
    String expected =
        "Combination{player=PLAYER, combinationList=[CardCombination{combinationCards=[Card{suit=SPADES, value=SEVEN, active=true}, Card{suit=SPADES, value=EIGHT, active=true}, Card{suit=SPADES, value=NINE, active=true}], suit=SPADES}]}";

    combination.add(tertz);
    System.out.println(combination);

    assertThat(combination.toString()).isEqualTo(expected);
  }
}
