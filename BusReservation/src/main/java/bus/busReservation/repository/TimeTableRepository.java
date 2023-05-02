package bus.busReservation.repository;
import bus.busReservation.domain.BusStop;
import bus.busReservation.domain.Timetable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TimeTableRepository {
    private final EntityManager em;
    //All 조회
    public List<Timetable> findAll(){
        return em.createQuery("select t from Timetable t", Timetable.class)
                .getResultList();
    }
    //버스 이름으로 조회
    public List<Timetable> findByBusName(String name){
        return em.createQuery("select t from Timetable t join t.bus b"
                + " where b.name like :name ", Timetable.class)
                .setParameter("name", name)
                .getResultList();
    }
    //timetable Id로 조회
    public Optional<Timetable> findById(Long id){
        return Optional.ofNullable(
                em.createQuery("select t from Timetable t"
                        + " where t.id = :id", Timetable.class)
                .setParameter("id", id)
                .getSingleResult());
    }

    public List<Timetable> start_end_id(Long start, Long end){//출발지와 도착지 사이의 timetable 반환
        return em.createQuery("select t from Timetable t"
                + " where t.id >= :start_id and t.id <= :end_id", Timetable.class)
                        .setParameter("start_id", start)
                        .setParameter("end_id", end)
                .getResultList();
    }

    //timetableId로 버스 정류장 아이디 알기
    public Optional<BusStop> findEndByTimetableId(Long id){
        return em.createQuery("select t.busStop from Timetable t"
                        + " where t.id = :id", BusStop.class)
                .setParameter("id", id)
                .getResultList()
                .stream().findFirst();
    }
}
