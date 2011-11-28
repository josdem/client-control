package com.all.login.view;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import com.all.core.common.view.AllLoader;
import com.all.i18n.Messages;

public class LoaderLoginPanel extends MessageLoginPanel {

	private static final long serialVersionUID = 1L;

	private static final int TIMER_DELAY = 10000;

	private static final Rectangle ALL_LOADER_BOUNDS = new Rectangle(467, 127, 142, 142);

	private static final String NAME = "loaderPanelBackground";

	private AllLoader allLoader;

	private String loadingLabelText;

	public LoaderLoginPanel() {
		super();
	}

	@Override
	protected void initialize() {
		super.initialize();
		this.setName(NAME);
		this.add(getAllLoader());
		getNewUserLabel().setVisible(false);
		getCreateAccountLabel().setVisible(false);
		getSignUpButton().setVisible(false);
		setListeners();
	}

	private void setListeners() {
		final Timer timer = new Timer(TIMER_DELAY, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getTitleLabel().setText(loadingLabelText);
			}
		});
		timer.setRepeats(false);
		timer.start();
	}

	private AllLoader getAllLoader() {
		if (allLoader == null) {
			allLoader = new AllLoader();
			allLoader.setBounds(ALL_LOADER_BOUNDS);
		}
		return allLoader;
	}

	@Override
	public void internationalize(Messages messages) {
		super.internationalize(messages);
		getTitleLabel().setText(messages.getMessage("login.logging"));
		loadingLabelText = messages.getMessage("login.loading");
	}
}
