package com.g3usa.conectmongo.Services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.g3usa.conectmongo.Entities.User;
import com.g3usa.conectmongo.Repositories.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public User loginUser(String username, String password){
        Optional<User> optuser = userRepository.findByUsernameAndPassword(username, password);
        if (optuser.isPresent())
            return optuser.get();
        else
            return null;
    }

    public User registerUser(User user){
        User usernameunico = findByUsername(user.getUsername());
        User usererror;

        if (usernameunico == null){
            return userRepository.save(user);
        }
        else {
            usererror = new User();
            usererror.setUsername(user.getUsername());
            usererror.setName("Error: username existente");
            return usererror;
        }
    }

    public boolean deleteUser(String username){
        User usernameunico = findByUsername(username);

        if (usernameunico == null)
            return false;
        else {
            userRepository.delete(usernameunico);
            return true;
        }
    }

    public User updateUser(User user){
        User usernameunico = findByUsername(user.getUsername());
        User usererror;

        if (usernameunico == null){        
            usererror = new User();
            usererror.setUsername(user.getUsername());
            usererror.setName("Error: username NO existente");
            return user;            
        }
        else {
            if (user.getCelular() != null)
                usernameunico.setCelular(user.getCelular());

            if (user.getIdentificacion() != null)
                usernameunico.setIdentificacion(user.getIdentificacion());

            if (user.getName() != null)
                usernameunico.setName(user.getName());

            if (user.getPassword() != null)
                usernameunico.setPassword(user.getPassword());

            return userRepository.save(usernameunico);
        }
    }

}
