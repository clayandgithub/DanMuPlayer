/**
 * MainWindowUsingNullLayout.java
 * Copyright 2016 escenter@zju.edu.cn, all rights reserved.
 * any form of usage is subject to approval.
 */
package com.danmuplayer.view;

import com.danmuplayer.presenter.MainPresenter;
import com.danmuplayer.util.SwingUtil;

import javax.swing.*;
import java.awt.*;

/**
 * @author wangweiwei
 *
 */
public class MainWindow extends TransparentWindow {

    private MainPresenter mMainPresenter;

    private DanMuPanel mDanMuPanel;

    public MainWindow(MainPresenter mainPresenter, int width, int height) {
        super(width, height);
        SwingUtil.layoutAtScreenCenter(this);
        mMainPresenter = mainPresenter;
        this.setTitle("弹幕");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationByPlatform(true);
        this.setResizable(false);//TODO
        this.setAlwaysOnTop(true);

//        setImageBackground("/img/border.png");//TODO
        addComponents();

        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private void addComponents() {
        final Container container = this.getContentPane();
        final GridBagLayout layout = new GridBagLayout();
        container.setLayout(layout);
        GridBagConstraints constraints = new GridBagConstraints();

        // add danmu panel
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.gridheight = GridBagConstraints.REMAINDER;
        constraints.weightx = 1;
        constraints.weighty = 1;
        mDanMuPanel = new DanMuPanel(this.getWidth(), this.getHeight());
        this.add(mDanMuPanel, constraints);
    }

    public void clearAllDanMu() {
        mDanMuPanel.clearAllDanMu();
    }

    public void addDanMu(String content, String colorHexStr, int lineNo) {
        mDanMuPanel.addDanMu(content, colorHexStr, lineNo);
    }

    public void danMuTick() {
        mDanMuPanel.danMuTick();
    }

}
