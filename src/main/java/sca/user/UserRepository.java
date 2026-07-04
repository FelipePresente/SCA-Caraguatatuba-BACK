package sca.user;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByUsername(String username);
    User findByUsername(String username);
    @Query("SELECT u FROM User u WHERE u.role.name = :name")
    List<User> findByRoleName(@Param("name") String name);
}
