package bus.busReservation.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bus_id", nullable = false)
    private Bus bus;

    private boolean gradient;//0이면 빈자리, 1이면 자리없음
}
