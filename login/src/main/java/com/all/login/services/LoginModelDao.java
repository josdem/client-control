package com.all.login.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.all.login.model.Login;
import com.all.shared.model.City;
import com.all.shared.model.User;

@Service
public class LoginModelDao {

	private static final Log LOG = LogFactory.getLog(LoginModelDao.class);
	
	private List<City> cities = null;

	@Autowired
	private LoginDatabaseAccess db;

	public LoginModelDao() {
	}

	public List<Login> getLastLogins() {
		List<Login> allLogins = new ArrayList<Login>(db.getDB().getLogins());
		Collections.sort(allLogins);
		return allLogins.subList(0, Math.min(5, allLogins.size()));
	}

	private Login findLogin(String email) {
		if (email == null) {
			return null;
		}
		Set<Login> logins = db.getDB().getLogins();
		for (Login login : logins) {
			if (email.equals(login.getEmail())) {
				return login;
			}
		}
		return null;
	}

	public void registerNewLogin(User user, boolean rememberme) {
		if (user == null) {
			return;
		}
		String email = user.getEmail();
		Login login = findLogin(email);
		User localUser = getUser(email);

		if (login == null) {
			login = new Login(email);
		}
		if (localUser == null) {
			localUser = user;
		}
		login.refreshDate();

		db.getDB().getLogins().remove(login);
		db.getDB().getUsers().remove(user);

		db.getDB().getLogins().add(login);
		db.getDB().getUsers().add(user);

		db.getDB().setRememberUser(rememberme ? user.getEmail() : "");
	}

	public List<City> findAllCities() {
		return getCities();
	}

	private List<City> getCities() {
		if (cities == null) {
			synchronized (this) {
				if (cities == null) {
					List<City> cities = new ArrayList<City>();
					Scanner scanner = null;
					try {
						scanner = new Scanner(getClass().getResourceAsStream("/scripts/cities.txt"));
						while (scanner.hasNextLine()) {
							String text = scanner.nextLine();
							if (text.startsWith("('")) {
								try {
									City city = new City();
									String[] data = getSmartData(text);
									city.setCityId(data[0]);
									city.setCityName(data[1]);
									city.setCountryId(data[2]);
									city.setCountryName(data[3]);
									city.setStateId(data[4]);
									city.setStateName(data[5]);
									city.setPopIndex(data[6]);
									cities.add(city);
								} catch (Exception e) {
									LOG.error(e, e);
								}
							}
						}
					} catch (Exception e) {
						LOG.error(e, e);
					} finally {
						try {
							scanner.close();
						} catch (Exception e) {
							LOG.error(e, e);
						}
					}

					this.cities = cities;
				}
			}
		}
		return cities;
	}

	private String[] getSmartData(String text) {
		text = text.substring(text.indexOf('(') + 1, text.indexOf(')'));
		String[] split = text.split(",");
		for (String string : split) {
			string = string.substring(string.indexOf('\'') + 1, string.indexOf('\''));
		}
		return split;
	}

	public City findCity(String cityId) {
		for (City city : getCities()) {
			if (cityId.equals(city.getCityId())) {
				return city;
			}
		}
		return null;
	}

	public User getUser(String email) {
		return db.getDB().getUser(email);
	}

	public boolean isRememberMe(String email) {
		return email.equals(db.getDB().getRememberUser());
	}
}
