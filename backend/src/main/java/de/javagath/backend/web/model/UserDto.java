package de.javagath.backend.web.model;

public class UserDto {
  private String name;
  private String email;
  private String salt;
  private String password;
  private String lastGameResult;
  private int totalWins;
  private int totalLoses;
  private long winRate;

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

  @Override
  public int hashCode() {
    int result = getName() != null ? getName().hashCode() : 0;
    result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
    result = 31 * result + (getSalt() != null ? getSalt().hashCode() : 0);
    result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
    result = 31 * result + (getLastGameResult() != null ? getLastGameResult().hashCode() : 0);
    result = 31 * result + getTotalWins();
    result = 31 * result + getTotalLoses();
    result = 31 * result + (int) (getWinRate() ^ (getWinRate() >>> 32));
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
    if (getName() != null ? !getName().equals(userDto.getName()) : userDto.getName() != null) {
      return false;
    }
    if (getEmail() != null ? !getEmail().equals(userDto.getEmail()) : userDto.getEmail() != null) {
      return false;
    }
    if (getSalt() != null ? !getSalt().equals(userDto.getSalt()) : userDto.getSalt() != null) {
      return false;
    }
    if (getPassword() != null
        ? !getPassword().equals(userDto.getPassword())
        : userDto.getPassword() != null) {
      return false;
    }
    if (getLastGameResult() != null
        ? !getLastGameResult().equals(userDto.getLastGameResult())
        : userDto.getLastGameResult() != null) {
      return false;
    }

    return true;
  }

  @Override
  public String toString() {
    return "UserDto{"
        + "name='"
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
        + '}';
  }
}
