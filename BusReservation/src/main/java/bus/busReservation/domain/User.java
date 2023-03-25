package bus.busReservation.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class User {
    @Id
    @Column(name ="user_id")
    private String id;

    @Column(name = "name", nullable = false)
    private String username;
    @Column(nullable = false)
    private Integer age;
    @Column(nullable = false)
    private String phone;
    @Column(nullable = false)
    private String password;
    private String role;

    @Builder
    public User(String username, int age, String phone, String password, String role){
        this.username = username;
        this.age = age;
        this.phone = phone;
        this.password = password;
        this.role = role;
    }

}
