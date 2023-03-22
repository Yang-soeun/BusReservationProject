package bus.busReservation.auth;

import bus.busReservation.domain.User;
import bus.busReservation.repository.UserRepo;
import bus.busReservation.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//http://localhost:8080/login => 동작x
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {

        User userEntity=userService.findById(id);
        if(userEntity!=null){
            return new PrincipalDetails(userEntity);
        }
        return null;
    }
}
