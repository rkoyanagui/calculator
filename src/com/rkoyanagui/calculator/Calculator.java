package com.rkoyanagui.calculator;

import com.rkoyanagui.calculator.vision.Display;
import com.rkoyanagui.calculator.vision.Numpad;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.Serial;
import javax.swing.JFrame;

public class Calculator extends JFrame
{
  public Calculator()
  {
    setupLayout();

    setSize(232, 322);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setVisible(true);
  }

  protected void setupLayout()
  {
    setLayout(new BorderLayout());

    Display display = new Display();
    display.setPreferredSize(new Dimension(233, 60));
    add(display, BorderLayout.NORTH);

    add(new Numpad(), BorderLayout.CENTER);
  }

  public static void main(String[] args)
  {
    new Calculator();
  }

  @Serial
  private static final long serialVersionUID = 3573453883493997436L;
}
