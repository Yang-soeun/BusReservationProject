package bus.busReservation.repository;

import bus.busReservation.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {
    public User findById(String id);
    public User findByIdContains(String id);
}