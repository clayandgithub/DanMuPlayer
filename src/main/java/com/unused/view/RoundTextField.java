package com.unused.view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class RoundTextField extends JTextField
{
    int mArcWidth;
    int mArcHeight;
    boolean mIsAutoArc;

    public RoundTextField(int columns, int arcWidth, int arcHeight){
        super(columns);
        mIsAutoArc = false;
        mArcWidth = arcWidth;
        mArcHeight = arcHeight;
        init();
    }

    public RoundTextField(int columns){
        super(columns);
        mIsAutoArc = true;
        init();
    }

    private void init() {
        setOpaque(false);
        setMargin(new Insets(0,5,0,5));
    }

    @Override
    protected void paintBorder(Graphics g)
    {
        int height = getHeight();
        int width = getWidth();

        Graphics2D g2d = (Graphics2D)g.create();
        Shape shape = g2d.getClip();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setClip(shape);
        if (mIsAutoArc) {
//            g2d.drawRoundRect(0, 0, width - 2, height - 2, mArcWidth, mArcHeight);
        } else {
//            g2d.drawRoundRect(0, 0, width - 2, height - 2, height, height);
        }
//        super.paintBorder(g2d);
        g2d.dispose();

    }
}