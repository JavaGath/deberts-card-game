package de.javagath.backend.game.model.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents all suits of cards.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.0
 * @since 1.0
 */
public enum Suit {
  @JsonProperty("diamonds")
  DIAMONDS(),
  @JsonProperty("hearts")
  HEARTS(),
  @JsonProperty("spades")
  SPADES(),
  @JsonProperty("clubs")
  CLUBS()
}
