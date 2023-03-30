package bus.busReservation.repository;

import bus.busReservation.domain.Bus;
import bus.busReservation.domain.Timetable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BusRepository {
    private final EntityManager em;

    //저장된 버스 모두 찾기
    public List<Bus> findAll(){
        return em.createQuery("select b from Bus b", Bus.class)
                .getResultList();
    }

    //버스 이름에 해당하는 것만 찾기
    public List<Bus> findByName(String busName) {
        return em.createQuery("select b from Bus b"
                + " where b.name like :name", Bus.class)
                .setParameter("name", busName)
                .getResultList();
    }

    public Optional<Bus> findById(Long id){
        return Optional.ofNullable(
                em.createQuery("select b from Bus b"
                                + " where b.id = :id", Bus.class)
                        .setParameter("id", id)
                        .getSingleResult());
    }
}
