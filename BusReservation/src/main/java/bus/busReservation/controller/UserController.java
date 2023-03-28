package bus.busReservation.controller;

import bus.busReservation.domain.User;
import bus.busReservation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder; //비밀번호 암호화


    @GetMapping("/loginForm")
    public String loginForm(){
        return "user/loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm(){
        return "user/joinForm";
    }

    @GetMapping("/user/joinForm")
    public String userJoinForm(){
        return "user/userJoinForm";
    }

    @GetMapping("/bus/joinForm")
    public String busJoinForm(){
        return "user/busJoinForm";
    }

    //사용자(장애인) 전용 회원가입
    @PostMapping("user/join")
    public String userJoin(User user){

        user.setRole("USER");

        String passwrod = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(passwrod);

        userService.save(user);

        return "redirect:/loginForm";
    }

    //버스 전용 회원가입
    @PostMapping("bus/join")
    public String busJoin(User user){

        user.setRole("BUS");

        String passwrod = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(passwrod);

        userService.save(user);

        return "redirect:/loginForm";
    }
}
