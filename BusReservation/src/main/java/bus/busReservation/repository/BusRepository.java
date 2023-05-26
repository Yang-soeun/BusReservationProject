package bus.busReservation.repository;

import bus.busReservation.domain.Bus;
import bus.busReservation.domain.BusStop;
import bus.busReservation.domain.Timetable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BusRepository {
    private final EntityManager em;

    //저장된 버스 모두 찾기
    public List<Bus> findAll(){
        return em.createQuery("select b from Bus b", Bus.class)
                .setHint("org.hibernate.readOnly", true)
                .getResultList();
    }

    public List<String> findName(){
        return em.createQuery("select b.name from Bus b", String.class)
                .setHint("org.hibernate.readOnly", true)
                .getResultList();
    }


    //버스 이름에 해당하는 것만 찾기
    public List<Bus> findByName(String busName) {
        return em.createQuery("select b from Bus b"
                + " where b.name like :name", Bus.class)
                .setParameter("name", busName)
                .setHint("org.hibernate.readOnly", true)
                .getResultList();
    }

    public Optional<Bus> findById(Long id){
        return Optional.ofNullable(
                em.createQuery("select b from Bus b"
                                + " where b.id = :id", Bus.class)
                        .setParameter("id", id)
                        .setHint("org.hibernate.readOnly", true)
                        .getSingleResult());
    }

    //버스 이름으로 종점 id알기
    public Optional<BusStop> findEndByBusName(String busName){
        return em.createQuery("select b.busStop_end from Bus b"
                        + " where b.name like :name", BusStop.class)
                .setParameter("name", busName)
                .setHint("org.hibernate.readOnly", true)
                .getResultList()
                .stream().findFirst();
    }
}
