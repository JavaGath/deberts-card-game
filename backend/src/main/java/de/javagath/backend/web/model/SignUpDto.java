package de.javagath.backend.web.model;

public class SignUpDto {
  private String username;
  private String email;
  private String password;

  public String getUsername() {
    return username;
  }

  public void setUsername(String name) {
    this.username = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public int hashCode() {
    int result = getUsername().hashCode();
    result = 31 * result + getEmail().hashCode();
    result = 31 * result + getPassword().hashCode();
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
    return getPassword().equals(signUpDto.getPassword());
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
        + '}';
  }
}
