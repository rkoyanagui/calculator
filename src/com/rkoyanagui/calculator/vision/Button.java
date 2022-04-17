package com.rkoyanagui.calculator.vision;

import java.awt.Color;
import java.awt.Font;
import java.io.Serial;
import javax.swing.BorderFactory;
import javax.swing.JButton;

public class Button extends JButton
{
  public Button(String text, Color bgColor)
  {
    setText(text);
    setOpaque(true);
    setBackground(bgColor);
    setForeground(Color.WHITE);
    setFont(new Font("Courier New", Font.PLAIN, 25));
    setBorder(BorderFactory.createLineBorder(Color.BLACK));
  }

  @Serial
  private static final long serialVersionUID = -926796210957349137L;
}
