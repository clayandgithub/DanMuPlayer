package com.unused.view;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class BoxLayoutTest extends JFrame {

    public BoxLayoutTest(){
        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.setBackground(Color.red);
        this.add(main);
        JPanel northPanel = new JPanel();

        JPanel middle = new JPanel();
        middle.setLayout(new BoxLayout(middle, BoxLayout.X_AXIS));

        String[] sss = {"aaaaaaaaaaa", "bbbbbbbbbbb", "cccccccccccc"};
        JComboBox b = new JComboBox(sss);
        b.setPreferredSize(new Dimension(100,16)); //uncomment this to see the layout I would like to achieve
//        b.setMaximumSize(new Dimension(100,16));
        middle.add(b); //uncomment this line

        middle.add(new JButton("FOO"));
        middle.add(Box.createHorizontalGlue());

        JPanel aPanel = new JPanel();
        aPanel.setBackground(Color.black);



        middle.setBackground(Color.green);
        northPanel.setBackground(Color.blue);

        main.add(northPanel);
        main.add(middle);
        main.add(Box.createVerticalGlue());

        this.setSize(800,600);
        this.setResizable(true);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new BoxLayoutTest();
    }

}