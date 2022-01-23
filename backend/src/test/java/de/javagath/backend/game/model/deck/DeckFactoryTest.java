package de.javagath.backend.game.model.deck;

import static org.assertj.core.api.Assertions.assertThat;

import de.javagath.backend.game.model.enums.Owner;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DeckFactoryTest {

  @Test
  void createDeck_OwnerNobody_newCardDeck() {
    Deck deck = DeckFactory.getDeck(Owner.NOBODY);

    assertThat(deck).isInstanceOf(CardDeck.class);
  }

  @ParameterizedTest
  @EnumSource(
      value = Owner.class,
      names = {"BOT", "PLAYER"})
  void createDeck_OwnerBot_newHandDeck(Owner owner) {
    Deck deck = DeckFactory.getDeck(owner);

    assertThat(deck).isInstanceOf(HandDeck.class);
  }
}
