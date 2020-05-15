package wiley.lms.userprocessor.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    private String ltiUserId;

    private String firstName;

    private String lastName;

    private String primaryEmail;

    private Role role;

    private boolean activated;

    private boolean termsOfServiceAccepted;

    private Date createdDate;

    @Column(nullable = true)
    private Date updatedDate;

}
