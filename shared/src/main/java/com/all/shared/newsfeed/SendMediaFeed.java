package com.all.shared.newsfeed;

import java.util.ArrayList;
import java.util.List;

import com.all.shared.model.ContactInfo;
import com.all.shared.model.FeedTrack;
import com.all.shared.model.ModelCollection;
import com.all.shared.model.Track;

public class SendMediaFeed extends LibraryContentFeed {
	private ContactInfo recipient;
	List<FeedTrack> feedTracks = new ArrayList<FeedTrack>();
	private int countTotalTracks;

	@Deprecated
	public SendMediaFeed() {
	}

	public SendMediaFeed(ContactInfo owner, ContactInfo recipient, ModelCollection model) {
		super(owner, model, FeedType.SEND_MEDIA);
		this.recipient = recipient;
		countTotalTracks = model.trackCount();
		createFeedTracks(model);
	}

	private void createFeedTracks(ModelCollection model) {
		for (Track track : model.getTracks()) {
			feedTracks.add(new FeedTrack(track.getHashcode(), track.getName(), track.getArtist()));
		}
	}

	public int getCountTotalTracks() {
		return countTotalTracks;
	}

	public void setCountTotalTracks(int countTotalTracks) {
		this.countTotalTracks = countTotalTracks;
	}

	public ContactInfo getRecipient() {
		return recipient;
	}

	public void setRecipient(ContactInfo recipient) {
		this.recipient = recipient;
	}

	public List<FeedTrack> getTracks() {
		return feedTracks;
	}

	public void setTracks(List<FeedTrack> tracks) {
		feedTracks = tracks;
	}
}
