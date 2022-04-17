package com.rkoyanagui.calculator.vision;

import com.rkoyanagui.calculator.model.Memory;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Numpad extends JPanel implements ActionListener
{
  private static final Color DARK_GRAY = new Color(68, 68, 68);
  private static final Color LIGHT_GRAY = new Color(99, 99, 99);
  private static final Color ORANGE = new Color(242, 163, 60);

  public Numpad()
  {
    GridBagLayout layout = new GridBagLayout();
    GridBagConstraints c = new GridBagConstraints();

    setLayout(layout);

    c.weightx = 1;
    c.weighty = 1;
    c.fill = GridBagConstraints.BOTH;

    c.gridwidth = 3;
    addButton("AC", DARK_GRAY, c, 0, 0);
    c.gridwidth = 1;
    addButton("/", ORANGE, c, 0, 3);

    addButton("7", LIGHT_GRAY, c, 1, 0);
    addButton("8", LIGHT_GRAY, c, 1, 1);
    addButton("9", LIGHT_GRAY, c, 1, 2);
    addButton("*", ORANGE, c, 1, 3);

    addButton("4", LIGHT_GRAY, c, 2, 0);
    addButton("5", LIGHT_GRAY, c, 2, 1);
    addButton("6", LIGHT_GRAY, c, 2, 2);
    addButton("-", ORANGE, c, 2, 3);

    addButton("1", LIGHT_GRAY, c, 3, 0);
    addButton("2", LIGHT_GRAY, c, 3, 1);
    addButton("3", LIGHT_GRAY, c, 3, 2);
    addButton("+", ORANGE, c, 3, 3);

    c.gridwidth = 2;
    addButton("0", LIGHT_GRAY, c, 4, 0);
    c.gridwidth = 1;
    addButton(".", LIGHT_GRAY, c, 4, 2);
    addButton("=", ORANGE, c, 4, 3);
  }

  protected void addButton(String text, Color bgColor, GridBagConstraints c, int row, int column)
  {
    c.gridy = row;
    c.gridx = column;
    Button button = new Button(text, bgColor);
    button.addActionListener(this);
    add(button, c);
  }

  @Override
  public void actionPerformed(ActionEvent e)
  {
    if (e.getSource() instanceof JButton button)
    {
      Memory.getInstance().processInput(button.getText());
    }
  }

  @Serial
  private static final long serialVersionUID = -4096120638491473379L;
}
