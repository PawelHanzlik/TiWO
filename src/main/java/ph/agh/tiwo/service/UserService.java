package ph.agh.tiwo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ph.agh.tiwo.dto.UserDto;
import ph.agh.tiwo.entity.User;
import ph.agh.tiwo.exception.Classes.NoSuchUserException;
import ph.agh.tiwo.exception.Classes.UserAlreadyExistsException;
import ph.agh.tiwo.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public User getUserById(Long userId) throws NoSuchUserException {
        Optional<User> userOptional = this.userRepository.findById(userId);
        if (userOptional.isEmpty()){
            throw new NoSuchUserException();
        }
        return userOptional.get();
    }

    public User getUserByEmail(String userEmail) throws NoSuchUserException {
        Optional<User> userOptional = this.userRepository.findByEmail(userEmail);
        if (userOptional.isEmpty()){
            throw new NoSuchUserException();
        }
        return userOptional.get();
    }
    
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }
    
    public User addUser(User user) throws UserAlreadyExistsException{
        Optional<User> userOptional = this.userRepository.findByEmail(user.getEmail());
        if (userOptional.isPresent()){
            throw new UserAlreadyExistsException();
        }
        return this.userRepository.save(user);
    }

    public User updateUser(Long userId, UserDto userDto) throws NoSuchUserException{
        Optional<User> userOptional = this.userRepository.findById(userId);
        if (userOptional.isEmpty()){
            throw new NoSuchUserException();
        }
        User user =  userOptional.get();
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setEmail(userDto.getEmail());
        user.setProductLists(userDto.getProductLists());
        return this.userRepository.save(user);
    }
    public void deleteUser(Long userId) throws NoSuchUserException {
        Optional<User> userOptional = this.userRepository.findById(userId);
        if (userOptional.isEmpty()){
            throw new NoSuchUserException();
        }
        else{
            User user = userOptional.get();
            this.userRepository.delete(user);
        }
    }

    public User buildUser(UserDto userDto, String password){
        return User.builder().name(userDto.getName())
                .surname(userDto.getSurname())
                .email(userDto.getEmail())
                .password(password)
                .productLists(Collections.emptySet()).build();
    }
}
