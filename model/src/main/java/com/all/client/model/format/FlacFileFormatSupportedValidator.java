package com.all.client.model.format;

public class FlacFileFormatSupportedValidator implements FileFormatSupportedValidator {

	@Override
	public boolean isAudioFile() {
		return false;
	}

	@Override
	public boolean isDrmProtected() {
		return false;
	}

	@Override
	public boolean isAllowedToBeImportedByBusinessRule() {
		return false;
	}

}
