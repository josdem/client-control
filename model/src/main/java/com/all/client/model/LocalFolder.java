package com.all.client.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.all.client.data.Hashcoder;
import com.all.shared.model.Folder;
import com.all.shared.model.Playlist;
import com.all.shared.model.Track;
import com.all.shared.sync.SyncAble;
import com.all.shared.sync.SyncAbleAbstractImpl;
import com.all.shared.sync.SyncUpdateAble;

@Entity(name = "Folder")
@Table(name = "Folder")
public class LocalFolder extends SyncAbleAbstractImpl implements Folder {

	private static final long serialVersionUID = 7192286912254518183L;

	@Transient
	private static final Log log = LogFactory.getLog(LocalFolder.class);

	public static final int NAME_MAXLENGTH = 80;

	public static final int NAME_MINLENGTH = 1;
	@Id
	@SyncUpdateAble
	private String hashcode;
	@Column
	@SyncUpdateAble
	private String name;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = LocalPlaylist.class)
	@JoinColumn(name = "folderId")
	private Set<Playlist> playlists = new LinkedHashSet<Playlist>();
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	@SyncUpdateAble
	private Date modifiedDate;
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;

	@Deprecated
	public LocalFolder() {
		creationDate = new Date();
		hashcode = createHashcode(name, creationDate);
	}

	public LocalFolder(String name) {
		setName(name);
		creationDate = new Date();
		hashcode = createHashcode(name, creationDate);
	}

	@Override
	public List<Playlist> getPlaylists() {
		return new ArrayList<Playlist>(this.playlists);
	}

	public final void setName(String newName) {
		if (newName != null) {
			if (newName.length() > NAME_MAXLENGTH) {
				throw new IllegalArgumentException("folder name can not have more than " + NAME_MAXLENGTH + " characters");
			} else if (newName.trim().length() < NAME_MINLENGTH) {
				throw new IllegalArgumentException("folder name can not have less than " + NAME_MINLENGTH + " characters");
			}
		}
		name = newName;
		if (name != null && name.length() > NAME_MAXLENGTH) {
			name = name.substring(0, NAME_MAXLENGTH);
		}
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}

	public Playlist add(Playlist playlist) {
		if (playlist != null && !playlists.contains(playlist)) {
			Date now = new Date();
			this.setModifiedDate(now);
			if (playlist instanceof LocalPlaylist) {
				((LocalPlaylist) playlist).setModifiedDate(now);
			}
			((LocalPlaylist) playlist).setParentFolder(this);
			playlists.add(playlist);
		}
		return playlist;
	}

	public void remove(Playlist playlist) {
		playlists.remove(playlist);
	}

	public void updatePlaylist(Playlist playlist) {
		setModifiedDate(new Date());
	}

	public int compareTo(Folder that) {
		if (this.name == null && that.getName() == null) {
			return 0;
		}
		if (this.name == null) {
			return 1;
		}
		if (that.getName() == null) {
			return -1;
		}
		return this.name.compareToIgnoreCase(that.getName());
	}

	@Override
	public Playlist getPlaylist() {
		Set<Track> tracks = new HashSet<Track>();
		for (Playlist playlist : playlists) {
			tracks.addAll(playlist.getTracks());
		}
		return new LocalPlaylist(tracks);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		boolean result = false;
		if (obj instanceof LocalFolder) {
			LocalFolder f = (LocalFolder) obj;
			if (f.hashcode != null && this.hashcode != null) {
				result = f.hashcode.equals(this.hashcode);
			}
		}
		return result;
	}

	@Override
	public int hashCode() {
		return hashcode == null ? super.hashCode() : hashcode.hashCode();
	}

	public void setHashcode(String hashcode) {
		this.hashcode = hashcode;
	}

	@Override
	public String getHashcode() {
		return this.hashcode;
	}

	public final String createHashcode(String name, Date creationDate) {
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

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Override
	public Date getModifiedDate() {
		return modifiedDate;
	}

	public Iterable<Track> getTracks() {
		return getPlaylist().getTracks();
	}

	@Override
	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date date) {
		creationDate = date;
	}

	@Override
	public String getSyncAbleId() {
		return this.hashcode;
	}

	public void clone(SyncAble entity) {
		LocalFolder folder = (LocalFolder) entity;
		this.setModifiedDate(folder.getModifiedDate());
		this.setName(folder.getName());
	}

	@Override
	public boolean isNewContent() {
		for (Playlist playlist : getPlaylists()) {
			if (playlist.isNewContent()) {
				return true;
			}
		}
		return false;
	}

}
