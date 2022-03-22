package de.javagath.backend.game.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import de.javagath.backend.game.model.deck.Card;
import de.javagath.backend.game.model.deck.Challenge;
import de.javagath.backend.game.model.deck.Deck;
import de.javagath.backend.game.model.enums.Owner;
import de.javagath.backend.game.model.enums.Suit;
import de.javagath.backend.game.model.enums.Value;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;

@SuppressWarnings("javadoc")
@SpringBootTest
public class PartyTest {

  @Test
  void newInstance_newPartyWithTheFirstRound_roundHasBeginner() {
    Party newParty = Party.newInstance();
    PartyInformation information = newParty.getPartyInformation();

    assertThat(information.getRoundInformation().getTurn()).isInstanceOf(Owner.class);
  }

  @Test
  void playTurn_secondRoundInTheParty_winnerDealsCards() {
    int expectedRoundNumber = 1;
    int expectedCardNumber = 12;
    Owner expectedWinner = Owner.PLAYER;
    Owner expectedLoser = Owner.BOT;
    Card winnerCard = Card.newInstance(Suit.DIAMONDS, Value.ACE);
    Card loserCard = Card.newInstance(Suit.DIAMONDS, Value.TEN);
    Party newParty = Party.newInstance();
    PartyInformation information = newParty.getPartyInformation();
    RoundInformation roundInformation = information.getRoundInformation();
    Deck playerDeck = information.getRoundInformation().getPlayerDeck();
    Deck botDeck = information.getRoundInformation().getBotDeck();

    newParty.playTrump(Suit.HEARTS, Owner.PLAYER);
    newParty.switchPhase();
    newParty.switchPhase();
    while (roundInformation.countHandCards() > 0) {
      playerDeck.dealRandomCard();
      botDeck.dealRandomCard();
    }
    playerDeck.addCard(winnerCard);
    botDeck.addCard(loserCard);
    roundInformation.addPoints(expectedWinner, 100);
    Challenge<Card> challenge = new Challenge<>();
    challenge.setAttacker(expectedWinner);
    challenge.setDefender(expectedLoser);
    challenge.setAttackerValue(winnerCard);
    challenge.setDefenderValue(loserCard);
    newParty.playTurn(challenge);

    assertThat(information.getRoundInformation().countHandCards()).isEqualTo(expectedCardNumber);
    assertThat(information.getRoundInformation().getTurn()).isEqualTo(expectedWinner);
    assertThat(information.getRoundScoreHistory().size()).isEqualTo(expectedRoundNumber);
  }

  @ParameterizedTest(name = "{index} => a={0}, b={1}")
  @CsvSource({"PLAYER, BOT", "BOT, PLAYER"})
  void playTurn_finalRoundInTheParty_playerWins(Owner winner, Owner loser) {
    int expectedRoundNumber = 1;
    int expectedCardNumber = 0;
    Card winnerCard = Card.newInstance(Suit.DIAMONDS, Value.ACE);
    Card loserCard = Card.newInstance(Suit.DIAMONDS, Value.TEN);
    Party newParty = Party.newInstance();
    PartyInformation information = newParty.getPartyInformation();
    RoundInformation roundInformation = information.getRoundInformation();
    Deck playerDeck = information.getRoundInformation().getPlayerDeck();
    Deck botDeck = information.getRoundInformation().getBotDeck();

    newParty.playTrump(Suit.HEARTS, Owner.PLAYER);
    newParty.switchPhase();
    newParty.switchPhase();
    while (roundInformation.countHandCards() > 0) {
      playerDeck.dealRandomCard();
      botDeck.dealRandomCard();
    }
    playerDeck.addCard(winnerCard);
    botDeck.addCard(loserCard);
    roundInformation.addPoints(winner, 502);
    Challenge<Card> challenge = new Challenge<>();
    challenge.setAttacker(winner);
    challenge.setDefender(loser);
    challenge.setAttackerValue(winnerCard);
    challenge.setDefenderValue(loserCard);
    newParty.playTurn(challenge);

    assertThat(information.getRoundInformation().countHandCards()).isEqualTo(expectedCardNumber);
    assertThat(information.getWinner()).isEqualTo(winner);
    assertThat(information.getRoundScoreHistory().size()).isEqualTo(expectedRoundNumber);
  }
}
