package bus.busReservation.dto;

import bus.busReservation.domain.Reservation;
import bus.busReservation.domain.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDto {
    private String busStart_name;//탑승지 정보
    private String busEnd_name;//하차지 정보
    private Long id;
    private ReservationStatus status;

    public ReservationDto(Reservation reservation){
        busStart_name= reservation.getOnInfo().getBusStop().getName();
        busEnd_name=reservation.getOffInfo().getBusStop().getName();
        id = reservation.getId();
        status = reservation.getStatus();
    }
}
