package de.javagath.backend.game.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import de.javagath.backend.game.model.deck.Card;
import de.javagath.backend.game.model.deck.Challenge;
import de.javagath.backend.game.model.enums.Owner;
import de.javagath.backend.game.model.enums.Suit;
import de.javagath.backend.game.model.enums.Value;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SuppressWarnings("javadoc")
@SpringBootTest
public class RoundTest {

  @Test
  void decideChallenge_decideChallengeInTradePhase_throwsIllegalStateException() {
    Card attackerCard = Card.newInstance(Suit.DIAMONDS, Value.NINE);
    Card defenderCard = Card.newInstance(Suit.DIAMONDS, Value.JACK);
    Owner attacker = Owner.PLAYER;
    Owner defender = Owner.BOT;
    Challenge<Card> challenge = new Challenge<>();
    challenge.setAttacker(attacker);
    challenge.setDefender(defender);
    challenge.setAttackerValue(attackerCard);
    challenge.setDefenderValue(defenderCard);
    Round round = Round.newInstance(Owner.NOBODY);

    assertThatThrownBy(() -> round.decideChallenge(challenge))
        .isInstanceOf(IllegalStateException.class);
  }

  @Test
  void switchTrumpSeven_switchTrumpSevenInTheRound_throwsIllegalStateException() {
    Round round = Round.newInstance(Owner.NOBODY);

    assertThatThrownBy(round::switchTrumpSeven).isInstanceOf(IllegalStateException.class);
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
