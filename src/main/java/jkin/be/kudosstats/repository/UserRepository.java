package jkin.be.kudosstats.repository;

import jkin.be.kudosstats.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    User findByNickname(String nickname);
}
