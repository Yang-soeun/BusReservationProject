package bus.busReservation.controller;
import bus.busReservation.domain.Bus;
import bus.busReservation.domain.Timetable;
import bus.busReservation.service.BusService;
import bus.busReservation.service.TimeTableService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Log4j2
public class TimetableController {
    private final TimeTableService timeTableService;
    private final BusService busService;

    @GetMapping
    public String timetableList(Model model){
        List<Bus> busList = busService.findAllName();//버스 이름 찾기
        model.addAttribute("busList", busList);

        return "timetable/timeList";
    }

    @GetMapping("/{busName}")//버스번호별로 시간표 조회하는 controller
    public String oneTimetables(@PathVariable("busName") String busName,Model model){

        Long c = busService.findStartBusId(busName);//버스 이름으로 출발 정류장 id 값 가져오기

        List<Bus> busList = busService.findAllName();
        List<Timetable> timetables = timeTableService.findTimetable(busName);//시간표 찾기

        model.addAttribute("c", c);//출발 정류장
        model.addAttribute("busName", busName);//버스 정류장
        model.addAttribute("timetables", timetables);//버스 시간표
        model.addAttribute("busList", busList);

        return "timetable/oneTimeList";
    }
}
