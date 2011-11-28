package com.all.core.events;

import com.all.event.EventObject;
import com.all.shared.model.Folder;
import com.all.shared.model.Playlist;
import com.all.shared.model.Root;
import com.all.shared.model.SmartPlaylist;
import com.all.shared.model.TrackContainer;

public class ContainerModifiedEvent extends EventObject {
	private final TrackContainer containerModelObject;

	public ContainerModifiedEvent(TrackContainer object) {
		this.containerModelObject = object;
	}

	public TrackContainer getContainerModelObject() {
		return containerModelObject;
	}

	public boolean isModified(Object object) {
		if (containerModelObject == null) {
			return true;
		}
		if (object instanceof SmartPlaylist || object instanceof Root) {
			return containerModelObject instanceof Root;
		}
		if (object instanceof Playlist) {
			return object == containerModelObject || object.equals(containerModelObject);
		}
		if (object instanceof Folder) {
			if (containerModelObject instanceof Folder) {
				return object == containerModelObject || object.equals(containerModelObject);
			}
			if (containerModelObject instanceof Playlist) {
				Folder parentFolder = ((Playlist) containerModelObject).getParentFolder();
				return (object == parentFolder) || object.equals(parentFolder);
			}
		}
		return false;
	}

}
