package com.all.client.model;

import java.io.File;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.all.client.data.Hashcoder;

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class TrackFile {
	private static final long serialVersionUID = 1L;

	@Transient
	private static final Log log = LogFactory.getLog(TrackFile.class);

	@Id
	@Column(length = 500)
	private String hashcode;

	@Column(name = "file_name", length = 5000)
	private String filename;

	public TrackFile() {
	}

	public TrackFile(String hashcode) {
		this.hashcode = hashcode;
	}

	public TrackFile(File file) {
		this(Hashcoder.createHashCode(file));
		this.filename = file.getAbsolutePath();
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getHashcode() {
		return hashcode;
	}

	public File getFile() {
		return filename == null ? null : new File(filename);
	}

	public boolean deleteFile() {
		try {
			// Added this log because there is a defect when deleting physically a file
			log.debug("track to delete : " + filename);
			// return true;
			return this.exists() ? getFile().delete() : false;
		} catch (Exception e) {
			log.error("File could not be deleted", e);
			return false;
		}
	}

	public String getFileName() {
		return getFile().getName().replaceFirst("\\.\\w+$", "");
	}

	public boolean exists() {
		return getFile() == null ? false : getFile().exists();
	}

}
