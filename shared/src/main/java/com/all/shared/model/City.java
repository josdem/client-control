package com.all.shared.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "city")
public class City implements Comparable<City>, Serializable {
	private static final long serialVersionUID = 7778923522767457621L;

	@Id
	@Column(name = "city_id")
	private String cityId;
	@Column(name = "city_name")
	private String cityName;
	@Column(name = "country_id")
	private String countryId;
	@Column(name = "country_name")
	private String countryName;
	@Column(name = "state_id")
	private String stateId;
	@Column(name = "state_name")
	private String stateName;
	@Column(name = "pop_index")
	private String popIndex;

	public City() {
		cityId = "";
		cityName = "";
		countryId = "";
		countryName = "";
		stateId = "";
		stateName = "";
		popIndex = "";
	}

	@Override
	public String toString() {
		return cityName + ", " + stateName + ", " + countryName;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getStateId() {
		return stateId;
	}

	public void setStateId(String stateId) {
		this.stateId = stateId;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public void setPopIndex(String pop_index) {
		this.popIndex = pop_index;
	}

	public String getPopIndex() {
		return popIndex;
	}

	public int compareTo(City o) {
		return this.toString().compareTo(o.toString());
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof City)) {
			return false;
		}
		return compareTo((City) obj) == 0;
	}

}
