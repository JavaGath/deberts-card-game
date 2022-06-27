package de.javagath.backend.web.model;

/**
 * DTO class for user registration.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.0
 * @since 1.0
 */
public class SignUpDto implements Response {
  private String username;
  private String email;
  private String password;
  private String errorMsg;

  @Override
  public int hashCode() {
    int result = getUsername().hashCode();
    result = 31 * result + getEmail().hashCode();
    result = 31 * result + getPassword().hashCode();
    result = 31 * result + (getErrorMsg() != null ? getErrorMsg().hashCode() : 0);
    return result;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof SignUpDto)) {
      return false;
    }

    SignUpDto signUpDto = (SignUpDto) o;

    if (!getUsername().equals(signUpDto.getUsername())) {
      return false;
    }
    if (!getEmail().equals(signUpDto.getEmail())) {
      return false;
    }
    if (!getPassword().equals(signUpDto.getPassword())) {
      return false;
    }
    return getErrorMsg() != null
        ? getErrorMsg().equals(signUpDto.getErrorMsg())
        : signUpDto.getErrorMsg() == null;
  }

  @Override
  public String toString() {
    return "SignUpDto{"
        + "username='"
        + username
        + '\''
        + ", email='"
        + email
        + '\''
        + ", password='"
        + password
        + '\''
        + ", errorMsg='"
        + errorMsg
        + '\''
        + '}';
  }

  public String getErrorMsg() {
    return errorMsg;
  }

  public void setErrorMsg(String errorMsg) {
    this.errorMsg = errorMsg;
  }

  /**
   * Returns username from the registration data.
   *
   * @return username
   */
  public String getUsername() {
    return username;
  }

  /**
   * Sets new username to the registration data.
   *
   * @param name new username
   */
  public void setUsername(String name) {
    this.username = name;
  }

  /**
   * Returns email from the registration data.
   *
   * @return email
   */
  public String getEmail() {
    return email;
  }

  /**
   * Sets email to the registration data.
   *
   * @param email new email
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Returns user password in plain text from the registration data.
   *
   * @return users password
   */
  public String getPassword() {
    return password;
  }

  /**
   * Sets new password in plain text to the registration data.
   *
   * @param password users password
   */
  public void setPassword(String password) {
    this.password = password;
  }
}
