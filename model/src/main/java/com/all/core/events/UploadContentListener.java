package com.all.core.events;


public interface UploadContentListener {

	void onContentUploadStarted(UploadContentStartedEvent event);

	void onContentUploadUpdated(UploadContentUpdateEvent event);

	void onContentUploadDone(UploadContentDoneEvent event);

}
