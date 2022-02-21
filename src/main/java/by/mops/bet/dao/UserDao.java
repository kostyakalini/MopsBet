package by.mops.bet.dao;


import by.mops.bet.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Long> {
    User findByUsername(String username);
    void deleteById(Long id);
}
