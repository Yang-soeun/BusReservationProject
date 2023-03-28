package bus.busReservation.controller;

import bus.busReservation.domain.Timetable;
import bus.busReservation.dto.TimetableDto;
import bus.busReservation.repository.TimeTableRepository;
import bus.busReservation.service.BusService;
import bus.busReservation.service.ReservationService;
import bus.busReservation.service.TimeTableService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;
    private final TimeTableService timeTableService;
    private final TimeTableRepository timeTableRepository;
    private final BusService busService;

    @GetMapping("/reservation")
    public String res(){
        return "reservation/reservationList";
    }

    @GetMapping("/reservation/search")
    public String reservation(@RequestParam(value="keyword") String keyword,Model model){
        List<TimetableDto> timetableDtoList=reservationService.findByBusStopName(keyword);

        model.addAttribute("timetableList",timetableDtoList);

        return "reservation/reservationList";
    }

    //예약-도착지 선택하는 부분
    @GetMapping("/reservation/{id}/{busName}")
    public String destination(@PathVariable("id") Long id, @PathVariable("busName") String busName, Model model ){

        if(timeTableRepository.findById(id).isPresent()) {
            Timetable start = timeTableRepository.findById(id).get();
            List<TimetableDto> destinationDtoList = timeTableService.destinationList(busName, id);

            model.addAttribute("start", start);
            model.addAttribute("timetableList", destinationDtoList);

            Long endId = busService.findEndBusStopId(busName);

            List<Long> NoLists = new ArrayList<>();

            if(timeTableService.NoReservation(id, endId) != id)
            {
                Long newId = timeTableService.NoReservation(id, endId);

                NoLists = timeTableService.NoList(newId, endId);
                model.addAttribute("NoLists", NoLists);
            }
            else{
                model.addAttribute("NoLists", NoLists);
            }
            return "reservation/destination";
        }
        return null;
    }

   /*
   * 예약완료
   */
    @GetMapping("/complete/{start_id}/{end_id}")
    public String completeReservation(@PathVariable("start_id") Long start_id, @PathVariable("end_id") Long end_id, Model model ){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails)principal).getUsername();

        if(timeTableRepository.findById(start_id).isPresent() && timeTableRepository.findById(end_id).isPresent()) {

                Timetable start = timeTableRepository.findById(start_id).get();
                Timetable end = timeTableRepository.findById(end_id).get();

                reservationService.saveReservation(username, start_id, end_id);//reservation table 에 예약 정보 저장
                timeTableService.changeTrue(start_id, end_id);//timetable 의 예약 상태가 출발지~도착지까지 true 로 변경 됨

                model.addAttribute("start", start);
                model.addAttribute("end", end);

                return "reservation/complete";
        }
        return null;
    }
}
