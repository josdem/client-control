package com.all.shared.json.readers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONObject;

import com.all.shared.json.JsonConverter;
import com.all.shared.model.ContactInfo;
import com.all.shared.model.FeedTrack;
import com.all.shared.newsfeed.AbstractFeed;
import com.all.shared.newsfeed.AllFeed;
import com.all.shared.newsfeed.AvatarFeed;
import com.all.shared.newsfeed.ContactsFeed;
import com.all.shared.newsfeed.DeviceExportFeed;
import com.all.shared.newsfeed.FeedType;
import com.all.shared.newsfeed.FriendshipFeed;
import com.all.shared.newsfeed.MediaImportFeed;
import com.all.shared.newsfeed.PostedListeningTrackTwitterFeed;
import com.all.shared.newsfeed.QuoteFeed;
import com.all.shared.newsfeed.RecommendedMediaFacebookFeed;
import com.all.shared.newsfeed.RecommendedTrackFacebookFeed;
import com.all.shared.newsfeed.RecommendedTrackTwtterFeed;
import com.all.shared.newsfeed.RemoteLibraryBrowsingFeed;
import com.all.shared.newsfeed.SendMediaFeed;
import com.all.shared.newsfeed.TopHundredFeed;
import com.all.shared.newsfeed.TotalTracksFeed;
import com.all.shared.newsfeed.TrackContentFeed;
import com.all.shared.newsfeed.UpdateProfileFeed;

public class AllFeedJsonReader implements JsonReader<AllFeed> {

	private static final HashMap<Integer, Class<? extends AllFeed>> ALL_FEED_TYPES = new HashMap<Integer, Class<? extends AllFeed>>();

	private static final AllFeedJsonReader INSTANCE = new AllFeedJsonReader();

	static {
		ALL_FEED_TYPES.put(FeedType.RECOMMENDED_TRACK_FACEBOOK, RecommendedTrackFacebookFeed.class);
		ALL_FEED_TYPES.put(FeedType.RECOMMENDED_MEDIA_FACEBOOK, RecommendedMediaFacebookFeed.class);
		ALL_FEED_TYPES.put(FeedType.RECOMMENDED_TRACK_TWITTER, RecommendedTrackFacebookFeed.class);
		ALL_FEED_TYPES.put(FeedType.BROWSE_REMOTE_LIBRARY, RemoteLibraryBrowsingFeed.class);
		ALL_FEED_TYPES.put(FeedType.EXPORT_TO_DEVICE, DeviceExportFeed.class);
		ALL_FEED_TYPES.put(FeedType.SEND_MEDIA, SendMediaFeed.class);
		ALL_FEED_TYPES.put(FeedType.AVATAR_UPDATE, AvatarFeed.class);
		ALL_FEED_TYPES.put(FeedType.QUOTE, QuoteFeed.class);
		ALL_FEED_TYPES.put(FeedType.FRIENDSHIP, FriendshipFeed.class);
		ALL_FEED_TYPES.put(FeedType.UPDATE_PROFILE, UpdateProfileFeed.class);
		ALL_FEED_TYPES.put(FeedType.MEDIA_IMPORT, MediaImportFeed.class);
		ALL_FEED_TYPES.put(FeedType.RECOMMENDED_TRACK_TWITTER, RecommendedTrackTwtterFeed.class);
		ALL_FEED_TYPES.put(FeedType.CONTACTS_FEED, ContactsFeed.class);
		ALL_FEED_TYPES.put(FeedType.PLAYED_TRACKS, TrackContentFeed.class);
		ALL_FEED_TYPES.put(FeedType.DOWNLOADED_TRACKS, TrackContentFeed.class);
		ALL_FEED_TYPES.put(FeedType.POSTED_TRACK_TWITTER, PostedListeningTrackTwitterFeed.class);
		ALL_FEED_TYPES.put(FeedType.TOP_HUNDRED, TopHundredFeed.class);
		ALL_FEED_TYPES.put(FeedType.TOTAL_TRACKS, TotalTracksFeed.class);
	}

	public static JsonReader<AllFeed> getInstance() {
		return INSTANCE;
	}

	private AllFeedJsonReader() {
	}

	@Override
	public AllFeed read(String json) {
		JSONObject jsonObj = JSONObject.fromObject(json);
		int type = jsonObj.getInt("type");
		Class<? extends AllFeed> clazz = ALL_FEED_TYPES.get(type);
		AllFeed feed = JsonConverter.toBean(json, clazz);

		if (feed instanceof RemoteLibraryBrowsingFeed) {
			ContactInfo visited = JsonConverter.toBean(jsonObj.getJSONObject("visited").toString(), ContactInfo.class);
			((RemoteLibraryBrowsingFeed) feed).setVisited(visited);
		}

		if (feed instanceof SendMediaFeed) {
			ContactInfo recipient = JsonConverter.toBean(jsonObj.getJSONObject("recipient").toString(), ContactInfo.class);
			((SendMediaFeed) feed).setRecipient(recipient);
		}

		if (feed instanceof TrackContentFeed) {
			String tracksJson = jsonObj.getJSONArray("tracks").toString();
			@SuppressWarnings("unchecked")
			ArrayList<FeedTrack> tracks = JsonConverter.toTypedCollection(tracksJson, ArrayList.class, FeedTrack.class);
			((TrackContentFeed) feed).setTracks(tracks);
		}

		if (feed instanceof AbstractFeed) {
			String ownerJson = jsonObj.getJSONObject("owner").toString();
			long date = jsonObj.getLong("date");
			ContactInfo owner = JsonConverter.toBean(ownerJson, ContactInfo.class);
			((AbstractFeed) feed).setOwner(owner);
			((AbstractFeed) feed).setDate(new Date(date));
		}

		if (feed instanceof RecommendedMediaFacebookFeed) {
			String feedTracksJson = jsonObj.getJSONArray("tracks").toString();
			@SuppressWarnings("unchecked")
			List<FeedTrack> tracks = JsonConverter.toTypedCollection(feedTracksJson, ArrayList.class, FeedTrack.class);
			((RecommendedMediaFacebookFeed) feed).setTracks(tracks);
		}

		return feed;
	}
}
