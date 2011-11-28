package com.all.core.actions;

import java.io.File;
import java.util.List;

import com.all.action.ActionObject;
import com.all.shared.model.TrackContainer;
import com.all.shared.stats.MediaImportStat.ImportType;

public class ModelImportAction extends ActionObject {
	private final TrackContainer target;
	private final FileSystemValidatorLight files;
	private final ImportType importType;

	public ModelImportAction(TrackContainer target, ImportType importType, File... files) {
		this(target, importType, new FileSystemValidatorLight(importType == ImportType.EXTERNAL_DEVICES, files));
	}

	public ModelImportAction(TrackContainer target, ImportType importType, List<File> files) {
		this(target, importType, new FileSystemValidatorLight(importType == ImportType.EXTERNAL_DEVICES, files));
	}

	public ModelImportAction(TrackContainer target, ImportType importType, FileSystemValidatorLight files) {
		this.target = target;
		this.importType = importType;
		this.files = files;
	}

	public TrackContainer getTarget() {
		return target;
	}

	public FileSystemValidatorLight getFiles() {
		return files;
	}

	public ImportType getImportType() {
		return importType;
	}

}
