package com.all.client.model.format;

public class M4pFileFormatSupportedValidator implements FileFormatSupportedValidator {

	@Override
	public boolean isAudioFile() {
		return true;
	}

	@Override
	public boolean isDrmProtected() {
		return true;
	}

	@Override
    public boolean isAllowedToBeImportedByBusinessRule() {
	    return false;
    }

}
