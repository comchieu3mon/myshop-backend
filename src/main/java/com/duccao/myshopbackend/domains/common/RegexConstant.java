package com.duccao.myshopbackend.domains.common;

public class RegexConstant {
  public static final String PASSWORD_PATTERN =
      "^(?=.*?[a-zA-Z])(?=.*?[0-9])(?=.*?[ #`~!@£\\$%\\^&\\*\\(\\)\\-_=\\+\\[\\]\\{\\};:\'\"‘“<>\\?,\\.\\/\\|\\\\]).+$";
}
