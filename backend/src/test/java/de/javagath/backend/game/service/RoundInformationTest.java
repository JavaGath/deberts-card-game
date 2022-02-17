package de.javagath.backend.game.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

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
public class RoundInformationTest {

  @Test
  void playTrump_playSpadesAllSpadesInHands_dummySpadesAce() {
    Deck cardDeck = DeckFactory.getDeck(Owner.NOBODY);
    Card trumpCard = cardDeck.dealCard(Suit.DIAMONDS, Value.JACK);
    Deck botDeck = DeckFactory.getDeck(Owner.BOT);
    botDeck.addCard(cardDeck.dealCard(Suit.SPADES, Value.SEVEN));
    botDeck.addCard(cardDeck.dealCard(Suit.SPADES, Value.EIGHT));
    botDeck.addCard(cardDeck.dealCard(Suit.SPADES, Value.NINE));
    botDeck.addCard(cardDeck.dealCard(Suit.SPADES, Value.TEN));
    botDeck.addCard(cardDeck.dealCard(Suit.SPADES, Value.JACK));
    botDeck.addCard(cardDeck.dealCard(Suit.SPADES, Value.QUEEN));
    Deck playerDeck = DeckFactory.getDeck(Owner.PLAYER);
    playerDeck.addCard(cardDeck.dealCard(Suit.SPADES, Value.KING));
    playerDeck.addCard(cardDeck.dealCard(Suit.SPADES, Value.ACE));
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
            .beginner(Owner.PLAYER)
            .build();

    Round round = Round.newInstance(newInformation, PhaseName.TRADE);
    round.playTrump(Suit.SPADES, Owner.PLAYER);
    round.switchPhase();

    assertThat(newInformation.getBeginner()).isEqualTo(Owner.PLAYER);
    assertThat(newInformation.getPlayerDeck(Owner.PLAYER)).isEqualTo(playerDeck);
    assertThat(newInformation.getPlayerDeck(Owner.BOT)).isEqualTo(botDeck);
    assertThat(newInformation.getCardDeck()).isEqualTo(cardDeck);
    assertThat(newInformation.getPhaseName()).isEqualTo(PhaseName.COMBO);
    assertThat(newInformation.getTrumpDeck().getTrumpCard())
        .isEqualTo(Card.newInstance(Suit.SPADES, Value.ACE));
  }

  @Test
  void playTrump_trumpChangeIsNotPossible_throwsIllegalStateException() {
    Deck cardDeck = DeckFactory.getDeck(Owner.NOBODY);
    Card trumpCard = cardDeck.dealCard(Suit.DIAMONDS, Value.JACK);
    Deck botDeck = DeckFactory.getDeck(Owner.BOT);
    botDeck.addCard(cardDeck.dealCard(Suit.SPADES, Value.SEVEN));
    botDeck.addCard(cardDeck.dealCard(Suit.SPADES, Value.EIGHT));
    botDeck.addCard(cardDeck.dealCard(Suit.SPADES, Value.NINE));
    botDeck.addCard(cardDeck.dealCard(Suit.SPADES, Value.TEN));
    botDeck.addCard(cardDeck.dealCard(Suit.SPADES, Value.JACK));
    botDeck.addCard(cardDeck.dealCard(Suit.SPADES, Value.QUEEN));
    Deck playerDeck = DeckFactory.getDeck(Owner.PLAYER);
    playerDeck.addCard(cardDeck.dealCard(Suit.SPADES, Value.KING));
    playerDeck.addCard(cardDeck.dealCard(Suit.SPADES, Value.ACE));
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
            .trumpChangePossible(false)
            .build();

    assertThatThrownBy(() -> newInformation.playTrump(Suit.CLUBS, Owner.PLAYER))
        .isInstanceOf(IllegalStateException.class);
  }

  @Test
  void getPlayerDeck_getDeckForOwnerNobody_throwsIllegalArgumentException() {
    Deck cardDeck = DeckFactory.getDeck(Owner.NOBODY);
    Card trumpCard = cardDeck.dealCard(Suit.DIAMONDS, Value.JACK);
    Deck botDeck = DeckFactory.getDeck(Owner.BOT);
    botDeck.addCard(cardDeck.dealRandomCard());
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

    RoundInformation newInformation =
        RoundInformation.builder()
            .cardDeck(cardDeck)
            .playerDeck(playerDeck)
            .botDeck(botDeck)
            .trumpDeck(Trump.newInstance(trumpCard))
            .trumpChangePossible(false)
            .build();

    assertThatThrownBy(() -> newInformation.getPlayerDeck(Owner.NOBODY))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void switchTrumpSeven_playerAndBotWithoutClubsSeven_throwsIllegalStateException() {
    Deck cardDeck = DeckFactory.getDeck(Owner.NOBODY);
    Card trumpCard = cardDeck.dealCard(Suit.CLUBS, Value.JACK);
    Deck botDeck = DeckFactory.getDeck(Owner.BOT);
    botDeck.addCard(cardDeck.dealCard(Suit.SPADES, Value.SEVEN));
    botDeck.addCard(cardDeck.dealCard(Suit.SPADES, Value.EIGHT));
    botDeck.addCard(cardDeck.dealCard(Suit.SPADES, Value.NINE));
    botDeck.addCard(cardDeck.dealCard(Suit.SPADES, Value.TEN));
    botDeck.addCard(cardDeck.dealCard(Suit.SPADES, Value.JACK));
    botDeck.addCard(cardDeck.dealCard(Suit.SPADES, Value.QUEEN));
    Deck playerDeck = DeckFactory.getDeck(Owner.PLAYER);
    playerDeck.addCard(cardDeck.dealCard(Suit.SPADES, Value.KING));
    playerDeck.addCard(cardDeck.dealCard(Suit.SPADES, Value.ACE));
    playerDeck.addCard(cardDeck.dealCard(Suit.DIAMONDS, Value.SEVEN));
    playerDeck.addCard(cardDeck.dealCard(Suit.DIAMONDS, Value.EIGHT));
    playerDeck.addCard(cardDeck.dealCard(Suit.DIAMONDS, Value.NINE));
    playerDeck.addCard(cardDeck.dealCard(Suit.DIAMONDS, Value.TEN));

    RoundInformation newInformation =
        RoundInformation.builder()
            .cardDeck(cardDeck)
            .playerDeck(playerDeck)
            .botDeck(botDeck)
            .trumpDeck(Trump.newInstance(trumpCard))
            .trumpChangePossible(true)
            .beginner(Owner.PLAYER)
            .build();

    assertThatThrownBy(newInformation::switchTrumpSeven).isInstanceOf(IllegalStateException.class);
  }

  @Test
  void getCombinationScore_playerWithTertzBotEmpty_player20Points() {
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
    round.switchPhase();

    assertThat(round.getInformation().getCombinationScore().getPoints(attacker))
        .isEqualTo(expectedPoints);
  }
}
