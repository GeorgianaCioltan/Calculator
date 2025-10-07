package calculator;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class pannel_calculator extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField display;
    private String operator = "";
    private double firstNumber = 0;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                pannel_calculator frame = new pannel_calculator();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public pannel_calculator() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("iPhone-style Calculator");
        setBounds(100, 100, 300, 450);
        setResizable(false);

        contentPane = new JPanel();
        contentPane.setBackground(new Color(30, 30, 30));
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setLayout(new BorderLayout(5, 5));
        setContentPane(contentPane);

        display = new JTextField();
        display.setFont(new Font("Arial", Font.BOLD, 28));
        display.setHorizontalAlignment(SwingConstants.RIGHT);
        display.setEditable(false);
        display.setBackground(new Color(20, 20, 20));
        display.setForeground(Color.WHITE);
        display.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.add(display, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 5, 5));
        buttonPanel.setBackground(new Color(30, 30, 30));
        contentPane.add(buttonPanel, BorderLayout.CENTER);

        String[] buttons = {
            "C", "+/-", "%", "/",
            "7", "8", "9", "x",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "0", ".", "=", ""
        };

        for (String text : buttons) {
            if (text.equals("")) {
                buttonPanel.add(new JLabel()); // spacer
                continue;
            }
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 22));
            button.setFocusPainted(false);
            //button.setBorder(new RoundedBorder(20));

            // Stilizare butoane
            if (text.equals("C") || text.equals("+/-") || text.equals("%")) {
                button.setBackground(new Color(200, 200, 200)); // gri deschis
                button.setForeground(Color.BLACK);
            } else if (text.equals("/") || text.equals("x") || text.equals("-") || text.equals("+") || text.equals("=")) {
                button.setBackground(new Color(255, 165, 0)); // portocaliu
                button.setForeground(Color.WHITE);
            } else {
                button.setBackground(new Color(50, 50, 50)); // gri închis pentru cifre
                button.setForeground(Color.WHITE);
            }

            button.addActionListener(new ButtonClickListener());
            buttonPanel.add(button);
        }
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = ((JButton) e.getSource()).getText();

            if (command.matches("[0-9]") || command.equals(".")) {
                display.setText(display.getText() + command);
            } else if (command.equals("C")) {
                display.setText("");
                operator = "";
                firstNumber = 0;
            } else if (command.equals("+/-")) {
                if (!display.getText().isEmpty()) {
                    double value = Double.parseDouble(display.getText());
                    value = -value;
                    display.setText(String.valueOf(value));
                }
            } else if (command.equals("=")) {
                double secondNumber = Double.parseDouble(display.getText());
                double result = 0;

                switch (operator) {
                    case "+": result = firstNumber + secondNumber; break;
                    case "-": result = firstNumber - secondNumber; break;
                    case "x": result = firstNumber * secondNumber; break;
                    case "/": result = firstNumber / secondNumber; break;
                    case "%": result = firstNumber % secondNumber; break;
                }
                display.setText(String.valueOf(result));
                operator = "";
            } else { // operator
                if (!display.getText().isEmpty()) {
                    firstNumber = Double.parseDouble(display.getText());
                    display.setText("");
                    operator = command;
                }
            }
        }
    }

    private static class RoundedBorder implements Border {
        private int radius;

        RoundedBorder(int radius) {
            this.radius = radius;
        }

        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
        }

        public boolean isBorderOpaque() { return true; }

        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.setColor(Color.GRAY);
            g.drawRoundRect(x, y, width-1, height-1, radius, radius);
        }
    }
}
