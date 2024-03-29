package ph.agh.tiwo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ph.agh.tiwo.dto.UserDto;
import ph.agh.tiwo.entity.User;
import ph.agh.tiwo.exception.Classes.NegativeMoneyException;
import ph.agh.tiwo.exception.Classes.NoSuchUserException;
import ph.agh.tiwo.exception.Classes.UserAlreadyExistsException;
import ph.agh.tiwo.repository.UserRepository;
import ph.agh.tiwo.util.ProductMap;

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
        if (user.getMoney() == null){
            user.setMoney(0.0);
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
        user.setMoney(user.getMoney());
        user.setProductLists(userDto.getProductLists());
        return this.userRepository.save(user);
    }

    public User updateUser(String userEmail, UserDto userDto) throws NoSuchUserException{
        Optional<User> userOptional = this.userRepository.findByEmail(userEmail);
        if (userOptional.isEmpty()){
            throw new NoSuchUserException();
        }
        User user =  userOptional.get();
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setEmail(userDto.getEmail());
        user.setMoney(user.getMoney());
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
                .money(userDto.getMoney())
                .productLists(Collections.emptySet()).build();
    }

    public User buyProduct(String userEmail, String productName, Double productQuantity) throws NoSuchUserException{
        Optional<User> userOptional = this.userRepository.findByEmail(userEmail);
        if (userOptional.isEmpty()){
            throw new NoSuchUserException();
        }
        User user =  userOptional.get();
        if (user.getMoney() - ProductMap.getCost(productName) > 0) {
            user.setMoney(user.getMoney() - ProductMap.getCost(productName) * productQuantity);
        } else {
            throw new NegativeMoneyException();
        }
        return this.userRepository.save(user);
    }
}
