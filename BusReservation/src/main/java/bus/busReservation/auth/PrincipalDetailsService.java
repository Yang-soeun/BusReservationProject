package bus.busReservation.auth;

import bus.busReservation.domain.User;
import bus.busReservation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {

        User userEntity=userService.findById(id);

        if(userEntity!=null){//있는 경우
            return new PrincipalDetails(userEntity);//리턴된 값이 Authentication 내부에 들어가고
            //세션에 Authentication이 들어감
            //로그인 완료
        }

        return null;//없는 경우
    }
}
