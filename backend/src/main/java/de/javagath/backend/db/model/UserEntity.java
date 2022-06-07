package de.javagath.backend.db.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity class for the table users.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name = "users")
public class UserEntity {

  @Id
  @Column(name = "usr_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "usr_name")
  private String name;

  @Column(name = "usr_email")
  private String email;

  @Column(name = "usr_salt")
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String salt;

  @Column(name = "usr_password")
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String password;

  @Column(name = "usr_last_game_result")
  private String lastGameResult;

  @Column(name = "usr_total_wins")
  private int totalWins = 0;

  @Column(name = "usr_total_loses")
  private int totalLoses = 0;

  @Column(name = "usr_win_rate")
  private long winRate = 0;

  @Column(name = "usr_actual_win_streak")
  private int actualWinStreak = 0;

  @Column(name = "usr_best_win_streak")
  private int bestWinSteak = 0;

  @Override
  public int hashCode() {
    int result = getId().hashCode();
    result = 31 * result + (getName() != null ? getName().hashCode() : 0);
    result = 31 * result + getEmail().hashCode();
    result = 31 * result + getSalt().hashCode();
    result = 31 * result + getPassword().hashCode();
    result = 31 * result + (getLastGameResult() != null ? getLastGameResult().hashCode() : 0);
    result = 31 * result + getTotalWins();
    result = 31 * result + getTotalLoses();
    result = 31 * result + (int) (getWinRate() ^ (getWinRate() >>> 32));
    result = 31 * result + getActualWinStreak();
    result = 31 * result + getBestWinSteak();
    return result;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof UserEntity)) {
      return false;
    }

    UserEntity entity = (UserEntity) o;

    if (getTotalWins() != entity.getTotalWins()) {
      return false;
    }
    if (getTotalLoses() != entity.getTotalLoses()) {
      return false;
    }
    if (getWinRate() != entity.getWinRate()) {
      return false;
    }
    if (getActualWinStreak() != entity.getActualWinStreak()) {
      return false;
    }
    if (getBestWinSteak() != entity.getBestWinSteak()) {
      return false;
    }
    if (!getId().equals(entity.getId())) {
      return false;
    }
    if (getName() != null ? !getName().equals(entity.getName()) : entity.getName() != null) {
      return false;
    }
    if (!getEmail().equals(entity.getEmail())) {
      return false;
    }
    if (!getSalt().equals(entity.getSalt())) {
      return false;
    }
    if (!getPassword().equals(entity.getPassword())) {
      return false;
    }
    return getLastGameResult() != null
        ? getLastGameResult().equals(entity.getLastGameResult())
        : entity.getLastGameResult() == null;
  }

  @Override
  public String toString() {
    return "UserEntity{"
        + "id="
        + id
        + ", name='"
        + name
        + '\''
        + ", email='"
        + email
        + '\''
        + ", salt='"
        + salt
        + '\''
        + ", password='"
        + password
        + '\''
        + ", lastGameResult='"
        + lastGameResult
        + '\''
        + ", totalWins="
        + totalWins
        + ", totalLoses="
        + totalLoses
        + ", winRate="
        + winRate
        + ", actualWinStreak="
        + actualWinStreak
        + ", bestWinSteak="
        + bestWinSteak
        + '}';
  }

  /**
   * Returns actual winner streak of the player.
   *
   * @return actual winner streak
   */
  public int getActualWinStreak() {
    return actualWinStreak;
  }

  /**
   * Sets new winner streak for the user.
   *
   * @param actualWinStreak winner streak to set
   */
  public void setActualWinStreak(int actualWinStreak) {
    this.actualWinStreak = actualWinStreak;
  }

  /**
   * Returns the best winner streak in users history.
   *
   * @return best winner streak
   */
  public int getBestWinSteak() {
    return bestWinSteak;
  }

  /**
   * Sets new best winner streak for the user.
   *
   * @param bestWinSteak best winner streak to set
   */
  public void setBestWinSteak(int bestWinSteak) {
    this.bestWinSteak = bestWinSteak;
  }

  /**
   * Returns user id.
   *
   * @return user id
   */
  public Integer getId() {
    return id;
  }

  /**
   * Sets user id.
   *
   * @param id user id
   */
  public void setId(Integer id) {
    this.id = id;
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
   * Sets username.
   *
   * @param name username
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Returns users email.
   *
   * @return users email
   */
  public String getEmail() {
    return email;
  }

  /**
   * Sets users email.
   *
   * @param email users email
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Returns users salt for password.
   *
   * @return users salt
   */
  public String getSalt() {
    return salt;
  }

  /**
   * Sets users salt for password.
   *
   * @param salt users salt
   */
  public void setSalt(String salt) {
    this.salt = salt;
  }

  /**
   * Returns users password.
   *
   * @return users password
   */
  public String getPassword() {
    return password;
  }

  /**
   * Sets users password.
   *
   * @param password users password
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Returns the result of the last game.
   *
   * @return last result
   */
  public String getLastGameResult() {
    return lastGameResult;
  }

  /**
   * Sets result for the last users game.
   *
   * @param lastGameResult result of the last game
   */
  public void setLastGameResult(String lastGameResult) {
    this.lastGameResult = lastGameResult;
  }

  /**
   * Returns the total wins number of the user.
   *
   * @return total wins
   */
  public int getTotalWins() {
    return totalWins;
  }

  /**
   * Sets new total wins number for the user.
   *
   * @param totalWins total wins
   */
  public void setTotalWins(int totalWins) {
    this.totalWins = totalWins;
  }

  /**
   * Returns total number of loses.
   *
   * @return total loses
   */
  public int getTotalLoses() {
    return totalLoses;
  }

  /**
   * Sets new total loses number for the user.
   *
   * @param totalLoses total loses
   */
  public void setTotalLoses(int totalLoses) {
    this.totalLoses = totalLoses;
  }

  /**
   * Returns users win rate.
   *
   * @return win rate
   */
  public long getWinRate() {
    return winRate;
  }

  /**
   * Sets new win rate of the user.
   *
   * @param winRate win rate
   */
  public void setWinRate(long winRate) {
    this.winRate = winRate;
  }
}
