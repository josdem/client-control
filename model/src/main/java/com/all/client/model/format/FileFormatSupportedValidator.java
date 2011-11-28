package com.all.client.model.format;

public interface FileFormatSupportedValidator {
	boolean isDrmProtected();
	boolean isAudioFile();
	boolean isAllowedToBeImportedByBusinessRule();
}
