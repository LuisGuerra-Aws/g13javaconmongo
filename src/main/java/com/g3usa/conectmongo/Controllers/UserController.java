package com.g3usa.conectmongo.Controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.g3usa.conectmongo.Entities.User;
import com.g3usa.conectmongo.Services.UserService;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserService userService;
    
    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/all")
    public List<User> getAll(){
        return userService.getAll();
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user){
        User usuarioencontrado;
        if ((user.getUsername().trim().length() > 5) && (user.getPassword().trim().length() > 4)){
            usuarioencontrado = userService.loginUser(user.getUsername(), user.getPassword());
            if (usuarioencontrado != null){
                return ResponseEntity.ok(usuarioencontrado);
            }
            else {
                return ResponseEntity.badRequest().body("Usuario y clave no encontrado");
            }       
        }
        else {
            return ResponseEntity.badRequest().body("Información Ingreso Incompleta");
        }
    }


    @GetMapping("/{username}")
    public ResponseEntity<?> getUser(@PathVariable("username") String username){
        User usuarioencontrado;
        usuarioencontrado = userService.findByUsername(username);
        if (usuarioencontrado != null){
            return ResponseEntity.ok(usuarioencontrado);
        }
        else 
            return ResponseEntity.badRequest().body("Usuario no Encontrado");
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public User registerUser(@RequestBody User user){
        return userService.registerUser(user);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.CREATED)
    public User updateUser(@RequestBody User user){
        return userService.updateUser(user);
    }


    @DeleteMapping("{username}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> deleteUser(@PathVariable("username") String username){
        boolean deleteFlag  = userService.deleteUser(username);

        if (deleteFlag)
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.badRequest().body("Usuario no Encontrado");

    }

}
