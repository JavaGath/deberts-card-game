package de.javagath.backend.game.model;

import java.lang.invoke.MethodHandles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Factory class to create different {@code Deck} implementations.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.0
 * @see CardDeck
 * @since 1.0
 */
public class DeckFactory {

  private static final Logger LOG =
      LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

  private DeckFactory() {}

  /**
   * Returns new Deck entity depends on the input parameter. If the input parameter belongs to the
   * player or bot method creates a HandDeck for the player with ownership. Otherwise, returns a
   * CardDeck
   *
   * @param owner owner of the generated deck
   * @return new Deck entity
   */
  public static Deck getDeck(Owner owner) {
    if (owner.equals(Owner.BOT) || owner.equals(Owner.PLAYER)) {
      LOG.debug("Try to create a HandDeck for: {}", owner);
      // ToDo: Implement HandDeck
      return null;
    } else {
      LOG.debug("Try to create a CardDeck");
      return new CardDeck();
    }
  }
}
