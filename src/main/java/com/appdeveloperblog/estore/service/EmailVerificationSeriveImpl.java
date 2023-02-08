package com.appdeveloperblog.estore.service;

import com.appdeveloperblog.estore.model.User;

public class EmailVerificationSeriveImpl implements EmailVerificationSerive {

    @Override
    public void scheduleEmailConfirmation(User user) {
        // Put user details into email queue
        System.out.println("Put user details into email queue");
    }
}
