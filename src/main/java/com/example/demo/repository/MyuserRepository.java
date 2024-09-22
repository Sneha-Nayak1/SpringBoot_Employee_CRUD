package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.MyUser;

public interface MyuserRepository extends JpaRepository<MyUser,Long>{
Optional<MyUser> findByUserName(String username);
}
