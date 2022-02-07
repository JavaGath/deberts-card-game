package de.javagath.backend.game.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import de.javagath.backend.game.model.deck.Card;
import de.javagath.backend.game.model.deck.Challenge;
import de.javagath.backend.game.model.deck.Combination;
import de.javagath.backend.game.model.enums.Owner;
import de.javagath.backend.game.model.enums.Suit;
import de.javagath.backend.game.model.enums.Value;
import java.util.Set;
import java.util.TreeSet;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SuppressWarnings("javadoc")
@SpringBootTest
public class RoundTest {

  @Test
  void decideChallenge_inTradePhase_throwsIllegalStateException() {
    Challenge<Card> challenge = new Challenge<>();
    Round round = Round.newInstance(Owner.NOBODY);

    assertThatThrownBy(() -> round.decideChallenge(challenge))
        .isInstanceOf(IllegalStateException.class);
  }

  @Test
  void decideChallenge_inCombinationPhase_throwsIllegalArgumentException() {
    Round round = Round.newInstance(Owner.NOBODY);
    int expectedPoints = 20;
    Owner attacker = Owner.PLAYER;
    Owner defender = Owner.BOT;
    Set<Card> attackerTertz = new TreeSet<>();
    attackerTertz.add(Card.newInstance(Suit.SPADES, Value.SEVEN));
    attackerTertz.add(Card.newInstance(Suit.SPADES, Value.EIGHT));
    attackerTertz.add(Card.newInstance(Suit.SPADES, Value.NINE));
    Challenge<Combination> challenge = new Challenge<>();
    challenge.setAttacker(attacker);
    challenge.setDefender(defender);
    Combination attackerCombination = Combination.newInstance(attacker);
    attackerCombination.add(attackerTertz);
    Combination defenderCombination = Combination.newInstance(defender);
    challenge.setAttackerValue(attackerCombination);
    challenge.setDefenderValue(defenderCombination);

    round.switchPhase();
    round.decideChallenge(challenge);

    assertThat(challenge.getPoints(Suit.SPADES)).isEqualTo(expectedPoints);
  }

  @Test
  void switchTrumpSeven_switchTrumpSevenInTheRound_throwsIllegalStateException() {
    Round round = Round.newInstance(Owner.NOBODY);

    assertThatThrownBy(round::switchTrumpSeven).isInstanceOf(IllegalStateException.class);
  }

  @Test
  void playTrump_switchTrumpSevenInTheRound_throwsIllegalStateException() {
    Round round = Round.newInstance(Owner.NOBODY);
    round.playTrump(Suit.DIAMONDS, Owner.PLAYER);
    round.switchPhase();

    assertThatThrownBy(() -> round.playTrump(Suit.DIAMONDS, Owner.PLAYER))
        .isInstanceOf(IllegalStateException.class);
  }

  @Test
  void newInstance_newRound_eachPlayerHas6CardsInTheHand() {
    int expectedCardNumber = 12;

    Round round = Round.newInstance(Owner.NOBODY);

    assertThat(round.countHandCards()).isEqualTo(expectedCardNumber);
  }

  @Test
  void newInstance_newPartyWithTheFirstRound_trumpDeckIsNotEmpty() {
    Round round = Round.newInstance(Owner.NOBODY);

    assertThat(round.getTrumpSuit()).isInstanceOf(Suit.class);
  }
}
