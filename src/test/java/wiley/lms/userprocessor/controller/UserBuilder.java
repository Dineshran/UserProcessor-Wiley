package wiley.lms.userprocessor.controller;

import wiley.lms.userprocessor.model.dto.UserDto;
import wiley.lms.userprocessor.model.entity.Role;
import wiley.lms.userprocessor.model.entity.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class UserBuilder {

    public static User build() {
        User user = new User();
        user.setUserId((long) 1);
        user.setRole(Role.ADMIN);
        user.setActivated(true);
        user.setFirstName("Name" + 1);
        user.setLastName("LName" + 1);
        user.setPrimaryEmail("email" + 1 + "@gamil.com");
        user.setLtiUserId("LTIUSID" + 1);
        user.setTermsOfServiceAccepted(true);
        user.setCreatedDate(new Date());
        user.setUpdatedDate(new Date());
        return user;
    }

    public static List<User> buildUserList(){
        Random random = new Random();
        List<User> users = new ArrayList<>();
        for(int i=0; i<10 ; i++){
            User user = new User();
            user.setUserId((long)i);
            user.setRole((Role) Role.values()[random.nextInt(Role.values().length)]);
            user.setActivated(i%2==0);
            user.setFirstName("Name"+i);
            user.setLastName("LName"+i);
            user.setPrimaryEmail("email"+i+"@gamil.com");
            user.setLtiUserId("LTIUSID"+i);
            user.setTermsOfServiceAccepted(i%2==0);
            user.setCreatedDate(new Date());
            user.setUpdatedDate(new Date());
            users.add(user);
        }
        return users;
    }

    public static UserDto getUserDto(){
        UserDto userDto = new UserDto();
        userDto.setLtiUserId("LTI1");
        userDto.setFirstName("Test");
        userDto.setLastName("User");
        userDto.setPrimaryEmail("Dinesh@gmail.com");
        userDto.setActivated(true);
        userDto.setTermsOfServiceAccepted(true);
        userDto.setRole(Role.AUTHOR);
        return  userDto;
    }

    public static User getUserFromDto(UserDto userDto){
        User user = new User();
        user.setUserId(1L);
        user.setLtiUserId(userDto.getLtiUserId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPrimaryEmail(userDto.getPrimaryEmail());
        user.setRole(userDto.getRole());
        user.setActivated(userDto.isActivated());
        user.setTermsOfServiceAccepted(userDto.isTermsOfServiceAccepted());
        user.setCreatedDate(new Date());
        user.setUpdatedDate(new Date());
        return user;
    }

    public static List<User> getActiveUser(boolean bool){
        List<User> users = buildUserList().stream()
                .filter(p -> p.isActivated() == bool).collect(Collectors.toList());
        return users;
    }

    public static List<User> getRoleUser(Role role){
        List<User> users = buildUserList().stream()
                .filter(p -> p.getRole() == role).collect(Collectors.toList());
        return users;
    }
}
