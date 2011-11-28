package com.all.shared.model;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import org.junit.After;
import org.junit.Test;

public class TestLazyPlaylist {
	private Date modifiedDate = new Date();
	private Date createdDate = new Date();
	private String id = "id";
	private String name = "name";
	private ExecutorService executor = Executors.newFixedThreadPool(3);

	@After
	public void teardown() {
		executor.shutdownNow();
	}

	@Test
	public void shouldCreateASimpleLazyPlaylist() throws Exception {
		Track track = mock(Track.class);
		ArrayList<Track> list = new ArrayList<Track>();
		list.add(track);
		TrackContainer trackSource = new SimpleTrackSource(0, null, list);
		Playlist playlist = new SimpleLazyPlaylist(name, id, createdDate, modifiedDate, trackSource);
		assertEquals(0, playlist.compareTo(playlist));
		assertTrue(playlist.contains(track));
		assertTrue(playlist.equals(playlist));
		assertEquals(createdDate, playlist.getCreationDate());
		assertEquals(id, playlist.getHashcode());
		assertEquals(modifiedDate, playlist.getLastPlayed());
		assertEquals(modifiedDate, playlist.getModifiedDate());
		assertEquals(name, playlist.getName());
		assertEquals("LAZY", playlist.getOwner());
		assertNull(playlist.getParentFolder());
		assertEquals(track, playlist.getTrack(0));
		List<Track> tracks = playlist.getTracks();
		assertArrayEquals(list.toArray(), tracks.toArray());
		assertFalse(playlist.isEmpty());
		assertTrue(playlist.isNewContent());
		assertFalse(playlist.isSmartPlaylist());
		assertEquals(1, playlist.trackCount());
		assertEquals(0, playlist.trackPosition(track));
	}

	@Test(timeout = 10000)
	public void shouldBlockAndLoad() throws Exception {
		Semaphore semaphore = new Semaphore(0);
		Track track = mock(Track.class);
		ArrayList<Track> list = new ArrayList<Track>();
		list.add(track);
		TrackContainer trackSource = new SimpleTrackSource(1, semaphore, list);
		Playlist playlist = new SimpleLazyPlaylist(name, id, createdDate, modifiedDate, trackSource);

		Semaphore initSemaphore = new Semaphore(0);
		Semaphore endSemaphore = new Semaphore(0);
		executor.execute(new SimpleTrackConsumer(playlist, initSemaphore, endSemaphore));
		executor.execute(new SimpleTrackConsumer(playlist, initSemaphore, endSemaphore));
		executor.execute(new SimpleTrackConsumer(playlist, initSemaphore, endSemaphore));
		initSemaphore.acquire(3);
		semaphore.release();
		endSemaphore.acquire(3);

	}

	class SimpleTrackConsumer implements Runnable {
		private final Playlist playlist;
		private final Semaphore initSemaphore;
		private final Semaphore endSemaphore;

		public SimpleTrackConsumer(Playlist playlist, Semaphore initSemaphore, Semaphore endSemaphore) {
			this.playlist = playlist;
			this.initSemaphore = initSemaphore;
			this.endSemaphore = endSemaphore;
		}

		@Override
		public void run() {
			initSemaphore.release();
			playlist.getTracks();
			endSemaphore.release();
		}
	}

	class SimpleTrackSource implements TrackContainer {
		private final int permits;
		private final Semaphore semaphore;
		private final ArrayList<Track> arrayList;

		public SimpleTrackSource(int permits, Semaphore semaphore, ArrayList<Track> arrayList) {
			this.permits = permits;
			this.semaphore = semaphore;
			this.arrayList = arrayList;
		}

		@Override
		public Iterable<Track> getTracks() {
			try {
				semaphore.acquire(permits);
			} catch (Exception e) {
			}
			return arrayList;
		}

	}
}

class SimpleLazyPlaylist extends LazyPlaylist {
	private static final long serialVersionUID = 1L;

	public SimpleLazyPlaylist(String name, String id, Date createdDate, Date modifiedDate, TrackContainer trackSource) {
		this.name = name;
		this.id = id;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		setTrackSource(trackSource);
	}

	private String owner = "LAZY";
	private final Date createdDate;
	private final Date modifiedDate;
	private final String name;
	private final String id;

	@Override
	public String getOwner() {
		return owner;
	}

	@Override
	public Date getModifiedDate() {
		return modifiedDate;
	}

	@Override
	public Date getCreationDate() {
		return createdDate;
	}

	@Override
	public Date getLastPlayed() {
		return modifiedDate;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getHashcode() {
		return id;
	}

	@Override
	public boolean isSmartPlaylist() {
		return false;
	}

	@Override
	public boolean isNewContent() {
		return true;
	}

	@Override
	public Folder getParentFolder() {
		return null;
	}

}
