package com.all.client.data;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.jaudiotagger.tag.id3.ID3v24Tag;

import com.all.client.model.LocalTrack;
import com.all.shared.genre.GenreTypes;

public class Mp3MetadataReader extends MetadataReader {
	private final MP3File audioMP3;
	Tag tag;

	private final Log log = LogFactory.getLog(this.getClass());

	public Mp3MetadataReader(AudioFile file) {
		super(file);
		audioMP3 = (MP3File) file;
	}

	@Override
	public Tag getTag() {
		if (audioMP3.hasID3v1Tag() || audioMP3.hasID3v2Tag()) {
			tag = audioMP3.getTag();
		} else {
			tag = new ID3v24Tag();
			audioMP3.setID3v2TagOnly((AbstractID3v2Tag) tag);
			try {
				audioMP3.commit();
			} catch (CannotWriteException ignore) {
				log.error(ignore, ignore);
			}
		}
		return tag;
	}

	@Override
	public void getBitrate(AudioHeader header, LocalTrack track) {
		String bitRate = "";
		try {
			bitRate = header.getBitRate();
			if (bitRate.startsWith("~")) {
				track.setVbr();
				bitRate = bitRate.substring(1); // removes the ~
			}
		} catch (UnsupportedOperationException e) {
			log.debug(e, e);
		}
		track.setBitRate(bitRate.trim());
	}

	@Override
	public String getGenre() {
		String tmpGenre = "";
		int index = 0;
		tmpGenre = tag.getFirst(FieldKey.GENRE);
		try {
			index = Integer.valueOf(tmpGenre);
			return GenreTypes.getGenreByCode(index);
		} catch (NumberFormatException nfe) {
			if (tmpGenre != null && tmpGenre.startsWith("(")) {
				index = Integer.valueOf(tmpGenre.substring(tmpGenre
						.indexOf('(') + 1, tmpGenre.indexOf(')')));

				return GenreTypes.getGenreByCode(index);
			} else {
				return tag.getFirst(FieldKey.GENRE);
			}
		}
	}

}
