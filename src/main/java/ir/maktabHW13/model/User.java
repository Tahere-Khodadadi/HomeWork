package ir.maktabHW13.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Setter
@Getter

@Inheritance(strategy = InheritanceType.JOINED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

   @Column(nullable = false)
    private String password;

   @Enumerated(EnumType.STRING)
   @Column(nullable = false)
   private Roles roles;

   @Enumerated(EnumType.STRING)
   @Column(nullable = false)
    private UserStatus userStatus=UserStatus.Pending;

   @Column(nullable = false)
   private String code;


   @ManyToMany (mappedBy = "students")
   private Set<Course> studentCourses = new HashSet<>();


   @OneToMany(mappedBy = "teacher")
   private Set<Course> teachingCourses = new HashSet<>();

    @Override
    public String toString() {
        return " User { " +
                "id = " + id +
                ", firstName = '" + firstName + '\'' +
                ", lastName = '" + lastName + '\'' +
                ", password = '" + password + '\'' +
                ", roles = " + roles +'\''+
                ", userStatus = " + userStatus +'\''+
                ", code = '" + code + '\'' +
                " } ";
    }

}
