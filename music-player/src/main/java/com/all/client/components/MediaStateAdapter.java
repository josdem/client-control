package com.all.client.components;

import com.sun.media.jmc.event.MediaStateEvent;
import com.sun.media.jmc.event.MediaStateListener;

public abstract class MediaStateAdapter implements MediaStateListener {

	@Override
	public void endOfMediaReached(MediaStateEvent arg0) {

	}

	@Override
	public void playerRepeated(MediaStateEvent arg0) {
	}

	@Override
	public void playerStarted(MediaStateEvent arg0) {

	}

	@Override
	public void playerStopped(MediaStateEvent arg0) {

	}

	@Override
	public void stopTimeReached(MediaStateEvent arg0) {

	}

}
