package wiley.lms.userprocessor.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wiley.lms.userprocessor.model.entity.Role;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    private String ltiUserId;

    private String firstName;

    private String lastName;

    private String primaryEmail;

    private Role role;

    private boolean isActivated;

    private boolean isTermsOfServiceAccepted;
}
