package de.javagath.backend.web.model;

import de.javagath.backend.db.model.UserEntity;

/**
 * DTO for communication with frontend. Contains important information from {@code UserEntity} and
 * generated JWT.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.0
 * @since 1.0
 */
public class UserDto implements Response {

  private final String name;
  private final String email;
  private final String lastGameResult;
  private final int totalWins;
  private final int totalLoses;
  private final long winRate;
  private final int actualWinStreak;
  private final int bestWinSteak;
  private final String token;
  private String errorMsg;

  /**
   * Creates new UserDto using UserEntity and JWT.
   *
   * @param dbEntity UserEntity
   * @param token JWT
   */
  public UserDto(UserEntity dbEntity, String token) {
    this.name = dbEntity.getName();
    this.email = dbEntity.getEmail();
    this.lastGameResult = dbEntity.getLastGameResult();
    this.totalWins = dbEntity.getTotalWins();
    this.totalLoses = dbEntity.getTotalLoses();
    this.winRate = dbEntity.getWinRate();
    this.actualWinStreak = dbEntity.getActualWinStreak();
    this.bestWinSteak = dbEntity.getBestWinSteak();
    this.token = token;
  }

  @Override
  public String getErrorMsg() {
    return errorMsg;
  }

  @Override
  public void setErrorMsg(String errorMsg) {
    this.errorMsg = errorMsg;
  }

  /**
   * Returns username.
   *
   * @return username
   */
  public String getName() {
    return name;
  }

  /**
   * Returns user email.
   *
   * @return users email
   */
  public String getEmail() {
    return email;
  }

  /**
   * Returns result of the last game.
   *
   * @return last game result
   */
  public String getLastGameResult() {
    return lastGameResult;
  }

  /**
   * Returns total number of wins.
   *
   * @return total number of wins
   */
  public int getTotalWins() {
    return totalWins;
  }

  /**
   * Returns total number of loses
   *
   * @return total number of loses
   */
  public int getTotalLoses() {
    return totalLoses;
  }

  /**
   * Return current win rate
   *
   * @return win rate
   */
  public long getWinRate() {
    return winRate;
  }

  /**
   * Returns actual win streak.
   *
   * @return win streak
   */
  public int getActualWinStreak() {
    return actualWinStreak;
  }

  /**
   * Returns best win streak.
   *
   * @return best win streak
   */
  public int getBestWinSteak() {
    return bestWinSteak;
  }

  /**
   * Returns users Authentication-token.
   *
   * @return Authentication-token
   */
  public String getToken() {
    return token;
  }

  @Override
  public int hashCode() {
    int result = getName() != null ? getName().hashCode() : 0;
    result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
    result = 31 * result + (getLastGameResult() != null ? getLastGameResult().hashCode() : 0);
    result = 31 * result + getTotalWins();
    result = 31 * result + getTotalLoses();
    result = 31 * result + (int) (getWinRate() ^ (getWinRate() >>> 32));
    result = 31 * result + getActualWinStreak();
    result = 31 * result + getBestWinSteak();
    result = 31 * result + (getToken() != null ? getToken().hashCode() : 0);
    result = 31 * result + (getErrorMsg() != null ? getErrorMsg().hashCode() : 0);
    return result;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof UserDto)) {
      return false;
    }

    UserDto userDto = (UserDto) o;

    if (getTotalWins() != userDto.getTotalWins()) {
      return false;
    }
    if (getTotalLoses() != userDto.getTotalLoses()) {
      return false;
    }
    if (getWinRate() != userDto.getWinRate()) {
      return false;
    }
    if (getActualWinStreak() != userDto.getActualWinStreak()) {
      return false;
    }
    if (getBestWinSteak() != userDto.getBestWinSteak()) {
      return false;
    }
    if (getName() != null ? !getName().equals(userDto.getName()) : userDto.getName() != null) {
      return false;
    }
    if (getEmail() != null ? !getEmail().equals(userDto.getEmail()) : userDto.getEmail() != null) {
      return false;
    }
    if (getLastGameResult() != null
        ? !getLastGameResult().equals(userDto.getLastGameResult())
        : userDto.getLastGameResult() != null) {
      return false;
    }
    if (getToken() != null ? !getToken().equals(userDto.getToken()) : userDto.getToken() != null) {
      return false;
    }
    return getErrorMsg() != null
        ? getErrorMsg().equals(userDto.getErrorMsg())
        : userDto.getErrorMsg() == null;
  }
}
