package bus.busReservation;

import bus.busReservation.domain.Reservation;
import bus.busReservation.domain.Timetable;
import bus.busReservation.dto.TimetableDto;
import bus.busReservation.repository.ReservationRepository;
import bus.busReservation.service.ReservationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ReservationServiceTest {

    @Autowired
    ReservationService reservationService;
    @Autowired
    ReservationRepository reservationRepository;

    @Test
    public void timetabletest() throws Exception{
        //given
        List<TimetableDto> result = reservationService.findByBusStopName("동대구역");

        System.out.println(result);
    }

    @Test
    public void 이전시간표_찾기(){
        List<Timetable> timetableList = reservationRepository.findByTime();
        for (Timetable timetable : timetableList) {
            System.out.println("timetable.getTime() = " + timetable.getTime()+ ",   "+ timetable.getId());
        }
    }

    @Test
    public void 이후시간표_찾기(){
        List<Timetable> timetableList = reservationRepository.findByBusStopName("건너");
        for (Timetable timetable : timetableList) {
            System.out.println("timetable.getTime() = " + timetable.getTime()+ ",   "+ timetable.getId());
        }

        String currentDate = new SimpleDateFormat("yyMMddhhmmss").format(
                new java.sql.Date(System.currentTimeMillis()));
        Date sqlDate = new java.sql.Date(System.currentTimeMillis());
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println(currentDate);
    }

    @Test
    public void 예약찾기()throws Exception{
        List<Reservation> reservations= reservationRepository.findByReservation("bus200");
        for(Reservation reservation:reservations){
            System.out.println("버스정류장: "+reservation.getOnInfo().getBusStop().getName());
        }
    }
}
