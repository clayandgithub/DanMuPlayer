package com.unused.view;

/**
 * Created by clay on 2016/9/11.
 */
import javax.swing.*;

import java.awt.*;

public class GridLayoutDemo extends JFrame {

    public GridLayoutDemo() {

        setLayout(new GridLayout(0,2));           //设置为网格布局，未指定行数

        setFont(new Font("Helvetica", Font.PLAIN, 14));

        getContentPane().add(new JButton("Button 1"));

        getContentPane().add(new JButton("Button 2"));

        getContentPane().add(new JButton("Button 3"));

        getContentPane().add(new JButton("Button 4"));

        JPanel bottom = new JPanel();
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.X_AXIS));

        String[] sss = { "java笔记", "C#笔记", "HTML5笔记" };
        JComboBox b = new JComboBox(sss);
        b.setPreferredSize(new Dimension(100,16)); //uncomment this to see the layout I would like to achieve
//        b.setMaximumSize(new Dimension(100,16));
        bottom.add(b); //uncomment this line
        bottom.add(new JButton("Button 5"));
        bottom.add(Box.createHorizontalGlue());

        getContentPane().add(bottom);


    }

    public static void main(String args[]) {

        GridLayoutDemo f = new GridLayoutDemo();

        f.setTitle("GridWindow Application");

        f.pack();

        f.setVisible(true);

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        f.setLocationRelativeTo(null);           //让窗体居中显示

    }

}