package com.all.core.actions;

import java.util.List;

import com.all.action.ActionObject;
import com.all.client.model.Download;

public class MoveDownloadsAction extends ActionObject {

	private final List<Download> movedDownloads;
	private final int row;

	public MoveDownloadsAction(List<Download> movedDownloads, int row) {
		this.movedDownloads = movedDownloads;
		this.row = row;
	}

	public List<Download> getMovedDownloads() {
		return movedDownloads;
	}

	public int getRow() {
		return row;
	}

}
