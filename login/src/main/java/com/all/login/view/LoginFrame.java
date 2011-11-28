package com.all.login.view;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JMenuBar;

import com.all.core.common.view.AllBaseFrame;
import com.all.i18n.Messages;

public class LoginFrame extends AllBaseFrame {

	private static final long serialVersionUID = -4054126802286753195L;

	private static final Dimension DEFAULT_DIMENSION = new Dimension(740, 500);

	private static final Dimension LOGIN_MENU_BAR_DEFAULT_SIZE = new Dimension(740, 19);

	private static final Dimension MINIMUM_SIZE = new Dimension(740, 500);

	private static final String LOGIN_MENU_BAR_NAME = "loginMenuBar";

	private JMenuBar loginMenuBar;

	public LoginFrame(Messages messages) {
		super(messages, false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		customize();
	}

	private void customize() {
		setJMenuBar(getLoginMenuBar());
		draggerMouseListener.setup(getLoginMenuBar());

		setSize(DEFAULT_DIMENSION);
		setPreferredSize(DEFAULT_DIMENSION);
		setMinimumSize(MINIMUM_SIZE);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();

		setBounds(new Rectangle(new Point((screenSize.width - DEFAULT_DIMENSION.width) / 2,
				(screenSize.height - DEFAULT_DIMENSION.height) / 2), DEFAULT_DIMENSION));

		getMaximizeButton().setEnabled(false);
		customContentPane().remove(getBottomPanel());
		getLayeredPane().remove(getTopResizer());
		getLayeredPane().remove(getTopLeftResizer());
		getLayeredPane().validate();
	}

	private JMenuBar getLoginMenuBar() {
		if (loginMenuBar == null) {
			loginMenuBar = new JMenuBar();
			loginMenuBar.setName(LOGIN_MENU_BAR_NAME);
			loginMenuBar.setSize(LOGIN_MENU_BAR_DEFAULT_SIZE);
			loginMenuBar.setPreferredSize(LOGIN_MENU_BAR_DEFAULT_SIZE);
		}
		return loginMenuBar;
	}
}
