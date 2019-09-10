package org.zjw.web.dao;


import org.zjw.web.entity.User;

import java.util.List;

public interface UserDao {

    void insert(User user);

    List<User> selectIncrement(int minute);
}