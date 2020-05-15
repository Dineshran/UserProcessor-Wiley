package wiley.lms.userprocessor.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import wiley.lms.userprocessor.TestUtil;
import wiley.lms.userprocessor.controller.error.UserExceptionController;
import wiley.lms.userprocessor.model.dto.UserDto;
import wiley.lms.userprocessor.model.entity.Role;
import wiley.lms.userprocessor.model.entity.User;
import wiley.lms.userprocessor.model.exception.ErrorDetails;
import wiley.lms.userprocessor.model.exception.NoUsersException;
import wiley.lms.userprocessor.model.exception.UserNotFoundException;
import wiley.lms.userprocessor.service.UserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(SpringExtension.class)
public class UserControllerTest {

    @Mock
    UserService userServiceMock;

    @InjectMocks
    UserController controller;

    MockMvc mockMvc;

    private ArrayList<User> list = new ArrayList<>();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).setControllerAdvice(new UserExceptionController()).build();
    }

    /**
     * test method for testConnection
     * success scenario
     *
     * @throws Exception
     */
    @Test
    void testConnection() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/test"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Connection Success"));
    }

    /**
     * test method for getAllUsers
     * success scenario
     *
     * @throws Exception
     */
    @Test
    void getAllUsers() throws Exception {

        List<User> expectedUser = UserBuilder.buildUserList();
        Mockito.when(userServiceMock.getAllUsers()).thenReturn(expectedUser);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        List<User> actualResult = Arrays.asList(TestUtil.mapFromJson(mvcResult.getResponse().getContentAsString(), User[].class));
        for (int i = 0; i < 10; i++) {
            userAssert(expectedUser.get(i), actualResult.get(i));
        }
    }

    /**
     * test method for getAllUsersNoUsers
     * but no users found in the system
     *
     * @throws Exception
     */
    @Test
    void getAllUsersNoUsers() throws Exception {

        NoUsersException noUsersException = new NoUsersException("no users found in system");
        Mockito.when(userServiceMock.getAllUsers()).thenThrow(noUsersException);

        ErrorDetails expectedErrorDetails = new ErrorDetails();
        expectedErrorDetails.setMessage(noUsersException.getMessage());
        expectedErrorDetails.setDetails("uri=/users");

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();

        ErrorDetails actualErrorDetails = TestUtil.mapFromJson(mvcResult.getResponse().getContentAsString(), ErrorDetails.class);

        exceptionAssert(expectedErrorDetails, actualErrorDetails);
    }

    /**
     * test method for deleteUser
     * success scenario
     *
     * @throws Exception
     */
    @Test
    void addUser() throws Exception {

        UserDto userDto = UserBuilder.getUserDto();
        User expectedUser = UserBuilder.getUserFromDto(userDto);
        Mockito.when(userServiceMock.saveUser(any(UserDto.class))).thenReturn(expectedUser);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.mapToJson(userDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        User actualUser = TestUtil.mapFromJson(mvcResult.getResponse().getContentAsString(), User.class);
        userAssert(actualUser, expectedUser);
    }

    /**
     * test method for getUser
     * success scenario
     *
     * @throws Exception
     */
    @Test
    void getUser() throws Exception {

        User expectedUser = UserBuilder.build();
        Mockito.when(userServiceMock.getUserById(anyLong())).thenReturn(expectedUser);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/users/1"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        User actualUser = TestUtil.mapFromJson(mvcResult.getResponse().getContentAsString(), User.class);
        userAssert(actualUser, expectedUser);
    }

    /**
     * test method for getUser
     * success scenario
     *
     * @throws Exception
     */
    @Test
    void getUserNotFound() throws Exception {

        UserNotFoundException userNotFoundException = new UserNotFoundException("User Not Found for given Id: 1");
        Mockito.when(userServiceMock.getUserById(anyLong())).thenThrow(userNotFoundException);

        ErrorDetails expectedErrorDetails = new ErrorDetails();
        expectedErrorDetails.setMessage(userNotFoundException.getMessage());
        expectedErrorDetails.setDetails("uri=/users/1");

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/users/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();

        ErrorDetails actualErrorDetails = TestUtil.mapFromJson(mvcResult.getResponse().getContentAsString(), ErrorDetails.class);

        exceptionAssert(expectedErrorDetails, actualErrorDetails);
    }

    /**
     * test method for updateUser
     * success scenario
     *
     * @throws Exception
     */
    @Test
    void updateUser() throws Exception {

        UserDto userDto = UserBuilder.getUserDto();
        User expectedUser = UserBuilder.getUserFromDto(userDto);

        Mockito.when(userServiceMock.updateUser(anyLong(), any(UserDto.class))).thenReturn(expectedUser);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.mapToJson(userDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        User actualUser = TestUtil.mapFromJson(mvcResult.getResponse().getContentAsString(), User.class);
        userAssert(actualUser, expectedUser);
    }

    /**
     * test method for updateUser
     * but user not found in system
     *
     * @throws Exception
     */
    @Test
    void updateUserNotFound() throws Exception {

        UserDto userDto = UserBuilder.getUserDto();
        UserNotFoundException userNotFoundException = new UserNotFoundException("User Not Found for given Id: 1");
        Mockito.when(userServiceMock.updateUser(anyLong(), any(UserDto.class))).thenThrow(userNotFoundException);

        ErrorDetails expectedErrorDetails = new ErrorDetails();
        expectedErrorDetails.setMessage(userNotFoundException.getMessage());
        expectedErrorDetails.setDetails("uri=/users/1");

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.mapToJson(userDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();

        ErrorDetails actualErrorDetails = TestUtil.mapFromJson(mvcResult.getResponse().getContentAsString(), ErrorDetails.class);

        exceptionAssert(expectedErrorDetails, actualErrorDetails);
    }

    /**
     * test method for deleteUser
     * success scenario
     *
     * @throws Exception
     */
    @Test
    void deleteUser() throws Exception {
        Mockito.doNothing().when(userServiceMock).deleteUserById(anyLong());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("User Deleted Successfully : 1"))
                .andReturn();
    }

    /**
     * test method for deleteUser
     * but user not found in system
     *
     * @throws Exception
     */
    @Test
    void deleteUserNotFound() throws Exception {
        UserNotFoundException userNotFoundException = new UserNotFoundException("User Not Found for given Id: 1");
        Mockito.doThrow(userNotFoundException).when(userServiceMock).deleteUserById(anyLong());

        ErrorDetails expectedErrorDetails = new ErrorDetails();
        expectedErrorDetails.setMessage(userNotFoundException.getMessage());
        expectedErrorDetails.setDetails("uri=/users/1");

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();

        ErrorDetails actualErrorDetails = TestUtil.mapFromJson(mvcResult.getResponse().getContentAsString(), ErrorDetails.class);

        exceptionAssert(expectedErrorDetails, actualErrorDetails);
    }

    /**
     * test method for getAllActiveUsers
     * success scenario
     *
     * @throws Exception
     */
    @Test
    void getAllActiveUsers() throws Exception {
        List<User> expectedUser = UserBuilder.getActiveUser(true);
        Mockito.when(userServiceMock.getActiveUsers(Mockito.anyBoolean())).thenReturn(expectedUser);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/users/active/true"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        List<User> actualResult = Arrays.asList(TestUtil.mapFromJson(mvcResult.getResponse().getContentAsString(), User[].class));
        for (int i = 0; i < expectedUser.size(); i++) {
            userAssert(expectedUser.get(i), actualResult.get(i));
            Assertions.assertTrue(actualResult.get(i).isActivated());
        }
    }

    /**
     * test method for getAllActiveUsers
     * but no user found in the system
     *
     * @throws Exception
     */
    @Test
    void getAllActiveUsersNotFound() throws Exception {

        NoUsersException noUsersException = new NoUsersException("no users found in system");

        ErrorDetails expectedErrorDetails = new ErrorDetails();
        expectedErrorDetails.setMessage(noUsersException.getMessage());
        expectedErrorDetails.setDetails("uri=/users/active/true");

        Mockito.when(userServiceMock.getActiveUsers(Mockito.anyBoolean())).thenThrow(noUsersException);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/users/active/true"))
                .andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();

        ErrorDetails actualErrorDetails = TestUtil.mapFromJson(mvcResult.getResponse().getContentAsString(), ErrorDetails.class);
        exceptionAssert(expectedErrorDetails, actualErrorDetails);
    }

    /**
     * test method for getAllUserByRole
     * success scenario
     *
     * @throws Exception
     */
    @Test
    void getAllUsersByRole() throws Exception {
        List<User> expectedUser = UserBuilder.getRoleUser(Role.ADMIN);
        Mockito.when(userServiceMock.getUsersByRole(any(Role.class))).thenReturn(expectedUser);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/users/role/ADMIN"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        List<User> actualResult = Arrays.asList(TestUtil.mapFromJson(mvcResult.getResponse().getContentAsString(), User[].class));
        for (int i = 0; i < expectedUser.size(); i++) {
            userAssert(expectedUser.get(i), actualResult.get(i));
            Assertions.assertEquals(actualResult.get(i).getRole(), Role.ADMIN);
        }
    }

    /**
     * test method for getAllUserByRole
     * but no user found in the system
     *
     * @throws Exception
     */
    @Test
    void getAllUsersByRoleNotFound() throws Exception {
        NoUsersException noUsersException = new NoUsersException("no users found in system");

        ErrorDetails expectedErrorDetails = new ErrorDetails();
        expectedErrorDetails.setMessage(noUsersException.getMessage());
        expectedErrorDetails.setDetails("uri=/users/role/ADMIN");

        Mockito.when(userServiceMock.getUsersByRole(any(Role.class))).thenThrow(noUsersException);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/users/role/ADMIN"))
                .andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();

        ErrorDetails actualErrorDetails = TestUtil.mapFromJson(mvcResult.getResponse().getContentAsString(), ErrorDetails.class);
        exceptionAssert(expectedErrorDetails, actualErrorDetails);
    }

    /**
     * generic assret method for users
     *
     * @param actualUser
     * @param expectedUser
     */
    private void userAssert(User actualUser, User expectedUser) {
        Assertions.assertEquals(actualUser.getFirstName(), expectedUser.getFirstName());
        Assertions.assertEquals(actualUser.getLastName(), expectedUser.getLastName());
        Assertions.assertEquals(actualUser.getUserId(), expectedUser.getUserId());
        Assertions.assertEquals(actualUser.getLtiUserId(), expectedUser.getLtiUserId());
        Assertions.assertEquals(actualUser.getPrimaryEmail(), expectedUser.getPrimaryEmail());
        Assertions.assertEquals(actualUser.getRole(), expectedUser.getRole());
        Assertions.assertEquals(actualUser.isActivated(), expectedUser.isActivated());
        Assertions.assertEquals(actualUser.isTermsOfServiceAccepted(), expectedUser.isActivated());
    }

    /**
     * generic assert method for exceptions
     *
     * @param expectedErrorDetails
     * @param actualErrorDetails
     */
    private void exceptionAssert(ErrorDetails expectedErrorDetails, ErrorDetails actualErrorDetails) {
        Assertions.assertEquals(expectedErrorDetails.getDetails(), actualErrorDetails.getDetails());
        Assertions.assertEquals(expectedErrorDetails.getMessage(), actualErrorDetails.getMessage());
    }
}