package com.all.client.model;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.io.Serializable;

import com.all.core.model.ContactCollection;

public class ContactTransferable implements Transferable, Serializable {
	private static final long serialVersionUID = 1L;
	public static final DataFlavor CONTACT_FLAVOR = new DataFlavor(ContactCollection.class, "shared-contactInfo-entity");
	private ContactCollection draggedObjects;

	public ContactTransferable(ContactCollection draggedObjects) {
		this.draggedObjects = draggedObjects;
	}

	@Override
	public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
		if (flavor.equals(CONTACT_FLAVOR)) {
			return draggedObjects;
		} else {
			throw new UnsupportedFlavorException(flavor);
		}
	}

	@Override
	public DataFlavor[] getTransferDataFlavors() {
		return new DataFlavor[] { CONTACT_FLAVOR };
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return flavor.isMimeTypeEqual(CONTACT_FLAVOR.getMimeType());
	}
}
