/**
 * MainPresenter.java
 * Copyright 2016 escenter@zju.edu.cn, all rights reserved.
 * any form of usage is subject to approval.
 */
package com.danmuplayer.presenter;

import javax.swing.*;
import javax.swing.Timer;

import com.danmuplayer.constants.Constants;
import com.danmuplayer.util.FileUtil;
import com.danmuplayer.model.SingleDanMu;
import com.danmuplayer.util.SwingUtil;
import com.danmuplayer.view.ControllerWindow;
import com.danmuplayer.view.MainWindow;

import com.danmuplayer.model.DanMuResource;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * @author wangweiwei
 *
 */
public class MainPresenter {
    
    private MainWindow mMainWindow;

    private ControllerWindow mControllerWindow;

    private Timer mClockTimer;// used to update time
    private long mMaxTime = 0;
    private long mPassedTime = 0;
    private long mTimePassedBeforeLastPlay = 0;
    private long mLastPlayTime = 0;

    private Timer mTickTimer;//used to do logic and ui update

    private DanMuResource mDanMuResource;

    private boolean mIsPlaying = false;

    public static void main(String[] args) {
        MainPresenter mainPresenter = new MainPresenter();
        mainPresenter.applicationInit();
        mainPresenter.showWindows();
    }

    private boolean applicationInit() {
        FileUtil.createDir(Constants.DANMU_RESOURCE_DIR);
        FileUtil.createDir(Constants.BILIBILI_DANMU_RESOURCE_DIR);

        mClockTimer = new Timer(Constants.MSPF, new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                clockTick();
            }
        });

        mTickTimer = new Timer(Constants.MSPF, new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                tick();
            }
        });

        mDanMuResource = new DanMuResource();

        return true;
    }

    private void showWindows() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();

        mMainWindow = new MainWindow(this, screenSize.width, screenSize.height);
        mMainWindow.setVisible(true);

        mControllerWindow = new ControllerWindow(this, Constants.WIDTH, Constants.HEIGHT, mMainWindow);
        mControllerWindow.pack();
        SwingUtil.layoutAtScreenCenter(mControllerWindow);
        mControllerWindow.setVisible(true);
    }

    public void loadDanMuResourceFromFile(String danMuFilePath) {
        stop(true);
        mDanMuResource.initByFilePath(danMuFilePath);
        mDanMuResource.loadFromOriginalList();
        mMaxTime = mDanMuResource.getMaxTime() + Constants.FINAL_DELAY_TIME;

        updateTimePanel();
    }

    public boolean play() {
        if (mIsPlaying) {
            return true;
        }
        if (!mDanMuResource.isLoaded()) {
            if (mDanMuResource.isInitialized()) {
                mDanMuResource.loadFromOriginalList();
                mMaxTime = mDanMuResource.getMaxTime() + Constants.FINAL_DELAY_TIME;
            } else {
                return false;
            }
        }

        mIsPlaying = true;
        mClockTimer.start();
        mLastPlayTime = System.currentTimeMillis();

        mTickTimer.start();
        mMainWindow.setAlwaysOnTop(true);
        return true;
    }

    public boolean pause() {
        if (!mIsPlaying) {
            return false;
        }

        mClockTimer.stop();
        mTickTimer.stop();

        mIsPlaying = false;
        mTimePassedBeforeLastPlay += (System.currentTimeMillis() - mLastPlayTime);
        mPassedTime = mTimePassedBeforeLastPlay;

        updateTimePanel();
        mMainWindow.setAlwaysOnTop(false);
        return true;
    }

    public void stop(boolean updateTimePanel) {
        //logic
        mClockTimer.stop();
        mTickTimer.stop();
        mDanMuResource.needReload();
        mIsPlaying = false;
        mTimePassedBeforeLastPlay = 0;
        mPassedTime = 0;
        mLastPlayTime = 0;

        //ui
        mMainWindow.clearAllDanMu();
        mControllerWindow.changePlayOrPauseButton(true);
        if (updateTimePanel) {
            updateTimePanel();
        }
        mMainWindow.setAlwaysOnTop(false);
    }

    public boolean isPlaying() {
        return mIsPlaying;
    }

    private void clockTick() {
        mPassedTime = mTimePassedBeforeLastPlay + System.currentTimeMillis() - mLastPlayTime;
        if (mPassedTime >= mMaxTime) {
            stop(true);
        }
        updateTimePanel();
    }

    private boolean updateTimePanel() {
        mControllerWindow.setMaxTimeInMs((int)mMaxTime);
        mControllerWindow.setCurrentTimeInMs((int)mPassedTime);
        return true;
    }

    private void tick() {
        List<SingleDanMu> danmuList = mDanMuResource.getDanMuListByCurTime(mPassedTime);
        for(SingleDanMu danmu : danmuList) {
            mMainWindow.addDanMu(danmu.getContent(), danmu.getColorHexStr(), danmu.getLineNo());
        }
        mMainWindow.danMuTick();
    }

    public boolean jumpTime(long time) {
        if (!mDanMuResource.isInitialized()) {
            return false;
        }
        mDanMuResource.jumpLoad(time);
        mMaxTime = mDanMuResource.getMaxTime() + Constants.FINAL_DELAY_TIME;
        mTimePassedBeforeLastPlay = time;
        return play();
    }
}
