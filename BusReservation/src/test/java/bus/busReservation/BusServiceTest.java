package bus.busReservation;

import bus.busReservation.domain.Bus;
import bus.busReservation.repository.BusRepository;
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

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class BusServiceTest {
    @Autowired BusRepository busRepository;
    @Autowired  BusService busService;
    
    @Test
    public void 버스_이름_찾기(){
        List<Bus> allName = busService.findAllName();
        for (Bus bus : allName) {
            System.out.println("bus = " + bus.getName());
        }
    }

    @Test
    public void 정류장개수_찾기(){
        Long cnt = busService.findCnt("100");
        System.out.println("cnt = " + cnt);
    }
}
