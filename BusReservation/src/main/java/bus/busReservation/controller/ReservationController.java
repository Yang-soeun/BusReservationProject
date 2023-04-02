package bus.busReservation.controller;

import bus.busReservation.domain.Timetable;
import bus.busReservation.dto.TimetableDto;
import bus.busReservation.repository.TimeTableRepository;
import bus.busReservation.service.BusService;
import bus.busReservation.service.ReservationService;
import bus.busReservation.service.TimeTableService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Log4j2
public class ReservationController {
    private final ReservationService reservationService;
    private final TimeTableService timeTableService;
    private final TimeTableRepository timeTableRepository;
    private final BusService busService;

    @RequestMapping("/reservation")
    public String res(){
        return "reservation/reservationList";
    }

    @RequestMapping("/reservation/search")
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

            List<Long> NoLists = new ArrayList<>();

            TimetableDto last = destinationDtoList.get(destinationDtoList.size() - 1);//timetable의 마지막 timetable id 반환
            Long endId = last.getId();

            Long newId = timeTableService.NoReservation(id, endId);

            if(newId==id)//중간에 예약자가 없는 경우
            {
                model.addAttribute("NoLists", NoLists);

            }
            else{//중간에 예약자가 있는 경우
                NoLists = timeTableService.NoList(newId, endId);
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

            Long bus_id = start.getBus().getId();

            reservationService.saveReservation(username, bus_id, start_id, end_id);//reservation table 에 예약 정보 저장
            timeTableService.changeTrue(start_id, end_id);//timetable 의 예약 상태가 출발지~도착지까지 true 로 변경 됨

            model.addAttribute("start", start);
            model.addAttribute("end", end);

            return "reservation/complete";
        }
        return null;
    }
}
