package com.manneung.careerup.domain.user.repository;

import com.manneung.careerup.domain.user.model.User;
import org.hibernate.sql.ordering.antlr.ColumnMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {


    Optional<User> findByEmail(String email);

    User findUserByNickname(String nickname);
}
