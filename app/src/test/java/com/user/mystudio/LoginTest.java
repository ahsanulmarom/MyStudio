package com.user.mystudio;

import com.google.firebase.auth.FirebaseAuth;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by user on 01/10/2017.
 */
public class LoginTest {
    private Login login;

    @Before
    public void setUp() throws Exception {
        login = new Login();
    }

    @Test
    public void loginEmailTest() {
        login.login("aaaaaa@gmail.com", "111111");
        String result = FirebaseAuth.getInstance().getCurrentUser().getEmail().toString();
        Assert.assertEquals("aaaaaa@gmail.com", result);
    }

    @After
    public void tearDown() throws Exception {
       login = null;
    }

}