package com.manneung.careerup.domain.user.repository;

import com.manneung.careerup.domain.user.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @EntityGraph(attributePaths = "authorities")
    Optional<User> findOneWithAuthoritiesByUsername(String username);
//    boolean existsUserByEmail(String email);
//    Optional<User> findByEmail(String email);


    @EntityGraph(attributePaths = "maps")
    Optional<User> findOneWithMapsByUsername(String username);


    User findUserByNickname(String nickname);
}
