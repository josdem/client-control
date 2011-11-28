package com.all.core.events;

import com.all.shared.model.Folder;
import com.all.shared.model.Playlist;
import com.all.shared.model.Root;
import com.all.shared.model.SmartPlaylist;
import com.all.shared.model.TrackContainer;

public class ModelContainer {
	private final Root root;
	private final TrackContainer target;

	public ModelContainer(Root root, TrackContainer target) {
		this.root = root;
		this.target = target;
	}

	public Root getRoot() {
		return root;
	}

	public TrackContainer getTarget() {
		return target;
	}

	public boolean isModified(TrackContainer container) {
		if (target == null) {
			return true;
		}
		if (container instanceof SmartPlaylist || container instanceof Root) {
			return target instanceof Root;
		}
		if (container instanceof Playlist) {
			return container == target || container.equals(target);
		}
		if (container instanceof Folder) {
			if (target instanceof Folder) {
				return container == target || container.equals(target);
			}
			if (target instanceof Playlist) {
				Folder parentFolder = ((Playlist) target).getParentFolder();
				return (container == parentFolder) || container.equals(parentFolder);
			}
		}
		return false;
	}

	public String getRootName() {
		return root.getName();
	}

	public String getMail() {
		return root.getOwnerMail();
	}

}
