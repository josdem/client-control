package com.all.shared.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestUltrapeerNode {

	@Test
	public void shouldBeComaparable() throws Exception {
		UltrapeerNode ultrapeerA = new UltrapeerNode("192.168.1.1");
		UltrapeerNode ultrapeerB = new UltrapeerNode("192.168.1.2");
		UltrapeerNode ultrapeerC = new UltrapeerNode("192.168.1.3");

		ultrapeerA.incrementSuccesfulConnections();
		ultrapeerA.incrementSuccesfulConnections();
		ultrapeerA.incrementSuccesfulConnections();

		ultrapeerB.incrementSuccesfulConnections();
		ultrapeerB.incrementSuccesfulConnections();
		ultrapeerB.incrementSuccesfulConnections();
		ultrapeerB.incrementUnsuccesfulConnections();

		ultrapeerC.incrementSuccesfulConnections();
		ultrapeerC.incrementSuccesfulConnections();

		assertEquals(1, ultrapeerA.compareTo(ultrapeerB));
		assertEquals(0, ultrapeerB.compareTo(ultrapeerC));
	}

	@Test
	public void shouldBeEqualIfSameAddress() throws Exception {
		UltrapeerNode ultrapeerA = new UltrapeerNode("192.168.1.2");
		UltrapeerNode ultrapeerB = new UltrapeerNode("192.168.1.2");
		UltrapeerNode ultrapeerC = new UltrapeerNode("192.168.1.3");
		UltrapeerNode ultrapeerD = new UltrapeerNode();

		assertTrue(ultrapeerA.equals(ultrapeerB));
		assertTrue(ultrapeerB.equals(ultrapeerA));
		assertFalse(ultrapeerA.equals(ultrapeerC));
		assertFalse(ultrapeerA.equals(ultrapeerD));
		assertFalse(ultrapeerA.equals(null));
	}

}
