package com.all.shared.stats.usage;

/*
 * INTEGER CONSTANTS TO MAP THE USER ACTION EVENTS.
 * */
public interface UserActions {

	String USER_ACTION_MESSAGE_TYPE = "USER_ACTION";

	// README: DONT CHANGE ANY CONSTANT VALUE SINCE THESE ARE MAPPED TO THE
	// REPORTS DB.

	interface Player {
		/*
		 * All import events are exclusive. The Local HD import event is the sum of
		 * the users that have used the file chooser and the ones that have done a
		 * system drag. The external device event only takes into account the
		 * imports that came from the external devices section within the mc app.
		 */
		int IMPORT_MEDIA_ITUNES = 0;
		int IMPORT_MEDIA_FILECHOOSER = 3;
		int IMPORT_MEDIA_SYSTEM_DRAG = 9;
		int IMPORT_MEDIA_REMOTE_LIBRARY = 10;
		int IMPORT_MEDIA_MC_EXTERNAL_DEVICE = 11;

		int FIND_LOCAL_MEDIA = 5;
		int EXPORT_TO_EXTERNAL_DEVICES = 6;
		int PLAYCOUNT = 7;
		int SKIPCOUNT = 8;
		int SEARCH_TRACK_LOCALLY = 12;
	}

	interface Friendships {
		int SEARCH_USER = 100;
		int FRIENDSHIP_REQUEST = 101;
		int EMAIL_INVITATION = 102;
	}

	interface AllNetwork {
		int BROWSE_REMOTE_LIBRARY = 200;
		int ALERT_MC_CONTENT = 203;
		int ALERT_MC_REQUEST = 204;
		int MC_CHAT = 205;
		int BROWSE_FRIEND_PROFILE = 206;
		int BROWSE_ONLINE_USERS = 207;
		int WALL_POST = 208;
		int LOGIN = 209;
		int ACCESS_ALERTS_PANEL = 210;
	}

	interface Downloads {
		int SEARCH_MEDIA = 300;
		int GNUTELLA_DOWNLOAD = 301;
		int LAN_DOWNLOAD = 302;
		int MC_DOWNLOAD = 303;
		int MC_UPLOAD = 304;
		int BROWSE_DOWNLOAD_TABLE = 305;
		int DOWNLOAD_SEARCH_RESULT = 306;
		int DRAG_SEARCH_RESULT = 307;
		int BROWSE_SEARCH_TABLE = 308;
		int GNUTELLA_ULTRAPEER_CONNECTION = 309;
	}

	interface SocialNetworks {
		int ACCESS_TWITTER_PANEL = 400;
		int ACCESS_FACEBOOK_PANEL = 401;
		int ACCESS_YOUTUBE_PANEL = 402;
		int ACCESS_GROOVESHARK_PANEL = 403;
		int TWITTER_LOGIN = 404;
		int TWITTER_TRACK = 405;
		int TWITTER_RECOMMENDATION = 406;
		int TWITTER_STATUS = 407;
		int ACCESS_AREAMUSIC_PANEL = 408;
		int ACCESS_ALL_PANEL = 409;
		int FACEBOOK_CHAT_LOGIN = 410;
		int FACEBOOK_CHAT_MESSAGE_SENT = 411;
	}

	interface UserData {
		int UPDATE_PROFILE = 500;
		int UPDATE_AVATAR = 501;
		int UPDATE_QUOTE = 502;
	}

	interface Wizard {
		int UNCHECK_WIZARD = 600;
		int REOPEN_WIZARD = 601;
	}

	interface Crawler {
		int EXECUTE_CRAWLER = 700;
		int ACCESS_CRAWLER = 701;
	}
	
	interface ContactList {
		int SEARCH_CONTACT_LOCALLY = 800;
	}

	interface Errors {
		/*
		 * This event is reported when the application starts for the first time and
		 * it is closed without a login.
		 */
		int USER_DID_NOT_LOGIN_ON_FIRST_TIME = 9000;
		int BROWSER_INITIZATION_FAIL = 9001;
	}
	
	interface Toolbar {
		int ACCESS_HOME = 900;
		
		int ACCESS_PROFILE = 901;
		
		int ACCESS_HUNDRED = 902;

		int ACCESS_SEND_MUSIC = 903;

		int ACCESS_FIND_FRIENDS = 904;
	}

}
