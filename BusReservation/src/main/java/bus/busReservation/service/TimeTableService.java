package bus.busReservation.service;

import bus.busReservation.domain.Bus;
import bus.busReservation.domain.Timetable;
import bus.busReservation.dto.TimetableDto;
import bus.busReservation.repository.BusRepository;
import bus.busReservation.repository.ReservationRepository;
import bus.busReservation.repository.TimeTableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TimeTableService {

    private final TimeTableRepository timeTableRepository;
    private final BusRepository busRepository;
    private final ReservationRepository reservationRepository;
    private final BusService busService;

    //버스 이름 별
    public List<Timetable> findTimetable(String name){
        return timeTableRepository.findByBusName(name);
    }
    public List<Timetable> findAll(){
        return timeTableRepository.findAll();
    }

    /**
     * 도착지 리스트 반환해주는 함수
     */
    public List<TimetableDto> destinationList(String busName, Long id){//id 이후부터 해당버스의 종점까지 출력해야함

        List<Timetable> list = new ArrayList<>();
        Long start_id = busService.findStartBusId(busName);//버스의 출발지
        Long timetable_id = id;//timetable 아이디

        if(timeTableRepository.findById(id).isPresent()) {
            Timetable destination = timeTableRepository.findById(id).get();//아이디에 해당하는 timetable
            Long cu_bus_id = destination.getBus().getId();//현재 버스 id
            Long busStop_id = destination.getBusStop().getId(); //정류장 id


            while (timetable_id < 262L) {//262이후로 타임 테이블이 없음
                timetable_id++;

                destination = timeTableRepository.findById(timetable_id).get();
                busStop_id = destination.getBusStop().getId();
                Long next_bus_id = destination.getBus().getId();

                if ((start_id == busStop_id) || (cu_bus_id != next_bus_id))//다른 버스이거나 종점이후의 정류장인경우
                    break;

                list.add(destination);
            }

            List<TimetableDto> timetableDtoList = list.stream()
                    .map(t -> new TimetableDto(t))
                    .collect(Collectors.toList());

            return timetableDtoList;
        }
        return null;
    }

//    public Long timetableEndId(Long id, String busName){
//        Long endBusStopId = busService.findEndBusStopId(busName);
//
//
//    }


    //예약된 timetable 의 status 를 true 로 변경하기
    public void changeTrue(Long start, Long end){
        List<Timetable> timetables = timeTableRepository.start_end_id(start, end);
        for (Timetable timetable : timetables) {
            timetable.trueStatus();
        }
    }

    //예약이 완료된 timetable 의 status 를 false 로 변경하기
    public void changeFalse(Long start, Long end){
        List<Timetable> timetables = timeTableRepository.start_end_id(start, end);
        for (Timetable timetable : timetables) {
            timetable.falseStatus();
        }
    }

    public Long NoReservation(Long start, Long end){//사이에 예약자가 있는지 확인
        List<Timetable> timetables = timeTableRepository.start_end_id(start, end);

        for (Timetable timetable : timetables) {
            if(timetable.isStatus() == true)//예약자가 있는 경우
                return timetable.getId();
        }
        return start;
    }

    public List<Long> NoList(Long start, Long end)
    {
        List<Long> lists = new ArrayList<>();

        List<Timetable> timetables = timeTableRepository.start_end_id(start, end);
        for (Timetable timetable : timetables) {
            lists.add(timetable.getId());
        }

        return lists;
    }

}
