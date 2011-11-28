package com.all.client.model;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.annotations.Index;

import com.all.client.data.Hashcoder;
import com.all.shared.model.Folder;
import com.all.shared.model.Playlist;
import com.all.shared.model.Track;
import com.all.shared.sync.ComplexSyncAbleAbstractImpl;
import com.all.shared.sync.ComplexSyncAbleField;
import com.all.shared.sync.SyncAble;
import com.all.shared.sync.SyncUpdateAble;

@Entity(name = "Playlist")
@Table(name = "Playlist")
public class LocalPlaylist extends ComplexSyncAbleAbstractImpl implements Playlist {

	private static final Log log = LogFactory.getLog(LocalPlaylist.class);

	private static final long serialVersionUID = 2333941440940269474L;
	@Id
	@SyncUpdateAble
	private String hashcode;
	@Column
	@SyncUpdateAble
	String name;
	@Column
	private String owner = "local";
	@Column
	@SyncUpdateAble
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	@SyncUpdateAble
	private Date lastPlayed;
	@OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<PlaylistTrack> playlistTracks = new ArrayList<PlaylistTrack>();
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER, targetEntity = LocalFolder.class, optional = true)
	@JoinColumn(name = "folderId")
	@Index(name = "PARENT_FOLDER_IDX")
	@SyncUpdateAble
	@ComplexSyncAbleField
	private Folder parentFolder;
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;

	@Transient
	private boolean smartPlaylist = false;
	@Transient
	private List<Track> smartTracks;
	@Transient
	private static final Comparator<PlaylistTrack> playlistTrackComparator = new PlaylistTrackComparator();

	public static LocalPlaylist createUntitledPlaylist(Iterable<? extends Playlist> playlists, String baseName) {
		Set<String> names = new HashSet<String>();
		for (Playlist p : playlists) {
			names.add(p.getName());
		}
		int i = 1;
		String name;
		String zero;
		do {
			if (i <= 9) {
				zero = "0";
			} else {
				zero = "";
			}
			name = baseName + zero + i;
			i++;
		} while (names.contains(name));
		LocalPlaylist playlist = new LocalPlaylist(name);
		return playlist;
	}

	public LocalPlaylist() {
		creationDate = new Date();
		hashcode = createHashcode(this.creationDate);
	}

	public LocalPlaylist(String name) {
		setName(name);
		creationDate = new Date();
		hashcode = createHashcode(name, this.creationDate);
	}

	public LocalPlaylist(Collection<Track> fixedTracks) {
		this.creationDate = new Date();
		this.hashcode = createHashcode(this.creationDate);
		this.smartTracks = new ArrayList<Track>(fixedTracks);
		this.smartPlaylist = true;
	}

	public void add(Collection<Track> allTracks) {
		for (Track track : allTracks) {
			add(track);
		}
	}

	@Override
	public final boolean contains(Track track) {
		PlaylistTrack playlistTrack = getPlaylistTrack(track);
		if (playlistTrack != null) {
			return true;
		}
		return false;
	}

	private PlaylistTrack getPlaylistTrack(Track track) {
		for (PlaylistTrack plt : playlistTracks) {
			if (plt.getTrack().getHashcode().equals(track.getHashcode())) {
				return plt;
			}
		}
		return null;
	}

	public void add(Track track) {
		if (track != null && !contains(track)) {
			this.modifiedDate = new Date();
			PlaylistTrack playlistTrack = getPlaylistTrack(track);
			if (playlistTrack == null) {
				playlistTrack = createPlaylistTrack(track);
				playlistTracks.add(playlistTrack);
			}
		}
	}

	private PlaylistTrack createPlaylistTrack(Track track) {
		PlaylistTrack playlistTrack = new PlaylistTrack((LocalTrack) track, this);
		playlistTrack.setTrackPosition(playlistTracks.size());
		return playlistTrack;
	}

	@Override
	public List<Track> getTracks() {
		if (isSmartPlaylist()) {
			return smartTracks;
		}
		Collections.sort(playlistTracks, getPlaylistTrackComparator());
		List<Track> tracks = new ArrayList<Track>();
		for (PlaylistTrack playlistTrack : playlistTracks) {
			tracks.add(playlistTrack.getTrack());
		}
		return tracks;
	}

	private static Comparator<PlaylistTrack> getPlaylistTrackComparator() {
		return playlistTrackComparator;
	}

	@Override
	public final String toString() {
		return name == null ? "<Unnamed>" : name;
	}

	public final void setName(String newName) {
		if (newName.trim().length() < NAME_MINLENGTH) {
			throw new IllegalArgumentException("Playlist name can not have less than " + NAME_MINLENGTH + " characters");
		}
		name = newName;
		if (name != null && name.length() > NAME_MAXLENGTH) {
			name = name.substring(0, NAME_MAXLENGTH);
		}
	}

	@Override
	public final String getOwner() {
		return owner;
	}

	public final void setOwner(String owner) {
		this.owner = owner;
	}

	@Override
	public final int compareTo(Playlist that) {
		if (this.getName() == null && that.getName() == null) {
			return 0;
		}
		if (this.getName() == null) {
			return 1;
		}
		if (that.getName() == null) {
			return -1;
		}
		return this.getName().compareToIgnoreCase(that.getName());
	}

	@Override
	public final boolean isEmpty() {
		return playlistTracks.isEmpty();
	}

	@Override
	public final int trackCount() {
		if (isSmartPlaylist()) {
			return smartTracks != null ? smartTracks.size() : 0;
		}
		return playlistTracks != null ? playlistTracks.size() : 0;
	}

	@Override
	public final Track getTrack(int position) {
		if (isSmartPlaylist()) {
			return smartTracks.get(position);
		}
		for (PlaylistTrack playlistTrack : playlistTracks) {
			if (playlistTrack.getTrackPosition() == position) {
				return playlistTrack.getTrack();
			}
		}
		return null;
	}

	public final void setParentFolder(Folder folder) {
		if (parentFolder != null) {
			((LocalFolder) parentFolder).remove(this);
		}
		this.parentFolder = (LocalFolder) folder;
	}

	public PlaylistTrack remove(Track track) {
		PlaylistTrack playlistTrack = getPlaylistTrack(track);
		if (playlistTrack != null) {
			playlistTracks.remove(playlistTrack);
			this.modifiedDate = new Date();
		}
		return playlistTrack;
	}

	@Override
	public final Folder getParentFolder() {
		return parentFolder;
	}

	@Override
	public final boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		boolean result = false;
		if (obj instanceof LocalPlaylist) {
			LocalPlaylist playlist = (LocalPlaylist) obj;
			if (this.isSmartPlaylist() && playlist.isSmartPlaylist()) {
				if (name == null) {
					return false; // TODO: review this!
				}
				result = name.equals(playlist.name);
			} else if (playlist.hashcode != null && this.hashcode != null) {
				result = playlist.hashcode.equals(this.hashcode);
			}
		}
		return result;
	}

	@Override
	public final int hashCode() {
		if (isSmartPlaylist()) {
			if (name != null) {
				return name.hashCode();
			}
		} else if (hashcode != null) {
			return hashcode.hashCode();
		}
		return super.hashCode();
	}

	@Override
	public final Date getModifiedDate() {
		return modifiedDate;
	}

	public final void setModifiedDate(Date date) {
		this.modifiedDate = date;
	}

	@Override
	public final int trackPosition(Track track) {
		PlaylistTrack plTrack = getPlaylistTrack(track);
		if (plTrack == null) {
			return -1;
		}
		return plTrack.getTrackPosition();
	}

	public final void setLastPlayed(Date lastPlayed) {
		this.lastPlayed = lastPlayed;
	}

	@Override
	public final Date getLastPlayed() {
		return lastPlayed;
	}

	public final void setSmartPlaylist(boolean isSmartPlaylist) {
		this.smartPlaylist = isSmartPlaylist;
	}

	public final boolean isSmartPlaylist() {
		return smartPlaylist;
	}

	public final String getName() {
		return name;
	}

	public final void setHashcode(String hashcode) {
		this.hashcode = hashcode;
	}

	@Override
	public final String getHashcode() {
		return this.hashcode;
	}

	private String createHashcode(Date creationDate) {
		return createHashcode(String.valueOf(new Random().nextLong()), creationDate);
	}

	private String createHashcode(String name, Date creationDate) {
		String createdHashcode = name + creationDate.toString() + new Random().nextLong();
		try {
			MessageDigest md = MessageDigest.getInstance("SHA1");
			md.update(createdHashcode.getBytes());
			createdHashcode = Hashcoder.toHex(md.digest());
		} catch (NoSuchAlgorithmException e) {
			log.error(e, e);
		}
		return createdHashcode;

	}

	// AND YOU SHALL NOT INVOKE THIS ONE
	public final void _moveTracks(List<Track> movedTracks, int row) {
		int currentRow = row;
		List<Track> tracks = null;
		if (!isSmartPlaylist()) {
			tracks = new ArrayList<Track>(getTracks());
		} else {
			tracks = getTracks();
		}
		for (Track track : movedTracks) {
			int indexOf = tracks.indexOf(track);
			if (indexOf >= 0 && indexOf <= currentRow) {
				currentRow--;
			}
		}
		tracks.removeAll(movedTracks);
		tracks.addAll(currentRow, movedTracks);
		if (!isSmartPlaylist()) {
			int i = 0;
			for (Track track : tracks) {
				PlaylistTrack playlistTrack = getPlaylistTrack(track);
				playlistTrack.setTrackPosition(i);
				i++;
			}
		}

	}

	public final void setPlaylistTracks(List<PlaylistTrack> playlistTracks) {
		this.playlistTracks = playlistTracks;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public String getSyncAbleId() {
		return this.hashcode;
	}

	public void clone(SyncAble entity) {
		LocalPlaylist pl = (LocalPlaylist) entity;
		this.setLastPlayed(pl.getLastPlayed());
		this.setModifiedDate(pl.getModifiedDate());
		this.setName(pl.getName());
		this.setParentFolder(pl.getParentFolder());
	}

	@Override
	public boolean isNewContent() {
		try {
			for (PlaylistTrack playlistTtrack : playlistTracks) {
				LocalTrack track = playlistTtrack.getTrack();
				if (track.isNewContent()) {
					return true;
				}
			}
		} catch (Exception e) {
			log.error(e, e);
		}
		return false;
	}

	public void setSmartTracks(List<Track> smartTracks) {
		this.smartTracks = smartTracks;
	}

	private static final class PlaylistTrackComparator implements Comparator<PlaylistTrack>, Serializable {
		private static final long serialVersionUID = 1L;

		@Override
		public int compare(PlaylistTrack o1, PlaylistTrack o2) {
			return o1.getTrackPosition() - o2.getTrackPosition();
		}
	}

}
