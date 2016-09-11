/**
 * Main.java
 * Copyright 2016 escenter@zju.edu.cn, all rights reserved.
 * any form of usage is subject to approval.
 */
package com.danmuplayer.test;

/**
 * @author wangweiwei
 *
 */

import com.danmuplayer.view.TransparentWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class TestTranparentWindow {
    private static final String BORDER_IMG_PATH = "/img/border.png";
    private static final int BORDER_WIDTH = 512;
    private static final int BORDER_HEIGHT = 512;
    
    private static final String WINDOW_IMG_PATH = "/img/flower.png";
    private static final int WIDTH = 453;
    private static final int HEIGHT = 524;

    public static void main(String[] args) {
        final TransparentWindow window = new TransparentWindow(BORDER_WIDTH, BORDER_HEIGHT);
        Container container = window.getContentPane();
        
        JPanel background = new JPanel(){
//          @Override
//          public void paint(Graphics g) {
//            //调用的super.paint(g),让父类做一些事前的工作，如刷新屏幕
//              super.paint(g);   
//              Graphics2D g2d = (Graphics2D)g;  
//              g2d.setColor(Color.BLUE);//设置画图的颜色  
//              g2d.fill3DRect(0, 0, 100, 100, true);//填充一个矩形
//          }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                URL url = this.getClass().getResource(BORDER_IMG_PATH);
                System.out.println(url.toString());
                ImageIcon icon = new ImageIcon(this.getClass().getResource(TestTranparentWindow.BORDER_IMG_PATH));
                
//                Image img = Toolkit.getDefaultToolkit().getImage(WINDOW_IMG_PATH);
                g.drawImage(icon.getImage(), 0, 0, TestTranparentWindow.BORDER_WIDTH, TestTranparentWindow.BORDER_HEIGHT, this);
                
                url = this.getClass().getResource(WINDOW_IMG_PATH);
                System.out.println(url.toString());
                icon = new ImageIcon(this.getClass().getResource(TestTranparentWindow.WINDOW_IMG_PATH));
                
                g.drawImage(icon.getImage(), 0, 0, TestTranparentWindow.WIDTH, TestTranparentWindow.HEIGHT, this);
                
            }
        };
        background.setOpaque(false);

        JButton button = new JButton("Test~");
        button.setFocusPainted(false);
        button.setBounds((WIDTH - button.getPreferredSize().width)/2, (HEIGHT - button.getPreferredSize().height)/2, button.getPreferredSize().width, button.getPreferredSize().height);
        button.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                window.dispose();
                System.exit(0);
            }
        });
        background.add(button);
        container.add(background);

        window.setVisible(true);
    }
}
