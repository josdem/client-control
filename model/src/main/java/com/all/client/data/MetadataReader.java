package com.all.client.data;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;

import com.all.client.model.LocalTrack;

public class MetadataReader {
	private final AudioFile audioFile;
	private Tag tag;

	public MetadataReader(AudioFile audioFile) {
		this.audioFile = audioFile;
	}

	public Tag getTag() {
		tag = audioFile.getTag();
		return tag;
	}

	public void getBitrate(AudioHeader header, LocalTrack track) {
		String bitRate = header.getBitRate();
		track.setBitRate(bitRate);
	}

	public String getGenre() {
		return tag.getFirst(FieldKey.GENRE);
	}
	
	

}
