package com.danmuplayer.view;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by clay on 2016/9/10.
 */
public class DanMuSourceOptionBox extends JComboBox{

    private ControllerWindow mControllerWindow;

    public DanMuSourceOptionBox(String[] danmuSourceOptions, ControllerWindow window) {
        super(danmuSourceOptions);
        mControllerWindow = window;

        final DanMuSourceOptionBox box = this;
        this.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent event) {
                if (event.getStateChange() == ItemEvent.SELECTED) {
                    mControllerWindow.changeDanMuResource(box.getSelectedIndex());
                    mControllerWindow.repaint();
                } else if (event.getStateChange() == ItemEvent.DESELECTED){
                }
            }
        });
    }
}
