package com.all.core.actions;

import static com.all.action.ActionType.cm;

import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.RowSorter.SortKey;

import com.all.action.ActionType;
import com.all.action.EmptyAction;
import com.all.action.RequestAction;
import com.all.action.ValueAction;
import com.all.chat.ChatCredentials;
import com.all.chat.ChatType;
import com.all.client.model.DecoratedSearchData;
import com.all.client.model.DecoratedTwitterStatus;
import com.all.client.model.DeviceBase;
import com.all.client.model.Download;
import com.all.client.model.PlaylistOrder;
import com.all.client.model.RepeatType;
import com.all.client.model.TwitterProfile;
import com.all.core.model.ContainerView;
import com.all.core.model.DisplayableMetadataFields;
import com.all.core.model.FacebookPost;
import com.all.core.model.Model;
import com.all.core.model.ModelContainerView;
import com.all.core.model.SubViews;
import com.all.core.model.Tweet;
import com.all.core.model.TwitterUser;
import com.all.shared.alert.Alert;
import com.all.shared.alert.McRequestAlert;
import com.all.shared.command.LoginCommand;
import com.all.shared.external.email.EmailDomain;
import com.all.shared.messages.CrawlerResponse;
import com.all.shared.messages.FeedsResponse;
import com.all.shared.model.Category;
import com.all.shared.model.ChatMessage;
import com.all.shared.model.ContactInfo;
import com.all.shared.model.ModelCollection;
import com.all.shared.model.Playlist;
import com.all.shared.model.Root;
import com.all.shared.model.Track;
import com.all.shared.model.TrackContainer;

public interface Actions {

	interface Alerts {

		String ALERT_ACTION_ACCEPT_ID = "alerts.actionAccept";
		ActionType<ValueAction<Alert>> ALERT_ACTION_ACCEPT = cm(ALERT_ACTION_ACCEPT_ID);

		String ALERT_ACTION_DELETE_ID = "alerts.actionDelete";
		ActionType<ValueAction<Alert>> ALERT_ACTION_DELETE = cm(ALERT_ACTION_DELETE_ID);

		String SEND_REQUEST_ALERT_ID = "alerts.sendRequestAlert";
		ActionType<ValueAction<McRequestAlert>> SEND_REQUEST_ALERT = cm(SEND_REQUEST_ALERT_ID);

		String CANCEL_CONTENT_UPLOAD_ID = "alerts.cancelContentUpload";
		ActionType<ValueAction<Long>> CANCEL_CONTENT_UPLOAD = cm(CANCEL_CONTENT_UPLOAD_ID);
	}

	interface TopHundred {
		String getCategoriesId = "getTop100Categories";
		String getPlaylistsFromCategoryAsyncId = "getPlaylistsFromCategoryAsyncId";
		String GET_RANDOM_PLAYLIST_ID = "getRandomPlaylistId";

		ActionType<RequestAction<Void, List<Category>>> getCategories = cm(getCategoriesId);
		ActionType<RequestAction<Category, List<Playlist>>> getPlaylistsFromCategoryAsync = cm(getPlaylistsFromCategoryAsyncId);
		ActionType<RequestAction<Void, Playlist>> GET_RANDOM_PLAYLIST = cm(GET_RANDOM_PLAYLIST_ID);
	}

	interface Devices {
		String GET_DEVICES_ID = "devices.getDevices";
		ActionType<RequestAction<Void, List<DeviceBase>>> GET_DEVICES = cm(GET_DEVICES_ID);

		String DISCONECT_ID = "devices.disconect";
		ActionType<ValueAction<DeviceBase>> DISCONECT = cm(DISCONECT_ID);

		String COPY_ID = "devices.copy";
		ActionType<DevicesCopyAction> COPY = cm(COPY_ID);

		String DELETE_ID = "devices.delete";
		ActionType<ValueAction<List<File>>> DELETE = cm(DELETE_ID);

		String CANCEL_COPY_ID = "devices.cancel.copy";
		ActionType<EmptyAction> CANCEL_COPY = cm(CANCEL_COPY_ID);

	}

	interface Library {
		String IMPORT_FROM_ITUNES_ID = "library.importFromItunes";
		ActionType<ValueAction<File>> IMPORT_FROM_ITUNES = cm(IMPORT_FROM_ITUNES_ID);

		String SAVE_ITUNES_UNIMPORTED_FILES_ID = "library.saveItunesUnimportedFiles";
		ActionType<ValueAction<ModelCollection>> SAVE_ITUNES_UNIMPORTED_FILES = cm(SAVE_ITUNES_UNIMPORTED_FILES_ID);

		String DELETE_ITUNES_UNIMPORTED_FILES_ID = "library.deleteItunesUnimportedFiles";
		ActionType<EmptyAction> DELETE_ITUNES_UNIMPORTED_FILES = cm(DELETE_ITUNES_UNIMPORTED_FILES_ID);

		String IS_SYNCH_DOWNLOAD_ID = "library.getSyncDownload";
		ActionType<RequestAction<String, Boolean>> IS_SYNCH_DOWNLOAD = cm(IS_SYNCH_DOWNLOAD_ID);

		String REORDER_PLAYLIST_ID = "library.reorderPlaylist";
		ActionType<ReorderPlaylistAction> REORDER_PLAYLIST = cm(REORDER_PLAYLIST_ID);

		String LIBRARY_ROOT_REMOVED_ID = "view.libraryRootRemoved";
		ActionType<ValueAction<Root>> LIBRARY_ROOT_REMOVED = cm(LIBRARY_ROOT_REMOVED_ID);

		String GET_LAST_LIBRARIES_ID = "library.getLastLibraries";
		ActionType<RequestAction<Void, List<Root>>> GET_LAST_LIBRARIES = cm(GET_LAST_LIBRARIES_ID);

		String LOAD_CONTACT_LIBRARY_ID = "library.loadContactLibrary";
		ActionType<LoadContactLibraryAction> LOAD_CONTACT_LIBRARY = cm(LOAD_CONTACT_LIBRARY_ID);

		String LOAD_CONTACT_LIBRARY_REQUEST_ID = "library.loadContactLibraryRequest";
		ActionType<RequestAction<LoadContactLibraryAction, Void>> LOAD_CONTACT_LIBRARY_REQUEST = cm(LOAD_CONTACT_LIBRARY_REQUEST_ID);

		String REMOVE_FROM_NEW_TRACKS_ID = "library.removeFromNewTracks";
		ActionType<ValueAction<List<Track>>> REMOVE_FROM_NEW_TRACKS = cm(REMOVE_FROM_NEW_TRACKS_ID);

		String MODEL_MOVE_ID = "library.model.move";
		ActionType<ModelMoveAction> MODEL_MOVE = cm(MODEL_MOVE_ID);

		String MODEL_DELETE_ID = "library.model.delete";
		ActionType<ModelDeleteAction> MODEL_DELETE = cm(MODEL_DELETE_ID);

		String MODEL_IMPORT_ID = "library.model.import";
		ActionType<ModelImportAction> MODEL_IMPORT = cm(MODEL_IMPORT_ID);

		String MODEL_CREATE_PLAYLIST_ID = "library.model.createPlaylist";
		ActionType<EmptyAction> MODEL_CREATE_PLAYLIST = cm(MODEL_CREATE_PLAYLIST_ID);

		String MODEL_CREATE_FOLDER_ID = "library.model.createFolder";
		ActionType<EmptyAction> MODEL_CREATE_FOLDER = cm(MODEL_CREATE_FOLDER_ID);

		String UPDATE_TRACK_RATING_ID = "library.updateTrackRating";
		ActionType<UpdateTrackRatingAction> UPDATE_TRACK_RATING = cm(UPDATE_TRACK_RATING_ID);

		String TOGGLE_TRACK_ENABLED_ID = "library.updateTrackEnabled";
		ActionType<ValueAction<Track>> TOGGLE_TRACK_ENABLED = cm(TOGGLE_TRACK_ENABLED_ID);

		String MODEL_REQUEST_RENAME_ID = "library.requestRename";
		ActionType<EmptyAction> MODEL_REQUEST_RENAME = cm(MODEL_REQUEST_RENAME_ID);

		String FIND_TRACKS_LOCALLY_ID = "library.findTracksLocally";
		ActionType<ValueAction<File>> FIND_TRACKS_LOCALLY = cm(FIND_TRACKS_LOCALLY_ID);

		String DELETE_FILES_ID = "library.deleteFiles";
		ActionType<ValueAction<List<Track>>> DELETE_FILES = cm(DELETE_FILES_ID);

		String MODEL_FIND_TRACKS_ID = "library.findTracks";
		ActionType<RequestAction<List<String>, List<Track>>> MODEL_FIND_TRACKS = cm(MODEL_FIND_TRACKS_ID);

		String FIND_TRACKS_BY_HASHCODES_ID = "library.findTracksByHashcodes";
		ActionType<RequestAction<List<String>, List<Track>>> FIND_TRACKS_BY_HASHCODES = cm(FIND_TRACKS_BY_HASHCODES_ID);

		String CANCEL_SYNC_DOWNLOAD_ID = "library.cancelSyncDownload";
		ActionType<ValueAction<String>> CANCEL_SYNC_DOWNLOAD = cm(CANCEL_SYNC_DOWNLOAD_ID);
	}

	interface Downloads {
		String DELETE_DOWNLOAD_COLLECTION_ID = "downloads.delete.downloadCollection";
		ActionType<ValueAction<List<Download>>> DELETE_DOWNLOAD_COLLECTION = cm(DELETE_DOWNLOAD_COLLECTION_ID);

		String ADD_SEARCH_DATA_ID = "downloads.add.searchData";
		ActionType<ValueAction<DecoratedSearchData>> ADD_SEARCH_DATA = cm(ADD_SEARCH_DATA_ID);

		String RESUME_ID = "downloads.resume";
		ActionType<ValueAction<List<Download>>> RESUME = cm(RESUME_ID);

		String PAUSE_ID = "downloads.pause";
		ActionType<ValueAction<List<Download>>> PAUSE = cm(PAUSE_ID);

		String CLEAN_UP_ID = "downloads.cleanUp";
		ActionType<EmptyAction> CLEAN_UP = cm(CLEAN_UP_ID);

		String REQUEST_ALL_DOWNLOADS_ID = "downloads.requestAllDownloads";
		ActionType<RequestAction<Void, List<Download>>> REQUEST_ALL_DOWNLOADS = cm(REQUEST_ALL_DOWNLOADS_ID);

		String REQUEST_ADD_TRACK_ID = "downloads.requestAddTrack";
		ActionType<RequestAction<Track, Download>> REQUEST_ADD_TRACK = cm(REQUEST_ADD_TRACK_ID);

		String MOVE_ID = "downloads.move";
		ActionType<MoveDownloadsAction> MOVE = cm(MOVE_ID);

		String ADD_TRACK_LIST_ID = "downloads.addTrackList";
		ActionType<ValueAction<List<Track>>> ADD_TRACK_LIST = cm(ADD_TRACK_LIST_ID);

		String ADD_TRACK_CONTAINER_ID = "downloads.addTrackContainer";
		ActionType<ValueAction<TrackContainer>> ADD_TRACK_CONTAINER = cm(ADD_TRACK_CONTAINER_ID);

		String ADD_MODEL_COLLECTION_ID = "downloads.addModelCollection";
		ActionType<ValueAction<ModelCollection>> ADD_MODEL_COLLECTION = cm(ADD_MODEL_COLLECTION_ID);

		String SEARCH_ID = "downloads.search";
		ActionType<ValueAction<String>> SEARCH = cm(SEARCH_ID);

		String IS_UPLOADING_ID = "downloads.uploads.isUploading";
		ActionType<RequestAction<Void, Boolean>> IS_UPLOADING = cm(IS_UPLOADING_ID);
	}

	interface Application {

		String getTracksFromContainerId = "application.getTracksFromContainerId";

		ActionType<RequestAction<TrackContainer, Iterable<Track>>> getTracksFromContainer = cm(getTracksFromContainerId);
		ActionType<ValueAction<List<Track>>> SET_CURRENT_DISPLAYED_TRACKS = cm(Model.DISPLAYED_TRACK_LIST_ID);
		ActionType<ValueAction<Integer>> SET_CURRENT_DISPLAYED_ITEM_COUNT = cm(Model.DISPLAYED_ITEM_COUNT_ID);

		String UPDATE_USER_OBJECT_EDITTED_ID = "application.updateUserObjectEditted";
		ActionType<ValueAction<Object>> UPDATE_USER_OBJECT_EDITTED = cm(UPDATE_USER_OBJECT_EDITTED_ID);

		String REQUEST_IS_TRACK_DOWNLODABLE_ID = "applicaiton.requestIsTrackDownloadable";
		ActionType<RequestAction<TrackContainer, Boolean>> REQUEST_IS_TRACK_DOWNLODABLE = cm(REQUEST_IS_TRACK_DOWNLODABLE_ID);

		String REQUEST_FIND_TRACK_ID = "application.findTrack";
		ActionType<RequestAction<String, Track>> REQUEST_FIND_TRACK = cm(REQUEST_FIND_TRACK_ID);

		String APP_CLOSE_ID = "application.close";
		ActionType<ValueAction<CloseType>> APP_CLOSE = cm(APP_CLOSE_ID);

		String REQUEST_CRAWLER_EMAIL_CONTACTS_ID = "application.requestCrawlerEmailContacts";
		ActionType<RequestAction<Map<EmailDomain, List<LoginCommand>>, CrawlerResponse>> REQUEST_CRAWLER_EMAIL_CONTACTS = cm(REQUEST_CRAWLER_EMAIL_CONTACTS_ID);

	}

	interface Social {

		String SHOW_SEND_CONTENT_DIALOG_ID = "social.showSendContentDialog";
		ActionType<ShareContentAction> SHOW_SEND_CONTENT_DIALOG = cm(SHOW_SEND_CONTENT_DIALOG_ID);

		String REQUEST_UPLOADABLE_CONTENT_ID = "social.requestUploadableContent";
		ActionType<RequestAction<ModelCollection, ModelCollection>> REQUEST_UPLOADABLE_CONTENT = cm(REQUEST_UPLOADABLE_CONTENT_ID);

		String REQUEST_UPLOAD_TIME_ID = "social.requestUploadTime";
		ActionType<RequestAction<ModelCollection, Integer>> REQUEST_UPLOAD_TIME = cm(REQUEST_UPLOAD_TIME_ID);

		String SEND_CONTENT_ALERT_ID = "social.sendContentAlert";
		ActionType<SendContentAction> SEND_CONTENT_ALERT = cm(SEND_CONTENT_ALERT_ID);

		String LOAD_USER_PROFILE_ID = "social.loadUserProfile";
		ActionType<LoadContactProfileAction> LOAD_USER_PROFILE = cm(LOAD_USER_PROFILE_ID);

		String GET_CONTACT_INFO_ID = "social.getContactInfo";
		ActionType<RequestAction<Long, ContactInfo>> GET_CONTACT_INFO = cm(GET_CONTACT_INFO_ID);

		String IS_CONTACT_ACCESSIBLE_ID = "social.isContactAccessible";
		ActionType<RequestAction<ContactInfo, Boolean>> IS_CONTACT_ACCESSIBLE = cm(IS_CONTACT_ACCESSIBLE_ID);

		String SEARCH_CONTACTS_LOCALLY_ID = "social.searchContactsLocally";
		ActionType<ValueAction<String>> SEARCH_CONTACTS_LOCALLY = cm(SEARCH_CONTACTS_LOCALLY_ID);

		String REQUEST_FRIENDSHIP_ID = "social.simpleFriendshipRequest";
		ActionType<ValueAction<ContactInfo>> REQUEST_FRIENDSHIP = cm(REQUEST_FRIENDSHIP_ID);

		String MULTIPLE_REQUEST_FRIENDSHIP_ID = "social.multipleFriendshipRequest";
		ActionType<ValueAction<List<ContactInfo>>> MULTIPLE_REQUEST_FRIENDSHIP = cm(MULTIPLE_REQUEST_FRIENDSHIP_ID);

		String REQUEST_ONLINE_USERS_ID = "social.requestOnlineUsers";
		ActionType<RequestAction<Void, List<ContactInfo>>> REQUEST_ONLINE_USERS = cm(REQUEST_ONLINE_USERS_ID);

		String SEND_EMAIL_INVITATION_ID = "social.sendEmailInvitation";
		ActionType<SendEmailInvitationAction> SEND_EMAIL_INVITATION = cm(SEND_EMAIL_INVITATION_ID);

		String REQUEST_CONTACTS_ID = "social.requestContacts";
		ActionType<RequestAction<ChatType, List<ContactInfo>>> REQUEST_CONTACTS = cm(REQUEST_CONTACTS_ID);

		String DELETE_CONTACTS_ID = "social.deleteContacts";
		ActionType<ValueAction<Set<ContactInfo>>> DELETE_CONTACTS = cm(DELETE_CONTACTS_ID);

		String SEARCH_CONTACTS_ID = "social.searchContacts";
		ActionType<RequestAction<String, List<ContactInfo>>> SEARCH_CONTACTS = cm(SEARCH_CONTACTS_ID);

		String VALIDATE_SEARCH_KEYWORD_ID = "social.validateSearchKeyword";
		ActionType<RequestAction<String, Boolean>> VALIDATE_SEARCH_KEYWORD = cm(VALIDATE_SEARCH_KEYWORD_ID);

	}

	interface Chat {

		String REQUEST_SWITCH_CHAT_TYPE_ID = "chat.requestSwitchType";
		ActionType<RequestAction<ChatType, Boolean>> REQUEST_SWITCH_CHAT_TYPE = cm(REQUEST_SWITCH_CHAT_TYPE_ID);

		String LOGOUT_CHAT_TYPE_ID = "chat.Logout";
		ActionType<ValueAction<ChatType>> LOGOUT_CHAT_TYPE = cm(LOGOUT_CHAT_TYPE_ID);

		String REQUEST_SELECT_CHAT_TYPE_ID = "chat.selectChatType";
		ActionType<RequestAction<ChatType, Boolean>> REQUEST_SELECT_CHAT_TYPE = cm(REQUEST_SELECT_CHAT_TYPE_ID);

		String SENT_CONTENT_ID = "chat.SentContent";
		ActionType<RequestAction<ContactInfo, ChatMessage>> SENT_CONTENT = cm(SENT_CONTENT_ID);

		String SEND_MESSAGE_TO_CONTACT_ID = "chat.sendMessageToContact";
		ActionType<RequestAction<ContactMessage, ChatMessage>> SEND_MESSAGE_TO_CONTACT = cm(SEND_MESSAGE_TO_CONTACT_ID);

		String LOGIN_INTO_CHAT_ID = "chat.loginIntoChat";
		ActionType<RequestAction<ChatCredentials, Boolean>> LOGIN_INTO_CHAT = cm(LOGIN_INTO_CHAT_ID);

	}

	interface Facebook {
		String POST_TO_FACEBOOK_ID = "Facebook.PostToFacebook";
		ActionType<ValueAction<FacebookPost>> POST_TO_FACEBOOK = cm(POST_TO_FACEBOOK_ID);

		String GET_AUTH_AND_REDIRECT_URLS_ID = "Facebook.GetAuthAndRedirectUrls";
		ActionType<RequestAction<Void, String[]>> GET_AUTH_AND_REDIRECT_URLS = cm(GET_AUTH_AND_REDIRECT_URLS_ID);

		String AUTHORIZE_ID = "Facebook.Authenticate";
		ActionType<RequestAction<String, Void>> AUTHORIZE = cm(AUTHORIZE_ID);

		String CHAT_LOGIN_ID = "Facebook.Chat.Login";
		ActionType<EmptyAction> CHAT_LOGIN = cm(CHAT_LOGIN_ID);
	}

	interface Twitter {

		String LOGIN_ID = "twitter.login";
		ActionType<RequestAction<TwitterUser, Void>> LOGIN = cm(LOGIN_ID);

		String REQUEST_CREDENTIALS_ID = "twitter.requestCredentials";
		ActionType<RequestAction<Void, TwitterUser>> REQUEST_CREDENTIALS = cm(REQUEST_CREDENTIALS_ID);

		String UPDATE_STATUS_ID = "twitter.updateStatus";
		ActionType<RequestAction<Tweet, Boolean>> UPDATE_STATUS = cm(UPDATE_STATUS_ID);

		String REQUEST_USER_PROFILE_ID = "twitter.requestUserProfile";
		ActionType<RequestAction<String, TwitterProfile>> REQUEST_USER_PROFILE = cm(REQUEST_USER_PROFILE_ID);

		String LOAD_USER_TIMELINE_ID = "twitter.loadUserTimeline";
		ActionType<RequestAction<Void, List<DecoratedTwitterStatus>>> LOAD_USER_TIMELINE = cm(LOAD_USER_TIMELINE_ID);

		String REQUEST_TIMELINE_ID = "twitter.requestTimeline";
		ActionType<RequestAction<String, List<DecoratedTwitterStatus>>> REQUEST_TIMELINE = cm(REQUEST_TIMELINE_ID);

		String REQUEST_MENTIONS_ID = "twitter.requestMentions";
		ActionType<RequestAction<Void, List<DecoratedTwitterStatus>>> REQUEST_MENTIONS = cm(REQUEST_MENTIONS_ID);

		String REQUEST_DIRECT_MESSAGES_ID = "twitter.requestDirectMessages";
		ActionType<RequestAction<Void, List<DecoratedTwitterStatus>>> REQUEST_DIRECT_MESSAGES = cm(REQUEST_DIRECT_MESSAGES_ID);

		String FOLLOW_ID = "twitter.follow";
		ActionType<ValueAction<String>> FOLLOW = cm(FOLLOW_ID);

		String UNFOLLOW_ID = "twitter.unfollow";
		ActionType<ValueAction<String>> UNFOLLOW = cm(UNFOLLOW_ID);
		
		String POST_AUTH = "twitter.auth";
		ActionType<ValueAction<Boolean>> POST = cm(POST_AUTH);
		
		String POST_AUTH_READ = "twitter.auth.read";
		ActionType<RequestAction<Void, Boolean>> POST_AUTH_STATUS = cm(POST_AUTH_READ);

	}

	interface View {
		String TOGGLE_DRAWER_ID = "view.toggleDrawer";
		String HIDE_DRAWER_ID = "view.hideDrawer";
		ActionType<ValueAction<ContainerView>> setCurrentView = cm(Model.CURRENT_VIEW_ID);
		ActionType<ValueAction<SubViews>> setCurrentSubView = cm(Model.CURRENT_SUBVIEW_ID);

		String SET_TOP_HUNDRED_CATEGORY_VIEW_ID = "view.setTopHundredCategoryModel";
		ActionType<ValueAction<ModelContainerView>> SET_TOP_HUNDRED_CATEGORY_VIEW = cm(SET_TOP_HUNDRED_CATEGORY_VIEW_ID);

		String SET_TOP_HUNDRED_PLAYLIST_VIEW_ID = "view.setTopHundredPlaylistModel";
		ActionType<ValueAction<ModelContainerView>> SET_TOP_HUNDRED_PLAYLIST_VIEW = cm(SET_TOP_HUNDRED_PLAYLIST_VIEW_ID);

		ActionType<EmptyAction> TOGGLE_DRAWER = cm(TOGGLE_DRAWER_ID);
		ActionType<EmptyAction> HIDE_DRAWER = cm(HIDE_DRAWER_ID);

		String SHOW_ITUNES_UNIMPORTED_FILES_ID = "view.showItunesUnimportedFiles";
		ActionType<EmptyAction> SHOW_ITUNES_UNIMPORTED_FILES = cm(SHOW_ITUNES_UNIMPORTED_FILES_ID);

		String SELECT_TRACKCONTAINER_ID = "view.app.selectTrackContainer";
		ActionType<SelectTrackContainerAction> SELECT_TRACKCONTAINER = cm(SELECT_TRACKCONTAINER_ID);

		String SET_CLIPBOARD_SELECTION_ID = "clipboard.setSelection";
		ActionType<ValueAction<List<?>>> SET_CLIPBOARD_SELECTION = cm(SET_CLIPBOARD_SELECTION_ID);

		String CLIPBOARD_COPY_ID = "clipboard.copy";
		ActionType<EmptyAction> CLIPBOARD_COPY = cm(CLIPBOARD_COPY_ID);

		String CLIPBOARD_PASTE_ID = "clipboard.paste";
		ActionType<EmptyAction> CLIPBOARD_PASTE = cm(CLIPBOARD_PASTE_ID);

		String SELECT_ROOT_ID = "view.selectRoot";
		ActionType<ValueAction<Root>> SELECT_ROOT = cm(SELECT_ROOT_ID);

		String SET_CURRENT_COMPOSE_VIEW_ID = "view.setCurrentComposeView";
		ActionType<ValueAction<ComposeView>> SET_CURRENT_COMPOSE_VIEW = cm(SET_CURRENT_COMPOSE_VIEW_ID);

		String SELECT_CATEGORY_ID = "view.selectCategoryView";
		ActionType<ValueAction<String>> SELECT_CATEGORY = cm(SELECT_CATEGORY_ID);

		String SELECT_PLAYING_TRACKCONTAINER_ID = "view.selectPlayingTrackcontainer";
		ActionType<EmptyAction> SELECT_PLAYING_TRACKCONTAINER = cm(SELECT_PLAYING_TRACKCONTAINER_ID);
	}

	interface UserPreference {
		ActionType<ValueAction<Boolean>> SET_SKIP_DRAG_CONTENT_TO_CONTACT_CONFIRMATION = cm(Model.UserPreference.SKIP_DRAG_CONTENT_TO_CONTACT_CONFIRMATION_ID);
		ActionType<ValueAction<Boolean>> SET_SKIP_CONTACT_DELETION_CONFIRMATION = cm(Model.UserPreference.SKIP_CONTACT_DELETION_CONFIRMATION_ID);
		ActionType<ValueAction<SortKey>> SET_DOWNLOAD_TABLE_SORT_COLUMN = cm(Model.UserPreference.DOWNLOAD_TABLE_SORT_COLUMN_ID);
		ActionType<ValueAction<Rectangle>> SET_APPLICATION_BOUNDS = cm(Model.UserPreference.APPLICATION_BOUNDS_ID);
		ActionType<ValueAction<DisplayableMetadataFields>> SET_DISPLAYABLE_METADATA_FIELDS = cm(Model.UserPreference.DISPLAYABLE_METADATA_FIELDS_ID);
		ActionType<ValueAction<Boolean>> SET_SKIP_COPY_REFERENCES_TO_USB_WARN = cm(Model.UserPreference.SKIP_COPY_REFERENCES_TO_USB_WARN_ID);
	}

	interface UserProfile {
		String UPDATE_PROFILE_ID = "userProfile.updateProfile";
		ActionType<UpdateProfileAction> UPDATE_PROFILE = cm(UPDATE_PROFILE_ID);

		String UPDATE_QUOTE_ID = "userProfile.updateQuote";
		ActionType<ValueAction<String>> UPDATE_QUOTE = cm(UPDATE_QUOTE_ID);

		String UPDATE_AVATAR_ID = "userProfile.updateAvatar";
		ActionType<ValueAction<Image>> UPDATE_AVATAR = cm(UPDATE_AVATAR_ID);

	}

	interface Player {
		String REQUEST_CURRENT_TRACK_ID = "player.getCurrentTrack";
		ActionType<RequestAction<Void, Track>> REQUEST_CURRENT_TRACK = cm(REQUEST_CURRENT_TRACK_ID);

		String UPDATE_TIME_ID = "player.updateTime";
		ActionType<ValueAction<Long>> UPDATE_TIME = cm(UPDATE_TIME_ID);

		String REQUEST_TOGGLE_REPEAT_ID = "player.toggleRepeat";
		ActionType<RequestAction<Void, RepeatType>> REQUEST_TOGGLE_REPEAT = cm(REQUEST_TOGGLE_REPEAT_ID);

		String REQUEST_TOGGLE_SHUFFLE_ID = "player.toggleShuffle";
		ActionType<RequestAction<Void, Boolean>> REQUEST_TOGGLE_SHUFFLE = cm(REQUEST_TOGGLE_SHUFFLE_ID);

		String CHANGE_VELOCITY_ID = "player.changeVelocity";
		ActionType<ValueAction<Integer>> CHANGE_VELOCITY = cm(CHANGE_VELOCITY_ID);

		String PLAY_ID = "player.player";
		ActionType<EmptyAction> PLAY = cm(PLAY_ID);

		String STOP_ID = "player.stop";
		ActionType<EmptyAction> STOP = cm(STOP_ID);

		String PAUSE_ID = "player.pause";
		ActionType<EmptyAction> PAUSE = cm(PAUSE_ID);

		String PLAY_DOWNLOAD_ID = "player.playOrDownload";
		ActionType<EmptyAction> PLAY_DOWNLOAD = cm(PLAY_DOWNLOAD_ID);

		String UPDATE_PLAYLIST_ORDER_ID = "player.setPlaylist";
		ActionType<ValueAction<PlaylistOrder>> UPDATE_PLAYLIST_ORDER = cm(UPDATE_PLAYLIST_ORDER_ID);

		String TOGGLE_MUTE_ID = "player.toggleMute";
		ActionType<EmptyAction> TOGGLE_MUTE = cm(TOGGLE_MUTE_ID);

		String UPDATE_CURRENT_INDEX_ID = "player.updateCurrentIndex";
		ActionType<ValueAction<int[]>> UPDATE_CURRENT_INDEX = cm(UPDATE_CURRENT_INDEX_ID);

		String UPDATE_VOLUME_ID = "player.updateVolume";
		ActionType<ValueAction<Integer>> UPDATE_VOLUME = cm(UPDATE_VOLUME_ID);

		String FORWARD_ID = "player.forward";
		ActionType<EmptyAction> FORWARD = cm(FORWARD_ID);

		String PREVIOUS_ID = "player.previous";
		ActionType<EmptyAction> PREVIOUS = cm(PREVIOUS_ID);
	}

	interface Feeds {
		String REQUEST_FEEDS_ID = "feeds.getFeeds";
		ActionType<EmptyAction> REQUEST_FEEDS = cm(REQUEST_FEEDS_ID);
		String REQUEST_LAST_FEEDS_ID = "feeds.getLastFeeds";
		ActionType<EmptyAction> REQUEST_LAST_FEEDS = cm(REQUEST_LAST_FEEDS_ID);
		String REQUEST_OLD_FEEDS_ID = "feeds.getOldFeeds";
		ActionType<RequestAction<Long, FeedsResponse>> REQUEST_OLD_FEEDS = cm(REQUEST_OLD_FEEDS_ID);
	}

}
