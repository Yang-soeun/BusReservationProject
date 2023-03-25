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

    @Column(name="출발정류장",nullable = false)
    private Long start;

    @Column(name = "종점")
    private Long end;
}
