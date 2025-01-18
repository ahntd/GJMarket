package kr.ac.hansung.cse.gjmarekt.repository;

import kr.ac.hansung.cse.gjmarekt.entity.GJUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<GJUser, Integer> {
    Optional<GJUser> findByEmail(String email);

    Boolean existsByEmail(String email);
}
