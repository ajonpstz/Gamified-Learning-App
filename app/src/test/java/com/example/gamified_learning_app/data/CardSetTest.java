package com.example.gamified_learning_app.data;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CardSetTest {
	
	@Test
	public void add() {
		CardSet cards = new CardSet("owner", "name", "description");
		cards.add(new CardSet.Card("one", "1"));
		assertEquals(cards.cards.size(),1);
		assertEquals(cards.cards.get(0).term, "one");
		assertEquals(cards.cards.get(0).definition, "1");
	}
	
	@Test
	public void removeCard() {
		CardSet cards = new CardSet("owner", "name", "description");
		
		Date today = Calendar.getInstance().getTime();
		cards.add(new CardSet.Card("one", "1"));
		cards.removeCard("one");
		assertEquals(cards.cards.size(),0);
	}
	
	@Test
	public void add1() {
		CardSet cards = new CardSet("owner", "name", "description");
		Date today = Calendar.getInstance().getTime();
		cards.add(new CardSet.Event(today, 1,1));
		assertEquals(cards.history.size(), 1);
		assertEquals(cards.history.get(0).date, today);
		assertEquals(cards.history.get(0).duration, 1, 1e-9);
		assertEquals(cards.history.get(0).recallRate, 1, 1e-9);
	}
	
	@Test
	public void nextScheduled() {
		CardSet cards = new CardSet("owner", "name", "description");
		
		// Date scheduled is today if no activity
		Calendar date = Calendar.getInstance();
		date.setTime(cards.nextScheduled());
		Calendar date2 = Calendar.getInstance();
		assertEquals(date.get(Calendar.DAY_OF_YEAR), date2.get(Calendar.DAY_OF_YEAR));
		
		cards.add(new CardSet.Event(Calendar.getInstance().getTime(), 1, 1));
		Calendar date3 = Calendar.getInstance();
		date3.setTime(cards.nextScheduled());
		
		System.out.println(date.get(Calendar.DAY_OF_YEAR));
		System.out.println(date3.get(Calendar.DAY_OF_YEAR));
		assertNotEquals(date.get(Calendar.DAY_OF_YEAR), date3.get(Calendar.DAY_OF_YEAR));
	}
	
	@Test
	public void getIdentifier() {
		CardSet cards = new CardSet("owner", "name", "description");
		assertEquals(cards.getIdentifier(), "owner_name");
	}
}