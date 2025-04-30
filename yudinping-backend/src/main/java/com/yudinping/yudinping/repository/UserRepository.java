package com.yudinping.yudinping.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yudinping.yudinping.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public List<User> findAllList();
}   
