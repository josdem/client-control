package com.all.login.view;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JWindow;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.all.core.common.view.SynthFonts;
import com.all.core.common.view.transparency.TransparencyManagerFactory;

public class SplashWindow extends JWindow {

	private static final Log LOG = LogFactory.getLog(SplashWindow.class);

	private static final Rectangle MESSAGE_LABEL_BOUNDS = new Rectangle(55, 220, 350, 14);
	private static final Rectangle PROGRESS_BAR_BOUNDS = new Rectangle(53, 245, 545, 12);

	private static final String ARTIFACT_VERSION = "artifact.version";
	private static final String ARTIFACT_DESCRIPTION = "artifact.description";

	private static final String PROP_FILE = "config/clientSettings.properties";

	private static final long serialVersionUID = -4580366041236701085L;

	private static final int SPLASH_WIDTH = 650;
	private static final int SPLASH_HEIGHT = 340;
	private static final String VERSION_LABEL_NAME = "textVersion";
	private static final String PROGRESS_BAR_NAME = "bigProgressBar";// bigProgressBar
	private static final int PROGRESS_BAR_DEFAULT_VALUE = 0;
	private static final int PROGRESS_BAR_MAXIMUM_VALUE = 100;

	private Properties clientSettings;
	private String version;

	private static final int MINIMUM_DURATION = 500;

	private JPanel contentPanel = null;
	private JLabel messageLabel = null;
	private JSlider progressBar = null;

	private JLabel versionLabel;

	private String splashMessage;

	public SplashWindow() {
		super(TransparencyManagerFactory.getManager().getTranslucencyCapableGC());
		readPropertiesFile();
		initialize();
	}

	private void initialize() {
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screen.width - SPLASH_WIDTH) / 2;
		int y = (screen.height - SPLASH_HEIGHT) / 2;
		setBounds(x, y, SPLASH_WIDTH, SPLASH_HEIGHT);
		setContentPane(getSplashPanel());
		updateProgress(0);
		TransparencyManagerFactory.getManager().setWindowOpaque(this, false);
	}

	public void addProgress(int delta) {
		updateProgress(progressBar.getValue() + delta);
	}

	public void updateProgress(int newValue) {
		progressBar.setValue(newValue);
	}

	public void loadCompleted() {
		progressBar.setValue(100);
		try {
			Thread.sleep(MINIMUM_DURATION);
		} catch (Exception e) {
			LOG.error(e);
		}
	}

	private JLabel getVersionLabel() {
		if (versionLabel == null) {
			versionLabel = new JLabel();
			versionLabel.setName(VERSION_LABEL_NAME);
			versionLabel.setText(clientSettings.getProperty(ARTIFACT_DESCRIPTION) + " "
					+ clientSettings.getProperty(ARTIFACT_VERSION));
			versionLabel.setBounds(55, 160, 350, 25);
		}
		return versionLabel;
	}

	private JPanel getSplashPanel() {
		if (contentPanel == null) {
			contentPanel = new JPanel();
			contentPanel.setName("splashWindowBackgroundShadowed");
			contentPanel.setLayout(null);
			contentPanel.add(getMessageLabel());
			contentPanel.add(getProgressBar());
			contentPanel.add(getVersionLabel());
		}
		return contentPanel;
	}

	private JLabel getMessageLabel() {
		if (messageLabel == null) {
			messageLabel = new JLabel();
			messageLabel.setName(SynthFonts.PLAIN_FONT11_WHITE);
			messageLabel.setText(splashMessage);
			messageLabel.setBounds(MESSAGE_LABEL_BOUNDS);
		}
		return messageLabel;
	}

	private JSlider getProgressBar() {
		if (progressBar == null) {
			progressBar = new JSlider();
			progressBar.setName(PROGRESS_BAR_NAME);
			progressBar.setMaximum(PROGRESS_BAR_MAXIMUM_VALUE);
			progressBar.setRequestFocusEnabled(false);
			progressBar.setValue(PROGRESS_BAR_DEFAULT_VALUE);
			progressBar.setPaintLabels(false);
			progressBar.setOpaque(false);
			progressBar.setFocusable(false);
			progressBar.setEnabled(false);
			progressBar.setBounds(PROGRESS_BAR_BOUNDS);
		}
		return progressBar;
	}

	public void readPropertiesFile() {
		try {
			InputStream is = SplashWindow.class.getClassLoader().getResourceAsStream(PROP_FILE);
			clientSettings = new Properties();
			clientSettings.load(is);
			version = clientSettings.getProperty(ARTIFACT_VERSION) + " " + Locale.getDefault();
			// if (defaultLanguage != null && defaultLanguage.startsWith("es_")) {
			// splashMessage = Messages.getI18NProperty("splash.es_MX.message");
			// } else {
			// splashMessage = Messages.getI18NProperty("splash.en_US.message");
			// }
			is.close();
			LOG.info("Version: " + version);
		} catch (Exception e) {
			LOG.error("Failed to read from " + PROP_FILE + " file.");
		}
	}
}