package com.all.client.model;

import static com.all.client.model.FileExtension.AAC;
import static com.all.client.model.FileExtension.AIFF;
import static com.all.client.model.FileExtension.AMR;
import static com.all.client.model.FileExtension.AU;
import static com.all.client.model.FileExtension.M4A;
import static com.all.client.model.FileExtension.M4B;
import static com.all.client.model.FileExtension.M4P;
import static com.all.client.model.FileExtension.MP2;
import static com.all.client.model.FileExtension.MP3;
import static com.all.client.model.FileExtension.MP4;
import static com.all.client.model.FileExtension.OGG;
import static com.all.client.model.FileExtension.WAV;
import static com.all.client.model.FileExtension.WMA;

import java.io.File;

import com.all.client.model.format.AacFileFormatSupportedValidator;
import com.all.client.model.format.AiffFileFormatSupportedValidator;
import com.all.client.model.format.AmrFileFormatSupportedValidator;
import com.all.client.model.format.AuFileFormatSupportedValidator;
import com.all.client.model.format.FileFormatSupportedValidator;
import com.all.client.model.format.M4aFileFormatSupportedValidator;
import com.all.client.model.format.M4bFileFormatSupportedValidator;
import com.all.client.model.format.M4pFileFormatSupportedValidator;
import com.all.client.model.format.Mp2FileFormatSupportedValidator;
import com.all.client.model.format.Mp3FileFormatSupportedValidator;
import com.all.client.model.format.Mp4FileFormatSupportedValidator;
import com.all.client.model.format.OggFileFormatSupportedValidator;
import com.all.client.model.format.WavFileFormatSupportedValidator;
import com.all.client.model.format.WmaFileFormatSupportedValidator;
import com.all.client.util.FileUtil;

public class FileFormatSupportedValidatorFactory {

	final FileFormatSupportedValidator M4A_VALIDATOR = new M4aFileFormatSupportedValidator();
	final FileFormatSupportedValidator MP3_VALIDATOR = new Mp3FileFormatSupportedValidator();
	final FileFormatSupportedValidator WAV_VALIDATOR = new WavFileFormatSupportedValidator();
	final FileFormatSupportedValidator M4P_VALIDATOR = new M4pFileFormatSupportedValidator();
	final FileFormatSupportedValidator AIFF_VALIDATOR = new AiffFileFormatSupportedValidator();
	final FileFormatSupportedValidator AU_VALIDATOR = new AuFileFormatSupportedValidator();
	final FileFormatSupportedValidator M4B_VALIDATOR = new M4bFileFormatSupportedValidator();
	final FileFormatSupportedValidator WMA_VALIDATOR = new WmaFileFormatSupportedValidator();
	final FileFormatSupportedValidator MP2_VALIDATOR = new Mp2FileFormatSupportedValidator();
	final FileFormatSupportedValidator OGG_VALIDATOR = new OggFileFormatSupportedValidator();
	final FileFormatSupportedValidator AMR_VALIDATOR = new AmrFileFormatSupportedValidator();
	final FileFormatSupportedValidator ACC_VALIDATOR = new AacFileFormatSupportedValidator();

	public FileFormatSupportedValidator createValidator(File file) {
		String extension = FileUtil.getExtension(file);
		FileFormatSupportedValidator result = null;
		if (M4P.toString().equals(extension)) {
			result = M4P_VALIDATOR;
		} else if (M4A.toString().equals(extension)) {
			result = M4A_VALIDATOR;
		} else if (MP4.toString().equals(extension)) {
			result = new Mp4FileFormatSupportedValidator(file);
		} else if (MP3.toString().equals(extension)) {
			result = MP3_VALIDATOR;
		} else if (WAV.toString().equals(extension)) {
			result = WAV_VALIDATOR;
		} else if (AIFF.toString().equals(extension)) {
			result = AIFF_VALIDATOR;
		} else if (AU.toString().equals(extension)) {
			result = AU_VALIDATOR;
		} else if (M4B.toString().equals(extension)) {
			result = M4B_VALIDATOR;
		} else if (WMA.toString().equals(extension)) {
			result = WMA_VALIDATOR;
		} else if (MP2.toString().equals(extension)) {
			result = MP2_VALIDATOR;
		} else if (OGG.toString().equals(extension)) {
			result = OGG_VALIDATOR;
		} else if (AMR.toString().equals(extension)) {
			result = AMR_VALIDATOR;// TODO it is necessary to be statefull?
		} else if (AAC.toString().equals(extension)) {
			result = ACC_VALIDATOR;
		} else {
			result = null;
		}
		return result;
	}
}
