package wiley.lms.userprocessor.controller;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import wiley.lms.userprocessor.model.dto.UserDto;
import wiley.lms.userprocessor.model.entity.Role;
import wiley.lms.userprocessor.model.entity.User;
import wiley.lms.userprocessor.service.UserService;
import wiley.lms.userprocessor.util.Validator;

import java.text.MessageFormat;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/test")
    public ResponseEntity<String> testConnection() {
        ResponseEntity<String> responseEntity = new ResponseEntity<>("Connection Success", HttpStatus.OK);
        logger.debug("Successfully Entered testConnection Method");
        return responseEntity;
    }

    @GetMapping("")
    public ResponseEntity<List<User>> getAllUsers() {
        logger.debug("Successfully Entered getAllUsers Request");
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<User> addUser(@RequestBody UserDto userDto) {
        logger.debug("Successfully Entered addUser Request");
        User user = userService.saveUser(userDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable("userId") String userId) {
        logger.debug("Successfully Entered getUser Request");
        User user = userService.getUserById(Validator.stringToLong(userId));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable("userId") String userId, @RequestBody UserDto userDto) {
        logger.debug("Successfully Entered updateUser Method");
        User user = userService.updateUser(Validator.stringToLong(userId), userDto);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable("userId") String userId) {
        logger.debug("Successfully Entered addUser Method");
        userService.deleteUserById(Validator.stringToLong(userId));
        return new ResponseEntity<>("User Deleted Successfully : " + userId, HttpStatus.OK);
    }

    @GetMapping("/active/{active}")
    public ResponseEntity<List<User>> getAllActiveUsers(@PathVariable("active") String active) {
        logger.debug("Successfully Entered getAllActiveUsers Method");
        List<User> users = userService.getActiveUsers(Validator.stringToBoolean(active));
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<User>> getAllUsersByRole(@PathVariable("role") Role role) {
        logger.debug(MessageFormat.format("Successfully Entered getAllUsersByRole Method with Role : {0}", role.name()));
        List<User> users = userService.getUsersByRole(role);
        return  new ResponseEntity<>(users, HttpStatus.OK);
    }


}
