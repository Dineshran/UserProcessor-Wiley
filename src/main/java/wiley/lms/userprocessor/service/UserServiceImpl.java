package wiley.lms.userprocessor.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wiley.lms.userprocessor.model.dto.UserDto;
import wiley.lms.userprocessor.model.entity.Role;
import wiley.lms.userprocessor.model.entity.User;
import wiley.lms.userprocessor.model.exception.NoUsersException;
import wiley.lms.userprocessor.model.exception.UserNotFoundException;
import wiley.lms.userprocessor.model.repo.UserRepository;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {


    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserRepository userRepository;

    /**
     * no parameter needed
     *
     * @return List of Users
     */
    @Override
    public List<User> getAllUsers() {
        logger.debug("Entered getAllUsers Method");
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            logger.error("No Users Found in the System");
            throw new NoUsersException("No Users Found in the System");
        } else logger.info(MessageFormat.format("Returned {0} Users", users.size()));
        return users;
    }

    /**
     * fetch the user by userid
     *
     * @param userId
     * @return User
     */
    @Override
    public User getUserById(Long userId) {
        logger.debug("Entered getUserById Method");
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("No User found for the given id : " + userId));
    }

    /**
     * return all the users of the specific consumerKey
     *
     * @param role
     * @return List of Users
     */
    @Override
    public List<User> getUsersByRole(Role role) {
        logger.debug(MessageFormat.format("Entered Method getUsersByRole with : {0}", role.name()));
        List<User> users = userRepository.findByRole(role);
        if (users.isEmpty()) {
            logger.error(MessageFormat.format("No Users Found with role : {0}", role.name()));
            throw new NoUsersException("No Users found for given Role : " + role.name());
        }
        logger.info(MessageFormat.format("{0} Users Found with role : {1}", users.size(), role.name()));
        return users;
    }

    /**
     * return all the users of the specific consumerKey
     *
     * @param active
     * @return List of Users
     */
    @Override
    public List<User> getActiveUsers(boolean active) {
        logger.debug(MessageFormat.format("Entered Method getUsersByLtiUserId with isActive : {0}", active));
        List<User> users = userRepository.findByActivated(active);
        if (users.isEmpty()) {
            logger.error(MessageFormat.format("No Users found for isActive : {0}", active));
            throw new NoUsersException("No Users found for isActive : " + active);
        }
        logger.info(MessageFormat.format("{0} Users found for isActive :  {1} ", users.size(), active));
        return users;
    }

    /**
     * delete User by Id
     *
     * @param userId
     */
    @Override
    public void deleteUserById(Long userId) {
        logger.debug(MessageFormat.format("Entered Method deleteUserById with userId : {0}", userId));
        userRepository.deleteById(userId);
        logger.info(MessageFormat.format("User with userId : {0} deleted successfully", userId));
    }

    /**
     * save the user with information from
     * userDto
     *
     * @param userDto
     * @return User
     */
    @Override
    public User saveUser(UserDto userDto) {
        logger.debug(MessageFormat.format("Entered Method saveUser with user name : {0}", userDto.getFirstName()));
        User user = new User();
        user.setLtiUserId(userDto.getLtiUserId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPrimaryEmail(userDto.getPrimaryEmail());
        user.setRole(userDto.getRole());
        user.setActivated(userDto.isActivated());
        user.setTermsOfServiceAccepted(userDto.isTermsOfServiceAccepted());
        user.setCreatedDate(new Date());
        userRepository.save(user);
        logger.info(MessageFormat.format("User {0} Saved successfully with userId : {1}", userDto.getFirstName(), user.getUserId()));
        return user;
    }

    /**
     * update the user with information from
     * userUpdateDto
     *
     * @param userDto
     * @return
     */
    @Override
    public User updateUser(long userId, UserDto userDto) {
        logger.debug(MessageFormat.format("Entered Method updateUser with userId : {0}", userId));
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found for given Id : " + userId));
        user.setLtiUserId(userDto.getLtiUserId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPrimaryEmail(userDto.getPrimaryEmail());
        user.setRole(userDto.getRole());
        user.setActivated(userDto.isActivated());
        user.setTermsOfServiceAccepted(userDto.isTermsOfServiceAccepted());
        userRepository.save(user);
        logger.info(MessageFormat.format("User with userId {0} Updated Successfully", userId));
        return user;
    }
}
