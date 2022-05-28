package de.javagath.backend.db.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class UserEntity {

  /*
    usr_id                SERIAL PRIMARY KEY
   ,usr_name              VARCHAR(50) UNIQUE NOT NULL
   ,usr_email             VARCHAR(50) UNIQUE NOT NULL
   ,usr_salt              TEXT NOT NULL
   ,usr_password          TEXT NOT NULL
   ,usr_last_game_result  VARCHAR(4)
   ,usr_total_wins        INT
   ,usr_total_loses       INT
   ,usr_win_rate          NUMERIC(5, 2)
   ,usr_actual_win_streak INT
   ,usr_best_win_streak   INT
  */

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

  public int getActualWinStreak() {
    return actualWinStreak;
  }

  public void setActualWinStreak(int actualWinStreak) {
    this.actualWinStreak = actualWinStreak;
  }

  public int getBestWinSteak() {
    return bestWinSteak;
  }

  public void setBestWinSteak(int bestWinSteak) {
    this.bestWinSteak = bestWinSteak;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getSalt() {
    return salt;
  }

  public void setSalt(String salt) {
    this.salt = salt;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getLastGameResult() {
    return lastGameResult;
  }

  public void setLastGameResult(String lastGameResult) {
    this.lastGameResult = lastGameResult;
  }

  public int getTotalWins() {
    return totalWins;
  }

  public void setTotalWins(int totalWins) {
    this.totalWins = totalWins;
  }

  public int getTotalLoses() {
    return totalLoses;
  }

  public void setTotalLoses(int totalLoses) {
    this.totalLoses = totalLoses;
  }

  public long getWinRate() {
    return winRate;
  }

  public void setWinRate(long winRate) {
    this.winRate = winRate;
  }
}
