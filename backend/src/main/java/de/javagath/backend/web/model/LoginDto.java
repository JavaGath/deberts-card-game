package de.javagath.backend.web.model;

/**
 * DTO class for user authentication.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.0
 * @since 1.0
 */
public class LoginDto {

  private String login;
  private String password;
  private String errorMsg;

  /**
   * Returns error message from the login process.
   *
   * @return error massage
   */
  public String getErrorMsg() {
    return errorMsg;
  }

  /**
   * Sets error message from the login process.
   *
   * @param errorMsg error massage
   */
  public void setErrorMsg(String errorMsg) {
    this.errorMsg = errorMsg;
  }

  /**
   * Returns user login data (username or email).
   *
   * @return user login data
   */
  public String getLogin() {
    return login;
  }

  /**
   * Sets user login data (username or email).
   *
   * @param login username or email
   */
  public void setLogin(String login) {
    this.login = login;
  }

  /**
   * Returns users password.
   *
   * @return password
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

  @Override
  public int hashCode() {
    int result = getLogin().hashCode();
    result = 31 * result + getPassword().hashCode();
    result = 31 * result + (getErrorMsg() != null ? getErrorMsg().hashCode() : 0);
    return result;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof LoginDto)) {
      return false;
    }

    LoginDto loginDto = (LoginDto) o;

    if (!getLogin().equals(loginDto.getLogin())) {
      return false;
    }
    if (!getPassword().equals(loginDto.getPassword())) {
      return false;
    }
    return getErrorMsg() != null
        ? getErrorMsg().equals(loginDto.getErrorMsg())
        : loginDto.getErrorMsg() == null;
  }

  @Override
  public String toString() {
    return "LoginDto{"
        + "login='"
        + login
        + '\''
        + ", password='"
        + password
        + '\''
        + ", errorMsg='"
        + errorMsg
        + '\''
        + '}';
  }
}
