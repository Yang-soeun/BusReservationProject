package bus.busReservation.domain;

import lombok.Getter;
import javax.persistence.*;

@Entity
@Getter
public class Bus {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bus_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "차량번호", nullable = false)
    private String num;

    @ManyToOne
    @JoinColumn(name = "출발지", nullable = false)
    private BusStop busStop_start;

    @ManyToOne
    @JoinColumn(name = "종점", nullable = false)
    private BusStop busStop_end;
}
