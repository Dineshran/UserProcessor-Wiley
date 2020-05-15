package wiley.lms.userprocessor.service;

import wiley.lms.userprocessor.model.dto.UserDto;
import wiley.lms.userprocessor.model.entity.Role;
import wiley.lms.userprocessor.model.entity.User;

import java.util.List;

public interface UserService {

    /**
     * no parameter needed
     *
     * @return List of Users
     */
    public List<User> getAllUsers();

    /**
     * fetch the user by userid
     *
     * @param userId
     * @return User
     */
    public User getUserById(Long userId);

    /**
     * return all the users of the specific consumerKey
     *
     * @param role
     * @return List of Users
     */
    public List<User> getUsersByRole(Role role);

    /**
     * return all the users of the specific consumerKey
     *
     * @param active
     * @return List of Users
     */
    public List<User> getActiveUsers(boolean active);

    /**
     * delete User by Id
     *
     * @param userId
     */
    public void deleteUserById(Long userId);

    /**
     * save the user with information from
     * userDto
     *
     * @param userDto
     * @return User
     */
    public User saveUser(UserDto userDto);

    /**
     * update the user with information from
     * userDto
     *
     * @param userDto
     * @param id
     * @return updated User
     */
    public User updateUser(long id, UserDto userDto);
}
