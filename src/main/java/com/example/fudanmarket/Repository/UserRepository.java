package com.example.fudanmarket.Repository;
import com.example.fudanmarket.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);

    User findByUsernameAndPassword(String username, String password);

    boolean existsByUsernameAndPassword(String username, String password);

    boolean existsByUsername(String username);

    Page<User> findByUsernameContaining(String username, Pageable pageable);

    User findById(int id);

    boolean existsByEmail(String email);
}
