package by.mops.bet.services;


import by.mops.bet.DTO.UserDto;
import by.mops.bet.DTO.UserMapper;
import by.mops.bet.dao.UserDao;
import by.mops.bet.model.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void saveNewUser(User user) {
        //user.setConfirmPassword(user.getPassword());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        userDao.save(user);
    }

    public void save(User user) {
        userDao.save(user);
    }

    public List<User> listAll() {
        return userDao.findAll();
    }

    public Map<Long, User> mapAll() {
        Map<Long, User> users = new HashMap<>();
        for (User user : userDao.findAll()) {
            users.put(user.getId(), user);
        }
        return users;
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    public User get(Long id) {
        return userDao.findById(id).get();
    }


    private UserMapper mapper = new UserMapper() {
        @Override
        @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
        public void updateUserFromDto(UserDto dto, User entity){
            entity.setId(dto.getId());
            entity.setBalance(dto.getBalance());
            entity.setEnabled(dto.getEnabled());
            entity.setFirstName(dto.getFirstName());
            entity.setLastName(dto.getLastName());
            entity.setPassword(dto.getPassword());
            entity.setUsername(dto.getUsername());
            entity.setRole(dto.getRole());
        }
    };


    public void updateUser(UserDto dto) {
        User myUser = userDao.findById(dto.getId()).orElse(new User());

        mapper.updateUserFromDto(dto, myUser);
        userDao.save(myUser);
    }

}
