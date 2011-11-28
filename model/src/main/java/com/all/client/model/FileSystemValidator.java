package com.all.client.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.all.client.model.format.FileFormatSupportedValidator;
import com.all.client.util.FileUtil;
import com.all.core.actions.FileSystemValidatorLight;
import com.all.shared.model.Folder;
import com.all.shared.model.Playlist;
import com.all.shared.model.Root;

public class FileSystemValidator {

	BrokenLinkValidator brokenLinkValidator = new BrokenLinkValidator();
	FileFormatSupportedValidatorFactory fileFormatSupportedValidatorFactory = new FileFormatSupportedValidatorFactory();
	private static final String JPG_EXTENSION = "JPG";
	private List<Container<List<FileContainer>>> folderList = new ArrayList<Container<List<FileContainer>>>();
	private List<File> trackList = new ArrayList<File>();
	private List<FileContainer> playlistList = new ArrayList<FileContainer>();
	private List<File> errorList = new ArrayList<File>();
	private List<File> errorMessages = new ArrayList<File>();
	private final boolean fromExternalDevicesPanel;

	public FileSystemValidator(boolean fromExternalDevicesPanel) {
		this.fromExternalDevicesPanel = fromExternalDevicesPanel;
	}

	public FileSystemValidator(boolean fromExternalDevicesPanel, File... files) {
		this(fromExternalDevicesPanel);
		init(files);
	}

	public FileSystemValidator(boolean fromExternalDevicesPanel, List<File> files) {
		this(fromExternalDevicesPanel);
		if (files != null) {
			init(files.toArray(new File[] {}));
		}
	}

	public FileSystemValidator(boolean fromExternalDevicesPanel, List<File>... files) {
		this(fromExternalDevicesPanel);
		if (files != null) {
			for (List<File> list : files) {
				init(list.toArray(new File[] {}));
			}
		}
	}

	@SuppressWarnings("unchecked")
	public FileSystemValidator(FileSystemValidatorLight validator) {
		this(validator.isFromExternalDevicesPanel(), validator.getFolders(), validator.getPlaylists(), validator.getTracks());
	}

	private void init(File... files) {
		for (File file : files) {
			validateFile(file);
		}
	}

	public boolean isFromExternalDevicesPanel() {
		return fromExternalDevicesPanel;
	}

	/**
	 * Verifies the <code>file</code> is not hidden and it is a supported file so
	 * it could be treated as a track or as an application folder
	 * 
	 * @param file
	 */
	private void validateFile(File file) {
		if (!file.exists()) {
			addError(file);
			return;
		}
		if (file.isFile()) {
			addTrack(trackList, file);
		} else {
			boolean folderHasSubFolders = false;
			File[] listFiles = file.listFiles();
			if (listFiles == null) {
				return;
			}
			for (File file2 : listFiles) {
				if (isDirectory(file2)) {
					folderHasSubFolders = true;
				}
			}
			if (folderHasSubFolders) {
				addFolder(folderList, file);
			} else {
				addPlaylist(playlistList, file);
			}
		}
	}

	private boolean isDirectory(File file2) {
		return file2 != null && file2.isDirectory() && !file2.isHidden();
	}

	private void addTrack(List<File> trackList, File file) {
		if (this.isTrackValid(file)) {
			trackList.add(file);
		} else {
			addMessageOnInvalidTrack(file);
		}
	}

	private void addFolder(List<Container<List<FileContainer>>> folderList, File file) {
		if (isDirectoryAndValid(file)) {
			List<FileContainer> playlists = new ArrayList<FileContainer>();
			List<File> tracks = new ArrayList<File>();
			File[] listFiles = file.listFiles();
			for (File file2 : listFiles) {
				if (file2.isDirectory()) {
					addPlaylist(playlists, file2);
				} else {
					addTrack(tracks, file2);
				}
			}
			if (!playlists.isEmpty()) {
				if (!tracks.isEmpty()) {
					playlists.add(new FileContainer(null, tracks));

				}
				Container<List<FileContainer>> folder = new Container<List<FileContainer>>();
				folder.setName(file.getName());
				folder.setContent(playlists);
				folderList.add(folder);
			} else {
				if (!tracks.isEmpty()) {
					addPlaylist(playlistList, file);
				} else {
					errorMessages.add(file);
				}
			}
		} else {
			if (!file.exists()) {
				addError(file);
			}
		}
	}

	private void addPlaylist(List<FileContainer> playlistList, File file) {
		if (isDirectoryAndValid(file)) {
			File[] listFiles = file.listFiles();
			List<File> possibleFiles = new ArrayList<File>(listFiles.length);
			for (File file2 : listFiles) {
				if (file2.isDirectory()) {
					addPlaylist(playlistList, file2);
				} else {
					addTrack(possibleFiles, file2);
				}
			}
			if (!possibleFiles.isEmpty()) {
				playlistList.add(new FileContainer(file.getName(), possibleFiles));
			} else {
				errorMessages.add(file);
			}
		}
	}

	private void addInvalidFile(File invalidFile) {
		if (!this.errorMessages.contains(invalidFile)) {
			this.errorMessages.add(invalidFile);
		}
	}

	/**
	 * Verifies <code>directory</code> is a system directory and not hidden
	 * 
	 * @param directory
	 * @return
	 */
	private boolean isDirectoryAndValid(File directory) {
		return (directory.isDirectory() && !this.isHidden(directory));
	}

	private void addError(File file) {
		if (!errorList.contains(file)) {
			errorList.add(file);
		}
	}

	private void addMessageOnInvalidTrack(File file) {
		if (file.isFile() && !isHidden(file)) {
			addInvalidFile(file);
		}
	}

	private boolean isTrackValid(File fileInFolder) {
		boolean isTrackValid = !this.brokenLinkValidator.isBrokenLink(fileInFolder);
		FileFormatSupportedValidator validator = this.fileFormatSupportedValidatorFactory.createValidator(fileInFolder);
		if (validator == null) {
			isTrackValid = false;
		} else {
			if (isTrackValid) {
				isTrackValid = !validator.isDrmProtected();
			}
			if (isTrackValid) {
				isTrackValid = validator.isAllowedToBeImportedByBusinessRule();
			}
		}
		return isTrackValid;
	}

	private boolean isHidden(File file) {
		return (file.isHidden() || file.getName().startsWith("."));
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

	public List<Container<List<FileContainer>>> getFolders() {
		return folderList;
	}

	public List<FileContainer> getPlaylists() {
		return playlistList;
	}

	public List<File> getTracks() {
		return trackList;
	}

	public List<File> getErrorMessages() {
		return errorMessages;
	}

	public boolean hasError() {
		return isListNotEmpty(errorList);
	}

	public List<File> getErrors() {
		return errorList;
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

	// TODO improve this method so the class accepts valid image files and is
	// extensible
	public boolean hasValidImageFile() {
		if (folderList.isEmpty() && playlistList.isEmpty() && trackList.isEmpty()) {
			File image = null;
			if (getErrorMessages().isEmpty() && getErrors().size() == 1) {
				image = getErrors().get(0);
			} else if (getErrors().isEmpty() && getErrorMessages().size() == 1) {
				image = getErrorMessages().get(0);
			}
			if (image != null) {
				String extension = FileUtil.getExtension(image);
				return extension.equalsIgnoreCase(JPG_EXTENSION);
			}
		}
		return false;
	}

	// TODO improve me
	public File getValidImageFile() {
		if (hasValidImageFile()) {
			return getErrors().isEmpty() ? getErrorMessages().get(0) : getErrors().get(0);
		}
		return null;
	}

	public int getTrackCount() {
		int playlistCount = 0;
		for (FileContainer playlistContainer : playlistList) {
			playlistCount += playlistContainer.getFileCount();
		}
		for (Container<List<FileContainer>> folderContainer : folderList) {
			List<FileContainer> fileContainerList = folderContainer.getContent();
			for (FileContainer playlistContainer : fileContainerList) {
				playlistCount += playlistContainer.getFileCount();
			}
		}
		return trackList.size() + playlistCount;
	}

}
