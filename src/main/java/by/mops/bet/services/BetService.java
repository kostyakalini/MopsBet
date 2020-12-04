package by.mops.bet.services;

import by.mops.bet.DTO.BetDto;
import by.mops.bet.DTO.BetMapper;
import by.mops.bet.DTO.EventDto;
import by.mops.bet.DTO.EventMapper;
import by.mops.bet.dao.BetDao;
import by.mops.bet.model.Bet;
import by.mops.bet.model.Event;
import org.mapstruct.BeanMapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BetService {
    @Autowired
    private BetDao betDao;

    public List<Bet> listAll() {
        return betDao.findAll();
    }

    public List<Bet> listAllByEvent_id(Long event_id) {
        return betDao.getBetsByEvent_id(event_id);
    }

    public void save(Bet bet) {
        betDao.save(bet);
    }

    public Bet get(Long id) {
        return betDao.findById(id).get();
    }

    public void delete(Long id) {
        betDao.deleteById(id);
    }

    public Map<Long, Bet> mapAll() {
        Map<Long, Bet> bets = new HashMap<>();
        for (Bet bet : betDao.findAll()) {
            bets.put(bet.getId(), bet);
        }
        return bets;
    }

    private BetMapper mapper = new BetMapper() {
        @Override
        @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
        public void updateBetFromDto(BetDto dto, Bet entity){
            entity.setId(dto.getId());
            entity.setEvent_id(dto.getEvent_id());
            entity.setCoefficient(dto.getCoefficient());
            entity.setType_of_bet_id(dto.getType_of_bet_id());
            entity.setValue(dto.getValue());
        }
    };

    public void updateBet(BetDto dto) {
         Bet myBet = betDao.findById(dto.getId()).orElse(new Bet());

        mapper.updateBetFromDto(dto, myBet);
        betDao.save(myBet);
    }

}
