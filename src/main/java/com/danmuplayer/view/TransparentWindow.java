/**
 * TransparentWindow.java
 * Copyright 2016 escenter@zju.edu.cn, all rights reserved.
 * any form of usage is subject to approval.
 */
package com.danmuplayer.view;

import javax.swing.JFrame;

import com.sun.awt.AWTUtilities;

/**
 * @author wangweiwei
 *
 */
public class TransparentWindow extends JFrame {

    private static final long serialVersionUID = 5816079399320136426L;
    
    public TransparentWindow(int width, int height) {
        this.setSize(width, height);
        this.setUndecorated(true);
        AWTUtilities.setWindowOpaque(this, false);
    }

}
