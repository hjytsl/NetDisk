package com.example.net_disk.Repository;

import com.example.net_disk.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByIdAndPassword(Long username, String password);
    User findByUsername(String username);
}
