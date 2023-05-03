package bus.busReservation.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.*;
import java.sql.Time;

@Entity
@Getter
@Setter
public class Timetable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Time time;
    @Column(nullable = false)
    private Long seatStatus;

    @ManyToOne
    @JoinColumn(name = "bus_stop_id", nullable = false)
    private BusStop busStop;

    @ManyToOne
    @JoinColumn(name = "bus_id", nullable = false)
    private Bus bus;

    public void trueStatus(){
        this.setSeatStatus(1L);
    }//예약좌석
    public void falseStatus(){
        this.setSeatStatus(0L);
    }//빈좌석

    public Long isSeatStatus() {
        return seatStatus;
    }
}
