package bus.busReservation.service;

import bus.busReservation.domain.User;
import bus.busReservation.repository.UserRepo;
import bus.busReservation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService{

    private final UserRepo userRepo;
    private final UserRepository userRepository;

    @Transactional()
    public User findById(String id) {
        return userRepository.findById(id);
    }

    public User save(User user){
        return userRepo.save(user);
    }

}
