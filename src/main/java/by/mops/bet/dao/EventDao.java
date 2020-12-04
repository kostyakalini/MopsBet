package by.mops.bet.dao;

import by.mops.bet.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventDao extends JpaRepository<Event, Long> {

}
