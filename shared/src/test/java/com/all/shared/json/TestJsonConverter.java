package com.all.shared.json;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

import com.all.shared.alert.Alert;
import com.all.shared.alert.AllNotificationAlert;
import com.all.shared.alert.FriendshipRequestAlert;
import com.all.shared.alert.FriendshipResponseAlert;
import com.all.shared.alert.MusicContentAlert;
import com.all.shared.command.LoginCommand;
import com.all.shared.external.email.EmailDomain;
import com.all.shared.login.LoginError;
import com.all.shared.mc.TrackSearchResult;
import com.all.shared.messages.CrawlerRequest;
import com.all.shared.messages.CrawlerResponse;
import com.all.shared.messages.EmailContact;
import com.all.shared.messages.FeedsResponse;
import com.all.shared.messages.FriendshipRequestStatus;
import com.all.shared.messages.LoginResponse;
import com.all.shared.model.AllMessage;
import com.all.shared.model.Avatar;
import com.all.shared.model.Category;
import com.all.shared.model.ChatMessage;
import com.all.shared.model.City;
import com.all.shared.model.ContactInfo;
import com.all.shared.model.ContactLocation;
import com.all.shared.model.ContactRequest;
import com.all.shared.model.ContactStatus;
import com.all.shared.model.FeedTrack;
import com.all.shared.model.Folder;
import com.all.shared.model.Gender;
import com.all.shared.model.ModelCollection;
import com.all.shared.model.PendingEmail;
import com.all.shared.model.PendingEmailCollection;
import com.all.shared.model.Playlist;
import com.all.shared.model.PresenceInfo;
import com.all.shared.model.PushMessage;
import com.all.shared.model.RemoteFolder;
import com.all.shared.model.RemotePlaylist;
import com.all.shared.model.RemoteTrack;
import com.all.shared.model.SimpleSmartPlaylist;
import com.all.shared.model.SmartPlaylist;
import com.all.shared.model.Track;
import com.all.shared.model.UltrapeerNode;
import com.all.shared.model.UltrapeerSessionResponse;
import com.all.shared.model.User;
import com.all.shared.newsfeed.AllFeed;
import com.all.shared.newsfeed.AvatarFeed;
import com.all.shared.newsfeed.ContactsFeed;
import com.all.shared.newsfeed.DeviceExportFeed;
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
import com.all.shared.newsfeed.UpdateProfileFeed;
import com.all.shared.stats.AllStat;
import com.all.shared.stats.ErrorEventStat;
import com.all.shared.stats.FeedStat;
import com.all.shared.stats.PostedListeningTrackTwitterStat;
import com.all.shared.stats.RecommendedTrackFacebookStat;
import com.all.shared.stats.RecommendedTrackTwitterStat;
import com.all.shared.stats.TopHundredStat;
import com.all.shared.stats.UpdaterStat;
import com.all.shared.stats.usage.UserActionStat;
import com.all.shared.stats.usage.UserActions;

@SuppressWarnings("deprecation")
public class TestJsonConverter {
	private static final int TOTAL_CONTACTS = 3;

	@Test
	public void shouldConvertUser() throws Exception {
		User user = createUser();

		String json = JsonConverter.toJson(user);
		User result = (User) JsonConverter.toBean(json, User.class);
		assertNotNull(result);
		compareUser(user, result);
	}

	@Test
	public void shouldConvertContactInfo() throws Exception {
		ContactInfo contact = createContact();

		String json = JsonConverter.toJson(contact);
		ContactInfo result = (ContactInfo) JsonConverter.toBean(json, ContactInfo.class);

		compareContact(contact, result);

	}

	@Test
	public void shouldConvertContactRequest() throws Exception {
		ContactRequest request = createContactRequest();

		String json = JsonConverter.toJson(request);
		ContactRequest result = (ContactRequest) JsonConverter.toBean(json, ContactRequest.class);

		assertNotNull(result);
		compareContactRequest(request, result);
	}

	@Test
	public void shouldConvertPendingEmail() throws Exception {
		PendingEmail pendingEmail = createPendingEmail();

		String json = JsonConverter.toJson(pendingEmail);

		PendingEmail result = (PendingEmail) JsonConverter.toBean(json, PendingEmail.class);
		assertNotNull(result);
		comparePendingEmail(pendingEmail, result);
	}

	@Test
	public void shouldConvertPendingEmailCollection() throws Exception {
		PendingEmailCollection collection = createPendingEmailCollection();

		String json = JsonConverter.toJson(collection);
		PendingEmailCollection result = (PendingEmailCollection) JsonConverter.toBean(json,
				PendingEmailCollection.class);

		assertNotNull(result);
		assertEquals(collection.getFromMail(), result.getFromMail());
		assertEquals(collection.getToEmails(), result.getToEmails());
		assertEquals(collection.getSubject(), result.getSubject());
		assertEquals(collection.getMessage(), result.getMessage());
		assertEquals(collection.getUserId(), result.getUserId());
	}

	@Test
	public void shouldConvertFriendshipRequestAlert() throws Exception {

		FriendshipRequestAlert fra = new FriendshipRequestAlert(createContactRequest());

		String json = JsonConverter.toJson(fra);
		FriendshipRequestAlert result = (FriendshipRequestAlert) JsonConverter.toBean(json, Alert.class);

		assertNotNull(result);
		compareAlert(fra, result);
		compareContactRequest(fra.getRequest(), result.getRequest());
	}

	@Test
	public void shouldConvertFriendshipResponseAlert() throws Exception {
		FriendshipResponseAlert fra = new FriendshipResponseAlert(createContactRequest());

		String json = JsonConverter.toJson(fra);
		FriendshipResponseAlert result = (FriendshipResponseAlert) JsonConverter.toBean(json, Alert.class);

		assertNotNull(result);
		compareAlert(fra, result);
		compareContactRequest(fra.getRequest(), ((FriendshipResponseAlert) result).getRequest());
	}

	@Test
	public void shouldConvertPresenceInfo() throws Exception {

		PresenceInfo presenceInfo = new PresenceInfo();
		presenceInfo.setEmail("email@all.com");
		presenceInfo.setOnline(true);

		String json = JsonConverter.toJson(presenceInfo);

		PresenceInfo result = (PresenceInfo) JsonConverter.toBean(json, PresenceInfo.class);
		assertNotNull(result);
		assertEquals(presenceInfo.getEmail(), result.getEmail());
		assertEquals(presenceInfo.isOnline(), result.isOnline());
	}

	@Test
	public void shouldConvertCity() throws Exception {
		City city = createCity();
		String json = JsonConverter.toJson(city);

		City result = (City) JsonConverter.toBean(json, City.class);
		assertNotNull(result);
		compareCity(city, result);
	}

	@Test
	public void shouldConvertQuoteFeed() throws Exception {
		ContactInfo contact = createContact();
		QuoteFeed expected = new QuoteFeed(contact, "Quote example");
		String json = JsonConverter.toJson(expected);
		QuoteFeed actual = (QuoteFeed) JsonConverter.toBean(json, QuoteFeed.class);
		compareQuoteFeed(expected, actual);
	}

	@Test
	public void shouldConvertAvatarFeed() throws Exception {
		ContactInfo contact = createContact();
		AvatarFeed expected = new AvatarFeed(contact, new byte[] { 12, 13, 14, 16, 29 });
		String json = JsonConverter.toJson(expected);
		AvatarFeed actual = (AvatarFeed) JsonConverter.toBean(json, AvatarFeed.class);
		compareAvatarFeed(expected, actual);
	}

	@Test
	public void shouldConvertTwitterFeed() throws Exception {
		ContactInfo contact = createContact();
		String trackName = "trackName";
		String hashcode = "hashcode";
		String artist = "artist";
		FeedTrack feedTrack = new FeedTrack(hashcode, trackName, artist);
		RecommendedTrackTwtterFeed expected = new RecommendedTrackTwtterFeed(contact, feedTrack);
		String json = JsonConverter.toJson(expected);
		RecommendedTrackTwtterFeed actual = (RecommendedTrackTwtterFeed) JsonConverter.toBean(json,
				RecommendedTrackTwtterFeed.class);
		compareTwitterFeed(expected, actual);

	}

	@Test
	public void shouldConvertFeedsResponse() throws Exception {
		ArrayList<AllFeed> feedList = new ArrayList<AllFeed>();
		QuoteFeed quoteFeed = new QuoteFeed(new ContactInfo(createUser()), "My pimpy original quote");
		feedList.add(quoteFeed);
		Date currentServerTime = new Date();
		FeedsResponse expected = new FeedsResponse(feedList, 1L, currentServerTime);
		String json = JsonConverter.toJson(expected);
		FeedsResponse result = (FeedsResponse) JsonConverter.toBean(json, FeedsResponse.class);
		assertEquals(expected.getOwnerId(), result.getOwnerId());
		assertNotNull(result.getCurrentServerTime());
		assertEquals(expected.getFeeds(), result.getFeeds());
	}

	@Test
	public void shouldConvertPostedListeningTrackTwitter() throws Exception {
		FeedTrack feedTrack = new FeedTrack("hashcode", "album", "artist");
		PostedListeningTrackTwitterFeed postedListeningTrackTwitterFeed = new PostedListeningTrackTwitterFeed(
				createContact(), feedTrack);

		String json = JsonConverter.toJson(postedListeningTrackTwitterFeed);
		AllFeed actual = JsonConverter.toBean(json, AllFeed.class);

		assertNotNull(actual);
		assertEquals(postedListeningTrackTwitterFeed.getOwner(), actual.getOwner());
		assertEquals(postedListeningTrackTwitterFeed.getType(), actual.getType());
		assertEquals(postedListeningTrackTwitterFeed.getDate(), actual.getDate());
		assertEquals(postedListeningTrackTwitterFeed.getFeedTrack(), ((PostedListeningTrackTwitterFeed) actual)
				.getFeedTrack());
		compareContact(postedListeningTrackTwitterFeed.getOwner(), actual.getOwner());
	}

	@Test
	public void shouldConvertLoginCommand() throws Exception {
		LoginCommand loginCommand = new LoginCommand("email@all.com", "password");
		loginCommand.setEncryptedPwd("1234567890q234567890");
		String json = JsonConverter.toJson(loginCommand);
		LoginCommand result = (LoginCommand) JsonConverter.toBean(json, LoginCommand.class);
		assertEquals(loginCommand.getEmail(), result.getEmail());
		assertEquals(loginCommand.getPassword(), result.getPassword());
		assertEquals(loginCommand.getEncryptedPwd(), result.getEncryptedPwd());
	}

	private LoginResponse createLoginResponse() {
		LoginResponse loginResponse = new LoginResponse();
		User user = createUser();
		loginResponse.setUser(user);
		loginResponse.setError(LoginError.INVALID_CREDENTIALS);
		loginResponse.setFirstLogin(true);
		List<ContactRequest> pendingRequests = new ArrayList<ContactRequest>();
		ContactRequest request = createContactRequest();
		pendingRequests.add(request);
		loginResponse.setPendingRequests(pendingRequests);
		loginResponse.setSessionId("sessionId");
		return loginResponse;
	}

	@Test
	public void shouldConvertLoginResponse() throws Exception {
		LoginResponse loginResponse = createLoginResponse();
		String json = JsonConverter.toJson(loginResponse);

		LoginResponse result = (LoginResponse) JsonConverter.toBean(json, LoginResponse.class);
		compareLoginResponse(loginResponse, result);
	}

	@Test
	public void shouldConvertRemoteTrack() throws Exception {
		RemoteTrack track = createRemoteTrack();
		String json = JsonConverter.toJson(track);
		Thread.sleep(1);
		RemoteTrack result = (RemoteTrack) JsonConverter.toBean(json, RemoteTrack.class);

		compareTrack(track, result);
	}

	@Test
	public void shouldConvertRemotePlaylist() throws Exception {
		RemotePlaylist playlist = createRemotePlaylist();

		String json = JsonConverter.toJson(playlist);
		RemotePlaylist result = (RemotePlaylist) JsonConverter.toBean(json, RemotePlaylist.class);

		comparePlaylist(playlist, result);
	}

	@Test
	public void shouldConvertRemoteFolder() throws Exception {
		RemoteFolder folder = createRemoteFolder();

		String json = JsonConverter.toJson(folder);
		RemoteFolder result = (RemoteFolder) JsonConverter.toBean(json, RemoteFolder.class);
		compareFolder(folder, result);
	}

	@Test
	public void shouldConvertSimpleSmartPlaylist() throws Exception {
		SmartPlaylist smartPlaylist = createSmartPlaylist();

		String json = JsonConverter.toJson(smartPlaylist);
		SmartPlaylist result = (SmartPlaylist) JsonConverter.toBean(json, SimpleSmartPlaylist.class);
		compareSmartPlaylist(smartPlaylist, result);
	}

	@Test
	public void shouldConvertModelCollection() throws Exception {
		ModelCollection modelCollection = createModelCollection();

		String json = JsonConverter.toJson(modelCollection);
		ModelCollection result = (ModelCollection) JsonConverter.toBean(json, ModelCollection.class);

		compareModelCollection(modelCollection, result);

	}

	@Test
	public void shouldConvertMusicContentAlert() throws Exception {
		MusicContentAlert mca = new MusicContentAlert(createContact(), createContact(), new Date(),
				createModelCollection(), "personal message");

		String json = JsonConverter.toJson(mca);
		MusicContentAlert result = (MusicContentAlert) JsonConverter.toBean(json, Alert.class);

		assertNotNull(result);
		compareAlert(mca, result);
		compareModelCollection(mca.getModel(), result.getModel());
		assertEquals(mca.getPersonalizedMessage(), result.getPersonalizedMessage());
	}

	@Test
	public void shouldConvertAllMessageAlert() throws Exception {
		AllNotificationAlert ama = new AllNotificationAlert(createContact(), System.currentTimeMillis(), "header",
				"description", "link");

		String json = JsonConverter.toJson(ama);
		AllNotificationAlert result = (AllNotificationAlert) JsonConverter.toBean(json, Alert.class);

		assertNotNull(result);
		assertEquals(ama, result);
		compareContact(ama.getReceiver(), result.getReceiver());
		assertEquals(ama.getDate(), ama.getDate());
		assertEquals(ama.getType(), ama.getType());
		assertEquals(ama.getId(), result.getId());
		assertEquals(ama.getHeader(), result.getHeader());
		assertEquals(ama.getDescription(), result.getDescription());
		assertEquals(ama.getLink(), result.getLink());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldConvertAllMessageWithAllMessageBody() throws Exception {
		MusicContentAlert expectedInnerBody = new MusicContentAlert(createContact(), createContact(), new Date(),
				createModelCollection(), "personal message");
		String innerType = "innerType";
		String innerProp = "innerProp";
		AllMessage<Alert> innerMessage = new AllMessage<Alert>(innerType, expectedInnerBody);
		innerMessage.putProperty(innerProp, "inner Prop value");
		String outterType = "outterType";
		AllMessage<AllMessage<Alert>> outterMessage = new AllMessage<AllMessage<Alert>>(outterType, innerMessage);
		String outterProp = "outterProp";
		outterMessage.putProperty(outterProp, "outter prop value");

		String json = JsonConverter.toJson(outterMessage);
		AllMessage<AllMessage<Alert>> actualOutter = (AllMessage<AllMessage<Alert>>) JsonConverter.toBean(json,
				AllMessage.class);

		assertEquals(outterMessage.getType(), actualOutter.getType());
		assertEquals(outterMessage.getProperty(outterProp), actualOutter.getProperty(outterProp));
		AllMessage<Alert> actualInner = actualOutter.getBody();
		assertEquals(innerMessage.getType(), actualInner.getType());
		assertEquals(innerMessage.getProperty(innerProp), actualInner.getProperty(innerProp));
		Alert alertResult = actualInner.getBody();
		compareAlert(expectedInnerBody, alertResult);
		assertTrue(alertResult instanceof MusicContentAlert);
		compareModelCollection(expectedInnerBody.getModel(), ((MusicContentAlert) alertResult).getModel());
		assertEquals(expectedInnerBody.getPersonalizedMessage(), ((MusicContentAlert) alertResult)
				.getPersonalizedMessage());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldConvertAllMessageWithMusicContentAlert() throws Exception {
		String type = "My Type";
		String key = "key";
		String value = "value";
		String key2 = "llave";
		String value2 = "valor";
		MusicContentAlert body = new MusicContentAlert(createContact(), createContact(), new Date(),
				createModelCollection(), "personal message");
		AllMessage<Alert> message = new AllMessage<Alert>(type, body);
		message.putProperty(key, value);
		message.putProperty(key2, value2);

		String json = JsonConverter.toJson(message);
		AllMessage<Alert> result = (AllMessage<Alert>) JsonConverter.toBean(json, AllMessage.class);

		assertNotNull(result);
		assertEquals(result.getProperty(key), value);
		assertEquals(result.getProperty(key2), value2);
		Alert alertResult = result.getBody();
		compareAlert(body, alertResult);
		assertTrue(alertResult instanceof MusicContentAlert);
		compareModelCollection(body.getModel(), ((MusicContentAlert) alertResult).getModel());
		assertEquals(body.getPersonalizedMessage(), ((MusicContentAlert) alertResult).getPersonalizedMessage());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldConvertAllMessageWithTypedCollectionBody() throws Exception {
		ArrayList<Track> tracks = new ArrayList<Track>();
		tracks.add(createRemoteTrack());
		AllMessage<ArrayList<Track>> message = new AllMessage<ArrayList<Track>>("type", tracks);

		String json = JsonConverter.toJson(message);
		AllMessage<ArrayList<Track>> result = (AllMessage<ArrayList<Track>>) JsonConverter.toBean(json,
				AllMessage.class);

		assertNotNull(result);
		ArrayList<Track> resultContacts = result.getBody();
		assertEquals(tracks.size(), resultContacts.size());
		compareTrack(tracks.get(0), resultContacts.get(0));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldConvertAllMessageWithPrimitiveCollectionBody() throws Exception {
		HashSet<Long> expected = new HashSet<Long>();
		expected.add(1L);
		expected.add(2L);
		AllMessage<HashSet<Long>> message = new AllMessage<HashSet<Long>>("type", expected);

		String json = JsonConverter.toJson(message);
		AllMessage<HashSet<Long>> result = (AllMessage<HashSet<Long>>) JsonConverter.toBean(json, AllMessage.class);

		assertNotNull(result);
		HashSet<Long> actual = result.getBody();
		assertEquals(expected, actual);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldConvertAllMessageWithTypedCollectionBodyWithPolimorphism() throws Exception {
		TreeSet<Alert> alerts = new TreeSet<Alert>();
		alerts.add(new FriendshipRequestAlert(createContactRequest()));
		alerts.add(new MusicContentAlert(createContact(), createContact(), new Date(), createModelCollection(),
				"personal message"));
		AllMessage<TreeSet<Alert>> message = new AllMessage<TreeSet<Alert>>("type", alerts);

		String json = JsonConverter.toJson(message);
		AllMessage<TreeSet<Alert>> result = (AllMessage<TreeSet<Alert>>) JsonConverter.toBean(json, AllMessage.class);

		assertNotNull(result);
		TreeSet<Alert> resultSet = result.getBody();
		assertEquals(alerts.size(), resultSet.size());
		compareAlert(alerts.first(), resultSet.first());
		compareAlert(alerts.last(), resultSet.last());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldConvertAllMessageWithEmptyCollectionBody() throws Exception {
		ArrayList<Track> tracks = new ArrayList<Track>();
		AllMessage<ArrayList<Track>> message = new AllMessage<ArrayList<Track>>("type", tracks);

		String json = JsonConverter.toJson(message);
		AllMessage<ArrayList<Track>> result = (AllMessage<ArrayList<Track>>) JsonConverter.toBean(json,
				AllMessage.class);

		assertNotNull(result);
		ArrayList<Track> resultContacts = result.getBody();
		assertEquals(tracks.size(), resultContacts.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldConvertAllMessageWithTypedMapBody() throws Exception {
		HashMap<String, Alert> alertsResponse = new HashMap<String, Alert>();
		MusicContentAlert mca = new MusicContentAlert(createContact(), createContact(), new Date(),
				createModelCollection(), "personal message");
		FriendshipRequestAlert fra = new FriendshipRequestAlert(createContactRequest());
		alertsResponse.put(fra.getId(), fra);
		alertsResponse.put(mca.getId(), mca);
		AllMessage<HashMap<String, Alert>> message = new AllMessage<HashMap<String, Alert>>("e.g. alerts response",
				alertsResponse);

		String json = JsonConverter.toJson(message);
		AllMessage<HashMap<String, Alert>> result = (AllMessage<HashMap<String, Alert>>) JsonConverter.toBean(json,
				AllMessage.class);

		assertNotNull(result);
		HashMap<String, Alert> resultMap = result.getBody();
		assertNotNull(resultMap);
		compareAlert(alertsResponse.get(mca.getId()), resultMap.get(mca.getId()));
		compareAlert(alertsResponse.get(fra.getId()), resultMap.get(fra.getId()));
	}

	@Test
	public void shouldConvertChatMessage() throws Exception {
		ChatMessage chatMessage = new ChatMessage(createContact(), createContact(), "kiubolas");

		String json = JsonConverter.toJson(chatMessage);
		ChatMessage result = (ChatMessage) JsonConverter.toBean(json, ChatMessage.class);

		assertNotNull(result);
		compareContact(chatMessage.getSender(), result.getSender());
		compareContact(chatMessage.getRecipient(), result.getRecipient());
		assertEquals(chatMessage.getMessage(), result.getMessage());
		assertEquals(chatMessage.getTime(), result.getTime());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldConvertCollection() throws Exception {
		ArrayList<ContactInfo> list = new ArrayList<ContactInfo>();
		list.add(createContact());

		String json = JsonConverter.toJson(list);
		ArrayList<ContactInfo> result = JsonConverter.toTypedCollection(json, ArrayList.class, ContactInfo.class);

		assertNotNull(result);
		assertEquals(list.size(), result.size());
		compareContact(list.get(0), result.get(0));

		TreeSet<String> set = new TreeSet<String>();
		set.add("one");
		set.add("two");

		json = JsonConverter.toJson(set);
		TreeSet<String> resultSet = JsonConverter.toTypedCollection(json, TreeSet.class, String.class);

		assertEquals(set, resultSet);
	}

	@Test
	public void shouldConvertAvatar() throws Exception {
		byte[] avatarBytes = new byte[] { 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112 };
		Avatar avatar = new Avatar(1L, avatarBytes);

		String json = JsonConverter.toJson(avatar);
		assertEquals(json, "{\"avatarData\":\"ZWZnaGlqa2xtbm9w\",\"idUser\":1}");
		Avatar result = JsonConverter.toBean(json, Avatar.class);

		assertNotNull(result);

		assertEquals(avatar.getIdUser(), result.getIdUser());
		assertNotNull(result.getAvatarData());
		assertEquals(avatar.getAvatarData().length, result.getAvatarData().length);
		assertArrayEquals(avatar.getAvatarData(), result.getAvatarData());

		Avatar legacyResult = JsonConverter.toBean(
				"{\"avatarData\":[101,102,103,104,105,106,107,108,109,110,111,112],\"idUser\":1}", Avatar.class);

		assertNotNull(legacyResult);

		assertEquals(avatar.getIdUser(), legacyResult.getIdUser());
		assertNotNull(legacyResult.getAvatarData());
		assertEquals(avatarBytes.length, legacyResult.getAvatarData().length);
		assertArrayEquals(avatarBytes, legacyResult.getAvatarData());
	}

	@Test
	public void shouldConvertEmailContact() throws Exception {
		EmailContact emailContact = createEmailContact();

		String json = JsonConverter.toJson(emailContact);
		EmailContact actual = JsonConverter.toBean(json, EmailContact.class);

		assertNotNull(actual);
		assertEquals(emailContact.getEmail(), actual.getEmail());
		assertEquals(emailContact.getUnregisteredContacts(), actual.getUnregisteredContacts());
		assertEquals(emailContact.getRegisteredContacts(), actual.getRegisteredContacts());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldConvertAllMessageWithEmptyMap() throws Exception {
		AllMessage<HashMap<String, String>> message = new AllMessage<HashMap<String, String>>("type",
				new HashMap<String, String>());
		String json = JsonConverter.toJson(message);
		AllMessage<HashMap<String, String>> actual = JsonConverter.toBean(json, AllMessage.class);
		assertNotNull(actual);
		HashMap<String, String> body = actual.getBody();
		assertNotNull(body);
		assertTrue(body.isEmpty());
	}

	@Test
	public void shouldConvertBrowseRemoteLibrary() throws Exception {
		ContactInfo contact1 = createContact();
		ContactInfo contact2 = createContact();
		RemoteLibraryBrowsingFeed feed = new RemoteLibraryBrowsingFeed(contact1, contact2);
		String json = JsonConverter.toJson(feed);
		RemoteLibraryBrowsingFeed convertedFeed = (RemoteLibraryBrowsingFeed) JsonConverter.toBean(json, AllFeed.class);
		assertEquals(feed.getDate(), convertedFeed.getDate());
		new BeanAsserter(feed, convertedFeed).inDeep("getOwner",
				new BeanAsserter("Ownner").ignore("getAvatar").inDeep("getCity", new BeanAsserter("Owner.city")))
				.assertEquals();
		assertEquals("RemoteLibraryBrowsingFeed [visited=First Name Last Name]", feed.toString());
	}

	@Test
	public void shouldConvertDeviceExportFeed() throws Exception {
		ContactInfo owner = createContact();
		DeviceExportFeed feed = new DeviceExportFeed(owner, new ModelCollection());
		String json = JsonConverter.toJson(feed);
		DeviceExportFeed actual = JsonConverter.toBean(json, DeviceExportFeed.class);
		assertEquals(feed.getOwner(), actual.getOwner());
		assertNotNull(feed.getDate());
		compareContact(actual.getOwner(), owner);
		assertEquals(feed.getOwner(), actual.getOwner());
		assertEquals(feed.getType(), actual.getType());
		assertNotNull(actual.getDate());
		assertEquals(feed.getFolderCount(), actual.getFolderCount());
		assertEquals(feed.getFolderNames(), actual.getFolderNames());
		assertEquals(feed.getPlaylistCount(), actual.getPlaylistCount());
		assertEquals(feed.getPlaylistNames(), actual.getPlaylistNames());
		assertEquals(feed.getTrackCount(), actual.getTrackCount());
		assertEquals(feed.getTracks(), actual.getTracks());
	}

	private EmailContact createEmailContact() {
		List<ContactInfo> registeredContacts = new ArrayList<ContactInfo>();
		registeredContacts.add(createContact());
		Map<String, String> unregisteredContacts = new HashMap<String, String>();
		String key = "key";
		String value = "value";
		unregisteredContacts.put(key, value);
		EmailContact emailContact = new EmailContact("email@all.com", unregisteredContacts, registeredContacts);
		return emailContact;
	}

	@Test
	public void shouldConvertCrawlerRequest() throws Exception {
		CrawlerRequest request = new CrawlerRequest();
		Map<EmailDomain, List<LoginCommand>> accounts = new HashMap<EmailDomain, List<LoginCommand>>();
		List<LoginCommand> domainAccounts = new ArrayList<LoginCommand>();
		domainAccounts.add(new LoginCommand("some@hotmail.com", "hotmailpassword"));
		accounts.put(EmailDomain.HOTMAIL, domainAccounts);
		request.setAccounts(accounts);

		String json = JsonConverter.toJson(request);
		CrawlerRequest actual = JsonConverter.toBean(json, CrawlerRequest.class);

		assertNotNull(actual);
		Map<EmailDomain, List<LoginCommand>> actualAccounts = actual.getAccounts();
		assertNotNull(actualAccounts);
		List<LoginCommand> actualDomainAccounts = actualAccounts.get(EmailDomain.HOTMAIL);
		assertNotNull(actualDomainAccounts);
		assertTrue(actualDomainAccounts.get(0) instanceof LoginCommand);
	}

	@Test
	public void shouldConvertCrawlerResponse() throws Exception {
		CrawlerResponse crawlerResponse = new CrawlerResponse();
		EmailContact emailContact = createEmailContact();
		crawlerResponse.addEmailContact(emailContact);

		String json = JsonConverter.toJson(crawlerResponse);
		CrawlerResponse actual = JsonConverter.toBean(json, CrawlerResponse.class);

		assertNotNull(actual);
		assertEquals(emailContact.getUnregisteredContacts(), actual.getUnregisteredContacts());
		assertEquals(emailContact.getRegisteredContacts().size(), actual.getRegisteredUsers().size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldConvertTextMessage() throws Exception {
		String body = "some text";
		AllMessage<String> message = new AllMessage<String>("text", body);
		String json = JsonConverter.toJson(message);
		AllMessage<String> actual = JsonConverter.toBean(json, AllMessage.class);
		assertNotNull(actual);
		assertEquals(message.getBody(), actual.getBody());
	}

	@Test
	public void shouldConvertContactLocations() throws Exception {
		ContactLocation expected = new ContactLocation();
		expected.setContactId("somebody@all.com");
		Set<String> privateAddresses = new HashSet<String>();
		privateAddresses.add("192.168.1.123");
		expected.setPrivateAddresses(privateAddresses);
		Set<String> publicAddresses = new HashSet<String>();
		publicAddresses.add("78.56.123.56");
		expected.setPublicAddresses(publicAddresses);

		String json = JsonConverter.toJson(expected);
		ContactLocation actual = JsonConverter.toBean(json, ContactLocation.class);
		assertNotNull(actual);
		assertTrue(actual.getPublicAddresses().containsAll(expected.getPublicAddresses()));
		assertTrue(actual.getPrivateAddresses().containsAll(expected.getPrivateAddresses()));
		assertEquals(expected.getContactId(), actual.getContactId());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldConvertPrimitiveCollection() throws Exception {
		List<Long> longs = new ArrayList<Long>();
		longs.add(1L);
		longs.add(2L);
		String json = JsonConverter.toJson(longs);
		List<Long> actualLongs = JsonConverter.toTypedCollection(json, ArrayList.class, Long.class);
		assertNotNull(actualLongs);
		assertEquals(longs, actualLongs);

		List<Integer> integers = new ArrayList<Integer>();
		integers.add(1);
		integers.add(2);
		json = JsonConverter.toJson(integers);
		List<Integer> actualIntegers = JsonConverter.toTypedCollection(json, ArrayList.class, Integer.class);
		assertNotNull(actualIntegers);
		assertEquals(integers, actualIntegers);

		List<Double> doubles = new ArrayList<Double>();
		doubles.add(1.5);
		doubles.add(2.5);
		json = JsonConverter.toJson(doubles);
		List<Double> actualDoubles = JsonConverter.toTypedCollection(json, ArrayList.class, Double.class);
		assertNotNull(actualDoubles);
		assertEquals(doubles, actualDoubles);

		List<Float> floats = new ArrayList<Float>();
		floats.add(1.5f);
		floats.add(2.5f);
		json = JsonConverter.toJson(floats);
		List<Float> actualFloats = JsonConverter.toTypedCollection(json, ArrayList.class, Float.class);
		assertNotNull(actualFloats);
		assertEquals(floats, actualFloats);

		List<Boolean> booleans = new ArrayList<Boolean>();
		booleans.add(true);
		booleans.add(false);
		json = JsonConverter.toJson(booleans);
		List<Boolean> actualBooleans = JsonConverter.toTypedCollection(json, ArrayList.class, Boolean.class);
		assertNotNull(actualBooleans);
		assertEquals(booleans, actualBooleans);

		List<String> strings = new ArrayList<String>();
		strings.add("one");
		strings.add("two");
		json = JsonConverter.toJson(strings);
		List<String> actualStrings = JsonConverter.toTypedCollection(json, ArrayList.class, String.class);
		assertEquals(strings, actualStrings);

	}

	@Test
	public void shouldConvertUltrapeerNode() throws Exception {
		UltrapeerNode expected = new UltrapeerNode("192.168.1.1");
		String json = JsonConverter.toJson(expected);
		UltrapeerNode actual = JsonConverter.toBean(json, UltrapeerNode.class);
		assertEquals(expected.getAddress(), actual.getAddress());
	}

	@Test
	public void shouldConvertUltrapeerSessionResponse() throws Exception {
		UltrapeerSessionResponse expected = new UltrapeerSessionResponse();
		expected.setAccepted(true);
		expected.setDeprecatedUltrapeers(Arrays.asList(new UltrapeerNode[] { new UltrapeerNode("192.168.1.3") }));
		expected.setNewUltrapeers(Arrays.asList(new UltrapeerNode[] { new UltrapeerNode("192.168.1.1") }));
		String json = JsonConverter.toJson(expected);
		UltrapeerSessionResponse actual = JsonConverter.toBean(json, UltrapeerSessionResponse.class);
		assertNotNull(actual);
		assertEquals(expected.getDeprecatedUltrapeers(), actual.getDeprecatedUltrapeers());
		assertEquals(expected.getNewUltrapeers(), actual.getNewUltrapeers());
	}

	@Test
	public void shouldConvertTrackSearchResult() throws Exception {
		RemoteTrack track = createRemoteTrack();

		TrackSearchResult expected = new TrackSearchResult(track, 0.8);
		String json = JsonConverter.toJson(expected);
		TrackSearchResult actual = JsonConverter.toBean(json, TrackSearchResult.class);

		assertEquals(expected.getScore(), actual.getScore(), 0.0);
		compareTrack(expected.getTrack(), actual.getTrack());
	}

	@SuppressWarnings( { "unchecked" })
	@Test
	public void shouldConvertCollectionOfTrackSearchResult() throws Exception {
		RemoteTrack trackA = createRemoteTrack();
		RemoteTrack trackB = createRemoteTrack();

		TrackSearchResult expectedA = new TrackSearchResult(trackA, 0.8);
		TrackSearchResult expectedB = new TrackSearchResult(trackB, 0.5);
		String json = JsonConverter.toJson(Arrays.asList(new TrackSearchResult[] { expectedA, expectedB }));
		List<TrackSearchResult> actual = JsonConverter
				.toTypedCollection(json, ArrayList.class, TrackSearchResult.class);

		assertNotNull(actual);
		assertEquals(2, actual.size());
		TrackSearchResult actualA = actual.get(0);
		TrackSearchResult actualB = actual.get(1);

		assertEquals(expectedA.getScore(), actualA.getScore(), 0.0);
		compareTrack(expectedA.getTrack(), actualA.getTrack());

		assertEquals(expectedB.getScore(), actualB.getScore(), 0.0);
		compareTrack(expectedB.getTrack(), actualB.getTrack());
	}

	@Test
	public void shouldConvertUsageStat() throws Exception {
		UserActionStat expected = new UserActionStat();
		expected.setAction(UserActions.Downloads.SEARCH_MEDIA);
		expected.setEmail("user@all.com");
		expected.setTimes(3);
		expected.setTimestamp(System.currentTimeMillis());
		assertNull(expected.getId());

		String json = JsonConverter.toJson(expected);
		UserActionStat actual = JsonConverter.toBean(json, UserActionStat.class);

		assertEquals(expected.getId(), actual.getId());
		assertEquals(expected.getEmail(), actual.getEmail());
		assertEquals(expected.getTimes(), actual.getTimes());
		assertEquals(expected.getTimestamp(), actual.getTimestamp());
		assertEquals(expected.getId(), actual.getId());
	}

	@Test
	public void shouldConvertPrimitiveTypes() throws Exception {
		Integer expected = new Integer(5);

		String json = JsonConverter.toJson(expected);
		Integer actual = JsonConverter.toBean(json, Integer.class);

		assertEquals(expected, actual);
	}

	@Test
	public void shouldConvertErorEventStat() throws Exception {
		ErrorEventStat errorEventStat = createErrorEventStat();

		String json = JsonConverter.toJson(errorEventStat);
		ErrorEventStat result = (ErrorEventStat) JsonConverter.toBean(json, ErrorEventStat.class);
		assertNotNull(result);
		compareErrorEventStat(errorEventStat, result);
	}

	@Test
	public void shouldConvertUpdaterStat() throws Exception {
		UpdaterStat stat = new UpdaterStat("user@all.com", "0.0.27.1", "osX Lion");
		String json = JsonConverter.toJson(stat);
		UpdaterStat result = JsonConverter.toBean(json, UpdaterStat.class);
		assertNotNull(result);
		assertEquals(stat.getEmail(), result.getEmail());
		assertEquals(stat.getClientVersion(), result.getClientVersion());
		assertEquals(stat.getOsVersion(), result.getOsVersion());
		assertEquals(stat.getTimestamp(), result.getTimestamp());
	}

	@Test
	public void shouldConvertRecommendedTrackFacebookStat() throws Exception {
		RecommendedTrackFacebookStat recommendedTrackFacebookStat = new RecommendedTrackFacebookStat("email",
				"hashcode");

		String json = JsonConverter.toJson(recommendedTrackFacebookStat);
		RecommendedTrackFacebookStat result = JsonConverter.toBean(json, RecommendedTrackFacebookStat.class);

		assertNotNull(result);
		assertEquals(recommendedTrackFacebookStat.getStatType(), result.getStatType());
		assertEquals(recommendedTrackFacebookStat.getTimestamp(), result.getTimestamp());
		assertEquals(recommendedTrackFacebookStat.getEmail(), result.getEmail());
		assertEquals(recommendedTrackFacebookStat.getHashcode(), result.getHashcode());
	}

	@Test
	public void shouldConvertPostedListeningTrackTwitterStat() throws Exception {
		PostedListeningTrackTwitterStat postedListeningTrackTwitterStat = new PostedListeningTrackTwitterStat("email",
				"hashcode");
		String json = JsonConverter.toJson(postedListeningTrackTwitterStat);
		PostedListeningTrackTwitterStat result = JsonConverter.toBean(json, PostedListeningTrackTwitterStat.class);
		assertNotNull(result);
		assertEquals(postedListeningTrackTwitterStat.getEmail(), result.getEmail());
		assertEquals(postedListeningTrackTwitterStat.getHashcode(), result.getHashcode());
		assertEquals(postedListeningTrackTwitterStat.getStatType(), result.getStatType());
		assertEquals(postedListeningTrackTwitterStat.getTimestamp(), result.getTimestamp());
	}

	@Test
	public void shouldConvertRecommendedTrackTwitterStat() throws Exception {
		RecommendedTrackTwitterStat recommendedTrackTwitterStat = new RecommendedTrackTwitterStat("email", "hashcode");
		String json = JsonConverter.toJson(recommendedTrackTwitterStat);
		RecommendedTrackTwitterStat result = JsonConverter.toBean(json, RecommendedTrackTwitterStat.class);
		assertNotNull(result);
		assertEquals(recommendedTrackTwitterStat.getEmail(), result.getEmail());
		assertEquals(recommendedTrackTwitterStat.getHashcode(), result.getHashcode());
		assertEquals(recommendedTrackTwitterStat.getStatType(), result.getStatType());
		assertEquals(recommendedTrackTwitterStat.getTimestamp(), result.getTimestamp());

	}

	@Test
	public void shouldConvertFriendshipFeed() throws Exception {
		ContactInfo requester = createContact();
		ContactInfo requested = createContact();

		FriendshipFeed friendshipFeed = new FriendshipFeed(requester, requested);

		String json = JsonConverter.toJson(friendshipFeed);
		FriendshipFeed result = (FriendshipFeed) JsonConverter.toBean(json, AllFeed.class);

		compareContact(requester, result.getOwner());
		compareContact(requested, result.getRequested());
	}

	@Test
	public void shouldConvertUpdateProfileFeed() throws Exception {
		ContactInfo contactInfo = createContact();

		UpdateProfileFeed updateProfileFeed = new UpdateProfileFeed(contactInfo);

		String json = JsonConverter.toJson(updateProfileFeed);
		UpdateProfileFeed result = (UpdateProfileFeed) JsonConverter.toBean(json, AllFeed.class);
		compareContact(contactInfo, result.getOwner());
	}

	@Test
	public void shouldConvertContactFeed() throws Exception {
		ContactInfo contactInfo = createContact();

		ContactsFeed contactsFeed = new ContactsFeed(contactInfo, TOTAL_CONTACTS);

		String json = JsonConverter.toJson(contactsFeed);
		ContactsFeed result = (ContactsFeed) JsonConverter.toBean(json, AllFeed.class);

		compareContact(contactInfo, result.getOwner());
	}

	@Test
	public void shouldConvertMediaImportFeed() throws Exception {
		MediaImportFeed feed = new MediaImportFeed(createContact(), 5, 6, 7);
		feed.setFromItunes(true);
		String json = JsonConverter.toJson(feed);
		AllFeed actual = JsonConverter.toBean(json, AllFeed.class);
		assertNotNull(actual);
		assertTrue(actual instanceof MediaImportFeed);
		assertEquals(feed.getOwner(), actual.getOwner());
		assertEquals(feed.getType(), actual.getType());
		assertEquals(feed.getDate(), actual.getDate());
		assertEquals(feed.getTotalFolders(), ((MediaImportFeed) actual).getTotalFolders());
		assertEquals(feed.getTotalPlaylists(), ((MediaImportFeed) actual).getTotalPlaylists());
		assertEquals(feed.getTotalTracks(), ((MediaImportFeed) actual).getTotalTracks());
		assertEquals(feed.isFromItunes(), ((MediaImportFeed) actual).isFromItunes());
	}

	@Test
	public void shouldConvertSendContentFeed() throws Exception {
		ModelCollection model = createModelCollection();
		ContactInfo recipient = createContact();
		ContactInfo owner = createContact();
		SendMediaFeed feed = new SendMediaFeed(owner, recipient, model);
		String json = JsonConverter.toJson(feed);
		SendMediaFeed actual = (SendMediaFeed) JsonConverter.toBean(json, AllFeed.class);
		new BeanAsserter(feed, actual).assertEquals();
	}

	@Test
	public void shouldConvertRemoteLibBF() throws Exception {
		ContactInfo recipient = createContact();
		ContactInfo owner = createContact();
		RemoteLibraryBrowsingFeed feed = new RemoteLibraryBrowsingFeed(owner, recipient);
		String json = JsonConverter.toJson(feed);
		RemoteLibraryBrowsingFeed actual = (RemoteLibraryBrowsingFeed) JsonConverter.toBean(json, AllFeed.class);
		new BeanAsserter(feed, actual).assertEquals();

	}

	@Test
	public void shouldConvertRecommendedTrackFacebookFeed() throws Exception {
		FeedTrack feedTrack = new FeedTrack("hashcode", "album", "artist");
		RecommendedTrackFacebookFeed recommendedTrackFacebookFeed = new RecommendedTrackFacebookFeed(createContact(),
				feedTrack);

		String json = JsonConverter.toJson(recommendedTrackFacebookFeed);
		AllFeed actual = JsonConverter.toBean(json, AllFeed.class);

		assertNotNull(actual);
		assertEquals(recommendedTrackFacebookFeed.getOwner(), actual.getOwner());
		assertEquals(recommendedTrackFacebookFeed.getType(), actual.getType());
		assertEquals(recommendedTrackFacebookFeed.getDate(), actual.getDate());
		assertEquals(recommendedTrackFacebookFeed.getFeedTrack(), ((RecommendedTrackFacebookFeed) actual)
				.getFeedTrack());
	}

	@Test
	public void shouldConvertRecommendedMediaFacebookFeed() throws Exception {
		RecommendedMediaFacebookFeed recommendedMediaFacebookFeed = new RecommendedMediaFacebookFeed(createContact(),
				"facebookUsername", 1001, Arrays.asList("folder1", "folder2", "folder3"), Arrays.asList("playlist1",
						"playlist2", "playlist3"), Arrays.asList(new FeedTrack("hashcode", "album", "artist"),
						new FeedTrack("hashcode", "album", "artist"), new FeedTrack("hashcode", "album", "artist")));

		String json = JsonConverter.toJson(recommendedMediaFacebookFeed);
		AllFeed actual = JsonConverter.toBean(json, AllFeed.class);

		assertNotNull(actual);
		assertEquals(recommendedMediaFacebookFeed.getOwner(), actual.getOwner());
		assertEquals(recommendedMediaFacebookFeed.getType(), actual.getType());
		assertEquals(recommendedMediaFacebookFeed.getDate(), actual.getDate());
		assertEquals(recommendedMediaFacebookFeed.getFacebookUsername(), ((RecommendedMediaFacebookFeed) actual)
				.getFacebookUsername());
		assertEquals(recommendedMediaFacebookFeed.getNumberOfTracks(), ((RecommendedMediaFacebookFeed) actual)
				.getNumberOfTracks());
		assertEquals(recommendedMediaFacebookFeed.getFolders(), ((RecommendedMediaFacebookFeed) actual).getFolders());
		assertEquals(recommendedMediaFacebookFeed.getPlaylists(), ((RecommendedMediaFacebookFeed) actual)
				.getPlaylists());
		assertEquals(recommendedMediaFacebookFeed.getTracks().size(), ((RecommendedMediaFacebookFeed) actual)
				.getTracks().size());
		for (int i = 0; i < recommendedMediaFacebookFeed.getTracks().size(); i++) {
			assertEquals(recommendedMediaFacebookFeed.getTracks().get(i), ((RecommendedMediaFacebookFeed) actual)
					.getTracks().get(i));
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldConvertPushMessage() throws Exception {
		List<String> recipients = Arrays.asList("recipientA", "recipientB");
		AllMessage<ContactInfo> innerMessage = new AllMessage<ContactInfo>("innerType", createContact());
		String innerProp = "innerProperty";
		innerMessage.putProperty(innerProp, innerProp);
		PushMessage<ContactInfo> pushMessage = new PushMessage<ContactInfo>(innerMessage, recipients);
		String pushProp = "pushProp";
		pushMessage.putProperty(pushProp, pushProp);

		String json = JsonConverter.toJson(pushMessage);
		AllMessage<ContactInfo> result = JsonConverter.toBean(json, AllMessage.class);
		assertNotNull(result);
		assertTrue(result instanceof PushMessage);
		PushMessage<ContactInfo> actual = (PushMessage) result;
		assertEquals(recipients, actual.getRecipients());
		assertEquals(pushProp, actual.getProperty(pushProp));
		AllMessage<ContactInfo> actualBody = actual.getBody();
		assertEquals(innerMessage.getType(), actualBody.getType());
		compareContact(innerMessage.getBody(), actualBody.getBody());
		assertEquals(innerProp, actualBody.getProperty(innerProp));
	}

	@Test
	public void shouldConvertTopHundredStat() throws Exception {
		Category category = mock(Category.class);
		Playlist playlist = mock(Playlist.class);
		when(category.getId()).thenReturn(12L);
		when(playlist.getHashcode()).thenReturn("hash");
		TopHundredStat original = new TopHundredStat("a@a.com", category, playlist);
		String json = JsonConverter.toJson(original);
		TopHundredStat copy = (TopHundredStat) JsonConverter.toBean(json, AllStat.class);
		new BeanAsserter(original, copy).inDeep("id", new BeanAsserter()).ignore("category", "playlist").assertEquals();
	}

	@Test
	public void shouldConvertFeedStat() throws Exception {
		ContactInfo contactInfo = createContact();

		ContactsFeed contactsFeed = new ContactsFeed(contactInfo, TOTAL_CONTACTS);
		FeedStat original = new FeedStat(contactsFeed);
		String json = JsonConverter.toJson(original);
		FeedStat other = (FeedStat) JsonConverter.toBean(json, AllStat.class);
		new BeanAsserter().assertEquals(original, other);
	}

	@Test
	public void shouldConvertTopHundredFeed() throws Exception {
		Category category = mock(Category.class);
		Playlist playlist = mock(Playlist.class);
		when(category.getId()).thenReturn(12L);
		when(playlist.getHashcode()).thenReturn("hash");
		when(category.getName()).thenReturn("category");
		when(playlist.getName()).thenReturn("playlist");
		TopHundredFeed original = new TopHundredFeed(createContact(), category, playlist);
		String json = JsonConverter.toJson(original);
		TopHundredFeed copy = (TopHundredFeed) JsonConverter.toBean(json, AllFeed.class);
		new BeanAsserter(original, copy).assertEquals();

	}

	private ErrorEventStat createErrorEventStat() {
		ErrorEventStat errorEventStat = new ErrorEventStat();
		errorEventStat.setEmail("email");
		errorEventStat.setClientVersion("0.0.17");
		errorEventStat.setOs("Windows 7");
		errorEventStat.setOsVersion("6.1");
		errorEventStat.setJvm("1.6.0_21");
		errorEventStat.setThreadName("main");
		errorEventStat.setMessage("da message");
		errorEventStat.setFileName("PruebaTesta.java");
		errorEventStat.setLineNumber("760");
		errorEventStat.setMethodName("method");
		errorEventStat.setClassName("qualifiedClassName");
		errorEventStat.setStackTrace(null);
		return errorEventStat;
	}

	private void compareErrorEventStat(ErrorEventStat errorEventStat, ErrorEventStat result) {
		assertEquals(errorEventStat.getEmail(), result.getEmail());
		assertEquals(errorEventStat.getClientVersion(), result.getClientVersion());
		assertEquals(errorEventStat.getOs(), result.getOs());
		assertEquals(errorEventStat.getOsVersion(), result.getOsVersion());
		assertEquals(errorEventStat.getJvm(), result.getJvm());
		assertEquals(errorEventStat.getThreadName(), result.getThreadName());
		assertEquals(errorEventStat.getMessage(), result.getMessage());
		assertEquals(errorEventStat.getFileName(), result.getFileName());
		assertEquals(errorEventStat.getLineNumber(), result.getLineNumber());
		assertEquals(errorEventStat.getMethodName(), result.getMethodName());
		assertEquals(errorEventStat.getClassName(), result.getClassName());
		assertEquals(errorEventStat.getStackTrace(), result.getStackTrace());
	}

	private void compareModelCollection(ModelCollection modelCollection, ModelCollection result) {
		assertNotNull(result);
		List<Track> expectedTracks = modelCollection.getTracks();
		List<Track> actualTracks = result.getTracks();
		assertNotNull(actualTracks);
		assertEquals(expectedTracks.size(), actualTracks.size());
		compareTrack(expectedTracks.get(0), actualTracks.get(0));

		List<Playlist> expectedPlaylists = modelCollection.getPlaylists();
		List<Playlist> actualPlaylists = result.getPlaylists();
		assertNotNull(actualPlaylists);
		assertEquals(expectedPlaylists.size(), actualPlaylists.size());
		comparePlaylist(expectedPlaylists.get(0), actualPlaylists.get(0));

		List<Folder> expectedFolders = modelCollection.getFolders();
		List<Folder> actualFolders = result.getFolders();
		assertNotNull(actualFolders);
		assertEquals(expectedFolders.size(), actualFolders.size());
		compareFolder(expectedFolders.get(0), actualFolders.get(0));

		List<SmartPlaylist> expectedSmartPlaylists = modelCollection.getSmartPlaylists();
		List<SmartPlaylist> actualSmartPlaylists = result.getSmartPlaylists();
		assertNotNull(actualSmartPlaylists);
		assertEquals(expectedSmartPlaylists.size(), actualSmartPlaylists.size());
		compareSmartPlaylist(expectedSmartPlaylists.get(0), actualSmartPlaylists.get(0));
	}

	private void compareFolder(Folder expected, Folder actual) {
		assertNotNull(actual);
		assertEquals(expected.getHashcode(), actual.getHashcode());
		assertEquals(expected.getName(), actual.getName());
		comparePlaylist(expected.getPlaylist(), actual.getPlaylist());
		List<Playlist> resultPlaylists = actual.getPlaylists();
		assertNotNull(resultPlaylists);
	}

	private void compareSmartPlaylist(SmartPlaylist expected, SmartPlaylist actual) {
		assertNotNull(actual);
		assertEquals(expected.getLabel(), actual.getLabel());
		comparePlaylist(expected.getPlaylist(), actual.getPlaylist());
		assertFalse(actual.dropAllowed());
		assertTrue(actual.isReadOnly());
	}

	private void comparePlaylist(Playlist playlist, Playlist result) {
		assertNotNull(result);
		assertEquals(playlist.getHashcode(), result.getHashcode());
		// assertNotNull(result.getModifiedDate());
		assertEquals(playlist.getName(), result.getName());
		assertEquals(playlist.getOwner(), result.getOwner());
		assertEquals(playlist.isSmartPlaylist(), result.isSmartPlaylist());
		assertEquals(playlist.getParentFolder(), result.getParentFolder());
		List<Track> resultTracks = result.getTracks();
		assertNotNull(resultTracks);
		List<Track> tracks = playlist.getTracks();
		assertEquals(tracks.size(), resultTracks.size());
		for (int i = 0; i < tracks.size(); i++) {
			compareTrack(tracks.get(i), resultTracks.get(i));
		}
		assertEquals(playlist.isEmpty(), result.isEmpty());
	}

	private void compareTrack(Track expected, Track actual) {
		assertNotNull(actual);
		assertEquals(expected.getAlbum(), actual.getAlbum());
		assertEquals(expected.getAlbumArtist(), actual.getAlbumArtist());
		assertEquals(expected.getArtist(), actual.getArtist());
		assertEquals(expected.getArtistAlbum(), actual.getArtistAlbum());
		assertEquals(expected.getBitRate(), actual.getBitRate());
		assertEquals(expected.getBitRateDesc(), actual.getBitRateDesc());
		assertEquals(expected.getDateAdded(), actual.getDateAdded());
		assertEquals(expected.getDownloadString(), actual.getDownloadString());
		assertEquals(expected.getDuration(), actual.getDuration());
		assertEquals(expected.getDurationMinutes(), actual.getDurationMinutes());
		assertEquals(expected.getFileFormat(), actual.getFileFormat());
		assertEquals(expected.getGenre(), actual.getGenre());
		assertEquals(expected.getHashcode(), actual.getHashcode());
		assertEquals(expected.getLastPlayed(), actual.getLastPlayed());
		assertEquals(expected.getLastSkipped(), actual.getLastSkipped());
		assertEquals(expected.getName(), actual.getName());
		assertEquals(expected.getPlaycount(), actual.getPlaycount());
		assertEquals(expected.getRating(), actual.getRating());
		assertEquals(expected.getSampleRate(), actual.getSampleRate());
		assertEquals(expected.getSize(), actual.getSize());
		assertEquals(expected.getSkips(), actual.getSkips());
		assertEquals(expected.getTrackNumber(), actual.getTrackNumber());
		assertEquals(expected.getYear(), actual.getYear());
		assertTrue(actual.isEnabled());
		assertTrue(actual.isVBR());
	}

	private void compareLoginResponse(LoginResponse expected, LoginResponse actual) {
		assertNotNull(actual);
		compareUser(expected.getUser(), actual.getUser());
		assertNotNull(actual.getPendingRequests());
		assertEquals(1, actual.getPendingRequests().size());
		ContactRequest actualRequest = actual.getPendingRequests().get(0);
		compareContactRequest(expected.getPendingRequests().get(0), actualRequest);
		assertEquals(expected.getError(), actual.getError());
		assertEquals(expected.getSessionId(), actual.getSessionId());
		assertTrue(actual.isFirstLogin());
	}

	private void compareAlert(Alert expected, Alert actual) {
		assertEquals(expected, actual);
		compareContact(expected.getReceiver(), actual.getReceiver());
		compareContact(expected.getSender(), actual.getSender());
		assertEquals(expected.getDate(), actual.getDate());
		assertEquals(expected.getType(), actual.getType());
		assertEquals(expected.getId(), actual.getId());
	}

	private void compareContact(ContactInfo expected, ContactInfo actual) {
		assertEquals(expected.getId(), actual.getId());
		assertEquals(expected.getEmail(), actual.getEmail());
		assertEquals(expected.getName(), actual.getName());
		assertEquals(expected.getFirstName(), actual.getFirstName());
		assertEquals(expected.getLastName(), actual.getLastName());
		assertEquals(expected.getGender(), actual.getGender());
		assertEquals(expected.getBirthday(), actual.getBirthday());
		assertEquals(expected.getIdLocation(), actual.getIdLocation());
		assertEquals(expected.getMessage(), actual.getMessage());
		assertEquals(expected.getStatus(), actual.getStatus());
		assertEquals(expected.getNickName(), actual.getNickName());
		compareCity(expected.getCity(), actual.getCity());
	}

	private void compareUser(User expected, User actual) {
		assertEquals(expected.getId(), actual.getId());
		assertEquals(expected.getFirstName(), actual.getFirstName());
		assertEquals(expected.getLastName(), actual.getLastName());
		assertEquals(expected.getNickName(), actual.getNickName());
		assertEquals(expected.getFullName(), actual.getFullName());
		assertEquals(expected.getSearchName(), actual.getSearchName());
		assertEquals(expected.getEmail(), actual.getEmail());
		assertEquals(expected.getPassword(), actual.getPassword());
		assertEquals(expected.getBirthday(), actual.getBirthday());
		assertEquals(expected.getGender(), actual.getGender());
		assertEquals(expected.getStatus(), actual.getStatus());
		assertEquals(expected.getIdLocation(), actual.getIdLocation());
		assertEquals(expected.getQuote(), actual.getQuote());
		compareCity(expected.getCity(), actual.getCity());

	}

	private void compareCity(City expected, City actual) {
		assertEquals(expected.getCityId(), actual.getCityId());
		assertEquals(expected.getCityName(), actual.getCityName());
		assertEquals(expected.getCountryId(), actual.getCountryId());
		assertEquals(expected.getCountryName(), actual.getCountryName());
		assertEquals(expected.getStateId(), actual.getStateId());
		assertEquals(expected.getStateName(), actual.getStateName());
		assertEquals(expected.getPopIndex(), actual.getPopIndex());
	}

	private User createUser() {
		User user = new User();
		user.setId(100L);
		user.setNickName("Nickname");
		user.setFirstName("First Name");
		user.setLastName("Last Name");
		user.setEmail("user@all.com");
		user.setPassword("password");
		user.setGender(Gender.FEMALE);
		user.setIdLocation("123456");
		user.setQuote("some quote");
		user.setZipCode("54766");
		user.setVersion(3L);
		user.setDay(24);
		user.setMonth(8);
		user.setYear(1984);
		City city = createCity();
		user.setCity(city);
		user.setAvatar(new byte[] { 1, 2, 3 });
		return user;
	}

	private ContactInfo createContact() {
		ContactInfo contact = new ContactInfo(createUser());
		contact.setStatus(ContactStatus.offline);
		return contact;
	}

	private City createCity() {
		City city = new City();
		city.setCityId("123456");
		city.setCityName("Villa Hermosa");
		city.setCountryId("521");
		city.setCountryName("Mexico");
		city.setStateId("56789");
		city.setStateName("Tabasco");
		city.setPopIndex("234");
		return city;
	}

	private void comparePendingEmail(PendingEmail expected, PendingEmail actual) {
		assertEquals(expected.getId(), actual.getId());
		assertEquals(expected.getFromMail(), actual.getFromMail());
		assertEquals(expected.getToMail(), actual.getToMail());
		assertEquals(expected.getSubject(), actual.getSubject());
		assertEquals(expected.getMessage(), actual.getMessage());
		assertEquals(expected.getUserId(), actual.getUserId());
	}

	private PendingEmail createPendingEmail() {
		PendingEmail pendingEmail = new PendingEmail();
		pendingEmail.setToMail("to@all.com");
		pendingEmail.setFromMail("from@all.com");
		pendingEmail.setId(1L);
		pendingEmail.setUserId(100L);
		pendingEmail.setMessage("Some text message");
		pendingEmail.setSubject("some subject");
		return pendingEmail;
	}

	private PendingEmailCollection createPendingEmailCollection() {
		PendingEmailCollection collection = new PendingEmailCollection();
		collection.setFromMail("from");
		collection.setMessage("message");
		collection.setSubject("subject");
		List<String> toEmails = new ArrayList<String>();
		toEmails.add("toA@all.com");
		toEmails.add("toB@all.com");
		collection.setToEmails(toEmails);
		collection.setUserId(100L);
		return collection;
	}

	private void compareContactRequest(ContactRequest expected, ContactRequest actual) {
		assertEquals(expected.getId(), actual.getId());
		assertEquals(expected.getIdRequester(), actual.getIdRequester());
		assertEquals(expected.getIdRequested(), actual.getIdRequested());
		assertEquals(expected.getRequestStatus(), actual.getRequestStatus());
		assertEquals(expected.getDate(), actual.getDate());
		assertTrue(actual.isAccepted());
		compareContact(expected.getRequester(), actual.getRequester());
		compareContact(expected.getRequested(), actual.getRequested());
	}

	private void compareQuoteFeed(QuoteFeed expected, QuoteFeed actual) {
		assertEquals(expected.getQuote(), actual.getQuote());
		assertNotNull(actual.getDate());
		compareContact(expected.getOwner(), actual.getOwner());
	}

	private void compareTwitterFeed(RecommendedTrackTwtterFeed expected, RecommendedTrackTwtterFeed actual) {
		assertEquals(expected.getFeedTrack(), actual.getFeedTrack());
		assertNotNull(actual.getDate());
		compareContact(expected.getOwner(), actual.getOwner());
	}

	private void compareAvatarFeed(AvatarFeed expected, AvatarFeed actual) {
		assertArrayEquals(expected.getAvatar(), actual.getAvatar());
		assertNotNull(actual.getDate());
		compareContact(expected.getOwner(), actual.getOwner());
	}

	private ContactRequest createContactRequest() {
		ContactInfo contactA = createContact();
		contactA.setEmail("userA@all.com");
		ContactInfo contactB = createContact();
		contactB.setEmail("userB@all.com");

		ContactRequest request = new ContactRequest(contactA, contactB);
		request.setId(1L);
		request.setRequestStatus(FriendshipRequestStatus.OK);
		request.setAccepted(true);
		return request;
	}

	private RemoteTrack createRemoteTrack() {
		RemoteTrack track = new RemoteTrack();
		track.setAlbum("some album \"with special <Characters>\"");
		track.setArtist("some artist");
		track.setBitRate("a vbr");
		track.setDateAdded(new Date());
		track.setDownloadString("some download string");
		track.setDuration(1000);
		track.setEnabled(true);
		track.setFileFormat("mp3");
		track.setGenre("pasito duranguense");
		track.setHashcode("some hashcode");
		track.setLastPlayed(new Date());
		track.setLastSkipped(new Date());
		track.setName("de quen chon");
		track.setPlaycount(150);
		track.setRating(0);
		track.setSampleRate("mmm dunno");
		track.setSize(1024 * 4);
		track.setSkips(100);
		track.setTrackNumber("1");
		track.setVBR(true);
		track.setYear("1979");
		return track;
	}

	private RemotePlaylist createRemotePlaylist() {
		RemoteTrack trackA = createRemoteTrack();
		trackA.setName("track A");
		RemoteTrack trackB = createRemoteTrack();
		trackB.setName("track B");
		List<Track> tracks = new ArrayList<Track>();
		tracks.add(trackA);
		tracks.add(trackB);
		RemotePlaylist playlist = new RemotePlaylist();
		playlist.setHashcode("playlist hashcode");
		playlist.setLastPlayed(new Date());
		playlist.setModifiedDate(new Date());
		playlist.setName("playlist name");
		playlist.setOwner("somebody@all.com");
		playlist.setSmartPlaylist(true);
		playlist.setTracks(tracks);
		return playlist;
	}

	private RemoteFolder createRemoteFolder() {
		RemoteFolder folder = new RemoteFolder();
		folder.setHashcode("folder hashcode");
		folder.setName("folder name");
		RemotePlaylist playlistA = createRemotePlaylist();
		RemotePlaylist playlistB = createRemotePlaylist();
		List<Playlist> playlists = new ArrayList<Playlist>();
		playlists.add(playlistA);
		playlists.add(playlistB);
		folder.setPlaylists(playlists);
		return folder;
	}

	private SmartPlaylist createSmartPlaylist() {
		SmartPlaylist smartPlaylist = new SimpleSmartPlaylist("smart playlist name", createRemotePlaylist(), true);
		return smartPlaylist;
	}

	private ModelCollection createModelCollection() {
		ModelCollection modelCollection = new ModelCollection();
		modelCollection.setRemote(true);
		List<Folder> folders = new ArrayList<Folder>();
		folders.add(createRemoteFolder());
		modelCollection.setFolders(folders);
		List<Playlist> playlists = new ArrayList<Playlist>();
		playlists.add(createRemotePlaylist());
		modelCollection.setPlaylists(playlists);
		List<Track> tracks = new ArrayList<Track>();
		tracks.add(createRemoteTrack());
		modelCollection.setTracks(tracks);
		List<SmartPlaylist> smartPlaylists = new ArrayList<SmartPlaylist>();
		smartPlaylists.add(createSmartPlaylist());
		modelCollection.setSmartPlaylists(smartPlaylists);
		return modelCollection;
	}

}
