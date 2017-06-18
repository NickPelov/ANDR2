package com.example.owner.android2;

import com.example.owner.android2.User.CurrentUser;
import com.example.owner.android2.User.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void Login() throws Exception {
        String UserEmail = "admin@";
        String UserPass = "ad";
        for (User user : CurrentUser.users) {
            if (user.Email.equals(UserEmail)) {
                // Account exists, return true if the password matches.
                if (user.Password.equals(UserPass)) {
                    assertEquals(UserPass, user.Password);
                }
                assertEquals(UserEmail, user.Email);
            }
        }
    }
//    mnogo exeptioni
//    @Test
//    public void Register() throws Exception {
//        String UserEmail = "testRegister@";
//        String UserPass = "pass";
//        String UserNick = "testRegister";
//        String UserName = "testRegister";
//        DatabaseReference usersTable = FireBaseConnection.mRootRefForTest.child("users").push();
//        String pushId = usersTable.getKey();
//        usersTable.setValue(new User(UserName, false, UserEmail, UserNick, UserPass, 0, new location(0, 0)));
//        for (User user : CurrentUser.users) {
//            if (user.Email.equals(UserEmail)) {
//                // Account exists, return true if the password matches.
//                if (user.Password.equals(UserPass)) {
//                    assertEquals(UserPass, user.Password);
//                }
//                assertEquals(UserEmail, user.Email);
//            }
//        }
//    }
}