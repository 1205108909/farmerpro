package com.phenix.farmer.orderbook;

import com.phenix.orderbook.Quote;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class QuoteTest {
	Quote quote = new Quote(0.2, 100.23);

	@Test
	public void testToString() {
		assertEquals("0.2@100.23", quote.toString());
	}
}
