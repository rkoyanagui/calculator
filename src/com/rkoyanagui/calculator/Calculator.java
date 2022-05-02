package com.rkoyanagui.calculator;

import com.rkoyanagui.calculator.model.Memory;
import com.rkoyanagui.calculator.vision.Display;
import com.rkoyanagui.calculator.vision.Keypad;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.io.Serial;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import javax.swing.JFrame;

public class Calculator extends JFrame
{
  // The calculator's width in proportion to screen width
  private static final BigDecimal CALC_WIDTH_PROPORTION = BigDecimal.valueOf(0.12083);
  // The calculator's height in proportion to its width
  private static final BigDecimal CALC_HEIGHT_PROPORTION = BigDecimal.valueOf(1.38793);
  // The display's height in proportion to the calculator's width
  private static final BigDecimal DISPLAY_HEIGHT_PROPORTION = BigDecimal.valueOf(0.25862);
  private static final MathContext MATH_CONTEXT = new MathContext(4, RoundingMode.HALF_UP);

  public Calculator()
  {
    setLayout(new BorderLayout());
    Memory memory = new Memory();

    int screenWidth = getScreenWidth();
    int calcWidth = calculateFullWidth(screenWidth);
    int calcHeight = calculateFullHeight(calcWidth);
    int displayHeight = calculateDisplayHeight(calcWidth);

    addDisplay(memory, calcWidth, displayHeight);
    add(new Keypad(memory), BorderLayout.CENTER);

    setSize(calcWidth, calcHeight);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setVisible(true);
  }

  public static void main(String[] args)
  {
    new Calculator();
  }

  protected void addDisplay(Memory memory, int width, int height)
  {
    Display display = new Display(memory);
    display.setPreferredSize(new Dimension(width, height));
    add(display, BorderLayout.NORTH);
  }

  protected int getScreenWidth()
  {
    return GraphicsEnvironment.getLocalGraphicsEnvironment()
        .getDefaultScreenDevice()
        .getDisplayMode().getWidth();
  }

  protected int calculateFullWidth(int screenWidth)
  {
    return BigDecimal.valueOf(screenWidth)
        .multiply(CALC_WIDTH_PROPORTION)
        .round(MATH_CONTEXT)
        .intValue();
  }

  protected int calculateFullHeight(int fullWidth)
  {
    return BigDecimal.valueOf(fullWidth)
        .multiply(CALC_HEIGHT_PROPORTION)
        .round(MATH_CONTEXT)
        .intValue();
  }

  protected int calculateDisplayHeight(int fullWidth)
  {
    return BigDecimal.valueOf(fullWidth)
        .multiply(DISPLAY_HEIGHT_PROPORTION)
        .round(MATH_CONTEXT)
        .intValue();
  }

  @Serial
  private static final long serialVersionUID = 3573453883493997436L;
}
