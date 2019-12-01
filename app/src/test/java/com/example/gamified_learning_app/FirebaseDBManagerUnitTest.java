package com.example.gamified_learning_app;

import com.example.gamified_learning_app.data.CardSet;
import com.example.gamified_learning_app.tool.FirebaseDBManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;

@RunWith(JUnit4.class)
public class FirebaseDBManagerUnitTest {
	static final String emailUsed = "aqn180001@utdallas.edu";
	static final String username = "m1sch3f";
	static final String password = emailUsed;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void FirebaseDBManagerInteractor() throws Exception {
		FirebaseDBManager.getCardSet(username, cardSets -> {
			for (CardSet set : cardSets) {
				System.out.println(set.name);
			}
			return null;
		}, e->{
			System.out.println(e);
			return null;
		});
		//temporary local data for experimentation
		CardSet.Card numberCards[] = new CardSet.Card[] {
			new CardSet.Card("one","1"),
			new CardSet.Card("two", "2"),
			new CardSet.Card("three", "3"),
			new CardSet.Card("four", "4")
		};
		CardSet.Card osCards[] = new CardSet.Card[] {
			new CardSet.Card("access method","The method that is used to find a file, a record, or a set of records."),
			new CardSet.Card("application programming interface (API)", "A standardized library of programming tools used by software developers to write applications that are compatible with a specific operating system or graphic user interface."),
			new CardSet.Card("asynchronous operation", "An operation that occurs without a regular or predictable time relationship to a specified event, for example, the calling of an error diagnostic routine that may receive control at any time during the execution of a computer program."),
			new CardSet.Card("Beowulf", "Defines a class of clustered computing that focuses on minimizing the price-to-performance ratio of the overall system without compromising its ability to perform the computation work for which it is being built. Most Beowulf systems are implemented on Linux computers.")
		};
		FirebaseDBManager.createCardSet(new CardSet(username, "Numbers", "description", null,
			new ArrayList<CardSet.Card>(Arrays.asList(numberCards))), cardSet -> {
			// If the operation is successful, this is executed
			return null;
		}, e->{
			// If the operation isn't, this is executed. e is the string representation of the error
			return null;
		});
		
		FirebaseDBManager.createCardSet(new CardSet(username, "Operating System", "description", null,
			new ArrayList<CardSet.Card>(Arrays.asList(osCards))), cardSet -> {
			// If the operation is successful, this is executed
			return null;
		}, e->{
			// If the operation isn't, this is executed. e is the string representation of the error
			return null;
		});
	}
}
