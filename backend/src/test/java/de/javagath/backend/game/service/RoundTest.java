package de.javagath.backend.game.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.javagath.backend.game.model.deck.Card;
import de.javagath.backend.game.model.deck.Challenge;
import de.javagath.backend.game.model.deck.Combination;
import de.javagath.backend.game.model.deck.Deck;
import de.javagath.backend.game.model.deck.DeckFactory;
import de.javagath.backend.game.model.deck.Trump;
import de.javagath.backend.game.model.enums.Owner;
import de.javagath.backend.game.model.enums.PhaseName;
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
  void switchTrumpSeven_switchTrumpSevenInTheTradePhase_throwsIllegalStateException() {
    Round round = Round.newInstance(Owner.NOBODY);

    assertThatThrownBy(round::switchTrumpSeven).isInstanceOf(IllegalStateException.class);
  }

  @Test
  void isSevenSwitchable_newRoundInTheTradePhase_false() {
    Round round = Round.newInstance(Owner.NOBODY);

    assertFalse(round.isSevenSwitchable());
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
  void switchTrumpSeven_newRoundActionPhaseBotFourSevens_throwsIllegalStateException() {
    Deck cardDeck = DeckFactory.getDeck(Owner.NOBODY);
    Card trumpCard = cardDeck.dealCard(Suit.DIAMONDS, Value.JACK);
    Deck botDeck = DeckFactory.getDeck(Owner.BOT);
    botDeck.addCard(cardDeck.dealCard(Suit.DIAMONDS, Value.SEVEN));
    botDeck.addCard(cardDeck.dealCard(Suit.SPADES, Value.SEVEN));
    botDeck.addCard(cardDeck.dealCard(Suit.CLUBS, Value.SEVEN));
    botDeck.addCard(cardDeck.dealCard(Suit.HEARTS, Value.SEVEN));
    botDeck.addCard(cardDeck.dealRandomCard());
    botDeck.addCard(cardDeck.dealRandomCard());
    botDeck.addCard(cardDeck.dealRandomCard());
    botDeck.addCard(cardDeck.dealRandomCard());
    botDeck.addCard(cardDeck.dealRandomCard());
    Deck playerDeck = DeckFactory.getDeck(Owner.PLAYER);
    playerDeck.addCard(cardDeck.dealRandomCard());
    playerDeck.addCard(cardDeck.dealRandomCard());
    playerDeck.addCard(cardDeck.dealRandomCard());
    playerDeck.addCard(cardDeck.dealRandomCard());
    playerDeck.addCard(cardDeck.dealRandomCard());
    playerDeck.addCard(cardDeck.dealRandomCard());
    playerDeck.addCard(cardDeck.dealRandomCard());
    playerDeck.addCard(cardDeck.dealRandomCard());
    playerDeck.addCard(cardDeck.dealRandomCard());
    RoundInformation newInformation =
        RoundInformation.builder()
            .cardDeck(cardDeck)
            .playerDeck(playerDeck)
            .botDeck(botDeck)
            .trumpDeck(Trump.newInstance(trumpCard))
            .trumpChangePossible(true)
            .build();

    Round round = Round.newInstance(newInformation, PhaseName.ACTION);

    assertThatThrownBy(round::switchTrumpSeven).isInstanceOf(IllegalStateException.class);
  }

  @Test
  void isSevenSwitchable_newRoundActionPhaseBotFourSevens_false() {
    Deck cardDeck = DeckFactory.getDeck(Owner.NOBODY);
    Card trumpCard = cardDeck.dealCard(Suit.DIAMONDS, Value.JACK);
    Deck botDeck = DeckFactory.getDeck(Owner.BOT);
    botDeck.addCard(cardDeck.dealCard(Suit.DIAMONDS, Value.SEVEN));
    botDeck.addCard(cardDeck.dealCard(Suit.SPADES, Value.SEVEN));
    botDeck.addCard(cardDeck.dealCard(Suit.CLUBS, Value.SEVEN));
    botDeck.addCard(cardDeck.dealCard(Suit.HEARTS, Value.SEVEN));
    botDeck.addCard(cardDeck.dealRandomCard());
    botDeck.addCard(cardDeck.dealRandomCard());
    botDeck.addCard(cardDeck.dealRandomCard());
    botDeck.addCard(cardDeck.dealRandomCard());
    botDeck.addCard(cardDeck.dealRandomCard());
    Deck playerDeck = DeckFactory.getDeck(Owner.PLAYER);
    playerDeck.addCard(cardDeck.dealRandomCard());
    playerDeck.addCard(cardDeck.dealRandomCard());
    playerDeck.addCard(cardDeck.dealRandomCard());
    playerDeck.addCard(cardDeck.dealRandomCard());
    playerDeck.addCard(cardDeck.dealRandomCard());
    playerDeck.addCard(cardDeck.dealRandomCard());
    playerDeck.addCard(cardDeck.dealRandomCard());
    playerDeck.addCard(cardDeck.dealRandomCard());
    playerDeck.addCard(cardDeck.dealRandomCard());
    RoundInformation newInformation =
        RoundInformation.builder()
            .cardDeck(cardDeck)
            .playerDeck(playerDeck)
            .botDeck(botDeck)
            .trumpDeck(Trump.newInstance(trumpCard))
            .trumpChangePossible(true)
            .build();

    Round round = Round.newInstance(newInformation, PhaseName.ACTION);

    assertThat(round.isSevenSwitchable()).isEqualTo(Boolean.FALSE);
  }

  @Test
  void isFourSevenResettable_newRoundTradePhaseBotFourSevens_true() {
    Deck cardDeck = DeckFactory.getDeck(Owner.NOBODY);
    Card trumpCard = cardDeck.dealCard(Suit.DIAMONDS, Value.JACK);
    Deck botDeck = DeckFactory.getDeck(Owner.BOT);
    botDeck.addCard(cardDeck.dealCard(Suit.DIAMONDS, Value.SEVEN));
    botDeck.addCard(cardDeck.dealCard(Suit.SPADES, Value.SEVEN));
    botDeck.addCard(cardDeck.dealCard(Suit.CLUBS, Value.SEVEN));
    botDeck.addCard(cardDeck.dealCard(Suit.HEARTS, Value.SEVEN));
    botDeck.addCard(cardDeck.dealRandomCard());
    botDeck.addCard(cardDeck.dealRandomCard());
    Deck playerDeck = DeckFactory.getDeck(Owner.PLAYER);
    playerDeck.addCard(cardDeck.dealRandomCard());
    playerDeck.addCard(cardDeck.dealRandomCard());
    playerDeck.addCard(cardDeck.dealRandomCard());
    playerDeck.addCard(cardDeck.dealRandomCard());
    playerDeck.addCard(cardDeck.dealRandomCard());
    playerDeck.addCard(cardDeck.dealRandomCard());
    RoundInformation newInformation =
        RoundInformation.builder()
            .cardDeck(cardDeck)
            .playerDeck(playerDeck)
            .botDeck(botDeck)
            .trumpDeck(Trump.newInstance(trumpCard))
            .trumpChangePossible(true)
            .build();

    Round round = Round.newInstance(newInformation, PhaseName.TRADE);

    assertThat(round.isFourSevenResettable()).isEqualTo(Boolean.TRUE);
  }

  @Test
  void switchTrumpSeven_newRoundComboPhasePlayerFourSevens_switchOk() {
    Deck cardDeck = DeckFactory.getDeck(Owner.NOBODY);
    Card trumpCard = cardDeck.dealCard(Suit.DIAMONDS, Value.JACK);
    Deck playerDeck = DeckFactory.getDeck(Owner.PLAYER);
    playerDeck.addCard(cardDeck.dealCard(Suit.DIAMONDS, Value.SEVEN));
    playerDeck.addCard(cardDeck.dealCard(Suit.SPADES, Value.SEVEN));
    playerDeck.addCard(cardDeck.dealCard(Suit.CLUBS, Value.SEVEN));
    playerDeck.addCard(cardDeck.dealCard(Suit.HEARTS, Value.SEVEN));
    playerDeck.addCard(cardDeck.dealRandomCard());
    playerDeck.addCard(cardDeck.dealRandomCard());
    playerDeck.addCard(cardDeck.dealRandomCard());
    playerDeck.addCard(cardDeck.dealRandomCard());
    playerDeck.addCard(cardDeck.dealRandomCard());
    Deck botDeck = DeckFactory.getDeck(Owner.BOT);
    botDeck.addCard(cardDeck.dealRandomCard());
    botDeck.addCard(cardDeck.dealRandomCard());
    botDeck.addCard(cardDeck.dealRandomCard());
    botDeck.addCard(cardDeck.dealRandomCard());
    botDeck.addCard(cardDeck.dealRandomCard());
    botDeck.addCard(cardDeck.dealRandomCard());
    botDeck.addCard(cardDeck.dealRandomCard());
    botDeck.addCard(cardDeck.dealRandomCard());
    botDeck.addCard(cardDeck.dealRandomCard());
    RoundInformation newInformation =
        RoundInformation.builder()
            .cardDeck(cardDeck)
            .playerDeck(playerDeck)
            .botDeck(botDeck)
            .trumpDeck(Trump.newInstance(trumpCard))
            .trumpChangePossible(true)
            .build();

    Round round = Round.newInstance(newInformation, PhaseName.COMBO);
    round.switchTrumpSeven();

    assertTrue(playerDeck.contains(trumpCard));
  }

  @Test
  void isSevenSwitchable_newRoundComboPhasePlayerFourSevens_true() {
    Deck cardDeck = DeckFactory.getDeck(Owner.NOBODY);
    Card trumpCard = cardDeck.dealCard(Suit.DIAMONDS, Value.JACK);
    Deck playerDeck = DeckFactory.getDeck(Owner.PLAYER);
    playerDeck.addCard(cardDeck.dealCard(Suit.DIAMONDS, Value.SEVEN));
    playerDeck.addCard(cardDeck.dealCard(Suit.SPADES, Value.SEVEN));
    playerDeck.addCard(cardDeck.dealCard(Suit.CLUBS, Value.SEVEN));
    playerDeck.addCard(cardDeck.dealCard(Suit.HEARTS, Value.SEVEN));
    playerDeck.addCard(cardDeck.dealRandomCard());
    playerDeck.addCard(cardDeck.dealRandomCard());
    playerDeck.addCard(cardDeck.dealRandomCard());
    playerDeck.addCard(cardDeck.dealRandomCard());
    playerDeck.addCard(cardDeck.dealRandomCard());
    Deck botDeck = DeckFactory.getDeck(Owner.BOT);
    botDeck.addCard(cardDeck.dealRandomCard());
    botDeck.addCard(cardDeck.dealRandomCard());
    botDeck.addCard(cardDeck.dealRandomCard());
    botDeck.addCard(cardDeck.dealRandomCard());
    botDeck.addCard(cardDeck.dealRandomCard());
    botDeck.addCard(cardDeck.dealRandomCard());
    botDeck.addCard(cardDeck.dealRandomCard());
    botDeck.addCard(cardDeck.dealRandomCard());
    botDeck.addCard(cardDeck.dealRandomCard());
    RoundInformation newInformation =
        RoundInformation.builder()
            .cardDeck(cardDeck)
            .playerDeck(playerDeck)
            .botDeck(botDeck)
            .trumpDeck(Trump.newInstance(trumpCard))
            .trumpChangePossible(true)
            .build();

    Round round = Round.newInstance(newInformation, PhaseName.COMBO);

    assertTrue(round.isSevenSwitchable());
  }

  @Test
  void isFourSevenResettable_newRoundComboPhasePlayerFourSevens_true() {
    Deck cardDeck = DeckFactory.getDeck(Owner.NOBODY);
    Card trumpCard = cardDeck.dealCard(Suit.DIAMONDS, Value.JACK);
    Deck playerDeck = DeckFactory.getDeck(Owner.PLAYER);
    playerDeck.addCard(cardDeck.dealCard(Suit.DIAMONDS, Value.SEVEN));
    playerDeck.addCard(cardDeck.dealCard(Suit.SPADES, Value.SEVEN));
    playerDeck.addCard(cardDeck.dealCard(Suit.CLUBS, Value.SEVEN));
    playerDeck.addCard(cardDeck.dealCard(Suit.HEARTS, Value.SEVEN));
    playerDeck.addCard(cardDeck.dealRandomCard());
    playerDeck.addCard(cardDeck.dealRandomCard());
    playerDeck.addCard(cardDeck.dealRandomCard());
    playerDeck.addCard(cardDeck.dealRandomCard());
    playerDeck.addCard(cardDeck.dealRandomCard());
    Deck botDeck = DeckFactory.getDeck(Owner.BOT);
    botDeck.addCard(cardDeck.dealRandomCard());
    botDeck.addCard(cardDeck.dealRandomCard());
    botDeck.addCard(cardDeck.dealRandomCard());
    botDeck.addCard(cardDeck.dealRandomCard());
    botDeck.addCard(cardDeck.dealRandomCard());
    botDeck.addCard(cardDeck.dealRandomCard());
    botDeck.addCard(cardDeck.dealRandomCard());
    botDeck.addCard(cardDeck.dealRandomCard());
    botDeck.addCard(cardDeck.dealRandomCard());
    RoundInformation newInformation =
        RoundInformation.builder()
            .cardDeck(cardDeck)
            .playerDeck(playerDeck)
            .botDeck(botDeck)
            .trumpDeck(Trump.newInstance(trumpCard))
            .trumpChangePossible(true)
            .build();

    Round round = Round.newInstance(newInformation, PhaseName.COMBO);

    assertTrue(round.isFourSevenResettable());
  }
}
