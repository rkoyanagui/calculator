package com.rkoyanagui.calculator.vision;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.rkoyanagui.calculator.model.Memory;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class KeypadTest
{
  Keypad keypad;
  Memory memory;

  @BeforeEach
  void beforeEach()
  {
    memory = mock(Memory.class);
    keypad = new Keypad(memory);
  }

  @Test
  void keyPressIsPassedOnToMemory()
  {
    ActionEvent actionEvent = mock(ActionEvent.class);
    when(actionEvent.getSource()).thenReturn(new JButton("1"));
    keypad.actionPerformed(actionEvent);
    verify(memory).processInput("1");
  }
}
