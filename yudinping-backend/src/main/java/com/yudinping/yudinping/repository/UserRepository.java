package com.yudinping.yudinping.repository;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yudinping.yudinping.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    public UserEntity findByUserid(String userid);

    public UserEntity findByUseridAndPassword(String userid, String password);

    List<UserEntity> findByUseridIn(Set<String> userIds);
}   
