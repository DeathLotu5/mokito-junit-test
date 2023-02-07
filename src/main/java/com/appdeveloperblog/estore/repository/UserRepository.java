package com.appdeveloperblog.estore.repository;

import com.appdeveloperblog.estore.model.User;

public interface UserRepository {
    boolean save(User user);
}
