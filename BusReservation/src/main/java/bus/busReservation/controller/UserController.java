package bus.busReservation.controller;

import bus.busReservation.domain.Reservation;
import bus.busReservation.domain.ReservationStatus;
import bus.busReservation.domain.User;
import bus.busReservation.dto.ReservationDto;
import bus.busReservation.repository.ReservationRepository;
import bus.busReservation.service.ReservationService;
import bus.busReservation.service.TimeTableService;
import bus.busReservation.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private TimeTableService timeTableService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder; //비밀번호 암호화

    @GetMapping("/bus")
    public String busForm(Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails)principal).getUsername();

        List<ReservationDto> reservationDtoList= reservationService.findByReservation(username);
        model.addAttribute("reservationList",reservationDtoList);

        return "user/busForm";
    }

    @PostMapping(value = "/bus/{reservationId}/cancel")//버스앱에서 완료 누르는 부분
    public String cancelReservation(@PathVariable("reservationId") Long reservationId){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails)principal).getUsername();

        Reservation cancelReservation = reservationRepository.findById(reservationId);//완료처리할 예약 정보

        Long start_id = cancelReservation.getOnInfo().getId();//출발지 정보
        Long end_id = cancelReservation.getOffInfo().getId();//도착지

        cancelReservation.setStatus(ReservationStatus.처리완료);//예약 상태 변경
        timeTableService.changeFalse(start_id, end_id);//timetable도 예약 상태 변경해주기

        return "redirect:/bus";
    }

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
        return "bus/userJoinForm";
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
