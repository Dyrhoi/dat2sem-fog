package domain.user;

import domain.user.exceptions.UserNotFoundException;

public interface UserRepository {
    User getUser(int id) throws UserNotFoundException;
    User getUser(String email) throws UserNotFoundException;
}
