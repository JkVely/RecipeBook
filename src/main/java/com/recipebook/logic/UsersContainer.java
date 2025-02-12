package com.recipebook.logic;

import java.util.ArrayList;
import java.util.List;

public class UsersContainer {
    private final List<User> users;

    public UsersContainer(){
        users = new ArrayList<>();
    }

    public void addUser(User user){
        users.add(user);
    }

    public void deleteUser(User user){
        users.remove(user);
    }

    public User selectUser(String username){
        for(User user : users){
            if(user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }

    public List<User> getUsers(){
        return users;
    }

    public void setUsers(List<User> users){
        this.users.clear();
        this.users.addAll(users);
    }
}
