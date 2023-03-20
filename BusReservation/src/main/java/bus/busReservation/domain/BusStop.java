package bus.busReservation.domain;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity

@Getter @Setter
public class BusStop {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bus_stop_id")
    private Long id;

    @Column(nullable = false)
    private String name;
}
