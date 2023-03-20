package bus.busReservation.domain;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@DynamicUpdate//변경된 칼럼이 있으면 update
public class Timetable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Time time;

    @Column(nullable = false)
    private boolean status;

    @ManyToOne
    @JoinColumn(name = "bus_stop_id", nullable = false)
    private BusStop busStop;

    @ManyToOne
    @JoinColumn(name = "bus_id", nullable = false)
    private Bus bus;

    @Column(name = "종점")
    private String end;

    public void trueStatus(){
        this.setStatus(true);
    }
    public void falseStatus(){
        this.setStatus(false);
    }

    public boolean isStatus() {
        return status;
    }
}
