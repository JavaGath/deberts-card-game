package de.javagath.backend.game.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import de.javagath.backend.game.model.deck.Deck;
import de.javagath.backend.game.model.enums.Owner;
import de.javagath.backend.game.model.enums.Suit;
import org.junit.jupiter.api.Test;
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
    int expectedCardNumber = 12;
    Owner expectedWinner = Owner.PLAYER;
    Party newParty = Party.newInstance();
    PartyInformation information = newParty.getPartyInformation();
    RoundInformation roundInformation = information.getRoundInformation();
    Deck playerDeck = information.getRoundInformation().getPlayerDeck();
    Deck botDeck = information.getRoundInformation().getBotDeck();

    newParty.playTrump(Suit.HEARTS, Owner.PLAYER);
    newParty.switchPhase();
    newParty.switchPhase();
    while (roundInformation.countHandCards() != 0) {
      playerDeck.dealRandomCard();
      botDeck.dealRandomCard();
    }
    roundInformation.addPoints(expectedWinner, 100);
    newParty.endRound();
    assertThat(information.getRoundInformation().countHandCards()).isEqualTo(expectedCardNumber);
    assertThat(information.getRoundInformation().getTurn()).isEqualTo(expectedWinner);
  }
}
