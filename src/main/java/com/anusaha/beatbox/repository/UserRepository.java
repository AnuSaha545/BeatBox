package com.anusaha.beatbox.repository;

import com.anusaha.beatbox.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}