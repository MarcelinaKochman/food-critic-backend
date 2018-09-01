package com.wfiis.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfiis.dao.UserDao;
import com.wfiis.exception.UserExistsException;
import com.wfiis.model.User;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public List<User> findAllUsers() {
        return userDao.findAll();
    }

    public Optional<User> findById(String id) {
        return userDao.findById(id);
    }

    public User findByLogin(String login) {
        return userDao.findByLogin(login);
    }

    public User registerNewUserAccount(User accountDto) throws UserExistsException {
        if (userExist(accountDto.getLogin())) {
            throw new UserExistsException();
        }

        return userDao.save(accountDto);
    }

    public boolean authorization(User accountDto) {
        User user = userDao.findByLogin(accountDto.getLogin());

        return user != null && user.getPassword().equals(accountDto.getPassword());

    }

    public void deleteUserById(String id) {
        userDao.deleteById(id);
    }

    private boolean userExist(String login) {
        User user = userDao.findByLogin(login);

        return user != null;
    }
}
