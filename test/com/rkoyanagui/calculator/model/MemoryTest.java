package com.rkoyanagui.calculator.model;

import static com.rkoyanagui.calculator.model.Memory.ERROR_MSG;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MemoryTest
{
  Memory memory;

  @BeforeEach
  void beforeEach()
  {
    memory = new Memory();
  }

  @Test
  void theInitialTextShouldBeZero()
  {
    assertEquals("0", memory.getCurrentText());
  }

  @Test
  void ifZeroIsPressedInitiallyThenTheTextShouldStillBeZero()
  {
    memory.processInput("0");
    memory.processInput("0");
    assertEquals("0", memory.getCurrentText());
  }

  @Test
  void ifANumberKeyIsPressedInitiallyThenTheTextShouldBeEqualToIt()
  {
    memory.processInput("1");
    assertEquals("1", memory.getCurrentText());
  }

  @Test
  void ifInitiallyTwoNumberKeysArePressedThenTheyShouldBeConcatenated()
  {
    memory.processInput("1");
    memory.processInput("2");
    assertEquals("12", memory.getCurrentText());
  }

  @Test
  void theInitialValuePlusSomeNumberShouldYieldTheSameNumber()
  {
    memory.processInput("+");
    memory.processInput("2");
    memory.processInput("=");
    assertEquals("2", memory.getCurrentText());
  }

  @Test
  void pressingAnArithmeticOperationKeyRepeatedlyShouldNotMakeAnyDifferenceToTheResult()
  {
    memory.processInput("+");
    memory.processInput("+");
    memory.processInput("2");
    memory.processInput("=");
    assertEquals("2", memory.getCurrentText());
  }

  @Test
  void aRegularArithmeticOperationShouldWork()
  {
    memory.processInput("2");
    memory.processInput("+");
    memory.processInput("3");
    memory.processInput("=");
    assertEquals("5", memory.getCurrentText());
  }

  @Test
  void aPartialResultShouldBeFedIntoTheNextOperation()
  {
    memory.processInput("2");
    memory.processInput("+");
    memory.processInput("3");
    memory.processInput("*"); // 5
    memory.processInput("4");
    memory.processInput("=");
    assertEquals("20", memory.getCurrentText());
  }

  @Test
  void aPreviousFullResultShouldBeFedIntoTheNextOperation()
  {
    memory.processInput("2");
    memory.processInput("+");
    memory.processInput("3");
    memory.processInput("="); // 5
    memory.processInput("*");
    memory.processInput("4");
    memory.processInput("=");
    assertEquals("20", memory.getCurrentText());
  }

  @Test
  void ifAnEqualsKeyPressIsFollowedByADigitKeyPressThenThePreviousResultShouldBeThrownOut()
  {
    memory.processInput("2");
    memory.processInput("+");
    memory.processInput("2");
    memory.processInput("="); // 4
    memory.processInput("5");
    memory.processInput("*");
    memory.processInput("3");
    memory.processInput("=");
    assertEquals("15", memory.getCurrentText());
  }

  @Test
  void aDivisionByZeroShouldYieldAnErrorText()
  {
    memory.processInput("2");
    memory.processInput("/");
    memory.processInput("0");
    memory.processInput("=");
    assertEquals(ERROR_MSG, memory.getCurrentText());
  }

  @Test
  void pressingEqualsRepeatedlyAfterAnErrorShouldNotDismissTheErrorNorDoAnythingElse()
  {
    memory.processInput("2");
    memory.processInput("/");
    memory.processInput("0");
    memory.processInput("="); // error
    memory.processInput("="); // still error
    assertEquals(ERROR_MSG, memory.getCurrentText());
  }

  @Test
  void anErrorFollowedByAnyOperationShouldYieldAnErrorStill()
  {
    memory.processInput("2");
    memory.processInput("/");
    memory.processInput("0");
    memory.processInput("="); // error
    memory.processInput("+");
    memory.processInput("1");
    memory.processInput("="); // still error
    assertEquals(ERROR_MSG, memory.getCurrentText());
  }

  @Test
  void anIllegalDigitShouldBeIgnoredInFavourOfTheCurrentTextsValue()
  {
    memory.processInput("2");
    memory.processInput("+");
    memory.processInput("A");
    memory.processInput("=");
    assertEquals("4", memory.getCurrentText());
  }

  @Test
  void anIllegalOperationShouldBeIgnoredInFavourOfConcatenatingAnyNewDigitToTheCurrentValue()
  {
    memory.processInput("2");
    memory.processInput("A");
    memory.processInput("3");
    memory.processInput("=");
    assertEquals("23", memory.getCurrentText());
  }

  @Test
  void theACKeyShouldClearTheCurrentValue()
  {
    memory.processInput("2");
    memory.processInput("AC");
    assertEquals("0", memory.getCurrentText());
  }

  @Test
  void theACKeyShouldClearBothTheStoredValueAndTheStoredOperationFromMemory()
  {
    memory.processInput("1");
    memory.processInput("+");
    memory.processInput("2");
    memory.processInput("AC");
    memory.processInput("5");
    memory.processInput("=");
    assertEquals("5", memory.getCurrentText());
  }

  @Test
  void anInitialACKeyPressShouldNotDoAnything()
  {
    memory.processInput("AC");
    assertEquals("0", memory.getCurrentText());
  }

  @Test
  void pressingZeroShouldBeRedundantInAZeroPointSomeDigitKindOfNumber()
  {
    memory.processInput(".");
    memory.processInput("2");
    memory.processInput("*");
    memory.processInput("3");
    memory.processInput("=");
    assertEquals("0.6", memory.getCurrentText());
  }

  @Test
  void pressingACShouldNotAffectThePointKeysBehaviour()
  {
    memory.processInput("AC");
    memory.processInput(".");
    memory.processInput("2");
    assertEquals("0.2", memory.getCurrentText());
  }

  @Test
  void pressingPointMoreThanOnceShouldJustKeepTheOneSingleOriginalPoint()
  {
    memory.processInput("1");
    memory.processInput(".");
    memory.processInput(".");
    memory.processInput("2");
    assertEquals("1.2", memory.getCurrentText());
  }

  @Test
  void ifAnEqualsKeyPressIsFollowedByAPointAndDigitKeyPressThenThePreviousResultShouldBeThrownOut()
  {
    memory.processInput("2");
    memory.processInput("+");
    memory.processInput("2");
    memory.processInput("="); // 4
    memory.processInput(".");
    memory.processInput("3");
    assertEquals("0.3", memory.getCurrentText());
  }

  @Test
  void negativeResultsShouldBeDisplayedWithAPrecedingMinusSign()
  {
    memory.processInput("2");
    memory.processInput("-");
    memory.processInput("3");
    memory.processInput("=");
    assertEquals("-1", memory.getCurrentText());
  }

  @Test
  void ifEqualsIsPressedRepeatedlyThenTheLastOperationShouldBeRepeatedlyAppliedToTheCurrentValue()
  {
    memory.processInput("3");
    memory.processInput("*");
    memory.processInput("2"); // Operation: times two
    memory.processInput("="); // 6
    memory.processInput("="); // 12
    assertEquals("12", memory.getCurrentText());
  }

  @Test
  void ifEqualsIsPressedRightAtTheBeginningThenNoOperationShouldBePerformed()
  {
    memory.processInput("=");
    memory.processInput("=");
    assertEquals("0", memory.getCurrentText());
  }

  @Test
  void ifEqualsIsPressedAfterADigitButWithoutAnOperationThenNoOperationShouldBePerformed()
  {
    memory.processInput("2");
    memory.processInput("=");
    memory.processInput("=");
    assertEquals("2", memory.getCurrentText());
  }
}
