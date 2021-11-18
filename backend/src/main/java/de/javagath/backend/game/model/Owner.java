package de.javagath.backend.game.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Arrays;

/**
 * Represents all owners for {@code Decks}.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.0
 * @since 1.0
 */
enum Owner {
  @JsonProperty("deck")
  NOBODY(0),
  @JsonProperty("bot")
  BOT(1),
  @JsonProperty("player")
  PLAYER(2),
  @JsonProperty("trump")
  TRUMP(3);

  private final int value;

  Owner(int value) {
    this.value = value;
  }

  /**
   * Returns an {@code Owner} by int value. Int value is used to determine random {@code Owner}.
   *
   * @param value {@code int} value of the {@code Owner}
   * @return {@code Owner}
   */
  public static Owner getOwnerByValue(int value) {
    return Arrays.stream(values()).filter(owner -> owner.value == value).findFirst().orElse(null);
  }
}
