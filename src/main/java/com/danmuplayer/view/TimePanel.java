package com.danmuplayer.view;

import com.danmuplayer.util.StringUtil;
import com.danmuplayer.presenter.MainPresenter;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TimePanel extends JPanel implements ChangeListener {

	private JLabel mTimeLabel;

	private JSlider mTimeSlider;

	private MainPresenter mMainPresenter;

	private JButton mPlayOrPauseButton;

	private boolean mIsJumping = false;

	public TimePanel(MainPresenter mainPresenter, int width, int height) {
		this.mMainPresenter = mainPresenter;
		this.setPreferredSize(new Dimension(width, height));
		this.setSize(width, height);
		this.setOpaque(false);

		addComponets();
	}
	
	public void addComponets() {
		this.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(5,0,5,5);

		// add mPauseButton
		constraints.gridwidth = 1;
		mPlayOrPauseButton = new JButton("播放");
		mPlayOrPauseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(mMainPresenter.isPlaying()) {
					if(mMainPresenter.pause()) {
						mPlayOrPauseButton.setText("播放");
					}
				} else {
					if(mMainPresenter.play()) {
						mPlayOrPauseButton.setText("暂停");
					}
				}
			}
		});
		this.add(mPlayOrPauseButton, constraints);

		// add mTimeSlider
		constraints.insets.left = 5;
		constraints.gridwidth = 4;
		constraints.weightx = 1;
		mTimeSlider = new JSlider();
		mTimeSlider.setValue(0);
		mTimeSlider.setMaximum(0);
		mTimeSlider.addMouseListener(
				new MouseAdapter() {
					public void mousePressed(MouseEvent event) {
						mMainPresenter.stop(false);
						mIsJumping = true;
					}
				});
		mTimeSlider.addChangeListener(this);
		this.add(mTimeSlider, constraints);

		// add mTimeLabel
		constraints.insets.right = 0;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		constraints.weightx = 0;
		mTimeLabel = new JLabel();
		mTimeLabel.setText("00:00/00:00");
		mTimeLabel.setHorizontalAlignment(SwingConstants.LEFT);
		mTimeLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		this.add(mTimeLabel, constraints);
	}

	public void setMaxTimeInMs(int maxTime) {
		mTimeSlider.setMaximum(maxTime);
	}

	private void updateTimeLabel() {
		StringBuilder sb = new StringBuilder(StringUtil.timeInSToString(mTimeSlider.getValue() / 1000));
		sb.append("/").append(StringUtil.timeInSToString(mTimeSlider.getMaximum() / 1000));
		mTimeLabel.setText(sb.toString());
	}

	public void setCurrentTimeInMs(int currentTime) {
		mTimeSlider.setValue(currentTime);
	}

	public void stateChanged(ChangeEvent event) {
		JSlider source = (JSlider) event.getSource();
		if (source.getVerifyInputWhenFocusTarget()) {
			updateTimeLabel();
		}
		if (!source.getValueIsAdjusting()) {
			if (mIsJumping) {
				mIsJumping = false;
				updateTimeLabel();
				if(mMainPresenter.jumpTime(source.getValue())) {
					mPlayOrPauseButton.setText("暂停");
				}
			}
		}
	}

    public void changePlayOrPauseButton(boolean isPlay) {
		if (isPlay) {
			mPlayOrPauseButton.setText("播放");
		} else {
			mPlayOrPauseButton.setText("暂停");
		}
    }
}
