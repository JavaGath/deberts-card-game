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
  void decideChallenge_inComboPhaseAttackerHasTertz_challengeGives20Points() {
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

    round.playTrump(Suit.HEARTS, Owner.PLAYER);
    round.switchPhase();
    round.decideChallenge(challenge);

    assertThat(round.getCombinationPoints(attacker)).isEqualTo(expectedPoints);
  }

  @Test
  void decideChallenge_inActionPhasePlayerDiamondAceBotDiamondTenLastCard_playerWins31Points() {
    int expectedPoints = 31;
    Owner attacker = Owner.PLAYER;
    Owner defender = Owner.BOT;
    Deck cardDeck = DeckFactory.getDeck(Owner.NOBODY);
    Card trumpCard = cardDeck.dealCard(Suit.DIAMONDS, Value.JACK);
    Deck botDeck = DeckFactory.getDeck(defender);
    botDeck.addCard(cardDeck.dealCard(Suit.DIAMONDS, Value.TEN));
    Deck playerDeck = DeckFactory.getDeck(attacker);
    playerDeck.addCard(cardDeck.dealCard(Suit.DIAMONDS, Value.ACE));
    RoundInformation newInformation =
        RoundInformation.builder()
            .cardDeck(cardDeck)
            .playerDeck(playerDeck)
            .botDeck(botDeck)
            .trumpDeck(Trump.newInstance(trumpCard))
            .trumpChangePossible(false)
            .build();
    Challenge<Card> challenge = new Challenge<>();
    challenge.setAttacker(attacker);
    challenge.setDefender(defender);
    challenge.setAttackerValue(Card.newInstance(Suit.DIAMONDS, Value.ACE));
    challenge.setDefenderValue(Card.newInstance(Suit.DIAMONDS, Value.TEN));

    Round round = Round.newInstance(newInformation, PhaseName.ACTION);
    round.decideChallenge(challenge);

    assertThat(round.getPoints(attacker)).isEqualTo(expectedPoints);
  }

  @Test
  void decideChallenge_inActionPhasePlayerSpadesKingBotSpadesTenNotLastCard_botWins14Points() {
    int expectedPoints = 14;
    Owner attacker = Owner.BOT;
    Owner defender = Owner.PLAYER;
    Deck cardDeck = DeckFactory.getDeck(Owner.NOBODY);
    Card trumpCard = cardDeck.dealCard(Suit.DIAMONDS, Value.JACK);
    Deck botDeck = DeckFactory.getDeck(attacker);
    botDeck.addCard(cardDeck.dealCard(Suit.SPADES, Value.TEN));
    Deck playerDeck = DeckFactory.getDeck(defender);
    playerDeck.addCard(cardDeck.dealCard(Suit.SPADES, Value.KING));
    botDeck.addCard(cardDeck.dealRandomCard());
    playerDeck.addCard(cardDeck.dealRandomCard());
    RoundInformation newInformation =
        RoundInformation.builder()
            .cardDeck(cardDeck)
            .playerDeck(playerDeck)
            .botDeck(botDeck)
            .trumpDeck(Trump.newInstance(trumpCard))
            .trumpChangePossible(false)
            .build();
    Challenge<Card> challenge = new Challenge<>();
    challenge.setAttacker(attacker);
    challenge.setDefender(defender);
    challenge.setAttackerValue(Card.newInstance(Suit.SPADES, Value.TEN));
    challenge.setDefenderValue(Card.newInstance(Suit.SPADES, Value.KING));

    Round round = Round.newInstance(newInformation, PhaseName.ACTION);
    round.decideChallenge(challenge);

    assertThat(round.getPoints(attacker)).isEqualTo(expectedPoints);
  }

  @Test
  void sumUp_playerPickedOwnTrumpLost_botGetsAllPointsWins14Points() {
    int expectedPoints = 27;
    Deck cardDeck = DeckFactory.getDeck(Owner.NOBODY);
    Card trumpCard = cardDeck.dealCard(Suit.DIAMONDS, Value.JACK);
    Card tradedTrump = cardDeck.dealCard(Suit.HEARTS, Value.JACK);
    Trump trumpDeck = Trump.newInstance(trumpCard);
    trumpDeck.setTradedTrump(tradedTrump);
    Deck botDeck = DeckFactory.getDeck(Owner.BOT);
    botDeck.addCard(cardDeck.dealCard(Suit.SPADES, Value.TEN));
    botDeck.addCard(cardDeck.dealCard(Suit.SPADES, Value.QUEEN));
    Deck playerDeck = DeckFactory.getDeck(Owner.PLAYER);
    playerDeck.addCard(cardDeck.dealCard(Suit.SPADES, Value.KING));
    playerDeck.addCard(cardDeck.dealCard(Suit.SPADES, Value.SEVEN));
    RoundInformation newInformation =
        RoundInformation.builder()
            .cardDeck(cardDeck)
            .playerDeck(playerDeck)
            .botDeck(botDeck)
            .trumpDeck(trumpDeck)
            .trumpPicker(Owner.PLAYER)
            .trumpChangePossible(false)
            .build();
    Challenge<Card> firstChallenge = new Challenge<>();
    firstChallenge.setAttacker(Owner.BOT);
    firstChallenge.setDefender(Owner.PLAYER);
    firstChallenge.setAttackerValue(Card.newInstance(Suit.SPADES, Value.QUEEN));
    firstChallenge.setDefenderValue(Card.newInstance(Suit.SPADES, Value.KING));
    Challenge<Card> secondChallenge = new Challenge<>();
    secondChallenge.setAttacker(Owner.PLAYER);
    secondChallenge.setDefender(Owner.BOT);
    secondChallenge.setAttackerValue(Card.newInstance(Suit.SPADES, Value.SEVEN));
    secondChallenge.setDefenderValue(Card.newInstance(Suit.SPADES, Value.TEN));

    Round round = Round.newInstance(newInformation, PhaseName.ACTION);
    round.decideChallenge(firstChallenge);
    round.decideChallenge(secondChallenge);
    round.sumUp();

    assertThat(round.getPoints(Owner.BOT)).isEqualTo(expectedPoints);
  }

  @Test
  void sumUp_playerPickedOwnTrumpEqualPoints_playerAndBotHave10Points() {
    int expectedPoints = 10;
    Deck cardDeck = DeckFactory.getDeck(Owner.NOBODY);
    Card trumpCard = cardDeck.dealCard(Suit.DIAMONDS, Value.JACK);
    Card tradedTrump = cardDeck.dealCard(Suit.HEARTS, Value.JACK);
    Trump trumpDeck = Trump.newInstance(trumpCard);
    trumpDeck.setTradedTrump(tradedTrump);
    Deck botDeck = DeckFactory.getDeck(Owner.BOT);
    botDeck.addCard(cardDeck.dealCard(Suit.SPADES, Value.TEN));
    botDeck.addCard(cardDeck.dealCard(Suit.SPADES, Value.SEVEN));
    Deck playerDeck = DeckFactory.getDeck(Owner.PLAYER);
    playerDeck.addCard(cardDeck.dealCard(Suit.SPADES, Value.NINE));
    playerDeck.addCard(cardDeck.dealCard(Suit.SPADES, Value.EIGHT));
    RoundInformation newInformation =
        RoundInformation.builder()
            .cardDeck(cardDeck)
            .playerDeck(playerDeck)
            .botDeck(botDeck)
            .trumpDeck(trumpDeck)
            .trumpPicker(Owner.PLAYER)
            .trumpChangePossible(false)
            .build();
    Challenge<Card> firstChallenge = new Challenge<>();
    firstChallenge.setAttacker(Owner.BOT);
    firstChallenge.setDefender(Owner.PLAYER);
    firstChallenge.setAttackerValue(Card.newInstance(Suit.SPADES, Value.TEN));
    firstChallenge.setDefenderValue(Card.newInstance(Suit.SPADES, Value.NINE));
    Challenge<Card> secondChallenge = new Challenge<>();
    secondChallenge.setAttacker(Owner.PLAYER);
    secondChallenge.setDefender(Owner.BOT);
    secondChallenge.setAttackerValue(Card.newInstance(Suit.SPADES, Value.EIGHT));
    secondChallenge.setDefenderValue(Card.newInstance(Suit.SPADES, Value.SEVEN));

    Round round = Round.newInstance(newInformation, PhaseName.ACTION);
    round.decideChallenge(firstChallenge);
    round.decideChallenge(secondChallenge);
    round.sumUp();

    assertThat(round.getPoints(Owner.BOT)).isEqualTo(expectedPoints);
    assertThat(round.getPoints(Owner.PLAYER)).isEqualTo(expectedPoints);
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
  void getTrumpSuit_newRoundWithDiamondsSuit_returnsDiamonds() {
    Round round = Round.newInstance(Owner.NOBODY);
    round.playTrump(Suit.DIAMONDS, Owner.PLAYER);
    round.switchPhase();

    assertThat(round.getTrumpSuit()).isEqualTo(Suit.DIAMONDS);
  }

  @Test
  void isOver_newRound_false() {
    Round round = Round.newInstance(Owner.NOBODY);

    assertFalse(round.isOver());
  }

  @Test
  void newInstance_newRound_eachPlayerHas6CardsInTheHand() {
    int expectedCardNumber = 12;

    Round round = Round.newInstance(Owner.NOBODY);

    assertThat(round.countHandCards()).isEqualTo(expectedCardNumber);
  }

  @Test
  void switchPhase_inActionPhase_throwsIllegalStateException() {
    Round round = Round.newInstance(Owner.NOBODY);

    round.playTrump(Suit.HEARTS, Owner.PLAYER);
    round.switchPhase();
    round.switchPhase();

    assertThatThrownBy(round::switchPhase).isInstanceOf(IllegalStateException.class);
  }

  @Test
  void switchPhase_inTradePhaseNotPickedTrump_throwsIllegalStateException() {
    Round round = Round.newInstance(Owner.NOBODY);

    assertThatThrownBy(round::switchPhase).isInstanceOf(IllegalStateException.class);
  }

  @Test
  void playTrump_inActionPhase_throwsIllegalStateException() {
    Round round = Round.newInstance(Owner.NOBODY);

    round.playTrump(Suit.HEARTS, Owner.PLAYER);
    round.switchPhase();
    round.switchPhase();

    assertThatThrownBy(() -> round.playTrump(Suit.HEARTS, Owner.PLAYER))
        .isInstanceOf(IllegalStateException.class);
  }

  @Test
  void isFourSevenResettable_inActionPhase_false() {
    Round round = Round.newInstance(Owner.NOBODY);

    round.playTrump(Suit.HEARTS, Owner.PLAYER);
    round.switchPhase();
    round.switchPhase();

    assertFalse(round.isFourSevenResettable());
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
  void resetCauseFourSevens_newRoundTradePhaseBotFourSevens_newRound() {
    int cardNumberTradePhase = 12;
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

    Round round = Round.newInstance(newInformation, PhaseName.COMBO);
    round.resetCauseFourSevens();

    assertThat(round.countHandCards()).isEqualTo(cardNumberTradePhase);
  }

  @Test
  void resetCauseFourSevens_newRoundTradePhaseBotThreeSevens_throwsIllegalStateException() {
    Deck cardDeck = DeckFactory.getDeck(Owner.NOBODY);
    Card trumpCard = cardDeck.dealCard(Suit.DIAMONDS, Value.JACK);
    Deck botDeck = DeckFactory.getDeck(Owner.BOT);
    botDeck.addCard(cardDeck.dealCard(Suit.DIAMONDS, Value.SEVEN));
    botDeck.addCard(cardDeck.dealCard(Suit.SPADES, Value.SEVEN));
    botDeck.addCard(cardDeck.dealCard(Suit.CLUBS, Value.SEVEN));
    Deck playerDeck = DeckFactory.getDeck(Owner.PLAYER);
    playerDeck.addCard(cardDeck.dealCard(Suit.HEARTS, Value.SEVEN));
    playerDeck.addCard(cardDeck.dealRandomCard());
    playerDeck.addCard(cardDeck.dealRandomCard());
    playerDeck.addCard(cardDeck.dealRandomCard());
    playerDeck.addCard(cardDeck.dealRandomCard());
    playerDeck.addCard(cardDeck.dealRandomCard());
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

    assertThatThrownBy(round::resetCauseFourSevens).isInstanceOf(IllegalStateException.class);
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
