package by.mops.bet.services;

import by.mops.bet.DTO.EventDto;
import by.mops.bet.DTO.EventMapper;
import by.mops.bet.dao.EventDao;
import by.mops.bet.model.Event;
import by.mops.bet.model.TypeOfBet;
import org.mapstruct.BeanMapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class EventService{

    @Autowired
    private EventDao repo;

    private EventMapper mapper = new EventMapper() {
        @Override
        @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
        public void updateEventFromDto(EventDto dto, Event entity){
            entity.setId(dto.getId());
            entity.setResult(dto.getResult());
            entity.setStatus(dto.getStatus());
            entity.setTeam1(dto.getTeam1());
            entity.setTeam2(dto.getTeam2());
            entity.setTotal(dto.getTotal());
        }
    };

    public void updateEvent(EventDto dto) {
        Event myEvent = repo.findById(dto.getId()).orElse(new Event());

        mapper.updateEventFromDto(dto, myEvent);
        repo.save(myEvent);
    }

    public List<Event> listAll() {
        return repo.findAll();
    }

    public Map<Long, Event> mapAll() {
        Map<Long, Event> events = new HashMap<>();
        for (Event event : repo.findAll()) {
            events.put(event.getId(), event);
        }
        return events;
    }



    public void save(Event event) {
        repo.save(event);
    }

    public Event get(long id) {
        return repo.findById(id).get();
    }

    public void delete(long id) {
        repo.deleteById(id);
    }
}
