package by.mops.bet.services;


import by.mops.bet.model.User;

public interface UserService {

    void saveNewUser(User user);

    User findByUsername(String username);
}
