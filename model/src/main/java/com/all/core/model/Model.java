package com.all.core.model;

import static com.all.model.ModelType.model;
import static com.all.model.ModelType.readOnly;

import java.awt.Rectangle;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.swing.RowSorter.SortKey;

import com.all.chat.ChatType;
import com.all.client.model.DeviceBase;
import com.all.client.model.Download;
import com.all.client.model.RepeatType;
import com.all.client.model.Trash;
import com.all.client.model.TwitterProfile;
import com.all.client.util.TrackRepository;
import com.all.model.ModelType;
import com.all.shared.alert.Alert;
import com.all.shared.model.ContactInfo;
import com.all.shared.model.Root;
import com.all.shared.model.Track;
import com.all.shared.model.TrackContainer;
import com.all.shared.model.User;

public interface Model {

	interface UserPreference {
		String PLAYER_VOLUME_ID = "preferences.playerVolume";
		ModelType<Integer> PLAYER_VOLUME = model(PLAYER_VOLUME_ID);

		String PLAYER_SHUFFLE_MODE_ID = "preferences.playerSuffleMode";
		ModelType<Boolean> PLAYER_SHUFFLE_MODE = model(PLAYER_SHUFFLE_MODE_ID);

		String PLAYER_REPEAT_MODE_ID = "preferences.playerRepeatMode";
		ModelType<RepeatType> PLAYER_REPEAT_MODE = model(PLAYER_REPEAT_MODE_ID);

		String SKIP_CONTACT_DELETION_CONFIRMATION_ID = "preferences.skipContactDeletionConfirmation";
		ModelType<Boolean> SKIP_CONTACT_DELETION_CONFIRMATION = model(SKIP_CONTACT_DELETION_CONFIRMATION_ID);

		String SKIP_DRAG_CONTENT_TO_CONTACT_CONFIRMATION_ID = "preferences.skipDragContentToContactConfirmation";
		ModelType<Boolean> SKIP_DRAG_CONTENT_TO_CONTACT_CONFIRMATION = model(SKIP_DRAG_CONTENT_TO_CONTACT_CONFIRMATION_ID);

		String DOWNLOAD_TABLE_SORT_COLUMN_ID = "preferences.downloadTableSortColumn";
		ModelType<SortKey> DOWNLOAD_TABLE_SORT_COLUMN = model(DOWNLOAD_TABLE_SORT_COLUMN_ID);

		String APPLICATION_BOUNDS_ID = "preferences.screeBounds";
		ModelType<Rectangle> APPLICATION_BOUNDS = model(APPLICATION_BOUNDS_ID);

		String DISPLAYABLE_METADATA_FIELDS_ID = "preferences.metadataFields";
		ModelType<DisplayableMetadataFields> DISPLAYABLE_METADATA_FIELDS = model(DISPLAYABLE_METADATA_FIELDS_ID);

		String SKIP_COPY_REFERENCES_TO_USB_WARN_ID = "preferences.skipCopyReferencesToUsbWarning";
		ModelType<Boolean> SKIP_COPY_REFERENCES_TO_USB_WARN = model(SKIP_COPY_REFERENCES_TO_USB_WARN_ID);

		String FACEBOOK_CHAT_STATUS_ID = "preferences.facebookChatStatus";
		ModelType<Boolean> FACEBOOK_CHAT_STATUS = model(FACEBOOK_CHAT_STATUS_ID);
	}

	String DISPLAYED_TRACK_LIST_ID = "view.DisplayedTrackList";
	ModelType<List<Track>> DISPLAYED_TRACK_LIST = model(DISPLAYED_TRACK_LIST_ID);

	String DISPLAYED_ITEM_COUNT_ID = "view.DisplayedItemCount";
	ModelType<Integer> DISPLAYED_ITEM_COUNT = model(DISPLAYED_ITEM_COUNT_ID);

	String CURRENT_VIEW_ID = "view.currentView";
	ModelType<Views> CURRENT_VIEW = model(CURRENT_VIEW_ID);

	String CURRENT_SUBVIEW_ID = "view.currentSubView";
	ModelType<SubViews> CURRENT_SUBVIEW = model(CURRENT_SUBVIEW_ID);

	String DRAWER_DISPLAYED_ID = "view.drawerDisplayed";
	ModelType<Boolean> DRAWER_DISPLAYED = model(DRAWER_DISPLAYED_ID);

	String ITUNES_UNIMPORTED_FILE_EXISTS_ID = "library.unimportedItunesFileExists";
	ModelType<Boolean> ITUNES_UNIMPORTED_FILE_EXISTS = model(ITUNES_UNIMPORTED_FILE_EXISTS_ID);

	String FACEBOOK_AUTHORIZED_ID = "facebook.loggedIn";
	ModelType<Boolean> FACEBOOK_AUTHORIZED = model(FACEBOOK_AUTHORIZED_ID);

	String TWITTER_LOGGED_IN_ID = "twitter.loggedIn";
	ModelType<Boolean> TWITTER_LOGGED_IN = model(TWITTER_LOGGED_IN_ID);

	String CURRENT_USER_ID = "application.currentUser";
	ModelType<User> CURRENT_USER = readOnly(CURRENT_USER_ID);

	String SELECTED_CONTAINER_ID = "application.selectedContainer";
	ModelType<TrackContainer> SELECTED_CONTAINER = model(SELECTED_CONTAINER_ID);

	String SELECTED_ROOT_ID = "application.selectedRoot";
	ModelType<Root> SELECTED_ROOT = model(SELECTED_ROOT_ID);

	String USER_ROOT_ID = "application.root";
	ModelType<Root> USER_ROOT = readOnly(USER_ROOT_ID);

	String USER_TRASH_ID = "application.trash";
	ModelType<Trash> USER_TRASH = readOnly(USER_TRASH_ID);

	String DEVICES_LIST_ID = "devices.list";
	ModelType<List<DeviceBase>> DEVICES_LIST = model(DEVICES_LIST_ID);

	String CURRENT_PROFILE_ID = "profile.currentProfile";
	ModelType<Profile> CURRENT_PROFILE = model(CURRENT_PROFILE_ID);

	String SELECTED_CHAT_TYPE_ID = "chat.selectedType";
	ModelType<ChatType> SELECTED_CHAT_TYPE = model(SELECTED_CHAT_TYPE_ID);

	String DISPLAYED_CONTACT_LIST_ID = "chat.displayedContactList";
	ModelType<List<ContactInfo>> DISPLAYED_CONTACT_LIST = model(DISPLAYED_CONTACT_LIST_ID);

	String CLIPBOARD_SELECTION_ID = "clipboard.currentSelection";
	ModelType<List<?>> CLIPBOARD_SELECTION = model(CLIPBOARD_SELECTION_ID);

	// TODO check how we can use only one collection for these three cases
	String ALL_DOWNLOADS_ID = "application.allDownloads";
	ModelType<Map<String, Download>> ALL_DOWNLOADS = model(ALL_DOWNLOADS_ID);

	String CURRENT_DOWNLOAD_IDS_ID = "application.currentDownloads";
	ModelType<List<String>> CURRENT_DOWNLOAD_IDS = model(CURRENT_DOWNLOAD_IDS_ID);

	String DOWNLOADS_SORTED_BY_PRIORITY_ID = "application.downloadSortedByPriority";
	ModelType<List<Download>> DOWNLOADS_SORTED_BY_PRIORITY = model(DOWNLOADS_SORTED_BY_PRIORITY_ID);
	// END TODO

	String CURRENT_ALERTS_ID = "application.currentAlerts";
	ModelType<Collection<Alert>> CURRENT_ALERTS = model(CURRENT_ALERTS_ID);

	String TWITTER_PROFILE_ID = "twitter.userProfile";
	ModelType<TwitterProfile> TWITTER_PROFILE = model(TWITTER_PROFILE_ID);

	String TRACK_REPOSITORY_ID = "application.trackRepository";
	ModelType<TrackRepository> TRACK_REPOSITORY = model(TRACK_REPOSITORY_ID);

	String PLAYING_TRACKCONTAINER_ID = "player.playingTrackContainer";
	ModelType<TrackContainer> PLAYING_TRACKCONTAINER = model(PLAYING_TRACKCONTAINER_ID);
}
