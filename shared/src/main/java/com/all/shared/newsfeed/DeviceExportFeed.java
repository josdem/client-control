package com.all.shared.newsfeed;

import com.all.shared.model.ContactInfo;
import com.all.shared.model.ModelCollection;

public class DeviceExportFeed extends LibraryContentFeed {
	@Deprecated
	public DeviceExportFeed() {
	}

	public DeviceExportFeed(ContactInfo owner, ModelCollection model) {
		super(owner, model, FeedType.EXPORT_TO_DEVICE);
	}
}
