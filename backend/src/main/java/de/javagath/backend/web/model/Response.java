package de.javagath.backend.web.model;

/**
 * This interface is used to mark DTOs for WebCommunication. Each Response-Object has a possible
 * errorMsg which should be used in frontend.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.0
 * @since 1.0
 */
public interface Response {

  /**
   * Returns error message from the Response-Object.
   *
   * @return error massage
   */
  String getErrorMsg();

  /**
   * Sets error message to the Response-Object.
   *
   * @param errorMsg error massage
   */
  void setErrorMsg(String errorMsg);
}
