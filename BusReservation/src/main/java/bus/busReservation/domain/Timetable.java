package bus.busReservation.domain;

import lombok.Getter;
import javax.persistence.*;
import java.sql.Time;

@Entity
@Getter
public class Timetable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Time time;
    @Column(nullable = false)
    private Long seatStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bus_stop_id", nullable = false)
    private BusStop busStop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bus_id", nullable = false)
    private Bus bus;

    public void trueStatus(){
        this.seatStatus = 1L;
    }//예약좌석
    public void falseStatus(){
        this.seatStatus = 0L;
    }//빈좌석

    public Long isSeatStatus() {
        return seatStatus;
    }
}
