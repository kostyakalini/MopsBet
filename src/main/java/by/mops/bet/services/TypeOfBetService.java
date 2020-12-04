package by.mops.bet.services;

import by.mops.bet.dao.EventDao;
import by.mops.bet.dao.TypeOfBetDao;
import by.mops.bet.model.Event;
import by.mops.bet.model.TypeOfBet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Transactional
public class TypeOfBetService {
    @Autowired
    private TypeOfBetDao repo;

    public Map<Long, String> listAll() {
        Map<Long, String> types = new HashMap<>();
        for (TypeOfBet typeOfBet : repo.findAll()) {
            types.put(typeOfBet.getId(), typeOfBet.getName());
        }
        return types;
    }

    public void save(TypeOfBet typeOfBet) {
        repo.save(typeOfBet);
    }

    public TypeOfBet get(Long id) {
        return repo.findById(id).get();
    }

    public void delete(long id) {
        repo.deleteById(id);
    }
}
