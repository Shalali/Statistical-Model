package statsmodel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class test extends JFrame {

  public test() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JPanel p = new JPanel(new BorderLayout());
    p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    JButton b = new JButton("Test");
    b.setHorizontalTextPosition(SwingConstants.LEFT);
    b.setIcon(new ImageIcon("red-ball.gif"));
    b.setRolloverIcon(new ImageIcon("blue-ball.gif"));
    b.setRolloverEnabled(true);

    b.setMnemonic('t');
    b.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        System.out.println("Button pressed");
      }
    });
    p.add(b);
    getContentPane().add(p);
    pack();
  }

  public static void main(String[] args) {
    test sb = new test();
    sb.setVisible(true);
  }
}