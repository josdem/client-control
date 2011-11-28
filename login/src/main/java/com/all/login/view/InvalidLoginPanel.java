package com.all.login.view;

import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JPanel;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import com.all.commons.SoundPlayer.Sound;
import com.all.i18n.Messages;

public final class InvalidLoginPanel extends MessageLoginPanel {

	private static final long serialVersionUID = 1L;

	private static final Rectangle HAND_PANEL_BOUNDS = new Rectangle(247, 82, 148, 236);

	private static final String HAND_FRAME_PANEL_NAME = "handFramePanel0";

	private JPanel handPanel;

	private static final int ANIMATION_FRAMES = 4;

	private Thread loadingAnimationThread;

	public InvalidLoginPanel() {
		super();
	}

	@Override
	protected void initialize() {
		super.initialize();
		this.add(getHandPanel());

		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentHidden(ComponentEvent e) {
				stopAnimation();
			}

			@Override
			public void componentShown(ComponentEvent e) {
				animate();
			}
		});

		this.addAncestorListener(new AncestorListener() {
			@Override
			public void ancestorRemoved(AncestorEvent event) {
				stopAnimation();
			}

			@Override
			public void ancestorMoved(AncestorEvent event) {
			}

			@Override
			public void ancestorAdded(AncestorEvent event) {
				animate();
			}
		});
	}

	private JPanel getHandPanel() {
		if (handPanel == null) {
			handPanel = new JPanel();
			handPanel.setName(HAND_FRAME_PANEL_NAME + "1");
			handPanel.setBounds(HAND_PANEL_BOUNDS);
		}
		return handPanel;
	}

	private void animate() {
		loadingAnimationThread = new Thread(new LoadingAnimationWorker());
		loadingAnimationThread.setName("Loading animation thread...");
		loadingAnimationThread.start();
	}

	private void stopAnimation() {
		loadingAnimationThread = null;
	}

	private class LoadingAnimationWorker implements Runnable {
		@Override
		public void run() {
			Thread thisThread = Thread.currentThread();
			int i = 1;
			while (thisThread == loadingAnimationThread) {
				Sound.LOGIN_WRONG_PASSWORD.play();
				if (i > ANIMATION_FRAMES) {
					i = 1;
				}
				handPanel.setName(HAND_FRAME_PANEL_NAME + i);
				try {
					Thread.sleep(250);
				} catch (InterruptedException e) {
				}
				i++;
			}
		}
	}

	@Override
	protected void internationalize(Messages messages) {
		super.internationalize(messages);
		getTitleLabel().setText(messages.getMessage("login.error.user"));
		getInstructionsLabel().setText(messages.getMessage("login.error.credentials"));
		this.setName(messages.getMessage("login.invalidLoginPanel.name"));
		this.invalidate();
		this.validate();
	}
}
