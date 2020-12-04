package by.mops.bet.services;


import by.mops.bet.DTO.BetDto;
import by.mops.bet.DTO.BetMapper;
import by.mops.bet.DTO.UserBetDto;
import by.mops.bet.DTO.UserBetMapper;
import by.mops.bet.dao.BetDao;
import by.mops.bet.dao.UserBetDao;
import by.mops.bet.model.Bet;
import by.mops.bet.model.UserBet;
import org.mapstruct.BeanMapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserBetService {
    @Autowired
    private UserBetDao userBetDao;

    public List<UserBet> listAll() {
        return userBetDao.findAll();
    }

    public List<UserBet> listUserBets(Long id) {
        return userBetDao.getUserBetsByUser_id(id);
    }

    public void saveUserBetStatus(Long userBetId, String status){
        userBetDao.saveUserBetStatus(userBetId, status);
    }

    public void save(UserBet userBet) {
        userBetDao.save(userBet);
    }

    public UserBet get(Long id) {
        return userBetDao.findById(id).get();
    }

    public void delete(Long id) {
        userBetDao.deleteById(id);
    }

    public Map<Long, UserBet> mapAll() {
        Map<Long, UserBet> userBets = new HashMap<>();
        for (UserBet userBet : userBetDao.findAll()) {
            userBets.put(userBet.getId(), userBet);
        }
        return userBets;
    }

    private UserBetMapper mapper = new UserBetMapper() {
        @Override
        @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
        public void updateUserBetFromDto(UserBetDto dto, UserBet entity){
            entity.setId(dto.getId());
            entity.setBet_id(dto.getBet_id());
            entity.setStatus(dto.getStatus());
            entity.setUser_id(dto.getUser_id());
            entity.setValue(dto.getValue());
        }
    };

    public void updateUserBet(UserBetDto dto) {
        UserBet myBet = userBetDao.findById(dto.getId()).orElse(new UserBet());

        mapper.updateUserBetFromDto(dto, myBet);
        userBetDao.save(myBet);
    }
}
