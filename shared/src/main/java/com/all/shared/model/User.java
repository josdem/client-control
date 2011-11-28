package com.all.shared.model;

import java.io.Serializable;
import java.text.Normalizer;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.all.shared.model.constraints.Email;
import com.all.shared.model.constraints.Name;
import com.all.shared.model.constraints.NotEmpty;
import com.all.shared.util.NickNameRefiner;

@Entity
@Table(name = "USERS", uniqueConstraints = @UniqueConstraint(columnNames = { "email" }))
public class User implements Serializable {

	private static final long serialVersionUID = 8038154473709651707L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Name
	private String firstName;
	@Name
	private String lastName;
	private String searchName;
	private String nickName;
	@Size(max = 50, min = 1)
	@Email
	private String email;
	@NotEmpty
	private String password;
	@NotNull
	private Integer day;
	@NotNull
	private Integer month;
	@NotNull
	private Integer year;
	@Enumerated(EnumType.ORDINAL)
	@NotNull
	private Gender gender;
	@Enumerated(EnumType.ORDINAL)
	@NotNull
	UserStatus status = UserStatus.Registered;
	@NotEmpty
	private String idLocation;
	private String quote = "Sharing is Caring!!";
	// TODO: FIX THIS AFTER PRESENTATION ON FEBRUARY 3RD 2010 3rd
	// @ZipCode
	private String zipCode;
	private Long version;
	private String fbToken;
	@Transient
	private static final Calendar calendar = new GregorianCalendar();

	@Temporal(TemporalType.TIMESTAMP)
	private Date registrationDate;
	@Transient
	private City city;
	@Transient
	private byte[] avatar;

	public User() {
		this.version = 0L;
	}

	String createSearchName(String firstName, String lastName) {
		StringBuilder fullName = new StringBuilder();
		fullName.append(firstName == null ? "" : firstName.trim().toLowerCase());
		fullName.append(" ");
		fullName.append(lastName == null ? "" : lastName.trim().toLowerCase());
		return Normalizer.normalize(fullName.toString().trim(), Normalizer.Form.NFD).replaceAll(
				"\\p{InCombiningDiacriticalMarks}+", "");
	}

	// THIS CONSTRUCTOR IS USED BY THE REGISTERUSERCOMMAND
	public User(String firstName, String lastName, String nickName, Gender gender, String email, String password,
			Date birthday, String idLocation, String zipCode) {
		this.nickName = nickName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.searchName = createSearchName(firstName, lastName);
		this.gender = gender;
		this.email = email;
		this.password = password;
		setBirthday(birthday);
		this.idLocation = idLocation;
		this.setZipCode(zipCode);
		this.registrationDate = new Date();
		this.version = 0L;
	}

	// THIS CONSTRUCTOR IS USED BY THE UPDATEUSERCOMMAND
	public User(Long id, String firstName, String lastName, String nickname, Date birthday, Gender gender,
			String idLocation, String zipCode) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.nickName = nickname;
		this.searchName = createSearchName(firstName, lastName);
		setBirthday(birthday);
		this.gender = gender;
		this.idLocation = idLocation;
		this.setZipCode(zipCode);
		this.version = 0L;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		if (firstName == null && nickName == null) {
			return null;
		}
		return NickNameRefiner.isNullOrEmpty(firstName) == true ? NickNameRefiner.refine(getNickName()) : firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
		this.searchName = createSearchName(firstName, lastName);
	}

	public String getLastName() {
		if (lastName == null && nickName == null) {
			return null;
		}
		return NickNameRefiner.isNullOrEmpty(lastName) == true ? NickNameRefiner.refine(getNickName()) : lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
		this.searchName = createSearchName(firstName, lastName);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
		return calendar.getTime();
	}

	public void setBirthday(Date birthday) {
		if (birthday == null) {
			day = month = year = null;
			return;
		}
		calendar.setTime(birthday);
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH);
		day = calendar.get(Calendar.DATE);
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getIdLocation() {
		return idLocation;
	}

	public void setIdLocation(String idLocation) {
		this.idLocation = idLocation;
	}

	public void confirm() {
		this.status = UserStatus.Confirmed;
	}

	public boolean isConfirmed() {
		return this.status == UserStatus.Confirmed;
	}

	public boolean isActive() {
		return this.status == UserStatus.Active;
	}

	public void active() {
		this.status = UserStatus.Active;
	}

	public String getFullName() {
		return getFirstName() + " " + getLastName();
	}

	public void setQuote(String quote) {
		this.quote = quote;
		setVersion(this.version + 1);
	}

	public String getQuote() {
		return quote;
	}

	public void update(User other) {
		firstName = other.firstName;
		lastName = other.lastName;
		nickName = other.nickName;
		searchName = createSearchName(firstName, lastName);
		gender = other.gender;
		idLocation = other.idLocation;
		zipCode = other.zipCode;
		city = other.city;
		fbToken = other.getFbToken();
		setBirthday(other.getBirthday());
		setVersion(this.version + 1);
	}

	public String getSearchName() {
		return searchName;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Long getVersion() {
		return version;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
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

	public void setFbToken(String fbToken) {
		this.fbToken = fbToken;
	}

	public String getFbToken() {
		return fbToken;
	}

	public byte[] getAvatar() {
		return avatar;
	}

	public void setAvatar(byte[] avatar) {
		this.avatar = avatar;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		User other = (User) obj;
		if (email == null) {
			if (other.email != null) {
				return false;
			}
		} else if (!email.equals(other.email)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

}
