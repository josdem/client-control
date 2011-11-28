package com.all.client.components;

import javax.swing.JButton;

public interface PlayerUiState extends State {
	String PLAY_BUTTON = "playButton"; 
	String STOP_BUTTON = "stopButton";
	String PAUSE_BUTTON = "pauseButton";
	String BACK_BUTTON = "rewindButton";
	String NEXT_BUTTON = "forwardButton";
	
	
	void changeButtons(JButton playButton, JButton nextButton, JButton backButton);
}
