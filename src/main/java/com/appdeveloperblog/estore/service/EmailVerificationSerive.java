package com.appdeveloperblog.estore.service;

import com.appdeveloperblog.estore.model.User;

public interface EmailVerificationSerive {
    void scheduleEmailConfirmation(User user);
}
