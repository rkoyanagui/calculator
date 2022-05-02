package com.rkoyanagui.calculator.model;

import static com.rkoyanagui.calculator.model.Operation.DO_NOTHING;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Memory
{
  static final String ERROR_MSG = "error";

  private final List<MemoryObserver> observers = new ArrayList<>();
  private Operation lastOperation;
  private boolean equalsWasPressed;
  private boolean digitWasPressed;
  private boolean replacesPreviousText;
  private String storedText;
  private String currentText;

  public Memory()
  {
    clear();
  }

  public void addObserver(MemoryObserver observer)
  {
    observers.add(observer);
  }

  public String getCurrentText()
  {
    return currentText;
  }

  public void processInput(String text)
  {
    Operation operation = detectOperation(text);
    switch (operation)
    {
      case DO_NOTHING:
        return;
      case CLEAR:
        clear();
        break;
      case DECIMAL_POINT:
        processDecimalPoint(text);
        break;
      case DIGIT:
        processDigit(text);
        break;
      case EQUALS:
        processEquals();
        break;
      default:
        processArithmeticOperators(operation);
    }
    notifyObservers(getCurrentText());
  }

  protected void processDecimalPoint(String text)
  {
    if (replacesPreviousText)
    {
      currentText = "0.";
    }
    else if (!currentText.contains("."))
    {
      currentText = currentText + text;
    }
    replacesPreviousText = false;
    digitWasPressed = true;
  }

  protected void processDigit(String text)
  {
    if (replacesPreviousText)
    {
      currentText = text;
    }
    else
    {
      currentText = currentText + text;
    }
    replacesPreviousText = false;
    digitWasPressed = true;
  }

  protected void processEquals()
  {
    String temp = currentText;
    replacesPreviousText = true;
    String x = storedText;
    String y = currentText;
    if (equalsWasPressed)
    {
      x = currentText;
      y = storedText;
    }
    else
    {
      storedText = temp;
    }
    doMaths(x, y, lastOperation).ifPresent(r -> currentText = r);
    equalsWasPressed = true;
    digitWasPressed = false;
  }

  protected void processArithmeticOperators(Operation operation)
  {
    replacesPreviousText = true;
    if (!equalsWasPressed && digitWasPressed)
    {
      doMaths(storedText, currentText, lastOperation).ifPresent(r -> currentText = r);
    }
    storedText = currentText;
    lastOperation = operation;
    equalsWasPressed = false;
    digitWasPressed = false;
  }

  protected Optional<String> doMaths(String x, String y, Operation op)
  {
    if (DO_NOTHING.equals(op))
    {
      return Optional.empty();
    }
    BigDecimal a;
    BigDecimal b;
    try
    {
      a = new BigDecimal(x);
      b = new BigDecimal(y);
    }
    catch (NumberFormatException e)
    {
      return Optional.of(ERROR_MSG);
    }
    try
    {
      BigDecimal result = switch (op)
          {
            case DIVIDE -> a.divide(b, MathContext.DECIMAL32);
            case MULTIPLY -> a.multiply(b);
            case SUBTRACT -> a.subtract(b);
            case ADD -> a.add(b);
            default -> throw new IllegalOperationException("Unknown operation: " + op);
          };
      return Optional.of(result.stripTrailingZeros().toPlainString());
    }
    catch (ArithmeticException | IllegalOperationException e)
    {
      return Optional.of(ERROR_MSG);
    }
  }

  protected Operation detectOperation(String text)
  {
    // Leading zero
    if ("0".equals(currentText) && "0".equals(text))
    {
      return DO_NOTHING;
    }

    // Digit
    if ("1234567890".contains(text))
    {
      return Operation.DIGIT;
    }

    // Other operations
    return switch (text)
        {
          case "AC" -> Operation.CLEAR;
          case "/" -> Operation.DIVIDE;
          case "*" -> Operation.MULTIPLY;
          case "-" -> Operation.SUBTRACT;
          case "+" -> Operation.ADD;
          case "=" -> Operation.EQUALS;
          case "." -> Operation.DECIMAL_POINT;
          default -> DO_NOTHING;
        };
  }

  protected void notifyObservers(String newValue)
  {
    observers.forEach(observer -> observer.valueUpdated(newValue));
  }

  protected void clear()
  {
    lastOperation = DO_NOTHING;
    equalsWasPressed = false;
    digitWasPressed = false;
    replacesPreviousText = true;
    storedText = "";
    currentText = "0";
  }
}
