package com.all.client.components;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import javax.annotation.PreDestroy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.all.client.data.ShortFilePathNative;
import com.all.commons.Environment;
import com.all.shared.model.Track;
import com.sun.media.jmc.MediaProvider;
import com.sun.media.jmc.control.AudioControl;
import com.sun.media.jmc.event.DurationChangedEvent;
import com.sun.media.jmc.event.MediaDurationListener;
import com.sun.media.jmc.event.MediaStateEvent;
import com.sun.media.jmc.event.MediaStateListener;

@Component
public class AllMediaProviderImpl extends MediaProvider implements AllMediaProvider, MediaStateListener,
		MediaDurationListener {
	private final static Log log = LogFactory.getLog(AllMediaProviderImpl.class);

	private Track track;

	private ValidatedEncapsulated<AllMediaProviderState> state = new ValidatedEncapsulated<AllMediaProviderState>(
			AllMediaProviderState.STOPED) {
		public void afterSet(AllMediaProviderState value) {
			log.info("Player setting state to: " + value);
		};
	};

	private AllMediaListener listener;

	public AllMediaProviderImpl() {
		super();
		this.listener = AllMediaListener.EMPTY;
		super.addMediaStateListener(this);
		super.addMediaDurationListener(this);
		super.setRepeating(false);
	}

	@PreDestroy
	public void destroy() {
		state.set(AllMediaProviderState.DESTROYED);
		track = null;
		if (super.isPlaying()) {
			super.pause();
			super.setMediaTime(0.0);
		}
	}

	public synchronized void play(Track track, File file) {
		stop();
		log.info("PLAY");
		if (state.set(AllMediaProviderState.STARTING)) {
			this.track = track;
			try {
				URI fileURI = createURI(file);
				log.info("setting player source to: " + fileURI);
				this.setSource(fileURI);
			} catch (Exception e) {
				try {
					stop();
				} catch (Throwable e1) {
				}
			}
		}
		if (state.set(AllMediaProviderState.PLAYING)) {
			log.info("Player playing: " + track);
			super.play();
		}
	}

	@Override
	public synchronized void play() {
		if (state.set(AllMediaProviderState.PLAYING)) {
			super.play();
		}
	}

	public void stop() {
		if (state.set(AllMediaProviderState.STOPED)) {
			track = null;
			super.pause();
			super.setMediaTime(0.0);
		}
	}

	public synchronized void pause() {
		log.info("PAUSE");
		if (state.set(AllMediaProviderState.PAUSED)) {
			super.pause();
		} else if (state.set(AllMediaProviderState.PLAYING)) {
			if (super.isPlaying()) {
				super.play();
				super.pause();
				super.play();
			}
			super.pause();
		}
	}

	public Track getTrack() {
		return track;
	}

	public boolean isPlaying(Track track) {
		return state.is(AllMediaProviderState.PLAYING, AllMediaProviderState.PAUSED) && track.equals(this.track);
	}

	public boolean isPlaying() {
		return state.is(AllMediaProviderState.PLAYING, AllMediaProviderState.PAUSED);
	}

	public double setMediaTime(double time) {
		if (state.is(AllMediaProviderState.PLAYING, AllMediaProviderState.PAUSED)) {
			return super.setMediaTime(time);
		}
		return 0;
	}

	public double getDuration() {
		if (state.is(AllMediaProviderState.PLAYING, AllMediaProviderState.PAUSED)) {
			return super.getDuration();
		}
		return 0;
	}

	public void setVolume(float playerVolume) {
		if (state.is(AllMediaProviderState.PLAYING, AllMediaProviderState.PAUSED)) {
			AudioControl audioControl = super.getControl(AudioControl.class);
			audioControl.setVolume(playerVolume);
			if (audioControl != null) {
				if (playerVolume <= 0) {
					audioControl.setMute(true);
				} else {
					audioControl.setMute(false);
				}
			}
		}
	}

	@Override
	public void mediaDurationChanged(DurationChangedEvent arg0) {
		listener.mediaDurationChanged(track, arg0.getDuration());
	}

	@Override
	public void endOfMediaReached(MediaStateEvent arg0) {
		Track track = this.track;
		double duration = getDuration();
		try {
			stop();
		} catch (Throwable e) {
		}
		listener.endOfMediaReached(track, duration);
	}

	@Override
	public void playerRepeated(MediaStateEvent arg0) {
	}

	@Override
	public void playerStarted(MediaStateEvent arg0) {
		listener.onPlaying(track);
	}

	@Override
	public void playerStopped(MediaStateEvent arg0) {
	}

	@Override
	public void stopTimeReached(MediaStateEvent arg0) {
	}

	private static final URI createURI(File soundFile) throws URISyntaxException {
		URI fileURI;
		if (Environment.isWindows()) {
			String shortPath = ShortFilePathNative.getShortPath(soundFile);
			String filePath = "file:/" + shortPath.replaceAll("\\\\", "/");
			fileURI = new URI(soundFile.toURI().toASCIIString());
			log.debug("media file path " + filePath);
		} else {
			fileURI = soundFile.toURI();
		}
		return fileURI;
	}

	@Override
	public void setAllMediaListener(AllMediaListener listener) {
		this.listener = listener == null ? AllMediaListener.EMPTY : listener;
	}
}
