package bus.busReservation;

import bus.busReservation.domain.Reservation;
import bus.busReservation.domain.Timetable;
import bus.busReservation.dto.TimetableDto;
import bus.busReservation.repository.ReservationRepository;
import bus.busReservation.repository.TimeTableRepository;
import bus.busReservation.service.TimeTableService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TimeTableServiceTest {

    @Autowired TimeTableRepository timeTableRepository;
    @Autowired TimeTableService timeTableService;
    @Autowired ReservationRepository reservationRepository;

    @Test
    public void 도착지_찾기(){
        //Timetable byId = timeTableRepository.findById(127L);

        List<TimetableDto> timetableDtos = timeTableService.destinationList("100", 11L);

        System.out.println("======================================================================================");
        for (TimetableDto timetableDto : timetableDtos) {
            System.out.println("timetableDto.getBusStop_name() = " + timetableDto.getBusStop_name());
        }
        System.out.println("======================================================================================");
    }
//
    @Test
    public void 상태변경(){
        timeTableService.changeTrue(240L, 244L);
    }

    @Test 
    public void 범위안_찾기(){
        List<Timetable> timetables = timeTableRepository.start_end_id(240L, 244L);
        for (Timetable timetable : timetables) {
            System.out.println("timetable.getId() = " + timetable.getId());
        }
    }
    @Test
    public void false로변경(){
        Reservation cancelReservation = reservationRepository.findById(1L);//완료처리할 예약 정보

        Long start_id = cancelReservation.getOnInfo().getId();
        Long end_id = cancelReservation.getOffInfo().getId();

        System.out.println("start_id = " + start_id);
        System.out.println("end_id = " + end_id);
    }

    @Test
    public void Nolist(){

        List<Long> NoLists = new ArrayList<>();

        if(timeTableService.NoReservation(53L, 55L) != 53L)//다르면 중간에 예약자가 있는 경우임
        {
            Long newId = timeTableService.NoReservation(53L, 55L);

            NoLists = timeTableService.NoList(newId, 55L);
        }

        for (Long noList : NoLists) {
            System.out.println(noList);
        }

    }

    @Test
    public void NoReservation(){
        Long aLong = timeTableService.NoReservation(57L, 66L);

        System.out.println(aLong);
    }
}
