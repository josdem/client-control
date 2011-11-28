package com.all.core.model;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import com.all.client.model.FileFormatSupportedValidatorFactory;
import com.all.client.model.format.AiffFileFormatSupportedValidator;
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

public class TestFileFormatSupportedValidatorFactory {

	FileFormatSupportedValidatorFactory factory;

	@Before
	public void setUp() {
		this.factory = new FileFormatSupportedValidatorFactory();
	}

	@Test
	public void shouldCreateM4pValidator() {
		File file = new File("xxx.m4p");
		FileFormatSupportedValidator v = factory.createValidator(file);
		assertTrue(v instanceof M4pFileFormatSupportedValidator);
	}

	@Test
	public void shouldCreateM4aValidator() {
		File file = new File("xxx.m4a");
		FileFormatSupportedValidator v = factory.createValidator(file);
		assertTrue(v instanceof M4aFileFormatSupportedValidator);
	}

	@Test
	public void shouldCreateMp4Validator() {
		File file = new File("xxx.mp4");
		FileFormatSupportedValidator v = factory.createValidator(file);
		assertTrue(v instanceof Mp4FileFormatSupportedValidator);
	}

	@Test
	public void shouldCreateMp3Validator() {
		File file = new File("xxx.mp3");
		FileFormatSupportedValidator v = factory.createValidator(file);
		assertTrue(v instanceof Mp3FileFormatSupportedValidator);
	}

	@Test
	public void shouldCreateWavValidator() {
		File file = new File("xxx.wav");
		FileFormatSupportedValidator v = factory.createValidator(file);
		assertTrue(v instanceof WavFileFormatSupportedValidator);
	}

	@Test
	public void shouldCreateAiffValidator() {
		File file = new File("xxx.aiff");
		FileFormatSupportedValidator v = factory.createValidator(file);
		assertTrue(v instanceof AiffFileFormatSupportedValidator);
	}

	@Test
	public void shouldCreateAuValidator() {
		File file = new File("xxx.au");
		FileFormatSupportedValidator v = factory.createValidator(file);
		assertTrue(v instanceof AuFileFormatSupportedValidator);
	}

	@Test
	public void shouldCreateM4bValidator() {
		File file = new File("xxx.m4b");
		FileFormatSupportedValidator v = factory.createValidator(file);
		assertTrue(v instanceof M4bFileFormatSupportedValidator);
	}

	@Test
	public void shouldCreateWmaValidator() {
		File file = new File("xxx.wma");
		FileFormatSupportedValidator v = factory.createValidator(file);
		assertTrue(v instanceof WmaFileFormatSupportedValidator);
	}

	@Test
	public void shouldCreateMp2Validator() {
		File file = new File("xxx.mp2");
		FileFormatSupportedValidator v = factory.createValidator(file);
		assertTrue(v instanceof Mp2FileFormatSupportedValidator);
	}

	@Test
	public void shouldCreateOggValidator() {
		File file = new File("xxx.ogg");
		FileFormatSupportedValidator v = factory.createValidator(file);
		assertTrue(v instanceof OggFileFormatSupportedValidator);
	}

	@Test
	public void shouldReturnNullWhenUnknownFileKind() {
		File file = new File("lskdkawoe.notExistExtension");
		FileFormatSupportedValidator v = factory.createValidator(file);
		assertNull(v);
	}
}
