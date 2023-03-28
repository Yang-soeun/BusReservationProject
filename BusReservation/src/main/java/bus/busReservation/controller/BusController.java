package bus.busReservation.controller;

import bus.busReservation.domain.Bus;
import bus.busReservation.domain.Reservation;
import bus.busReservation.domain.ReservationStatus;
import bus.busReservation.dto.BusDto;
import bus.busReservation.dto.ReservationDto;
import bus.busReservation.repository.BusRepository;
import bus.busReservation.repository.ReservationRepository;
import bus.busReservation.service.ReservationService;
import bus.busReservation.service.TimeTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BusController {

    @Autowired
    private ReservationService reservationService;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private TimeTableService timeTableService;
    @Autowired
    private BusRepository busRepository;

    @GetMapping("/bus/selection")//운행할 버스를 선택하는 controller
    public String busSelection(Model model){
        List<Bus> busList = busRepository.findAll();

        List<BusDto> busDtoList  = busList.stream()
                .map(r->new BusDto(r))
                .collect(Collectors.toList());

        model.addAttribute("busDtoList", busDtoList);

        return "user/busSelection";
    }

    /*
    @GetMapping("/bus")
    public String busForm(Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails)principal).getUsername();

        List<ReservationDto> reservationDtoList= reservationService.findByReservation(username);
        model.addAttribute("reservationList",reservationDtoList);

        return "user/busForm";
    }
    */

    @GetMapping("/bus/{id}")
    public String busForm(@PathVariable("id") Long id, Model model){

        List<ReservationDto> reservationDtoList= reservationService.findByReservation(id);
        model.addAttribute("reservationList",reservationDtoList);
        model.addAttribute("bus_id", id);

        return "user/busForm";
    }

    @PostMapping(value = "/bus/{reservationId}/cancel/{bus_id}")//버스앱에서 완료 누르는 부분
    public String cancelReservation(@PathVariable("reservationId") Long reservationId, @PathVariable("bus_id") Long bus_id){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails)principal).getUsername();

        Reservation cancelReservation = reservationRepository.findById(reservationId);//완료처리할 예약 정보

        Long start_id = cancelReservation.getOnInfo().getId();//출발지 정보
        Long end_id = cancelReservation.getOffInfo().getId();//도착지

        cancelReservation.setStatus(ReservationStatus.처리완료);//예약 상태 변경
        timeTableService.changeFalse(start_id, end_id);//timetable도 예약 상태 변경해주기

        return "redirect:/bus/{bus_id}";
    }
}
