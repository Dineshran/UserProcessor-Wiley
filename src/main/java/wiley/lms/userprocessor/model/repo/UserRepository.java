package wiley.lms.userprocessor.model.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wiley.lms.userprocessor.model.entity.Role;
import wiley.lms.userprocessor.model.entity.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * @param role
     * @return List of Users with Role role
     */
    List<User> findByRole(Role role);

    /**
     * @param active
     * @return List of Users with active status active
     */
    List<User> findByActivated(boolean active);

}
