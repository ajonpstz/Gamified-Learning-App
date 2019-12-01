package com.example.gamified_learning_app;

import android.content.Context;

import com.example.gamified_learning_app.data.CardSet;
import com.example.gamified_learning_app.tool.FirebaseDBManager;
import com.google.firebase.FirebaseApp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.Arrays;

@RunWith(RobolectricTestRunner.class)
public class FirebaseDBTest {
	
	public FirebaseDBTest() {
	}
	
	@Test
	public void FirebaseDBTestCorrect() throws Exception {
		FirebaseApp.initializeApp(Mockito.mock(Context.class));
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
		
		FirebaseDBManager.createCardSet(new CardSet("owner", "Numbers", "description", null,
			new ArrayList<CardSet.Card>(Arrays.asList(numberCards))),
			cardSet -> {
				// If successful, it will run this function
				System.out.println("SUCCESS");
				return null;
			}, s -> {
				// If not, it will run this function. s is the string representation of the error
				System.out.println(s);
				return null;
			});
	}
}
