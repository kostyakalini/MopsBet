package by.mops.bet.dao;

import by.mops.bet.model.User;
import by.mops.bet.model.UserBet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
public interface UserBetDao extends JpaRepository<UserBet, Long> {
    @Query("SELECT u FROM UserBet u WHERE u.user_id = :user_id")
    public List<UserBet> getUserBetsByUser_id(@Param("user_id") Long user_id);

    @Modifying
    @Query("UPDATE UserBet SET status = :status WHERE id = :userBetId")
    public void saveUserBetStatus(@Param("userBetId") Long userBetId, @Param("status")String status);
}
