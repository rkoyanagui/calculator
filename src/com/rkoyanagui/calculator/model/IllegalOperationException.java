package com.rkoyanagui.calculator.model;

import java.io.Serial;

class IllegalOperationException extends Exception
{
  IllegalOperationException()
  {
  }

  IllegalOperationException(String message)
  {
    super(message);
  }

  IllegalOperationException(String message, Throwable cause)
  {
    super(message, cause);
  }

  IllegalOperationException(Throwable cause)
  {
    super(cause);
  }

  IllegalOperationException(
      String message,
      Throwable cause,
      boolean enableSuppression,
      boolean writableStackTrace)
  {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  @Serial
  private static final long serialVersionUID = 3243696645496929986L;
}
