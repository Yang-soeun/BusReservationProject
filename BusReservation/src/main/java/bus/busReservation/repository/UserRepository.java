package bus.busReservation.repository;

import bus.busReservation.domain.Timetable;
import bus.busReservation.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserRepository{
    private final EntityManager em;

    //아이디로 사용자 조회
    public User findById(String id){
        return em.createQuery("select u from User u"
                        + " where u.id = :id ", User.class)
                .setParameter("id", id)
                .setHint("org.hibernate.readOnly", true)
                .getSingleResult();
    }
}
