package com.danmuplayer.view;

import com.danmuplayer.constants.Constants;
import com.danmuplayer.model.SingleDanMu;
import com.danmuplayer.util.SwingUtil;
import com.sun.glass.ui.Size;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class DanMuPanel extends JPanel{

	private static final long serialVersionUID = -2630361033538823440L;

	private static final int RANDOM_Y_DIFF = 32;

	List<SingleDanMuLabel> mDanMuLabelList = new LinkedList<SingleDanMuLabel>();

	private Random mRandom = new Random();
	
	public DanMuPanel(int width, int height) {
		this.setPreferredSize(new Dimension(width, height));
		this.setSize(width, height);
		this.setLayout(null);
		this.setOpaque(false);
		mRandom.setSeed(System.currentTimeMillis());
	}

	public void addDanMu(String content, String colorHexStr, int lineNo) {
		SingleDanMuLabel newDanMuLabel = new SingleDanMuLabel(content, colorHexStr, lineNo);
		mDanMuLabelList.add(newDanMuLabel);
		int y = this.getHeight() / Constants.MAX_LINE_COUNT * lineNo + mRandom.nextInt(RANDOM_Y_DIFF);
		newDanMuLabel.setLocation(this.getWidth(), y);
		this.add(newDanMuLabel);
	}
	
	public void clearAllDanMu() {
		for (JLabel l : mDanMuLabelList) {
			this.remove(l);
		}
		mDanMuLabelList.clear();
		repaint();
	}

	public void danMuTick() {
		for (Iterator<SingleDanMuLabel> it = mDanMuLabelList.iterator(); it.hasNext();) {
			SingleDanMuLabel l = it.next();
			l.setLocation(l.getX() - l.getSpeed(), l.getY());
//			l.setBounds(l.getX() - 2, l.getY(), l.getWidth(), l.getHeight());
			if (l.getX() + l.getWidth() < 0) {
				this.remove(l);
				it.remove();
			}
		}
	}
}
