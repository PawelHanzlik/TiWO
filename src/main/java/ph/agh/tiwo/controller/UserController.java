package ph.agh.tiwo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ph.agh.tiwo.dto.UserDto;
import ph.agh.tiwo.entity.User;
import ph.agh.tiwo.service.UserService;

@RestController
@RequestMapping("/tiwo")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public HttpStatus login(@RequestParam String email, @RequestParam String password){
        System.out.println(email);
        System.out.println(password);
        return HttpStatus.OK;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserDto userDto, @RequestParam String password){
        User user = userService.buildUser(userDto,password);
        return new ResponseEntity<>(userService.addUser(user), HttpStatus.CREATED);
    }
}
