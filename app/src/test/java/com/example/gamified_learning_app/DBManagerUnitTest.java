package com.example.gamified_learning_app;

import android.content.Context;

import com.example.gamified_learning_app.tool.DBManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import androidx.annotation.NonNull;
import androidx.test.platform.app.InstrumentationRegistry;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;

@RunWith(RobolectricTestRunner.class)
public class DBManagerUnitTest {
	
	static final String TEST_NAME = "admin",
						TEST_EMAIL = "aqn180001@utdallas.edu",
						TEST_PASSWORD = "sda982dslahsjhkadlshalk201h*@";
	
	
	@Test
	public void DBFirebaseInteractorCorrect() throws Exception {
		Context mockContext = InstrumentationRegistry.getInstrumentation().getContext();
		FirebaseApp.initializeApp(mockContext);
		DBManager manager = new DBManager(mockContext);
		
		// Remove account if exist
		assertNull(manager.interactor.createAccount(TEST_EMAIL,
			TEST_NAME, TEST_PASSWORD, new OnCompleteListener<AuthResult>() {
				@Override
				public void onComplete(@NonNull Task<AuthResult> task) {
					System.out.println("HI");
					manager.interactor.signIn(TEST_EMAIL, TEST_PASSWORD,
						new OnCompleteListener<AuthResult>() {
							@Override
							public void onComplete(@NonNull Task<AuthResult> task) {
								System.out.println("Logged In");
								assertEquals(TEST_EMAIL,
									manager.interactor.getCurrentUser().getEmail());
								assertEquals(true,
									manager.interactor.getCurrentUser().isEmailVerified());
								manager.interactor.signOut();
								assertNull(manager.interactor.getCurrentUser());
								manager.interactor.signIn(TEST_EMAIL, TEST_PASSWORD,
									new OnCompleteListener<AuthResult>() {
										@Override
										public void onComplete(@NonNull Task<AuthResult> task) {
											System.out.println("Loged in");
											assertEquals(TEST_EMAIL,
												manager.interactor.getCurrentUser().getEmail());
											assertEquals(true,
												manager.interactor.getCurrentUser().isEmailVerified());
											manager.interactor.deleteAccount(null);
											System.out.println("Deleted");
										}
									}
								);
							}
						}
					);
				}
			}));
		long t = System.currentTimeMillis();
		while (System.currentTimeMillis() - t < 10000);
	}
}
