package de.javagath.backend.game.service.phase;

import de.javagath.backend.game.model.deck.Card;
import de.javagath.backend.game.model.deck.Challenge;
import de.javagath.backend.game.model.deck.Deck;
import de.javagath.backend.game.model.enums.Owner;
import de.javagath.backend.game.model.enums.Suit;
import de.javagath.backend.game.service.RoundInformation;

/**
 * The state entity to help the class {@code Round} to manage different phases in it. Each phase has
 * Actions to switch it to the next one, decide a {@code Challenge} between the players, play trump,
 * get {@code Round Information} or play switch trump seven.
 *
 * <p>In the action phase each player has at the beginning 9 cards and plays it out in each turn. In
 * the current phase methods: switchPhase(), switchTrumpSeven() and playTrump() are not available.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.0
 * @since 1.0
 */
public class Action implements Phase {

  private final RoundInformation information;

  Action(RoundInformation information) {
    this.information = information;
  }

  @Override
  public Phase switchPhase() {
    throw new IllegalStateException("Action is the last phase of the round.");
  }

  @Override
  public void decideChallenge(Challenge<?> challenge) {
    Owner winner = challenge.getWinner(information.getTrumpSuit());
    Card attackerCard = (Card) challenge.getAttackerValue();
    Card defenderCard = (Card) challenge.getDefenderValue();
    removePlayedCards(attackerCard, defenderCard);

    int points = challenge.getPoints(information.getTrumpSuit());
    points = addLastHandPoints(points);
    information.addPoints(winner, points);
  }

  @Override
  public void switchTrumpSeven() {
    throw new IllegalStateException("The trump seven can not be switched in the current phase");
  }

  @Override
  public void playTrump(Suit suit, Owner picker) {
    throw new IllegalStateException("The trump can not be played in the current phase");
  }

  private void removePlayedCards(Card attacker, Card defender) {
    Deck playerDeck = information.getPlayerDeck();
    Deck botDeck = information.getBotDeck();
    if (playerDeck.contains(attacker)) {
      playerDeck.dealCard(attacker.getSuit(), attacker.getValue());
      botDeck.dealCard(defender.getSuit(), defender.getValue());
    } else {
      playerDeck.dealCard(defender.getSuit(), defender.getValue());
      botDeck.dealCard(attacker.getSuit(), attacker.getValue());
    }
  }

  private int addLastHandPoints(int points) {
    return information.countHandCards() == 0 ? points + 10 : points;
  }
}
