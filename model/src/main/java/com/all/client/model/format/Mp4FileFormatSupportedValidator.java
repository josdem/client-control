package com.all.client.model.format;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp4.Mp4AudioHeader;
import org.jaudiotagger.audio.mp4.atom.Mp4EsdsBox;
import org.jaudiotagger.tag.TagException;

import com.all.client.itunes.UnableReadMetadataException;

public class Mp4FileFormatSupportedValidator implements
FileFormatSupportedValidator {
	
	Boolean isDrmProtected = null;
	Boolean isAudioFile = null;
	File file = null;

	public Mp4FileFormatSupportedValidator(File file) {
		this.file = file;
	}

	private void readMetadata() throws UnableReadMetadataException{
		AudioFile f;
		try {
			f = AudioFileIO.read(file);
			AudioHeader header = f.getAudioHeader();
			if(header instanceof Mp4AudioHeader){
				//first approach to get metadata: from audio header
				initInstanceFieldsByMp4AudioHeader((Mp4AudioHeader) header);
			}else{
				String template = "ex-4444 It seems ''{0}'' is not an '.mp4' file";
				String message = MessageFormat.format(template, file.toString());
				throw new UnableReadMetadataException(message);
			}
			//this second approach to get metadata, used becouse it already proven to be reliable
			initInstanceFieldsByEncodingType(header);
		} catch (CannotReadException e) {
			String template = "ex-150 Unable to read metadata from ''{0}''";
			String message = MessageFormat.format(template, file.getAbsolutePath());
			throw new UnableReadMetadataException(message, e);
		} catch (TagException e) {
			String template = "ex-151 Unable to read metadata from ''{0}''";
			String message = MessageFormat.format(template, file.getAbsolutePath());
			throw new UnableReadMetadataException(message, e);
		} catch (ReadOnlyFileException e) {
			String template = "ex-152 Unable to read metadata from ''{0}''";
			String message = MessageFormat.format(template, file.getAbsolutePath());
			throw new UnableReadMetadataException(message, e);
		} catch (InvalidAudioFrameException e) {
			String template = "ex-153 Unable to read metadata from ''{0}''";
			String message = MessageFormat.format(template, file.getAbsolutePath());
			throw new UnableReadMetadataException(message, e);
		} catch (IOException e) {
			String template = "ex-154 Unable to read metadata from ''{0}''";
			String message = MessageFormat.format(template, file.getAbsolutePath());
			throw new UnableReadMetadataException(message, e);
		}
	}
	



	@Override
	public boolean isAudioFile() {
		if(this.isAudioFile==null){
			try {
				//lazy initialization
	            readMetadata();
            } catch (UnableReadMetadataException e) {
            	String pattern = "Invalid input file ''{0}''";
            	String message = MessageFormat.format(pattern, this.file);
            	throw new IllegalArgumentException(message, e);
            }
		}
		return this.isAudioFile;
	}

	@Override
	public boolean isDrmProtected() {
		if(this.isDrmProtected==null){
			try {
	            readMetadata();
            } catch (UnableReadMetadataException e) {
            	isDrmProtected = true;
            }
		}
		return this.isDrmProtected;
	}

	@Override
    public boolean isAllowedToBeImportedByBusinessRule() {
	    return false;
    }

	/**
	 * this info was not documented by jaudiotagger, so inaccurate interpretation 
	 * of identificators can result happen
	 * @param mp4Header
	 * @return
	 */
	private void initInstanceFieldsByMp4AudioHeader(Mp4AudioHeader mp4Header){
		this.isDrmProtected = null;
		Mp4EsdsBox.Kind kind = mp4Header.getKind();
		switch(kind){
		case ADPCM_AUDIO:
		case ALAW_AUDIO:
		case DOLBY_V3_AUDIO:
			this.isAudioFile = true;
			break;
		case H261_VIDEO:
		case H263_VIDEO:
		case H264_VIDEO:
		case JPEG_VIDEO:
			this.isAudioFile = false;
			break;
		case MPEG1_ADTS:
			this.isAudioFile = true;
			break;
		case MPEG1_VIDEO:
		case MPEG2_422_VIDEO:
			this.isAudioFile = false;
			break;
		case MPEG2_ADTS_MAIN:
			this.isAudioFile = true;
			break;
		case MPEG2_HIGH_VIDEO:
		case MPEG2_MAIN_VIDEO:
		case MPEG2_SIMPLE_VIDEO:
		case MPEG2_SNR_VIDEO:
		case MPEG2_SPATIAL_VIDEO:
			this.isAudioFile = false;
			break;
		case MPEG4_ADTS_LOW_COMPLEXITY:
		case MPEG4_ADTS_MAIN:
		case MPEG4_ADTS_SCALEABLE_SAMPLING:
		case MPEG4_AUDIO:
		case MPEG4_AVC_PPS:
		case MPEG4_AVC_SPS:
			this.isAudioFile = true;
			break;
		case MPEG4_VIDEO:
			this.isAudioFile = false;
			break;
		case MULAW_AUDIO:
		case PCM_BIG_ENDIAN_AUDIO:
		case PCM_LITTLE_ENDIAN_AUDIO:
		case PRIVATE_AUDIO:
			this.isAudioFile = true;
			break;
		case PRIVATE_VIDEO:
		case V1://not found information about it
		case V2://not found information about it
			this.isAudioFile = false;
			break;
		case VORBIS_AUDIO:
			this.isAudioFile = true;
			break;
		case YV12_VIDEO:
			this.isAudioFile = false;
			break;
		default:
			this.isAudioFile = false;
		}
	}


	/**
	 * this second method for validation was included becouse it actually proves to be worty
	 * @param header
	 */
	private void initInstanceFieldsByEncodingType(AudioHeader header) {
		String encodingType = header.getEncodingType();
		if(encodingType!=null){
			this.isDrmProtected = encodingType.startsWith("DRM ");
			if( encodingType.endsWith(" AAC") ){
				this.isAudioFile = true;
			}
		}
    }
}
