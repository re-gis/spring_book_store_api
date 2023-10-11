package dev.com.store.Services;


import dev.com.store.Entities.User;

public interface UserService {
    User getLoggedInUser() throws Exception; 
}
