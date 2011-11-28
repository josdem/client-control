package com.all.client.model;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;


public class DownloadTransferable implements Transferable, Serializable {
	private static final long serialVersionUID = 1L;
	public static final DataFlavor DOWNLOADS_FLAVOR = new DataFlavor(DownloadCollection.class, "download-domain-entity");
	private final DownloadCollection downloads;

	public DownloadTransferable(List<Download> downloads) {
		this.downloads = new DownloadCollection(downloads);
	}

	@Override
	public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
		if (flavor.equals(DOWNLOADS_FLAVOR)) {
			return downloads;
		} else {
			throw new UnsupportedFlavorException(flavor);
		}
	}

	@Override
	public DataFlavor[] getTransferDataFlavors() {
		return new DataFlavor[] { DOWNLOADS_FLAVOR };
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return flavor.isMimeTypeEqual(DOWNLOADS_FLAVOR.getMimeType()) || flavor.equals(DOWNLOADS_FLAVOR);
	}
}
