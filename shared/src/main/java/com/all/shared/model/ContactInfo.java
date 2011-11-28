package com.all.shared.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.all.chat.ChatStatus;
import com.all.chat.ChatType;
import com.all.chat.ChatUser;
import com.all.shared.sync.SyncAble;
import com.all.shared.sync.SyncAbleAbstractImpl;
import com.all.shared.sync.SyncUpdateAble;
import com.all.shared.util.NickNameRefiner;

@Entity
public class ContactInfo extends SyncAbleAbstractImpl implements ChatUser, Serializable {

	private static final long serialVersionUID = 3173471090676381694L;
	@Id
	@SyncUpdateAble
	private String email;
	@SyncUpdateAble
	private String name;
	@SyncUpdateAble
	private String firstName;
	@SyncUpdateAble
	private String lastName;
	@SyncUpdateAble
	private String nickName;
	@SyncUpdateAble
	private String message;
	@SyncUpdateAble
	private Gender gender;
	@SyncUpdateAble
	private boolean isDropping = false;
	private ContactStatus status = ContactStatus.offline;
	@SyncUpdateAble
	private String idLocation;
	@NotNull
	@SyncUpdateAble
	private Long id;
	@SyncUpdateAble
	private Integer day;
	@SyncUpdateAble
	private Integer month;
	@SyncUpdateAble
	private Integer year;
	@Transient
	private City city;
	@Transient
	private byte[] avatar;
	@Transient
	private ChatType chatType;
	@Transient
	private ChatStatus chatStatus;

	// CREATED ONLY FOR JSONCONVERTER
	@Deprecated
	public ContactInfo() {
	}

	public ContactInfo(ChatUser chatUser) {
		this.email = chatUser.getChatId();
		this.nickName = chatUser.getChatName();
		this.message = chatUser.getQuote();
		this.chatType = chatUser.getChatType();
		this.chatStatus = chatUser.getChatStatus();
		this.avatar = chatUser.getAvatar();
	}

	public ContactInfo(User userFriend) {
		this.id = userFriend.getId();
		this.name = userFriend.getFullName();
		this.firstName = userFriend.getFirstName();
		this.lastName = userFriend.getLastName();
		this.nickName = userFriend.getNickName();
		this.email = userFriend.getEmail();
		this.message = userFriend.getQuote();
		this.gender = userFriend.getGender();
		this.day = userFriend.getDay();
		this.month = userFriend.getMonth();
		this.year = userFriend.getYear();
		this.idLocation = userFriend.getIdLocation();
		this.city = userFriend.getCity();
		this.avatar = userFriend.getAvatar();
	}

	public ContactInfo(PendingEmail email) {
		this.id = email.getId();
		this.email = email.getToMail();
		this.status = ContactStatus.pending;
	}

	public String getName() {
		if (name == null && nickName == null) {
			return null;
		}
		return name == null ? getNickName() : name;
	}

	public String getMessage() {
		return message;
	}

	public Gender getGender() {
		return gender;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setGender(Gender sex) {
		this.gender = sex;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTooltipText() {
		if (getStatus() == ContactStatus.pending) {
			return getEmail();
		} else {
			String tooltip = getChatName();
			if (getQuote() != null) {
				tooltip += " - " + message;
			}
			return tooltip;
		}
	}

	public boolean isPending() {
		return ChatStatus.PENDING == getChatStatus();
	}

	public void setIsDropping(boolean isDropping) {
		this.isDropping = isDropping;
	}

	public boolean isIsDropping() {
		return isDropping;
	}

	public boolean isOnline() {
		return ChatStatus.ONLINE == getChatStatus();
	}

	public boolean isAway() {
		return ChatStatus.AWAY == getChatStatus();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ContactStatus getStatus() {
		return status;
	}

	public void setStatus(ContactStatus status) {
		this.status = status;
		this.chatStatus = ChatStatus.valueOf(status.toString().toUpperCase());
	}

	@Override
	public String toString() {
		if (name == null || "".equals(name.trim())) {
			return getEmail();
		}
		return name;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (obj instanceof ContactInfo) {
			ContactInfo contact = (ContactInfo) obj;
			if (this.getEmail() != null && contact.getEmail() != null) {
				return this.getEmail().equals(contact.getEmail());
			} else if (this.getId() != null && contact.getId() != null) {
				return this.getId().equals(contact.getId());
			} else if (this.getChatId() != null && contact.getChatId() != null) {
				return this.getChatId().equals(contact.getChatId());
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (getEmail() != null) {
			return getEmail().hashCode();
		}
		if (getId() != null) {
			return getId().hashCode();
		}
		return getChatId() == null ? super.hashCode() : getChatId().hashCode();
	}

	public String getFirstName() {
		splitNamesIfNecessary();
		if (firstName == null && nickName == null) {
			return null;
		}
		return NickNameRefiner.isNullOrEmpty(firstName) == true ? NickNameRefiner.refine(getNickName()) : firstName;
	}

	private void splitNamesIfNecessary() {
		if (firstName == null && lastName == null) {
			if (name != null) {
				int nameParstSeparatorIndex = name.lastIndexOf(" ");
				firstName = name.substring(0, nameParstSeparatorIndex);
				lastName = name.substring(nameParstSeparatorIndex);
			}
		}
	}

	public String getLastName() {
		splitNamesIfNecessary();
		if (lastName == null && nickName == null) {
			return null;
		}
		return NickNameRefiner.isNullOrEmpty(lastName) == true ? NickNameRefiner.refine(getNickName()) : lastName;
	}

	public void setFirstName(String first) {
		firstName = first;
	}

	public void setLastName(String last) {
		lastName = last;
	}

	public Date getBirthday() {
		if (day == null || month == null || year == null) {
			return null;
		}
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DATE, day);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public String getIdLocation() {
		return idLocation;
	}

	public void setIdLocation(String idLocation) {
		this.idLocation = idLocation;
	}

	@Override
	public int compareTo(ChatUser o) {
		if (o != null && o instanceof ContactInfo) {
			ContactInfo other = (ContactInfo) o;
			if (this.nickName == null || other.nickName == null) {
				return this.email.compareToIgnoreCase(other.email);
			}
			return this.nickName.compareToIgnoreCase(other.nickName);
		}
		return 0;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public Integer getDay() {
		return day;
	}

	public Integer getMonth() {
		return month;
	}

	public Integer getYear() {
		return year;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public void setAvatar(byte[] avatar) {
		this.avatar = avatar;
	}

	public String getSyncAbleId() {
		return this.email;
	}

	public String getNickName() {
		if (nickName == null && firstName == null && lastName == null) {
			return null;
		}
		if (nickName == null && firstName == null && lastName != null) {
			String defaultNickname = (getLastName()).replace(" ", "");
			return defaultNickname.length() > 25 ? defaultNickname.substring(0, 25) : defaultNickname;
		}
		if (nickName == null && firstName != null && lastName == null) {
			String defaultNickname = (getFirstName()).replace(" ", "");
			return defaultNickname.length() > 25 ? defaultNickname.substring(0, 25) : defaultNickname;
		}
		if (nickName == null) {
			String defaultNickname = (getFirstName() + "." + getLastName()).replace(" ", "");
			return defaultNickname.length() > 25 ? defaultNickname.substring(0, 25) : defaultNickname;
		}
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public void clone(SyncAble updatedEntity) {
		ContactInfo ci = (ContactInfo) updatedEntity;
		this.setEmail(ci.getEmail());
		this.setId(ci.getId());
		this.setName(ci.getName());
		this.setIdLocation(ci.getIdLocation());
		this.setFirstName(ci.getFirstName());
		this.setLastName(ci.getLastName());
		this.setNickName(ci.getNickName());
		this.setMessage(ci.getMessage());
		this.setGender(ci.getGender());
		this.setIsDropping(ci.isDropping);
		this.setDay(ci.getDay());
		this.setMonth(ci.getMonth());
		this.setYear(ci.getYear());
	}

	@Override
	public byte[] getAvatar() {
		return avatar;
	}

	@Override
	public String getChatId() {
		return getEmail();
	}

	@Override
	public String getChatName() {
		return getNickName();
	}

	@Override
	public ChatStatus getChatStatus() {
		// this is needed when the status is loaded from the local DB but the
		// chatStatus is null because it has not been set
		return chatStatus != null ? chatStatus : ChatStatus.valueOf(getStatus().toString().toUpperCase());
	}

	public void setChatStatus(ChatStatus chatStatus) {
		this.chatStatus = chatStatus;
		this.status = ContactStatus.valueOf(chatStatus.toString().toLowerCase());
	}

	@Override
	public ChatType getChatType() {
		return chatType != null ? chatType : ChatType.ALL;
	}

	public void setChatType(ChatType chatType) {
		this.chatType = chatType;
	}

	@Override
	public String getQuote() {
		return getMessage();
	}

}