package bus.busReservation.service;

import bus.busReservation.domain.Bus;
import bus.busReservation.domain.Reservation;
import bus.busReservation.domain.Timetable;
import bus.busReservation.domain.User;
import bus.busReservation.dto.ReservationDto;
import bus.busReservation.dto.TimetableDto;
import bus.busReservation.repository.BusRepository;
import bus.busReservation.repository.ReservationRepository;
import bus.busReservation.repository.TimeTableRepository;
import bus.busReservation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final TimeTableRepository timeTableRepository;
    private final UserRepository userRepository;

    private final BusRepository busRepository;

    @Transactional
    public List<TimetableDto> findByBusStopName(String keyword){
        List<Timetable> timetables=reservationRepository.findByBusStopName(keyword);

        List<TimetableDto> timetableDtoList=timetables.stream()
                .map(t-> new TimetableDto(t))
                .collect(Collectors.toList());

        if(timetables.isEmpty()) return timetableDtoList;

        return timetableDtoList;
    }

    //Test
    @Transactional
    public List<TimetableDto> TestBusStopName(String keyword){
        List<Timetable> timetables=reservationRepository.TestBusStopName(keyword);

        List<TimetableDto> timetableDtoList=timetables.stream()
                .map(t-> new TimetableDto(t))
                .collect(Collectors.toList());

        if(timetables.isEmpty()) return timetableDtoList;

        return timetableDtoList;
    }


    @Transactional
    public List<ReservationDto> findByReservation(Long bus_id){
        List<Reservation> reservations = reservationRepository.findReservationById(bus_id);

        List<ReservationDto> reservationDtoList=reservations.stream()
                .map(r->new ReservationDto(r))
                .collect(Collectors.toList());
        if(reservations.isEmpty())return reservationDtoList;
        return reservationDtoList;

    }

    //예약 정보
    @Transactional
    public Long saveReservation(String userId, Long bus_id, Long onInfoId, Long offInfoId){
        User user = userRepository.findById(userId);//사용자 정보 생성

        if(timeTableRepository.findById(onInfoId).isPresent() && timeTableRepository.findById(offInfoId).isPresent()) {
            Timetable onInfo = timeTableRepository.findById(onInfoId).get();//출발지 정보 생성
            Timetable offInfo = timeTableRepository.findById(offInfoId).get();//도착지 정보 생성
            Bus busInfo = busRepository.findById(bus_id).get();


            Reservation reservation = Reservation.createReservation(user, busInfo, onInfo, offInfo);//예약 생성
            reservationRepository.save(reservation);//예약 저장

            return reservation.getId();
        }
        return null;
    }
}
