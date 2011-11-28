package com.all.shared.stats;

import java.io.Serializable;
import java.lang.reflect.Field;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.all.shared.stats.usage.UserActionStat;
import com.all.shared.stats.usage.UserActions;

@Entity
public class UserSessionStat extends AbstractAllStat {
	private static final Log LOG = LogFactory.getLog(UserSessionStat.class);

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private long timestamp;
	@NotNull
	private String email;
	@NotNull
	private long sessionDuration = 0;

	@Transient
	private long sessionStart = System.currentTimeMillis();

	@UserAction({ UserActions.Player.PLAYCOUNT, UserActions.Player.SKIPCOUNT })
	private boolean played;

	@UserAction({ UserActions.Player.IMPORT_MEDIA_FILECHOOSER, UserActions.Player.IMPORT_MEDIA_ITUNES,
			UserActions.Player.IMPORT_MEDIA_MC_EXTERNAL_DEVICE, UserActions.Player.IMPORT_MEDIA_REMOTE_LIBRARY,
			UserActions.Player.IMPORT_MEDIA_SYSTEM_DRAG })
	private boolean imported;

	@UserAction(UserActions.Player.FIND_LOCAL_MEDIA)
	private boolean findLocalMedia;

	@UserAction(UserActions.Player.EXPORT_TO_EXTERNAL_DEVICES)
	private boolean exportToDevices;

	@UserAction(UserActions.Friendships.SEARCH_USER)
	private boolean searchUser;

	@UserAction(UserActions.Friendships.FRIENDSHIP_REQUEST)
	private boolean friendRequest;

	@UserAction(UserActions.Friendships.EMAIL_INVITATION)
	private boolean emailInvitation;

	@UserAction(UserActions.AllNetwork.BROWSE_REMOTE_LIBRARY)
	private boolean browseRemoteLibrary;

	@UserAction({ UserActions.AllNetwork.ALERT_MC_CONTENT, UserActions.AllNetwork.ALERT_MC_REQUEST })
	private boolean alertReceived;

	@UserAction(UserActions.AllNetwork.MC_CHAT)
	private boolean chat;

	@UserAction(UserActions.AllNetwork.BROWSE_FRIEND_PROFILE)
	private boolean browseProfile;

	@UserAction(UserActions.AllNetwork.BROWSE_ONLINE_USERS)
	private boolean browseOnlineUsers;

	@UserAction(UserActions.AllNetwork.WALL_POST)
	private boolean wallPost;

	@UserAction({ UserActions.Downloads.SEARCH_MEDIA, UserActions.Downloads.DOWNLOAD_SEARCH_RESULT })
	private boolean downloadSearch;

	@UserAction({ UserActions.Downloads.GNUTELLA_DOWNLOAD, UserActions.Downloads.LAN_DOWNLOAD,
			UserActions.Downloads.MC_DOWNLOAD })
	private boolean download;

	@UserAction({ UserActions.Downloads.MC_UPLOAD })
	private boolean upload;

	@UserAction({ UserActions.SocialNetworks.ACCESS_TWITTER_PANEL, UserActions.SocialNetworks.TWITTER_LOGIN,
			UserActions.SocialNetworks.TWITTER_RECOMMENDATION, UserActions.SocialNetworks.TWITTER_STATUS })
	private boolean twitter;

	@UserAction(UserActions.SocialNetworks.TWITTER_TRACK)
	private boolean twitterTrack;

	@UserAction(UserActions.SocialNetworks.ACCESS_FACEBOOK_PANEL)
	private boolean facebook;

	@UserAction(UserActions.SocialNetworks.ACCESS_YOUTUBE_PANEL)
	private boolean youtube;

	@UserAction(UserActions.SocialNetworks.ACCESS_GROOVESHARK_PANEL)
	private boolean grooveshark;

	@UserAction({ UserActions.UserData.UPDATE_PROFILE, UserActions.UserData.UPDATE_AVATAR,
			UserActions.UserData.UPDATE_QUOTE })
	private boolean updateProfile;

	@UserAction(UserActions.Downloads.GNUTELLA_ULTRAPEER_CONNECTION)
	private boolean gnutellaUltrapeerConnection;

	@UserAction(UserActions.SocialNetworks.FACEBOOK_CHAT_MESSAGE_SENT)
	private boolean facebookChat;

	@Deprecated
	public UserSessionStat() {
	}

	public UserSessionStat(String email) {
		setEmail(email);
	}

	public void add(AllStat stat) {
		if (stat instanceof UserActionStat) {
			UserActionStat userAction = (UserActionStat) stat;
			addStat(userAction);
		}
	}

	private void addStat(UserActionStat userActionStat) {
		try {
			Field[] declaredFields = getClass().getDeclaredFields();
			for (Field field : declaredFields) {
				if (field.getType().equals(boolean.class) && field.isAnnotationPresent(UserAction.class)) {
					UserAction userAction = field.getAnnotation(UserAction.class);
					for (int actionType : userAction.value()) {
						if (actionType == userActionStat.getAction()) {
							field.setAccessible(true);
							field.set(this, true);
						}
					}
				}
			}
		} catch (IllegalArgumentException e) {
			LOG.error(e, e);
		} catch (IllegalAccessException e) {
			LOG.error(e, e);
		}
	}

	public void close() {
		setSessionDuration(System.currentTimeMillis() - sessionStart);
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String getEmail() {
		return email;
	}

	@Override
	public Serializable getId() {
		return id;
	}

	@Override
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public long getTimestamp() {
		return timestamp;
	}

	public long getSessionDuration() {
		return sessionDuration;
	}

	public void setSessionDuration(long sessionDuration) {
		if (sessionDuration > 0) {
			this.sessionDuration = sessionDuration;
		} else {
			this.sessionDuration = 0;
		}
	}

	public boolean isPlayed() {
		return played;
	}

	public void setPlayed(boolean played) {
		this.played = played;
	}

	public boolean isImported() {
		return imported;
	}

	public void setImported(boolean imported) {
		this.imported = imported;
	}

	public boolean isFindLocalMedia() {
		return findLocalMedia;
	}

	public void setFindLocalMedia(boolean findLocalMedia) {
		this.findLocalMedia = findLocalMedia;
	}

	public boolean isExportToDevices() {
		return exportToDevices;
	}

	public void setExportToDevices(boolean exportToDevices) {
		this.exportToDevices = exportToDevices;
	}

	public boolean isSearchUser() {
		return searchUser;
	}

	public void setSearchUser(boolean searchUser) {
		this.searchUser = searchUser;
	}

	public boolean isFriendRequest() {
		return friendRequest;
	}

	public void setFriendRequest(boolean friendRequest) {
		this.friendRequest = friendRequest;
	}

	public boolean isEmailInvitation() {
		return emailInvitation;
	}

	public void setEmailInvitation(boolean emailInvitation) {
		this.emailInvitation = emailInvitation;
	}

	public boolean isBrowseRemoteLibrary() {
		return browseRemoteLibrary;
	}

	public void setBrowseRemoteLibrary(boolean browseRemoteLibrary) {
		this.browseRemoteLibrary = browseRemoteLibrary;
	}

	public boolean isAlertReceived() {
		return alertReceived;
	}

	public void setAlertReceived(boolean alertReceived) {
		this.alertReceived = alertReceived;
	}

	public boolean isChat() {
		return chat;
	}

	public void setChat(boolean chat) {
		this.chat = chat;
	}

	public boolean isBrowseProfile() {
		return browseProfile;
	}

	public void setBrowseProfile(boolean browseProfile) {
		this.browseProfile = browseProfile;
	}

	public boolean isBrowseOnlineUsers() {
		return browseOnlineUsers;
	}

	public void setBrowseOnlineUsers(boolean browseOnlineUsers) {
		this.browseOnlineUsers = browseOnlineUsers;
	}

	public boolean isWallPost() {
		return wallPost;
	}

	public void setWallPost(boolean wallPost) {
		this.wallPost = wallPost;
	}

	public boolean isDownloadSearch() {
		return downloadSearch;
	}

	public void setDownloadSearch(boolean downloadSearch) {
		this.downloadSearch = downloadSearch;
	}

	public boolean isDownload() {
		return download;
	}

	public void setDownload(boolean download) {
		this.download = download;
	}

	public boolean isUpload() {
		return upload;
	}

	public void setUpload(boolean upload) {
		this.upload = upload;
	}

	public boolean isTwitter() {
		return twitter;
	}

	public void setTwitter(boolean twitter) {
		this.twitter = twitter;
	}

	public boolean isTwitterTrack() {
		return twitterTrack;
	}

	public void setTwitterTrack(boolean twitterTrack) {
		this.twitterTrack = twitterTrack;
	}

	public boolean isFacebook() {
		return facebook;
	}

	public void setFacebook(boolean facebook) {
		this.facebook = facebook;
	}

	public boolean isYoutube() {
		return youtube;
	}

	public void setYoutube(boolean youtube) {
		this.youtube = youtube;
	}

	public boolean isGrooveshark() {
		return grooveshark;
	}

	public void setGrooveshark(boolean grooveshark) {
		this.grooveshark = grooveshark;
	}

	public boolean isUpdateProfile() {
		return updateProfile;
	}

	public void setUpdateProfile(boolean updateProfile) {
		this.updateProfile = updateProfile;
	}

	public void setGnutellaUltrapeerConnection(boolean gnutellaUltrapeerConnection) {
		this.gnutellaUltrapeerConnection = gnutellaUltrapeerConnection;
	}

	public boolean isGnutellaUltrapeerConnection() {
		return gnutellaUltrapeerConnection;
	}

	public boolean isFacebookChat() {
		return facebookChat;
	}

	public void setFacebookChat(boolean facebookChat) {
		this.facebookChat = facebookChat;
	}

}
