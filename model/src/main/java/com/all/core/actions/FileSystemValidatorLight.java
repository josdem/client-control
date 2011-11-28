package com.all.core.actions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.all.shared.model.Folder;
import com.all.shared.model.Playlist;
import com.all.shared.model.Root;

public class FileSystemValidatorLight {

	private final List<File> folderList = new ArrayList<File>();
	private final List<File> trackList = new ArrayList<File>();
	private final List<File> playlistList = new ArrayList<File>();
	private final boolean fromExternalDevicesPanel;

	public FileSystemValidatorLight(boolean fromExternalDevicesPanel, File... files) {
		this.fromExternalDevicesPanel = fromExternalDevicesPanel;
		for (File file : files) {
			validateFile(file);
		}
	}

	public FileSystemValidatorLight(boolean fromExternalDevicesPanel, Iterable<File> files) {
		this.fromExternalDevicesPanel = fromExternalDevicesPanel;
		for (File file : files) {
			validateFile(file);
		}
	}

	public boolean isFromExternalDevicesPanel() {
		return fromExternalDevicesPanel;
	}

	private void validateFile(File file) {
		if (file.isHidden()) {
			return;
		}
		if (file.isDirectory()) {
			File[] listFiles = file.listFiles();
			boolean fold = false;
			for (File file2 : listFiles) {
				if (file2.isDirectory() && !file2.isHidden()) {
					fold = true;
					break;
				}
			}
			if (fold) {
				folderList.add(file);
			} else {
				playlistList.add(file);
			}

		} else {
			trackList.add(file);
		}
	}

	public List<File> getFolders() {
		return folderList;
	}

	public List<File> getTracks() {
		return trackList;
	}

	public List<File> getPlaylists() {
		return playlistList;
	}

	public boolean hasPlaylists() {
		return isListNotEmpty(playlistList);
	}

	public boolean hasFolders() {
		return isListNotEmpty(folderList);
	}

	public boolean hasTracks() {
		return isListNotEmpty(trackList);
	}

	private boolean isListNotEmpty(List<?> list) {
		return list != null && !list.isEmpty();
	}

	public boolean hasError() {
		return false;
	}

	public boolean canBeInside(Object target) {
		Object receivedTarget = target;
		if (hasError()) {
			return false;
		}
		if (receivedTarget == null) {
			receivedTarget = Root.class;
		}
		if (!(receivedTarget instanceof Class<?>)) {
			if (receivedTarget instanceof Playlist) {
				receivedTarget = Playlist.class;
			}
			if (receivedTarget instanceof Folder) {
				receivedTarget = Folder.class;
			}
			if (receivedTarget instanceof Root) {
				receivedTarget = Root.class;
			}
		}
		if (receivedTarget == Playlist.class && (hasFolders() || hasPlaylists())) {
			return false;
		}
		if (receivedTarget == Folder.class && (hasFolders())) {
			return false;
		}
		if (receivedTarget == Root.class) {
			return true;
		}
		return true;
	}

}
