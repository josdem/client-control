package com.all.shared.stats;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import com.all.shared.stats.usage.UserActions;

@Entity
public class MediaImportStat extends AbstractAllStat {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private long timestamp;
	@NotNull
	private String email;

	private int importTypeAction = -1;

	private int totalTracks;

	private int totalPlaylists;

	private int totalFolders;

	public static enum ImportType {
		ITUNES(UserActions.Player.IMPORT_MEDIA_ITUNES), SYSTEM_DRAG(UserActions.Player.IMPORT_MEDIA_SYSTEM_DRAG), FILE_CHOOSER(
				UserActions.Player.IMPORT_MEDIA_FILECHOOSER), REMOTE_LIBRARY(UserActions.Player.IMPORT_MEDIA_REMOTE_LIBRARY), EXTERNAL_DEVICES(
				UserActions.Player.IMPORT_MEDIA_MC_EXTERNAL_DEVICE);

		private final int action;

		private ImportType(int action) {
			this.action = action;

		}

		public int action() {
			return action;
		}
	}

	@Deprecated
	public MediaImportStat() {
	}

	public MediaImportStat(String email, ImportType type, int totalTracks, int totalPlaylists, int totalFolders) {
		this.email = email;
		this.importTypeAction = type.action();
		this.totalTracks = totalTracks;
		this.totalPlaylists = totalPlaylists;
		this.totalFolders = totalFolders;
	}

	@Override
	public String getEmail() {
		return email;
	}

	@Deprecated
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public Serializable getId() {
		return id;
	}

	@Override
	public long getTimestamp() {
		return timestamp;
	}

	@Override
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public int getImportTypeAction() {
		return importTypeAction;
	}

	@Deprecated
	public void setImportTypeAction(int importTypeAction) {
		this.importTypeAction = importTypeAction;
	}

	public int getTotalTracks() {
		return totalTracks;
	}

	@Deprecated
	public void setTotalTracks(int totalTracks) {
		this.totalTracks = totalTracks;
	}

	public int getTotalPlaylists() {
		return totalPlaylists;
	}

	@Deprecated
	public void setTotalPlaylists(int totalPlaylists) {
		this.totalPlaylists = totalPlaylists;
	}

	public int getTotalFolders() {
		return totalFolders;
	}

	@Deprecated
	public void setTotalFolders(int totalFolders) {
		this.totalFolders = totalFolders;
	}
}
