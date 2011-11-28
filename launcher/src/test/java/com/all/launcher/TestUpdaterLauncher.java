package com.all.launcher;

import static org.junit.Assert.*;

import java.util.concurrent.Callable;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

import com.all.launcher.UpdaterLauncher.UpdaterFactory;


public class TestUpdaterLauncher {

	@InjectMocks
	private UpdaterLauncher updaterLauncher = new UpdaterLauncher();
	@Mock
	private UpdaterFactory updaterFactory;
	@Mock
	private Callable<Boolean> mockCallable;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldDelegateCallToCallable() throws Exception {
		when(updaterFactory.create(UpdaterLauncher.CHECK_UPDATER_APPLICATION)).thenReturn(mockCallable);
		when(mockCallable.call()).thenReturn(true);
		
		assertTrue(updaterLauncher.requiresUpdate());
		
		verify(mockCallable).call();
	}
	
	@Test
	public void shouldDelegateCall() throws Exception {
		when(updaterFactory.create(UpdaterLauncher.UPDATER_APPLICATION)).thenReturn(mockCallable);

		updaterLauncher.launch();
		
		verify(mockCallable).call();
	}
	
}
