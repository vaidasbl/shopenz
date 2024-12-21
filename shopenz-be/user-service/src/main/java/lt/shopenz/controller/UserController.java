package lt.shopenz.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/test")
    @PreAuthorize("hasAuthority('USER')")
    public String testEndpoint()
    {
        return "YES";
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Long id)
    {
        userService.deleteUserById(id);
    }
}
