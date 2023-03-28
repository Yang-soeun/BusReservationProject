package bus.busReservation.repository;

import bus.busReservation.domain.Reservation;
import bus.busReservation.domain.Timetable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReservationRepository {

    private final EntityManager em;
    //버스 정류장으로 조회
    public List<Timetable> findByBusStopName(String name) {
        return em.createQuery("select t from Timetable t "+
                        "join t.busStop s" +
                        " where s.name like concat('%',:name,'%') " +
                        "and t.time>= date_format(now(),'%H:%i:%s') "+
                        "group by t.bus.name,s.name order by t.time asc", Timetable.class)
                .setParameter("name", name)
                .getResultList();
    }

    //밤에 Test 할때 쓸라궁
    public List<Timetable> TestBusStopName(String name) {
        return em.createQuery("select t from Timetable t "+
                        "join t.busStop s" +
                        " where s.name like concat('%',:name,'%') " +
                        "group by t.bus.name,s.name order by t.time asc", Timetable.class)
                .setParameter("name", name)
                .getResultList();
    }

    //예약 정보 저장
    public void save(Reservation reservation) {
        em.persist(reservation);
    }

    //현재 시간 이전의 timetable 찾기
    public List<Timetable> findByTime(){
        return em.createQuery("select t from Timetable t "+
                        " where t.time < date_format(now(),'%H:%i:%s') ", Timetable.class)
                .getResultList();
    }

    public List<Reservation> findByReservation(String bus_name){
        return em.createQuery("select r from Reservation r "+
                        "join r.onInfo start where start.bus.name=substring(:bus_name,4,3) and r.status = '예약완료'"//상태정보 추가했어용
                , Reservation.class)
                .setParameter("bus_name", bus_name)
                .getResultList();
    }

    public List<Reservation> findReservationById(Long bus_id){
        return em.createQuery("select r from Reservation r "+
                                "join r.onInfo start where start.bus.id=:id and r.status = '예약완료'"//상태정보 추가했어용
                        , Reservation.class)
                .setParameter("id", bus_id)
                .getResultList();
    }

    public Reservation findById(Long id){
        return em.createQuery("select r from Reservation r"
        + " where r.id = :id", Reservation.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}
