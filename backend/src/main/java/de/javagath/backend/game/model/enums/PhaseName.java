package de.javagath.backend.game.model.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents two phases of the {@code Round}.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.0
 * @since 1.0
 */
public enum PhaseName {
  @JsonProperty("action")
  ACTION(0),
  @JsonProperty("combo")
  COMBO(3),
  @JsonProperty("trade")
  TRADE(6);

  private final int value;

  PhaseName(int value) {
    this.value = value;
  }

  /**
   * Returns the int value of the {@code Phase}. {@code Int} value is necessary to determine how
   * much {@code Cards} should be given to players.
   *
   * @return {@code int} value of the {@code Phase}
   */
  public int getValue() {
    return value;
  }
}
