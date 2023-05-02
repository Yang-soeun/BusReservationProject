package bus.busReservation;

import bus.busReservation.domain.Bus;
import bus.busReservation.domain.BusStop;
import bus.busReservation.repository.BusRepository;
import bus.busReservation.repository.TimeTableRepository;
import bus.busReservation.service.BusService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class BusServiceTest {
    @Autowired BusRepository busRepository;
    @Autowired  BusService busService;

    @Autowired
    TimeTableRepository timeTableRepository;
    
    @Test
    public void 버스_이름_찾기(){
        List<Bus> allName = busService.findAllName();
        for (Bus bus : allName) {
            System.out.println("bus = " + bus.getName());
        }
    }

    @Test
    public void 버스_이름으로_종점_아이디_찾기(){
        Optional<BusStop> endByBusName = busRepository.findEndByBusName("100");
        System.out.println(endByBusName.get().getId());
    }

}
