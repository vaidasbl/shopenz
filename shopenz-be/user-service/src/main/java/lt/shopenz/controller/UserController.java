package lt.shopenz.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lt.shopenz.model.User;
import lt.shopenz.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController
{
    private final UserService userService;

    public UserController(UserService pService)
    {
        userService = pService;
    }

    @GetMapping("")
    public List<User> getAllUsers()
    {
        return userService.getAllUsers();
    }

    @PostMapping("")
    public User createUser(@RequestBody User user)
    {
        return userService.createUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Long id)
    {
        userService.deleteUserById(id);
    }
}
