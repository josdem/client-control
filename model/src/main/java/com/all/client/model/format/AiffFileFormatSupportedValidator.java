package com.all.client.model.format;

public class AiffFileFormatSupportedValidator implements FileFormatSupportedValidator {

	@Override
	public boolean isAudioFile() {
		return true;
	}

	@Override
	public boolean isDrmProtected() {
		return false;
	}

	@Override
    public boolean isAllowedToBeImportedByBusinessRule() {
	    return true;
    }

}
