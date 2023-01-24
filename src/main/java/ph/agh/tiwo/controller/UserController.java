package ph.agh.tiwo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ph.agh.tiwo.dto.LoginDto;
import ph.agh.tiwo.dto.UserDto;
import ph.agh.tiwo.entity.Product;
import ph.agh.tiwo.entity.ProductList;
import ph.agh.tiwo.entity.User;
import ph.agh.tiwo.exception.Classes.NoSuchUserException;
import ph.agh.tiwo.exception.Classes.UserAlreadyExistsException;
import ph.agh.tiwo.repository.UserRepository;
import ph.agh.tiwo.security.JwtTokenGenerator;
import ph.agh.tiwo.service.UserService;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tiwo/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserService userService;
    private final JwtTokenGenerator jwtTokenGenerator;

    private final PasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    public UserController(UserService userService,JwtTokenGenerator jwtTokenGenerator, PasswordEncoder bCryptPasswordEncoder,
                          UserRepository userRepository) {
        this.userService = userService;
        this.jwtTokenGenerator = jwtTokenGenerator;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto){
        try {
            User user = this.userService.getUserByEmail(loginDto.getEmail());
            if (user == null)
                throw new NoSuchUserException();
            if (user.getPassword().equals(loginDto.getPassword()))
                return new ResponseEntity<>(jwtTokenGenerator.generateToken(
                        new UsernamePasswordAuthenticationToken(user.getEmail(),
                                user.getPassword(), null)), HttpStatus.OK);
            else
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }catch (NoSuchUserException e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserDto userDto, @RequestParam String password){
        User user = userService.buildUser(userDto,bCryptPasswordEncoder.encode(password));
        try {
            User addedUser = userService.addUser(user);
            return new ResponseEntity<>(addedUser, HttpStatus.CREATED);
        }catch (UserAlreadyExistsException e){
            throw new UserAlreadyExistsException();
        }
    }

    @GetMapping("/getLists")
    public ResponseEntity<List<ProductList>> getLists(@RequestParam String email){
        User user = userService.getUserByEmail(email);
        for (ProductList lista : user.getProductLists()) {
            lista.getProducts().sort(Comparator.comparing(Product::getId));
        }
        return new ResponseEntity<>(user.getProductLists().stream().sorted(Comparator.comparing(ProductList::getId))
                .collect(Collectors.toList()), HttpStatus.OK);
    }

}