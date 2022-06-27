package de.javagath.backend.game.model.deck;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import de.javagath.backend.game.model.enums.Owner;
import de.javagath.backend.game.model.enums.Suit;
import de.javagath.backend.game.model.enums.Value;
import java.util.Set;
import java.util.TreeSet;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SuppressWarnings({"javadoc"})
@SpringBootTest
@TestPropertySource(locations = "classpath:application-unittest.properties")
public class ChallengeTest {

  @Test
  void setAttacker_newChallengeSetBotAsAttacker_botIsAttacker() {
    Owner attacker = Owner.BOT;
    Challenge<Card> challenge = new Challenge<>();

    challenge.setAttacker(attacker);

    assertThat(challenge.getAttacker()).isEqualTo(attacker);
    assertThat(challenge.getDefender()).isNull();
  }

  @Test
  void setDefender_newChallengeSetPlayerAsDefender_playerIsDefender() {
    Owner defender = Owner.PLAYER;
    Challenge<Card> challenge = new Challenge<>();

    challenge.setDefender(defender);

    assertThat(challenge.getDefender()).isEqualTo(defender);
    assertThat(challenge.getAttacker()).isNull();
  }

  @Test
  void setDefender_newChallengeSetPlayerAsAttackerAndDefender_throwsIllegalArgumentException() {
    Owner player = Owner.PLAYER;
    Challenge<Card> challenge = new Challenge<>();

    challenge.setAttacker(player);

    assertThatThrownBy(() -> challenge.setDefender(player))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void setAttacker_newChallengeSetBotAsDefenderAndAttacker_throwsIllegalArgumentException() {
    Owner bot = Owner.BOT;
    Challenge<Card> challenge = new Challenge<>();

    challenge.setDefender(bot);

    assertThatThrownBy(() -> challenge.setAttacker(bot))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void setAttackerValue_newChallengeSetHeartsNineAsAttackerValue_heartsNineIsAttacker() {
    Card card = Card.newInstance(Suit.HEARTS, Value.NINE);
    Challenge<Card> challenge = new Challenge<>();

    challenge.setAttackerValue(card);

    assertThat(challenge.getAttackerValue()).isEqualTo(card);
    assertThat(challenge.getDefenderValue()).isNull();
  }

  @Test
  void setDefenderValue_newChallengeSetClubsEightAsDefenderValue_clubsEightIsDefender() {
    Card card = Card.newInstance(Suit.CLUBS, Value.EIGHT);
    Challenge<Card> challenge = new Challenge<>();

    challenge.setDefenderValue(card);

    assertThat(challenge.getDefenderValue()).isEqualTo(card);
    assertThat(challenge.getAttackerValue()).isNull();
  }

  @Test
  void
      setDefenderValue_newChallengeSetSpadesKingAsAttackerAndDefenderValue_throwsIllegalArgumentException() {
    Card card = Card.newInstance(Suit.DIAMONDS, Value.SEVEN);
    Challenge<Card> challenge = new Challenge<>();

    challenge.setAttackerValue(card);

    assertThatThrownBy(() -> challenge.setDefenderValue(card))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void
      setAttackerValue_newChallengeSetDiamondSevenAsDefenderAndAttackerValue_throwsIllegalArgumentException() {
    Card card = Card.newInstance(Suit.DIAMONDS, Value.SEVEN);
    Challenge<Card> challenge = new Challenge<>();

    challenge.setDefenderValue(card);

    assertThatThrownBy(() -> challenge.setAttackerValue(card))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void getWinner_newChallengeOnlyAttacker_throwsIllegalStateException() {
    Challenge<Card> challenge = new Challenge<>();

    challenge.setAttacker(Owner.BOT);

    assertThatThrownBy(() -> challenge.getWinner(Suit.CLUBS))
        .isInstanceOf(IllegalStateException.class);
  }

  @Test
  void getWinner_newChallengeAttackerTrumpNineDefenderTrumpJack_defenderWins() {
    Card attackerCard = Card.newInstance(Suit.DIAMONDS, Value.NINE);
    Card defenderCard = Card.newInstance(Suit.DIAMONDS, Value.JACK);
    Owner attacker = Owner.PLAYER;
    Owner defender = Owner.BOT;
    Challenge<Card> challenge = new Challenge<>();

    challenge.setAttacker(attacker);
    challenge.setDefender(defender);
    challenge.setAttackerValue(attackerCard);
    challenge.setDefenderValue(defenderCard);

    assertThat(challenge.getWinner(Suit.DIAMONDS)).isEqualTo(defender);
  }

  @Test
  void getWinner_newChallengeAttackerNotTrumpAceDefenderTrumpSeven_defenderWins() {
    Card attackerCard = Card.newInstance(Suit.HEARTS, Value.ACE);
    Card defenderCard = Card.newInstance(Suit.DIAMONDS, Value.SEVEN);
    Owner attacker = Owner.PLAYER;
    Owner defender = Owner.BOT;
    Challenge<Card> challenge = new Challenge<>();

    challenge.setAttacker(attacker);
    challenge.setDefender(defender);
    challenge.setAttackerValue(attackerCard);
    challenge.setDefenderValue(defenderCard);

    assertThat(challenge.getWinner(Suit.DIAMONDS)).isEqualTo(defender);
  }

  @Test
  void getWinner_newChallengeAttackerNotTrumpAceDefenderNotTrumpSevenSameSuit_attackerWins() {
    Card attackerCard = Card.newInstance(Suit.HEARTS, Value.ACE);
    Card defenderCard = Card.newInstance(Suit.HEARTS, Value.SEVEN);
    Owner attacker = Owner.PLAYER;
    Owner defender = Owner.BOT;
    Challenge<Card> challenge = new Challenge<>();

    challenge.setAttacker(attacker);
    challenge.setDefender(defender);
    challenge.setAttackerValue(attackerCard);
    challenge.setDefenderValue(defenderCard);

    assertThat(challenge.getWinner(Suit.DIAMONDS)).isEqualTo(attacker);
  }

  @Test
  void getWinner_newChallengeAttackerNotTrumpNineDefenderNotTrumpAceDifferentSuits_attackerWins() {
    Card attackerCard = Card.newInstance(Suit.HEARTS, Value.NINE);
    Card defenderCard = Card.newInstance(Suit.SPADES, Value.ACE);
    Owner attacker = Owner.PLAYER;
    Owner defender = Owner.BOT;
    Challenge<Card> challenge = new Challenge<>();

    challenge.setAttacker(attacker);
    challenge.setDefender(defender);
    challenge.setAttackerValue(attackerCard);
    challenge.setDefenderValue(defenderCard);

    assertThat(challenge.getWinner(Suit.CLUBS)).isEqualTo(attacker);
  }

  @Test
  void getWinner_newChallengeAttackerTrumpSevenDefenderTrumpEight_defenderWins() {
    Card attackerCard = Card.newInstance(Suit.DIAMONDS, Value.SEVEN);
    Card defenderCard = Card.newInstance(Suit.DIAMONDS, Value.EIGHT);
    Owner attacker = Owner.PLAYER;
    Owner defender = Owner.BOT;
    Challenge<Card> challenge = new Challenge<>();

    challenge.setAttacker(attacker);
    challenge.setDefender(defender);
    challenge.setAttackerValue(attackerCard);
    challenge.setDefenderValue(defenderCard);

    assertThat(challenge.getWinner(Suit.DIAMONDS)).isEqualTo(defender);
  }

  @Test
  void getPoints_newChallengeTrumpNineTrumpJack_34Points() {
    Card attackerCard = Card.newInstance(Suit.DIAMONDS, Value.NINE);
    Card defenderCard = Card.newInstance(Suit.DIAMONDS, Value.JACK);
    int expectedPoints = 34;
    Owner attacker = Owner.PLAYER;
    Owner defender = Owner.BOT;
    Challenge<Card> challenge = new Challenge<>();

    challenge.setAttacker(attacker);
    challenge.setDefender(defender);
    challenge.setAttackerValue(attackerCard);
    challenge.setDefenderValue(defenderCard);

    assertThat(challenge.getPoints(Suit.DIAMONDS)).isEqualTo(expectedPoints);
  }

  @Test
  void getPoints_newChallengeOnlyAttacker_throwsIllegalStateException() {
    Challenge<Card> challenge = new Challenge<>();

    challenge.setDefender(Owner.PLAYER);

    assertThatThrownBy(() -> challenge.getPoints(Suit.CLUBS))
        .isInstanceOf(IllegalStateException.class);
  }

  @Test
  void getPoints_attackerHasTwoTertzDefenderOneFifty_90Points() {
    int expectedPoints = 90;
    Owner attacker = Owner.PLAYER;
    Owner defender = Owner.BOT;
    Set<Card> attackerFirstTertz = new TreeSet<>();
    attackerFirstTertz.add(Card.newInstance(Suit.SPADES, Value.SEVEN));
    attackerFirstTertz.add(Card.newInstance(Suit.SPADES, Value.EIGHT));
    attackerFirstTertz.add(Card.newInstance(Suit.SPADES, Value.NINE));
    Set<Card> attackerSecondTertz = new TreeSet<>();
    attackerSecondTertz.add(Card.newInstance(Suit.HEARTS, Value.SEVEN));
    attackerSecondTertz.add(Card.newInstance(Suit.HEARTS, Value.EIGHT));
    attackerSecondTertz.add(Card.newInstance(Suit.HEARTS, Value.NINE));
    Set<Card> defenderFifty = new TreeSet<>();
    defenderFifty.add(Card.newInstance(Suit.DIAMONDS, Value.SEVEN));
    defenderFifty.add(Card.newInstance(Suit.DIAMONDS, Value.EIGHT));
    defenderFifty.add(Card.newInstance(Suit.DIAMONDS, Value.NINE));
    defenderFifty.add(Card.newInstance(Suit.DIAMONDS, Value.TEN));
    Challenge<Combination> challenge = new Challenge<>();

    challenge.setAttacker(attacker);
    challenge.setDefender(defender);
    Combination attackerCombination = Combination.newInstance(attacker);
    attackerCombination.add(attackerFirstTertz);
    attackerCombination.add(attackerSecondTertz);
    Combination defenderCombination = Combination.newInstance(defender);
    defenderCombination.add(defenderFifty);
    challenge.setAttackerValue(attackerCombination);
    challenge.setDefenderValue(defenderCombination);

    assertThat(challenge.getPoints(Suit.SPADES)).isEqualTo(expectedPoints);
  }

  @Test
  void getWinner_attackerHasTwoTertzDefenderOneFifty_defenderWins() {
    Owner winner = Owner.BOT;
    Owner attacker = Owner.PLAYER;
    Owner defender = Owner.BOT;
    Set<Card> attackerFirstTertz = new TreeSet<>();
    attackerFirstTertz.add(Card.newInstance(Suit.SPADES, Value.SEVEN));
    attackerFirstTertz.add(Card.newInstance(Suit.SPADES, Value.EIGHT));
    attackerFirstTertz.add(Card.newInstance(Suit.SPADES, Value.NINE));
    Set<Card> attackerSecondTertz = new TreeSet<>();
    attackerSecondTertz.add(Card.newInstance(Suit.HEARTS, Value.SEVEN));
    attackerSecondTertz.add(Card.newInstance(Suit.HEARTS, Value.EIGHT));
    attackerSecondTertz.add(Card.newInstance(Suit.HEARTS, Value.NINE));
    Set<Card> defenderFifty = new TreeSet<>();
    defenderFifty.add(Card.newInstance(Suit.DIAMONDS, Value.SEVEN));
    defenderFifty.add(Card.newInstance(Suit.DIAMONDS, Value.EIGHT));
    defenderFifty.add(Card.newInstance(Suit.DIAMONDS, Value.NINE));
    defenderFifty.add(Card.newInstance(Suit.DIAMONDS, Value.TEN));
    Challenge<Combination> challenge = new Challenge<>();

    challenge.setAttacker(attacker);
    challenge.setDefender(defender);
    Combination attackerCombination = Combination.newInstance(attacker);
    attackerCombination.add(attackerFirstTertz);
    attackerCombination.add(attackerSecondTertz);
    Combination defenderCombination = Combination.newInstance(defender);
    defenderCombination.add(defenderFifty);
    challenge.setAttackerValue(attackerCombination);
    challenge.setDefenderValue(defenderCombination);

    assertThat(challenge.getWinner(Suit.SPADES)).isEqualTo(winner);
  }

  @Test
  void hashCode_fiftyDiamondsTenAttackerAndFiftyDiamondsTenAttacker_isEqual() {
    Owner attacker = Owner.PLAYER;
    Set<Card> fifty = new TreeSet<>();
    fifty.add(Card.newInstance(Suit.DIAMONDS, Value.SEVEN));
    fifty.add(Card.newInstance(Suit.DIAMONDS, Value.EIGHT));
    fifty.add(Card.newInstance(Suit.DIAMONDS, Value.NINE));
    fifty.add(Card.newInstance(Suit.DIAMONDS, Value.TEN));

    Combination firstAttackerCombination = Combination.newInstance(attacker);
    firstAttackerCombination.add(fifty);
    Challenge<Combination> firstChallenge = new Challenge<>();
    firstChallenge.setAttacker(attacker);
    firstChallenge.setAttackerValue(firstAttackerCombination);

    Combination secondAttackerCombination = Combination.newInstance(attacker);
    secondAttackerCombination.add(fifty);
    Challenge<Combination> secondChallenge = new Challenge<>();
    secondChallenge.setAttacker(attacker);
    secondChallenge.setAttackerValue(secondAttackerCombination);

    Assertions.assertThat(firstChallenge).hasSameHashCodeAs(secondChallenge);
  }

  @Test
  void hashCode_fiftyDiamondsTenAttackerAndFiftyDiamondsTenDefender_isNotEqual() {
    Owner attacker = Owner.PLAYER;
    Owner defender = Owner.BOT;
    Set<Card> fifty = new TreeSet<>();
    fifty.add(Card.newInstance(Suit.DIAMONDS, Value.SEVEN));
    fifty.add(Card.newInstance(Suit.DIAMONDS, Value.EIGHT));
    fifty.add(Card.newInstance(Suit.DIAMONDS, Value.NINE));
    fifty.add(Card.newInstance(Suit.DIAMONDS, Value.TEN));

    Combination firstAttackerCombination = Combination.newInstance(attacker);
    firstAttackerCombination.add(fifty);
    Challenge<Combination> firstChallenge = new Challenge<>();
    firstChallenge.setAttacker(attacker);
    firstChallenge.setAttackerValue(firstAttackerCombination);

    Combination secondAttackerCombination = Combination.newInstance(defender);
    secondAttackerCombination.add(fifty);
    Challenge<Combination> secondChallenge = new Challenge<>();
    secondChallenge.setDefender(defender);
    secondChallenge.setDefenderValue(secondAttackerCombination);

    Assertions.assertThat(firstChallenge).doesNotHaveSameHashCodeAs(secondChallenge);
  }

  @Test
  void equals_fiftySpadesTenAttackerAndFiftySpadesTenAttacker_isEqual() {
    Owner attacker = Owner.PLAYER;
    Set<Card> fifty = new TreeSet<>();
    fifty.add(Card.newInstance(Suit.SPADES, Value.SEVEN));
    fifty.add(Card.newInstance(Suit.SPADES, Value.EIGHT));
    fifty.add(Card.newInstance(Suit.SPADES, Value.NINE));
    fifty.add(Card.newInstance(Suit.SPADES, Value.TEN));

    Combination firstAttackerCombination = Combination.newInstance(attacker);
    firstAttackerCombination.add(fifty);
    Challenge<Combination> firstChallenge = new Challenge<>();
    firstChallenge.setAttacker(attacker);
    firstChallenge.setAttackerValue(firstAttackerCombination);

    Combination secondAttackerCombination = Combination.newInstance(attacker);
    secondAttackerCombination.add(fifty);
    Challenge<Combination> secondChallenge = new Challenge<>();
    secondChallenge.setAttacker(attacker);
    secondChallenge.setAttackerValue(secondAttackerCombination);

    Assertions.assertThat(firstChallenge).isEqualTo(secondChallenge);
  }

  @Test
  void hashCode_fiftyClubsTenAttackerAndFiftyClubsTenDefender_isNotEqual() {
    Owner attacker = Owner.PLAYER;
    Owner defender = Owner.BOT;
    Set<Card> fifty = new TreeSet<>();
    fifty.add(Card.newInstance(Suit.DIAMONDS, Value.SEVEN));
    fifty.add(Card.newInstance(Suit.DIAMONDS, Value.EIGHT));
    fifty.add(Card.newInstance(Suit.DIAMONDS, Value.NINE));
    fifty.add(Card.newInstance(Suit.DIAMONDS, Value.TEN));

    Combination firstAttackerCombination = Combination.newInstance(attacker);
    firstAttackerCombination.add(fifty);
    Challenge<Combination> firstChallenge = new Challenge<>();
    firstChallenge.setAttacker(attacker);
    firstChallenge.setAttackerValue(firstAttackerCombination);

    Combination secondAttackerCombination = Combination.newInstance(defender);
    secondAttackerCombination.add(fifty);
    Challenge<Combination> secondChallenge = new Challenge<>();
    secondChallenge.setDefender(defender);
    secondChallenge.setDefenderValue(secondAttackerCombination);

    Assertions.assertThat(firstChallenge).isNotEqualTo(secondChallenge);
  }

  @Test
  void toString_CardChallengeWithAttackerSuitDiamondValueNine_expectedToString() {
    Card attackerCard = Card.newInstance(Suit.DIAMONDS, Value.KING);
    Owner attacker = Owner.PLAYER;
    Challenge<Card> challenge = new Challenge<>();
    String expectedString =
        "Challenge{"
            + "attacker="
            + attacker
            + ", defender="
            + null
            + ", attackerValue="
            + attackerCard
            + ", defenderValue="
            + null
            + '}';

    challenge.setAttacker(attacker);
    challenge.setAttackerValue(attackerCard);

    Assertions.assertThat(challenge.toString()).hasToString(expectedString);
  }
}
