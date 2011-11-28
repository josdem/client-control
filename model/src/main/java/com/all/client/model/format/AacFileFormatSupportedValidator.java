package com.all.client.model.format;

public class AacFileFormatSupportedValidator implements FileFormatSupportedValidator {

	@Override
	public boolean isAllowedToBeImportedByBusinessRule() {
		return true;
	}

	@Override
	public boolean isAudioFile() {
		return true;
	}

	@Override
	public boolean isDrmProtected() {
		return false;
	}

}
