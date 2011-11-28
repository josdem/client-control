package com.all.client.model;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import com.all.client.util.TrackRepository;
import com.all.shared.model.ModelCollection;
import com.all.shared.model.ModelSource;
import com.all.shared.model.Track;

public class ModelTransfereable implements Transferable, Serializable {
	private static final long serialVersionUID = 1L;
	public static final DataFlavor MODEL_FLAVOR = new DataFlavor(ModelCollection.class, "domain-dragable-entity");
	private ModelCollection draggedObjects;
	private List<File> files = new LinkedList<File>();

	private boolean success;

	public ModelTransfereable(ModelSource source, ModelCollection draggedObjects, TrackRepository trackRepository) {
		draggedObjects.source(source);
		this.draggedObjects = draggedObjects;
		for (Track track : draggedObjects.getTracks()) {
			if (trackRepository.isLocallyAvailable(track.getHashcode())) {
				files.add(trackRepository.getFile(track.getHashcode()));
			}
		}
	}

	@Override
	public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
		if (flavor.equals(MODEL_FLAVOR)) {
			return draggedObjects;
		} else if (flavor.equals(DataFlavor.javaFileListFlavor) && !files.isEmpty()) {
			return files;
		} else {
			throw new UnsupportedFlavorException(flavor);
		}
	}

	@Override
	public DataFlavor[] getTransferDataFlavors() {
		if (files.isEmpty()) {
			return new DataFlavor[] { MODEL_FLAVOR };
		} else {
			return new DataFlavor[] { MODEL_FLAVOR, DataFlavor.javaFileListFlavor };
		}
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		if (files.isEmpty()) {
			return flavor.isMimeTypeEqual(MODEL_FLAVOR.getMimeType());
		} else {
			return flavor.isMimeTypeEqual(MODEL_FLAVOR.getMimeType()) || flavor.equals(DataFlavor.javaFileListFlavor);
		}
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}
