package bus.busReservation.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Bus {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bus_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "차량번호", nullable = false)
    private String num;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "출발지", nullable = false)
    private BusStop busStop_start;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "종점", nullable = false)
    private BusStop busStop_end;
}
