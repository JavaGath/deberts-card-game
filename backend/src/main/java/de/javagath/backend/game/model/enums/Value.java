package de.javagath.backend.game.model.enums;

/**
 * Represents all relevant face values of cards.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.0
 * @since 1.0
 */
public enum Value {
  SEVEN(0, 0, 0),
  EIGHT(0, 0, 1),
  NINE(0, 14, 2),
  TEN(10, 10, 3),
  JACK(2, 20, 4),
  QUEEN(3, 3, 5),
  KING(4, 4, 6),
  ACE(11, 11, 7);

  private final int standardPoints;
  private final int trumpPoints;
  private final int position;

  Value(int standardPoints, int trumpPoints, int position) {
    this.standardPoints = standardPoints;
    this.trumpPoints = trumpPoints;
    this.position = position;
  }

  /**
   * Returns points of the card {@code FaceValue}. Each {@code FaceValue} has a pair of points,
   * first one is for the standard suit, the second one is for the trump. trump variable determines
   * which points will be used.
   *
   * @param trump determines which points should be returned.
   * @return points of the {@code FaceValue}
   */
  public int getPoints(boolean trump) {
    return trump ? trumpPoints : standardPoints;
  }

  /**
   * Returns position of the card {@code FaceValue}.
   *
   * @return position
   */
  public int getPosition() {
    return position;
  }
}
