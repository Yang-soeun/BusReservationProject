package bus.busReservation.service;

import bus.busReservation.domain.Bus;
import bus.busReservation.repository.BusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BusService {
    private final BusRepository busRepository;


    //모든 버스의 이름
    // 중복 제거 해야함 !!!!!!!!!!
    public List<Bus> findAllName() {
        List<Bus> list = busRepository.findAll();
        list.remove(1);
        return list;
    }

    //특정 버스의 정류장 개수
    public Long findCnt(String name){

        List<Bus> busList = busRepository.findByName(name);

        if(busList.size() !=0)//이거 안하면 오류남
        {
            Bus bus = busList.get(0);
            return bus.getCnt();//버스 정류장의 회차 번호 반환
        }

        return null;
    }
}
