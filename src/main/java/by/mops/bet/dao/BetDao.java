package by.mops.bet.dao;

import by.mops.bet.model.Bet;
import by.mops.bet.model.UserBet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface BetDao extends JpaRepository<Bet, Long> {
    @Query("SELECT b FROM Bet b WHERE b.event_id = :event_id")
    public List<Bet> getBetsByEvent_id(@Param("event_id") Long event_id);
}
