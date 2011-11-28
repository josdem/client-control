package com.all.client.data;

import java.io.File;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;

import com.all.client.model.InvalidFileException;
import com.all.client.model.LocalTrack;
import com.all.client.model.TrackFile;
import com.all.shared.model.Track;

public class JAudioTaggerStrategy implements MetadataStrategy {
	AudioFile audioFile;

	private static final Log log = LogFactory.getLog(JAudioTaggerStrategy.class);

	private MetadataReader metadataReader;

	public JAudioTaggerStrategy() {
		turnOffLogMessages();
	}

	private void turnOffLogMessages() {
		Handler[] handlers = Logger.getLogger("").getHandlers();
		for (int index = 0; index < handlers.length; index++) {
			handlers[index].setLevel(Level.OFF);
		}
	}

	@Override
	public Track createTrack(TrackFile trackFile) throws InvalidFileException {
		LocalTrack track = new LocalTrack(trackFile.getFileName(), trackFile.getHashcode());
		File file = trackFile.getFile();
		track.setFileFormat(file.getName().substring(file.getName().lastIndexOf('.') + 1, file.getName().length())
				.toUpperCase());
		track.setSize(file.length());
		try {
			if (!file.canWrite()) {
				file.setWritable(true);
			}
			if (file.exists()) {
				audioFile = AudioFileIO.read(file);
			}

			metadataReader = new MetadataReader(audioFile);
			if (audioFile instanceof MP3File) {
				metadataReader = new Mp3MetadataReader(audioFile);
			}
			Tag tag = metadataReader.getTag();

			readAudioHeader(track);

			readTag(track, file, tag);

			// TODO Model a Business Exception to propagate
			// low level exceptions to show an appropriate
			// message to the user

		} catch (Exception e) {
			log.error(e.getMessage());
		}
		track.setFileName(trackFile.getFile().getName());
		return track;
	}

	private void readTag(LocalTrack track, File file, Tag tag) {
		track.setArtist(tag.getFirst(FieldKey.ARTIST));

		track.setAlbum(tag.getFirst(FieldKey.ALBUM));

		track.setGenre(metadataReader.getGenre());

		String year = tag.getFirst(FieldKey.YEAR);
		if (year.length() > 4) {
			year = year.substring(0, 4);
		}
		track.setYear(year);

		String name = tag.getFirst(FieldKey.TITLE);
		if (StringUtils.isEmpty(name)) {
			name = file.getName().substring(0, file.getName().lastIndexOf('.'));
		}
		track.setName(name.trim());

		String trackNumber = "";

		if (audioFile.toString().contains("Flac")) {
			track.setTrackNumber(tag.getFirstField("TRCK").toString().trim());
		} else {
			// TrackNumber is not supported in ID3Tag version 1.0
			try {
				trackNumber = tag.getFirst(FieldKey.TRACK);
				track.setTrackNumber(trackNumber.trim());
			} catch (Exception nue) {
				log.debug(track.getName() + " Has a not valid TrackNumber");
			}
		}
	}

	private void readAudioHeader(LocalTrack track) {
		AudioHeader header = audioFile.getAudioHeader();

		metadataReader.getBitrate(header, track);

		String sampleRate = header.getSampleRate();

		track.setDuration(header.getTrackLength());

		track.setSampleRate(sampleRate.trim());
	}

}
