package com.rkoyanagui.calculator.vision;

import com.rkoyanagui.calculator.model.Memory;
import com.rkoyanagui.calculator.model.MemoryObserver;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.Serial;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Display extends JPanel implements MemoryObserver
{
  private final JLabel label;

  public Display()
  {
    Memory.getInstance().addObserver(this);

    setBackground(new Color(46, 49, 50));
    setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 25));

    label = new JLabel(Memory.getInstance().getCurrentText());
    label.setForeground(Color.WHITE);
    label.setFont(new Font("Courier New", Font.PLAIN, 30));
    add(label);
  }

  @Serial
  private static final long serialVersionUID = 7789517965735182388L;

  @Override
  public void valueUpdated(String newValue)
  {
    label.setText(newValue);
  }
}
