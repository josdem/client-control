package com.all.client.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "BrokenLinkFailedImport")
@Table(name = "BrokenLinkFailedImport")
public class BrokenLinkFailedImport {
	/**
	 * character used to separate locations of a track, in iTunes library
	 * for example:  MyFolder1/MyFolder2/MyPlaylist, to input in setITunesLocationString
	 */
	public static final String SOURCE_LOCATION_SEPARATION = "/";
	
	@Id
	String trackId;
	@Column
	String fileLocation;
	@Column
	String sourceLocationString;
	
	public void setTrackId(String trackId) {
		this.trackId = trackId;
	}

	public String getTrackId() {
		return trackId;
	}

	public String getFileLocation() {
		return fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	/**
	 * set the path (folders and paths) in itunes. for example: MyFolder1/MyFolder2/MyPlaylist
	 * see also  SOURCE_LOCATION_SEPARATION
	 * @param iTunesLocationString
	 */
	public void setSourceLocationString(String sourceLocationString) {
		this.sourceLocationString = sourceLocationString;
	}

	public String getSourceLocationString() {
		return sourceLocationString;
	}

	public List<String> getSourceLocationList(){
		List<String> sourceLocationList;
		if(this.sourceLocationString==null){
			sourceLocationList = Collections.emptyList();
		}else{
			String[] array = this.sourceLocationString.split(SOURCE_LOCATION_SEPARATION);
			sourceLocationList = Collections.unmodifiableList(Arrays.asList(array));
		}
		return sourceLocationList;
	}


}
