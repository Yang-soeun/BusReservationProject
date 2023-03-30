package bus.busReservation.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)//생성메서드를 사용해서 생성하도록 제약
public class Reservation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bus_id", nullable = false)
    private Bus bus_id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne
    @JoinColumn(name = "승차정보", nullable = false)
    private Timetable onInfo;

    @OneToOne
    @JoinColumn(name = "하차정보", nullable = false)
    private Timetable offInfo;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;//[예약완료(INCOMPLETE), 처리완료(COMPLETE)]

    //==생성 메서드==/
    public static Reservation createReservation(User user, Bus bus_id, Timetable onInfo, Timetable offInfo){

        Reservation reservation = new Reservation();

        reservation.setUser(user);
        reservation.setBus_id(bus_id);
        reservation.setOnInfo(onInfo);
        reservation.setOffInfo(offInfo);
        reservation.setStatus(ReservationStatus.INCOMPLETE);

        return reservation;
    }
}
