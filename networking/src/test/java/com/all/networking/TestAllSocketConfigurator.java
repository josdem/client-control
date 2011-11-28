package com.all.networking;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.ssl.SslFilter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.all.networking.util.IoFactory;

public class TestAllSocketConfigurator {

	private static final String[] CIPHER_SUITES = new String[] { "SSL_RSA_WITH_RC4_128_MD5", "SSL_RSA_WITH_RC4_128_SHA",
			"TLS_RSA_WITH_AES_128_CBC_SHA", "TLS_DHE_RSA_WITH_AES_128_CBC_SHA", "TLS_DHE_DSS_WITH_AES_128_CBC_SHA",
			"SSL_RSA_WITH_3DES_EDE_CBC_SHA", "SSL_DHE_RSA_WITH_3DES_EDE_CBC_SHA", "SSL_DHE_DSS_WITH_3DES_EDE_CBC_SHA",
			"SSL_RSA_WITH_DES_CBC_SHA", "SSL_DHE_RSA_WITH_DES_CBC_SHA", "SSL_DHE_DSS_WITH_DES_CBC_SHA",
			"SSL_RSA_EXPORT_WITH_RC4_40_MD5", "SSL_RSA_EXPORT_WITH_DES40_CBC_SHA", "SSL_DHE_RSA_EXPORT_WITH_DES40_CBC_SHA",
			"SSL_DHE_DSS_EXPORT_WITH_DES40_CBC_SHA" };

	private NetworkingSocketFactory socketConfigurator;

	private IoFactory ioFactory = mock(IoFactory.class);

	private IoConnector ioConnector = mock(IoConnector.class);

	private IoAcceptor ioAcceptor = mock(IoAcceptor.class);

	private DefaultIoFilterChainBuilder filterChain = mock(DefaultIoFilterChainBuilder.class);

	private ArgumentCaptor<IoFilter> filterCaptor = ArgumentCaptor.forClass(IoFilter.class);

	@Before
	public void setup() {
		socketConfigurator = new NetworkingSocketFactory(ioFactory);
	}

	@Test
	public void shouldConfigurePeerConnector() throws Exception {
		when(ioFactory.createConnector()).thenReturn(ioConnector);
		when(ioConnector.getFilterChain()).thenReturn(filterChain);

		IoConnector actual = socketConfigurator.newConnector();

		assertEquals(ioConnector, actual);
		verify(filterChain).addLast(eq(NetworkingConstants.SSL_FILTER), filterCaptor.capture());
		verify(filterChain).addLast(eq(NetworkingConstants.CODEC_FILTER), filterCaptor.capture());
		for (IoFilter filter : filterCaptor.getAllValues()) {
			if (filter instanceof SslFilter) {
				SslFilter sslFilter = (SslFilter) filter;
				assertTrue(Arrays.equals(CIPHER_SUITES, sslFilter.getEnabledCipherSuites()));
				assertTrue(sslFilter.isUseClientMode());
			} else {
				assertTrue(filter instanceof ProtocolCodecFilter);
			}
		}
	}

	@Test
	public void shouldConfigurePeerAcceptor() throws Exception {
		when(ioFactory.createAcceptor()).thenReturn(ioAcceptor);
		when(ioAcceptor.getFilterChain()).thenReturn(filterChain);

		IoAcceptor actual = socketConfigurator.newAcceptor();

		assertEquals(ioAcceptor, actual);
		verify(filterChain).addLast(eq(NetworkingConstants.SSL_FILTER), filterCaptor.capture());
		verify(filterChain).addLast(eq(NetworkingConstants.CODEC_FILTER), filterCaptor.capture());
		for (IoFilter filter : filterCaptor.getAllValues()) {
			if (filter instanceof SslFilter) {
				SslFilter sslFilter = (SslFilter) filter;
				assertTrue(Arrays.equals(CIPHER_SUITES, sslFilter.getEnabledCipherSuites()));
				assertFalse(sslFilter.isUseClientMode());
			} else {
				assertTrue(filter instanceof ProtocolCodecFilter);
			}
		}
	}

}
