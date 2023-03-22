package bus.busReservation.service;

import bus.busReservation.domain.User;
import bus.busReservation.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService{

    private final UserRepo userRepo;

    @Transactional()
    public User findById(String id) {
        return userRepo.findById(id);
    }

    public User save(User user){
        return userRepo.save(user);
    }

    public User findByIdContains(String id){
        return userRepo.findByIdContains(id);
    }
}
