package bus.busReservation.service;

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

    //도착지 리스트
    public List<TimetableDto> destinationList(String busName, Long id){

        List<Timetable> list = new ArrayList<>();
        Long c = busService.findCnt(busName);//버스의 출발지
        Long next_id = id;//timetable 아이디

        if(timeTableRepository.findById(next_id).isPresent()) {
            Timetable destination = timeTableRepository.findById(next_id).get();//아이디에 해당하는 timetable
            Long cu_bus = destination.getBus().getId();//현재 버스 id

            Long busStop_id = destination.getBusStop().getId(); //정류장 id
            //list.add(destination);


            while (next_id < 262L) {//262이후로 타임 테이블이 없음
                next_id++;
                destination = timeTableRepository.findById(next_id).get();
                busStop_id = destination.getBusStop().getId();
                Long next_bus = destination.getBus().getId();

                if ((c == busStop_id) || (cu_bus != next_bus))
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

    //해당 버스의 종점을 찾기
    public Long findEndId(Long id){
        Long next_id = id;
        while(next_id <262L){
            next_id ++;

            if(timeTableRepository.findById(next_id).isPresent()) {
                Timetable timetable = timeTableRepository.findById(next_id).get();
                String end = timetable.getEnd();

                if (end.equals("종점"))
                    return next_id;
            }
        }

        return null;
    }

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
            if(timetable.isStatus() == true)
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
