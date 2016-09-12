/**
 * MainWindowUsingNullLayout.java
 * Copyright 2016 escenter@zju.edu.cn, all rights reserved.
 * any form of usage is subject to approval.
 */
package com.danmuplayer.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import com.danmuplayer.constants.Constants;
import com.danmuplayer.util.ClawUtil;
import com.danmuplayer.util.FileUtil;
import com.danmuplayer.presenter.MainPresenter;
import com.danmuplayer.util.JTextfieldHintListener;
import com.danmuplayer.util.SwingUtil;
import com.unused.view.RoundTextField;

/**
 * @author wangweiwei
 *
 */
public class ControllerWindow extends JFrame {

    private MainPresenter mMainPresenter;

    private TimePanel mTimePanel;

    private MainWindow mMainWindow;

    private JTextField mURLInput;

    private DanMuSourceOptionBox mDanMuSourceOptionBox;

    private JButton mInitDanMuButton;

    public ControllerWindow(MainPresenter mainPresenter, int width, int height, MainWindow mainWindow) {
        mMainPresenter = mainPresenter;
        mMainWindow = mainWindow;
        this.setSize(width, height);
        this.setTitle("弹幕控制器");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
//        this.setLocationByPlatform(true);
        this.setAlwaysOnTop(true);

        SwingUtil.initGlobalFont(Constants.DEFAULT_FONT);
        setupMenu();
        addComponents();

    }

    private void setupMenu() {
        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);
        JMenu menu1 = new JMenu("帮助");
        menuBar.add(menu1);
        JMenuItem item1 = new JMenuItem("软件说明");
        item1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showIntroductionDialog();
            }
        });

        JMenuItem item2 = new JMenuItem("项目地址");
        item2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCodeAddressDialog();
            }
        });

        JMenuItem item3 = new JMenuItem("关于作者");
        item3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAuthorDialog();
            }
        });

        menu1.add(item1);
        menu1.addSeparator();
        menu1.add(item2);
        menu1.addSeparator();
        menu1.add(item3);
    }

    private void showIntroductionDialog() {
        JOptionPane.showMessageDialog(this, "详见https://github.com/clayandgithub/DanMuPlayer", "软件说明", JOptionPane.PLAIN_MESSAGE);
    }

    private void showCodeAddressDialog() {
        JOptionPane.showMessageDialog(this, "https://github.com/clayandgithub/DanMuPlayer", "项目地址", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showAuthorDialog() {
        JOptionPane.showMessageDialog(this, "详见http://clayandgithub.github.io/", "关于作者", JOptionPane.INFORMATION_MESSAGE);
    }

    private void addComponentsUsingBoxLayout() {
        final ControllerWindow window = this;
//        SwingUtil.addImageBackgroundPanel(this, "/img/border.png");
        final Container container = window.getContentPane();
        final BoxLayout layout = new BoxLayout(container, BoxLayout.Y_AXIS);
        container.setLayout(layout);
        container.setBackground(Color.green);

        // add searchPanel
        JPanel searchPanel = new JPanel();

        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
        mDanMuSourceOptionBox = new DanMuSourceOptionBox(Constants.DANMU_RESOURCE_OPTIONS, this);
        searchPanel.add(mDanMuSourceOptionBox);

        mURLInput = new JTextField(15);
        mURLInput.setEnabled(false);
        searchPanel.add(mURLInput);

        mInitDanMuButton = new JButton("导入");
        mInitDanMuButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                initDanMuButtonClicked();
            }
        });
        searchPanel.add(mInitDanMuButton);
        searchPanel.add(Box.createHorizontalGlue());

        container.add(searchPanel);

        // add mTimePanel
        mTimePanel = new TimePanel(mMainPresenter, this.getWidth(), this.getHeight());
        container.add(mTimePanel);

        container.add(Box.createVerticalGlue());
    }

    private void addComponents() {
        final ControllerWindow window = this;
        SwingUtil.addImageBackgroundPanel(this, "/img/bg8.png");
        final Container container = window.getContentPane();
        final GridBagLayout layout = new GridBagLayout();
        container.setLayout(layout);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(5,5,5,5);

        // add mTimePanel
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.gridheight = 1;
        constraints.weightx = 1;
        constraints.weighty = 0;
        mTimePanel = new TimePanel(mMainPresenter, this.getWidth(), this.getHeight());
        this.add(mTimePanel, constraints);

        // add mDanMuSourceOptionBox
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weightx = 0;
        constraints.weighty = 0;
        mDanMuSourceOptionBox = new DanMuSourceOptionBox(Constants.DANMU_RESOURCE_OPTIONS, this);
        this.add(mDanMuSourceOptionBox, constraints);

        // add mURLInput
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.gridheight = 1;
        constraints.weightx = 1;
        constraints.weighty = 0;
        mURLInput = new JTextField(15);
        mURLInput.setEnabled(false);
        mURLInput.addFocusListener(new JTextfieldHintListener(Constants.BILIBILI_URL_SAMPLE, mURLInput));
        this.add(mURLInput, constraints);

        // add mInitDanMuButton
        constraints.gridx = 3;
        constraints.gridy = 0;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.gridheight = 1;
        constraints.weightx = 0;
        constraints.weighty = 0;
        mInitDanMuButton = new JButton("导入");
        mInitDanMuButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                initDanMuButtonClicked();
            }
        });
        this.add(mInitDanMuButton, constraints);
    }

    public void initDanMuButtonClicked() {
        String filePath = null;
        switch (mDanMuSourceOptionBox.getSelectedIndex()) {
            case Constants.LOCAL_FILE_OPTION_INDEX:
                filePath = chooseDanMuFileToPlay();
                if (filePath != null) {
                    mURLInput.setText(filePath);
                    mMainPresenter.loadDanMuResourceFromFile(filePath);
                }
                break;
            case Constants.BILIBILI_OPTION_INDEX:
                String url = mURLInput.getText();
                if (validateBilibiliURL(url)) {
                    mURLInput.setText(url);
                    filePath = ClawUtil.getBilibiliDanmuFileByUrl(url);
                    mMainPresenter.loadDanMuResourceFromFile(filePath);
                } else {
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(this, "请输入正确的网址！（" + Constants.BILIBILI_URL_SAMPLE + "）", "错误", JOptionPane.ERROR_MESSAGE);
                }
                break;
        }
    }

    private boolean validateBilibiliURL(String url) {
        return url.matches("(http://)?(www.bilibili.com/video/av([0-9])*)(/)?");
    }

    public String chooseDanMuFileToPlay() {
        JFileChooser jfc = new JFileChooser(FileUtil.getCurDirPath() + "/" + Constants.DANMU_RESOURCE_DIR);
        jfc.setDialogTitle("请选择弹幕文件:");
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = jfc.showOpenDialog(this);
        if(JFileChooser.APPROVE_OPTION == result) {
            File file = jfc.getSelectedFile();
            return file.getAbsolutePath();
        } else {
            return null;
        }
    }

    public void setMaxTimeInMs(int maxTime) {
        mTimePanel.setMaxTimeInMs(maxTime);
    }

    public void setCurrentTimeInMs(int currentTime) {
        mTimePanel.setCurrentTimeInMs(currentTime);
    }

    public void changeDanMuResource(int selectedIndex) {
        switch (selectedIndex) {
            case Constants.LOCAL_FILE_OPTION_INDEX:
                mURLInput.setText(null);
                mURLInput.setEnabled(false);
                break;
            case Constants.BILIBILI_OPTION_INDEX:
                mURLInput.setText(Constants.BILIBILI_URL_SAMPLE);
                mURLInput.setEnabled(true);
                break;
            default:
        }
    }

    public void changePlayOrPauseButton(boolean isPlay) {
        mTimePanel.changePlayOrPauseButton(isPlay);
    }
}
