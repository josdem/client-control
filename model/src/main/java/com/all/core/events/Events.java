package com.all.core.events;

import static com.all.event.EventType.ev;

import java.io.File;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.all.chat.ChatType;
import com.all.client.model.DecoratedTwitterStatus;
import com.all.client.model.DeviceBase;
import com.all.client.model.Download;
import com.all.core.actions.CloseType;
import com.all.core.model.ContainerView;
import com.all.core.model.Model;
import com.all.core.model.ModelContainerView;
import com.all.core.model.Profile;
import com.all.core.model.SearchState;
import com.all.core.model.SubViews;
import com.all.downloader.search.SearchDataEvent;
import com.all.downloader.search.SearchErrorEvent;
import com.all.downloader.search.SearchProgressEvent;
import com.all.event.EmptyEvent;
import com.all.event.EventType;
import com.all.event.ValueEvent;
import com.all.shared.alert.Alert;
import com.all.shared.alert.McRequestAlert;
import com.all.shared.messages.FriendshipRequestStatus;
import com.all.shared.model.ChatMessage;
import com.all.shared.model.ContactInfo;
import com.all.shared.model.Folder;
import com.all.shared.model.ModelCollection;
import com.all.shared.model.Playlist;
import com.all.shared.model.Root;
import com.all.shared.model.Track;
import com.all.shared.model.TrackContainer;
import com.all.shared.model.User;
import com.all.shared.newsfeed.AllFeed;

public interface Events {
	/**
	 * Errores en acciones o en la aplicación usualmente reaccionan clases para mostrar dialogos o notificar al usuario
	 * con un popup imagen o algo.
	 */
	interface Errors {
		/**
		 * se intentó invocar una acción que requiere uso de red por lo que la acción falló pero arrojó este lindo evento
		 */
		String NETWORK_REQUIRED_FOR_ACTION_ID = "errors.networkRequiredForAction";
		EventType<NetworkActionErrorEvent> NETWORK_REQUIRED_FOR_ACTION = ev(NETWORK_REQUIRED_FOR_ACTION_ID);

		/** Se intento importar de itunes pero faltaron algunos archivos. */
		String IMPORT_FROM_ITUNES_MISSING_FILES_ID = "errors.importFromItunesHasMissingFiles";
		EventType<ValueEvent<ModelCollection>> IMPORT_FROM_ITUNES_MISSING_FILES = ev(IMPORT_FROM_ITUNES_MISSING_FILES_ID);

		String SYNC_UPLOAD_FAILED_ID = "errors.syncUploadFailed";
		EventType<EmptyEvent> SYNC_UPLOAD_FAILED = ev(SYNC_UPLOAD_FAILED_ID);

		String SYNC_DOWNLOAD_FAILED_ID = "errors.syncDownloadFailed";
		EventType<LibrarySyncEvent> SYNC_DOWNLOAD_FAILED = ev(SYNC_DOWNLOAD_FAILED_ID);

		String ERROR_MESSAGE_ID = "errors.message";
		EventType<ErrorMessageEvent> ERROR_MESSAGE = ev(ERROR_MESSAGE_ID);

		String DEVICE_FULL_ID = "errors.deviceFull";
		EventType<EmptyEvent> DEVICE_FULL = ev(DEVICE_FULL_ID);

		String EXCEPTION_ID = "errors.exception";
		EventType<ValueEvent<Exception>> EXCEPTION = ev(EXCEPTION_ID);

		String MODEL_IMPORT_INVALID_FILES_ID = "errors.modelImportInvalidFiles";
		EventType<ValueEvent<List<File>>> MODEL_IMPORT_INVALID_FILES = ev(MODEL_IMPORT_INVALID_FILES_ID);
	}

	/**
	 * Eventos que se originan por que la libreria del usuario está siendo modificada.
	 */
	interface Library {
		String LOADING_LIBRARY_ID = "library.LoadingLibrary";
		EventType<ValueEvent<String>> LOADING_LIBRARY = ev(LOADING_LIBRARY_ID);

		String IMPORTING_ITUNES_LIBRARY_ID = "library.ImportingItunes";
		EventType<EmptyEvent> IMPORTING_ITUNES_LIBRARY = ev(IMPORTING_ITUNES_LIBRARY_ID);

		String IMPORTING_ITUNES_LIBRARY_DONE_ID = "library.ImportingItunesDone";
		EventType<EmptyEvent> IMPORTING_ITUNES_LIBRARY_DONE = ev(IMPORTING_ITUNES_LIBRARY_DONE_ID);

		EventType<ValueEvent<Boolean>> ITUNES_UNIMPORTED_FILE_EXISTS_CHANGED = ev(Model.ITUNES_UNIMPORTED_FILE_EXISTS_ID);

		String RESTORE_SELECTION_ID = "library.restoreSelection";
		EventType<ValueEvent<Object>> RESTORE_SELECTION = ev(RESTORE_SELECTION_ID);

		String PLAYLIST_CREATED_ID = "library.playlistCreated";
		EventType<ValueEvent<Playlist>> PLAYLIST_CREATED = ev(PLAYLIST_CREATED_ID);

		String FOLDER_CREATED_ID = "library.folderCreated";
		EventType<ValueEvent<Folder>> FOLDER_CREATED = ev(FOLDER_CREATED_ID);

		String EDIT_CURRENT_SELECTION_ID = "library.editRequested";
		EventType<EmptyEvent> EDIT_CURRENT_SELECTION = ev(EDIT_CURRENT_SELECTION_ID);

		String IMPORT_PROGRESS_ID = "library.importProgress";
		EventType<ImportProgressEvent> IMPORT_PROGRESS = ev(IMPORT_PROGRESS_ID);

		String IMPORT_COMPLETED_ID = "library.importCompleted";
		EventType<EmptyEvent> IMPORT_COMPLETED = ev(IMPORT_COMPLETED_ID);

		String TREE_STRUCTURE_CHANGED_ID = "library.treeStructureChanged";
		EventType<ValueEvent<Root>> TREE_STRUCTURE_CHANGED = ev(TREE_STRUCTURE_CHANGED_ID);

		String CONTAINER_MODIFIED_ID = "library.containerModified";
		EventType<ContainerModifiedEvent> CONTAINER_MODIFIED = ev(CONTAINER_MODIFIED_ID);

		String TRACK_UPDATED_ID = "library.trackUpdated";
		EventType<ValueEvent<Track>> TRACK_UPDATED = ev(TRACK_UPDATED_ID);

		String FOLDER_UPDATED_ID = "library.folderUpdated";
		EventType<ValueEvent<Folder>> FOLDER_UPDATED = ev(FOLDER_UPDATED_ID);

		String SYNC_DOWNLOAD_EVENT_ID = "library.SyncDownloadEvent";
		EventType<LibrarySyncEvent> SYNC_DOWNLOAD_EVENT = ev(SYNC_DOWNLOAD_EVENT_ID);

		String SYNC_DOWNLOAD_PROGRESS_EVENT_ID = "library.SyncDownloadEventProgress";
		EventType<LibrarySyncProgressEvent> SYNC_DOWNLOAD_PROGRESS_EVENT = ev(SYNC_DOWNLOAD_PROGRESS_EVENT_ID);

		String SYNC_UPLOAD_EVENT_ID = "library.SyncUploadEvent";
		EventType<ValueEvent<LibrarySyncEventType>> SYNC_UPLOAD_EVENT = ev(SYNC_UPLOAD_EVENT_ID);

		String LIBRARY_ROOT_ADDED_ID = "view.libraryRootAdded";
		EventType<ValueEvent<Root>> LIBRARY_ROOT_ADDED = ev(LIBRARY_ROOT_ADDED_ID);

		String LIBRARY_ROOT_REMOVED_ID = "view.libraryRootRemoved";
		EventType<ValueEvent<Root>> LIBRARY_ROOT_REMOVED = ev(LIBRARY_ROOT_REMOVED_ID);

		String NEW_CONTENT_AVAILABLE_ID = "library.newContentAvailable";
		EventType<EmptyEvent> NEW_CONTENT_AVAILABLE = ev(NEW_CONTENT_AVAILABLE_ID);
	}

	/**
	 * Eventos relacionados al estado de la aplicación (cosas que se están mostrando, cosas seleccionadas, etc)
	 */
	interface Application {
		/**
		 * Estado de la busqueda de tracks, indica cuando se empieza y se termina de buscar
		 */
		String SEARCH_TRACKS_ID = "app.SearchTracks";
		EventType<ValueEvent<SearchState>> SEARCH_TRACKS = ev(SEARCH_TRACKS_ID);

		/** Notifica cuando cambian los tracks que está viendo el usuario. */
		EventType<ValueEvent<Integer>> DISPLAYED_ITEM_COUNT_CHANGED = ev(Model.DISPLAYED_ITEM_COUNT_ID);

		String STARTED_ID = "application.started";
		/**
		 * Notifica cuando la aplicación está lista para usarse (modelo inicializado y listo)
		 */
		EventType<ValueEvent<User>> STARTED = ev(STARTED_ID);

		String STOPED_ID = "application.userLogout";
		/** Notifica cuando cambia el usuario hace logout */
		EventType<ValueEvent<User>> STOPED = ev(STOPED_ID);

		EventType<ValueEvent<Collection<Alert>>> CURRENT_ALERTS_CHANGED = ev(Model.CURRENT_ALERTS_ID);

		String GENERIC_ERROR_MESSAGE_ID = "application.errorMessage";
		EventType<ValueEvent<String>> GENERIC_ERROR_MESSAGE = ev(GENERIC_ERROR_MESSAGE_ID);

		String APP_CLOSE_ID = "application.close";
		EventType<ValueEvent<CloseType>> APP_CLOSE = ev(APP_CLOSE_ID);

	}

	interface AutoUpdate {
		String UPDATE_FOUND_ID = "autoUpdate.updateFound";
		EventType<ValueEvent<String>> UPDATE_FOUND = ev(UPDATE_FOUND_ID);

		String UPDATE_DOWNLOAD_PROGRESS_ID = "autoUpdate.updateDownloadProgress";
		EventType<ValueEvent<Integer>> UPDATE_DOWNLOAD_PROGRESS = ev(UPDATE_DOWNLOAD_PROGRESS_ID);

		String UPDATE_DOWNLOAD_COMPLETED_ID = "autoUpdate.updateDownloadCompleted";
		EventType<ValueEvent<File>> UPDATE_DOWNLOAD_COMPLETED = ev(UPDATE_DOWNLOAD_COMPLETED_ID);

		String UPDATE_NOT_FOUND_ID = "autoUpdate.updateNotFound";
		EventType<EmptyEvent> UPDATE_NOT_FOUND = ev(UPDATE_NOT_FOUND_ID);

		String UPDATE_DOWNLOAD_ERROR_ID = "autoUpdate.updateDownloadError";
		EventType<ValueEvent<String>> UPDATE_DOWNLOAD_ERROR = ev(UPDATE_DOWNLOAD_ERROR_ID);
	}

	/** Eventos que son originados por el player. */
	interface Player {
		String VOLUME_CHANGED_ID = "player.volumeChanged";
		EventType<ValueEvent<Integer>> VOLUME_CHANGED = ev(VOLUME_CHANGED_ID);

		String PROGRESS_CHANGED_ID = "player.progressChanged";
		EventType<MediaPlayerProgressEvent> PROGRESS_CHANGED = ev(PROGRESS_CHANGED_ID);

		String PLAYCOUNT_CHANGED_ID = "player.playCountChanged";
		EventType<ValueEvent<Track>> PLAYCOUNT_CHANGED = ev(PLAYCOUNT_CHANGED_ID);

		String TRACK_PLAYED_ID = "player.trackPlayed";
		EventType<MediaPlayerTrackPlayedEvent> TRACK_PLAYED = ev(TRACK_PLAYED_ID);

		String PLAYING_PLAYLIST_CHANGED_ID = "player.playingPlaylistChanged";
		EventType<ValueEvent<TrackContainer>> PLAYING_PLAYLIST_CHANGED = ev(PLAYING_PLAYLIST_CHANGED_ID);

		String STATE_CHANGED_ID = "player.stateChanged";
		EventType<MediaPlayerStateEvent> STATE_CHANGED = ev(STATE_CHANGED_ID);

		String SELECTED_TRACKS_CHANGED_ID = "player.selectedTracksChanged";
		EventType<ValueEvent<List<Track>>> SELECTED_TRACKS_CHANGED = ev(SELECTED_TRACKS_CHANGED_ID);

		String PLAY_ERROR_ID = "player.error";
		EventType<MusicPlayerErrorEvent> PLAY_ERROR = ev(PLAY_ERROR_ID);
	}

	/**
	 * Eventos que se originan para modificar la vista, son derivados de acciones.
	 */
	interface View {
		/** Mostrar mensaje en barra de status. */
		String STATUS_BAR_MESSAGE_ID = "notifications.statusBarMessage";
		EventType<ValueEvent<String>> STATUS_BAR_MESSAGE = ev(STATUS_BAR_MESSAGE_ID);

		/** Cambiar la vista del usuario, (el panel central) */
		EventType<ValueEvent<ContainerView>> CURRENT_VIEW_CHANGED = ev(Model.CURRENT_VIEW_ID);
		/** Mostrar o ocultar el panel de alertas. */
		EventType<ValueEvent<Boolean>> DRAWER_DISPLAYED_CHANGED = ev(Model.DRAWER_DISPLAYED_ID);

		EventType<ValueEvent<SubViews>> CURRENT_SUBVIEW_CHANGED = ev(Model.CURRENT_SUBVIEW_ID);

		String SELECTED_TRACKCONTAINER_CHANGED_ID = "view.SelectedTrackContainseChanged";
		EventType<SelectTrackContainerEvent> SELECTED_TRACKCONTAINER_CHANGED = ev(SELECTED_TRACKCONTAINER_CHANGED_ID);

		String CLIPBOARD_SELECTION_CHANGED_ID = "clipboard.selectionChanged";
		EventType<ValueEvent<List<?>>> CLIPBOARD_SELECTION_CHANGED = ev(CLIPBOARD_SELECTION_CHANGED_ID);

		String TOP_HUNDRED_CATEGORY_MODEL_SELECTION_ID = "topHundred.modelCategorySelection";
		EventType<ValueEvent<ModelContainerView>> TOP_HUNDRED_CATEGORY_MODEL_SELECTION = ev(TOP_HUNDRED_CATEGORY_MODEL_SELECTION_ID);

		String TOP_HUNDRED_PLAYLIST_MODEL_SELECTION_ID = "topHundred.modelPlaylistSelection";
		EventType<ValueEvent<ModelContainerView>> TOP_HUNDRED_PLAYLIST_MODEL_SELECTION = ev(TOP_HUNDRED_PLAYLIST_MODEL_SELECTION_ID);

		String SELECTED_PLAYING_TRACKCONTAINER_ID = "view.selectedPlayingTrackcontainer";
		EventType<ValueEvent<TrackContainer>> SELECTED_PLAYING_TRACKCONTAINER = ev(SELECTED_PLAYING_TRACKCONTAINER_ID);
	 
		
	
	}

	interface Social {
		String SHOW_SEND_CONTENT_DIALOG_ID = "social.showSendContentDialog";
		EventType<SendContentEvent> SHOW_SEND_CONTENT_DIALOG = ev(SHOW_SEND_CONTENT_DIALOG_ID);

		String SHOW_POST_CONTENT_ON_FACEBOOK_DIALOG_ID = "social.showPostContentOnFacebookDialog";
		EventType<FacebookPostContentEvent> SHOW_POST_CONTENT_ON_FACEBOOK_DIALOG = ev(SHOW_POST_CONTENT_ON_FACEBOOK_DIALOG_ID);

		String PROFILE_LOAD_ID = "social.profileLoad";
		EventType<ProfileLoadEvent> PROFILE_LOAD = ev(PROFILE_LOAD_ID);

		EventType<ValueEvent<Profile>> CURRENT_PROFILE = ev(Model.CURRENT_PROFILE_ID);

		String TWITTER_NUMBER_OF_NEW_MESSAGES_RECEIVED_ID = "social.twitter.messagesUpdated";
		EventType<ValueEvent<Long>> TWITTER_NUMBER_OF_NEW_MESSAGES_RECEIVED = ev(TWITTER_NUMBER_OF_NEW_MESSAGES_RECEIVED_ID);

		String TWITTER_LOGGED_ID = "social.twitter.loggedIn";
		EventType<ValueEvent<Boolean>> TWITTER_LOGGED = ev(TWITTER_LOGGED_ID);

		String CONTACT_ADDED_ID = "social.contactAdded";
		EventType<ValueEvent<ContactInfo>> CONTACT_ADDED = ev(CONTACT_ADDED_ID);

		String CONTACT_DELETED_ID = "social.contactDeleted";
		EventType<ValueEvent<ContactInfo>> CONTACT_DELETED = ev(CONTACT_DELETED_ID);

		String CONTACT_UPDATED_ID = "social.contactUpdated";
		EventType<ValueEvent<ContactInfo>> CONTACT_UPDATED = ev(CONTACT_UPDATED_ID);

		String EMAIL_INVITATION_SENT_ID = "social.pendingEmailConfirmation";
		EventType<ValueEvent<List<String>>> EMAIL_INVITATION_SENT = ev(EMAIL_INVITATION_SENT_ID);

		String TWITTER_ERROR_ID = "social.twitterError";
		EventType<ValueEvent<Integer>> TWITTER_ERROR = ev(TWITTER_ERROR_ID);

		String TWITTER_USER_TIMELINE_CHANGED_ID = "social.twitterTimelineChanged";
		EventType<ValueEvent<List<DecoratedTwitterStatus>>> TWITTER_USER_TIMELINE_CHANGED = ev(TWITTER_USER_TIMELINE_CHANGED_ID);

		String REQUEST_FRIENDSHIP_RESPONSE_ID = "social.requestResult";
		EventType<ValueEvent<FriendshipRequestStatus>> REQUEST_FRIENDSHIP_RESPONSE = ev(REQUEST_FRIENDSHIP_RESPONSE_ID);
	}

	interface Chat {

		String LOGIN_ID = "chat.facebook.logging";
		EventType<ChatLoggingEvent> LOGIN = ev(LOGIN_ID);

		EventType<ValueEvent<ChatType>> SELECTED_CHAT_TYPE = ev(Model.SELECTED_CHAT_TYPE_ID);
		EventType<ValueEvent<List<ContactInfo>>> DISPLAYED_CONTACT_LIST_UPDATED = ev(Model.DISPLAYED_CONTACT_LIST_ID);

		String DISPLAY_FACEBOOK_AUTHORIZATION_DIALOG_ID = "chat.displayFacebookAuthorizationDialog";
		EventType<EmptyEvent> DISPLAY_FACEBOOK_AUTHORIZATION_DIALOG = ev(DISPLAY_FACEBOOK_AUTHORIZATION_DIALOG_ID);

		String INCOMING_MESSAGE_ID = "chat.incomingMessage";
		EventType<ValueEvent<ChatMessage>> INCOMING_MESSAGE = ev(INCOMING_MESSAGE_ID);

	}

	interface Downloads {
		String ADDED_ID = "download.added";
		EventType<ValueEvent<Download>> ADDED = ev(ADDED_ID);

		String REMOVED_ID = "download.removed";
		EventType<ValueEvent<Download>> REMOVED = ev(REMOVED_ID);

		String UPDATED_ID = "download.updated";
		EventType<ValueEvent<Download>> UPDATED = ev(UPDATED_ID);

		String ALL_MODIFIED_ID = "download.all.modified";
		EventType<EmptyEvent> ALL_MODIFIED = ev(ALL_MODIFIED_ID);

		String COMPLETED_ID = "download.completed";
		EventType<ValueEvent<Download>> COMPLETED = ev(COMPLETED_ID);
		
		String TRACK_ALREADY_AVAILABLE_ID = "track.Already.Available";
		EventType<EmptyEvent> TRACK_ALREADY_AVAILABLE = ev(TRACK_ALREADY_AVAILABLE_ID);
	}

	interface Search {
		String DATA_UPDATED_ID = "search.dataUpdated";
		EventType<ValueEvent<SearchDataEvent>> DATA_UPDATED = ev(DATA_UPDATED_ID);

		String PROGRESS_ID = "search.progress";
		EventType<ValueEvent<SearchProgressEvent>> PROGRESS = ev(PROGRESS_ID);

		String ERROR_ID = "search.error";
		EventType<ValueEvent<SearchErrorEvent>> ERROR = ev(ERROR_ID);
	}

	interface Devices {
		String ADD_DEVICE_ID = "devices.addDevice";
		EventType<ValueEvent<DeviceBase>> ADD_DEVICE = ev(ADD_DEVICE_ID);

		String REMOVE_DEVICE_ID = "devices.removeDevice";
		EventType<ValueEvent<DeviceBase>> REMOVE_DEVICE = ev(REMOVE_DEVICE_ID);

		String FILE_PROGRESS_ID = "devices.fileProgress";
		EventType<FileProgressEvent> FILE_PROGRESS = ev(FILE_PROGRESS_ID);

		String BYTE_PROGRESS_ID = "devices.byteProgress";
		EventType<ByteProgressEvent> BYTE_PROGRESS = ev(BYTE_PROGRESS_ID);

		String START_COPY_ID = "devices.startCopy";
		EventType<EmptyEvent> START_COPY = ev(START_COPY_ID);

		String FINISH_COPY_ID = "devices.finishCopy";
		EventType<EmptyEvent> FINISH_COPY = ev(FINISH_COPY_ID);

		String CANNOT_COPY_ID = "devices.cannotCopy";
		EventType<EmptyEvent> CANNOT_COPY = ev(CANNOT_COPY_ID);

		String DEVICE_FULL_ID = "devices.full";
		EventType<EmptyEvent> DEVICE_FULL = ev(DEVICE_FULL_ID);
	}

	interface UserProfile {
		String USER_PROFILE_UPDATED_ID = "user.profileUpdated";
		EventType<ValueEvent<User>> USER_PROFILE_UPDATED = ev(USER_PROFILE_UPDATED_ID);

	}

	interface Alerts {
		String CONFIRM_REQUEST_ALERT_ID = "alerts.sendRequestAlert";
		EventType<ValueEvent<McRequestAlert>> CONFIRM_REQUEST_ALERT = ev(CONFIRM_REQUEST_ALERT_ID);

		String UPLOAD_CONTENT_STARTED_ID = "alerts.uploadContentStarted";
		EventType<ValueEvent<Long>> UPLOAD_CONTENT_STARTED = ev(UPLOAD_CONTENT_STARTED_ID);

		String UPLOAD_CONTENT_DONE_ID = "alerts.uploadContentDone";
		EventType<ValueEvent<Long>> UPLOAD_CONTENT_DONE = ev(UPLOAD_CONTENT_DONE_ID);

		String UPLOAD_CONTENT_PROGRESS_ID = "alerts.uploadContentProgress";
		EventType<UploadContentProgressEvent> UPLOAD_CONTENT_PROGRESS = ev(UPLOAD_CONTENT_PROGRESS_ID);
		
		String NEW_ALERT_ID = "alerts.new";
		EventType<EmptyEvent> NEW_ALERT = ev(NEW_ALERT_ID);
		
		String RECOMMENDATION_RESPONSE_ID = "alerts.facebookRecommendationId";
		EventType<ValueEvent<Long>> RECOMMENDATION_RESPONSE = ev(RECOMMENDATION_RESPONSE_ID);
		
	}

	interface Feeds {
		String NEW_FEEDS_ID = "feeds.NewFeed";
		EventType<ValueEvent<Set<AllFeed>>> NEW_FEEDS = ev(NEW_FEEDS_ID);

		String UPDATE_FEED_TIME_VIEW_ID = "feeds.updateFeedTimeView";
		EventType<ValueEvent<Date>> UPDATE_FEED_TIME_VIEW = ev(UPDATE_FEED_TIME_VIEW_ID);
	}

}
