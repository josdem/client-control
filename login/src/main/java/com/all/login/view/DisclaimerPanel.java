package com.all.login.view;

import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;

import com.all.commons.Environment;
import com.all.core.common.view.SynthFonts;
import com.all.i18n.Internationalizable;
import com.all.i18n.Messages;
import com.all.observ.ObservValue;
import com.all.observ.Observable;
import com.all.observ.ObserverCollection;

public final class DisclaimerPanel extends JPanel implements Internationalizable {

	private static final long serialVersionUID = 1L;

	private final Log log = LogFactory.getLog(DisclaimerPanel.class);
	
	private static final Insets LICENSE_TEXT_AREA_INSETS = new Insets(5, 10, 5, 10);
	
	private static final Point ACCEPT_BUTTON_LINUX_LOCATION = new Point(180, 318);

	private static final Point REJECT_BUTTON_LINUX_LOCATION = new Point(58, 318);

	private static final Rectangle ACCEPT_BUTTON_BOUNDS = new Rectangle(166, 318, 110, 22);

	private static final Rectangle DISCLAIMER_PANEL_BOUNDS = new Rectangle(378, 25, 320, 350);
	
	private static final Rectangle DISCLAIMER_PANEL_LINUX_BOUNDS = new Rectangle(368, 25, 350, 350);

	private static final Rectangle REJECT_BUTTON_BOUNDS = new Rectangle(44, 318, 110, 22);

	private static final Rectangle SCROLL_PANE_BOUNDS = new Rectangle(10, 31, 298, 278);
	
	private static final Rectangle SCROLL_PANE_LINUX_BOUNDS = new Rectangle(10, 31, 328, 278);

	private static final Rectangle TITLE_DISCLAIMER_LABEL_BOUNDS = new Rectangle(10, 4, 280, 25);

	private static final String ACCEPT_BUTTON_NAME = "addFriendButton";

	private static final String NAME = "disclaimerFramePanelBackground";

	private static final String REJECT_BUTTON_NAME = "addFriendButton";

	private static final String VIEW_PORT_NAME = "disclaimerTextAreaBackground";

	private final Observable<ObservValue<Boolean>> acceptDisclaimerEvent = new Observable<ObservValue<Boolean>>();

	private JButton acceptButton;

	private JButton rejectButton;

	private JEditorPane licenseTextArea;

	private JLabel titleDisclamerLabel;

	private JScrollPane scrollPane;

	public DisclaimerPanel() {
		initialize();
	}

	private void initialize() {
		this.setLayout(null);
		this.setVisible(false);
		this.setName(NAME);
		if(!Environment.isLinux()){
			this.setBounds(DISCLAIMER_PANEL_BOUNDS);
		}else{
			this.setBounds(DISCLAIMER_PANEL_LINUX_BOUNDS);
		}
		this.add(getRejectButton());
		this.add(getAcceptButton());
		this.add(getTitleDisclaimerLabel());
		this.add(getScrollPane());
	}

	private JEditorPane getDisclaimerArea() {
		if (licenseTextArea == null) {
			licenseTextArea = new JEditorPane();
			licenseTextArea.setEditable(false);
			licenseTextArea.setMargin(LICENSE_TEXT_AREA_INSETS);
		}
		return licenseTextArea;
	}

	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.getViewport().setName(VIEW_PORT_NAME);
			scrollPane.setOpaque(false);
			if(!Environment.isLinux()){
				scrollPane.setBounds(SCROLL_PANE_BOUNDS);
			}else{
				scrollPane.setBounds(SCROLL_PANE_LINUX_BOUNDS);
			}
			scrollPane.setViewportView(getDisclaimerArea());
		}
		return scrollPane;
	}

	private JLabel getTitleDisclaimerLabel() {
		if (titleDisclamerLabel == null) {
			titleDisclamerLabel = new JLabel();
			titleDisclamerLabel.setName(SynthFonts.BOLD_FONT14_WHITE);
			titleDisclamerLabel.setBounds(TITLE_DISCLAIMER_LABEL_BOUNDS);
		}
		return titleDisclamerLabel;
	}

	private JButton getAcceptButton() {
		if (acceptButton == null) {
			acceptButton = new JButton();
			acceptButton.setName(ACCEPT_BUTTON_NAME);
			acceptButton.setBounds(ACCEPT_BUTTON_BOUNDS);
			if(Environment.isLinux()){
				acceptButton.setLocation(ACCEPT_BUTTON_LINUX_LOCATION);
			}
			acceptButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					hidePanel();
					acceptDisclaimerEvent.fire(new ObservValue<Boolean>(Boolean.TRUE));
				}
			});
		}
		return acceptButton;
	}

	private JButton getRejectButton() {
		if (rejectButton == null) {
			rejectButton = new JButton();
			rejectButton.setName(REJECT_BUTTON_NAME);
			rejectButton.setBounds(REJECT_BUTTON_BOUNDS);
			if(Environment.isLinux()){
				rejectButton.setLocation(REJECT_BUTTON_LINUX_LOCATION);
			}
			rejectButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					hidePanel();
					acceptDisclaimerEvent.fire(new ObservValue<Boolean>(Boolean.FALSE));
				}
			});
		}
		return rejectButton;
	}

	private void hidePanel() {
		this.setVisible(false);
	}

	public ObserverCollection<ObservValue<Boolean>> acceptDisclaimer() {
		return acceptDisclaimerEvent;
	}

	@Override
	public void internationalize(Messages messages) {
		rejectButton.setText(messages.getMessage("disclaimer.rejectButton"));
		acceptButton.setText(messages.getMessage("disclaimer.acceptButton"));
		titleDisclamerLabel.setText(messages.getMessage("disclaimer.title"));
		if(!Environment.isLinux()){
			loadDisclaimer(messages.getMessage("disclaimer.file"));
		}else{
			loadDisclaimer(messages.getMessage("disclaimer.linux.file"));
		}
		getRejectButton().setToolTipText(messages.getMessage("tooltip.rejectNewUsr"));
		getAcceptButton().setToolTipText(messages.getMessage("tooltip.acceptNewUsr"));
	}

	private void loadDisclaimer(String url) {
		try {
			getDisclaimerArea().setPage(new ClassPathResource(url).getURL());
		} catch (IOException e) {
			log.error(e, e);
		}
	}

	@Override
	public void removeMessages(Messages messages) {
		messages.remove(this);
	}

	@Override
	public void setMessages(Messages messages) {
		messages.add(this);
	}

}
