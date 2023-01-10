package com.customercvarchive.repository;

import com.customercvarchive.model.Role;
import com.customercvarchive.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Optional;


@Transactional
public interface UserRepository extends JpaRepository<User,Integer> {

    User getById(Integer integer);

    User getByUsername(String username);

    Optional<User> findByUsername(String username);

    @Modifying // select sorgusu yazsaydık böyle bir anatasyon yazmamıza gerek yoktu
    @Query("update User set role=:role where username=:username ")
    void updateUserRole(String username, Role role);
}
