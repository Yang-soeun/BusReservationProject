package bus.busReservation.dto;

import bus.busReservation.domain.Bus;
import bus.busReservation.domain.BusStop;
import bus.busReservation.domain.Timetable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimetableDto {
    private String bus_name;
    private String busStop_name;
    private Time time;
    private Long id;

    private Long seatStatus;

    public TimetableDto(Timetable timetable){
        bus_name= timetable.getBus().getName();
        busStop_name=timetable.getBusStop().getName();
        time=timetable.getTime();
        id = timetable.getId();
        seatStatus = timetable.isSeatStatus();
    }
}
