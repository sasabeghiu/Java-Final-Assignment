package service;

import dao.Database;
import model.User;

import java.util.List;

public class UserService {
    private final Database database = new Database();

    public User isValidUsername(String username, String password) {
        List<User> userList = database.getUserList();

        for (User user : userList) {
            if (user.getUsername().equals(username) && (user.getPassword().equals(password))) {
                return user;
            }
        }
        return null;
    }

    public Boolean isValidPassword(String password) {
        boolean number = false;
        boolean character = false;
        boolean specialCharacter = false;

        for (char ch : password.toCharArray()) {
            if (Character.isDigit(ch)) {
                number = true;
            } else if (Character.isLetter(ch)) {
                character = true;
            } else {
                specialCharacter = true;
            }
        }
        return password.length() > 7 && number && character && specialCharacter;
    }
}