package bus.busReservation.repository;
import bus.busReservation.domain.BusStop;
import bus.busReservation.domain.Timetable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TimeTableRepository {
    private final EntityManager em;
    //All 조회
    public List<Timetable> findAll(){
        return em.createQuery("select t from Timetable t", Timetable.class)
                .setHint("org.hibernate.readOnly", true)
                .getResultList();
    }
    //버스 이름으로 조회
    public List<Timetable> findByBusName(String name){
        return em.createQuery("select t from Timetable t join fetch t.bus b"
                + " where b.name like :name ", Timetable.class)
                .setParameter("name", name)
                .setHint("org.hibernate.readOnly", true)
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
                .setHint("org.hibernate.readOnly", true)
                .getResultList()
                .stream().findFirst();
    }

    //도착지 timetableId 찾기
    public Optional<Long> findDestinationId(Long bus_id, Long busStop_id, Time sTime){
        return em.createQuery("select min(t.id) from Timetable t" +
                        " join t.bus b join t.busStop s" +
                        " where b.id = :id and s.id = :busStop_id and t.time >= :sTime", Long.class)
                .setParameter("id", bus_id)
                .setParameter("busStop_id", busStop_id)
                .setParameter("sTime", sTime)
                .setHint("org.hibernate.readOnly", true)
                .getResultList()
                .stream().findFirst();

    }

    //도착지 timetable List 반환
    public List<Timetable> findDestinationList(Long start, Long end){
        return em.createQuery("select t from Timetable t" +
                " where t.id > :start and t.id <= :end" , Timetable.class)
                .setParameter("start", start)
                .setParameter("end", end)
                .setHint("org.hibernate.readOnly", true)
                .getResultList();
    }
}
