package com.softwarica.lostandfound;

import com.softwarica.lostandfound.Bill.LoginBLL;

import org.junit.Assert;
import org.junit.Test;

public class UnitTesting {
    String success;

    @Test
    public void testLogin() {
        LoginBLL loginBLL = new LoginBLL();
        Boolean result = loginBLL.checkUser("lucky@gmail.com", "lucky");
        Assert.assertEquals(true, result);
    }

    @Test
    public void logintest() {
        LoginBLL loginBLL = new LoginBLL();
        Boolean result = loginBLL.checkUser("", "lucky");
        Assert.assertEquals(false, result);
    }



}


