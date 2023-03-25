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
        List<Long> longs = timeTableService.NoList(2L, 11L);

        for (Long aLong : longs) {
            System.out.println("aLong = " + aLong);
        }
    }
}
