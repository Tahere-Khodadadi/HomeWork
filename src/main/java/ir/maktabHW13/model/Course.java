package ir.maktabHW13.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "courses")
public class Course   {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String title;

    @Column(unique = true, nullable = false)
    private String identifier;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "course_student",
            joinColumns =@ JoinColumn(name = "course_id")
            , inverseJoinColumns = @JoinColumn(name = "student_id")
    )

    private Set<User> students  =new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacher_id",nullable = true)
    private User teacher;



    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(this==obj) return true;
        if(!(obj instanceof Course)) return false;
        Course course = (Course)obj;
        return id != null && id.equals(course.id);
    }

    @Override
    public String toString() {
        return " Courses {" +
                "id=" + id +
                ", title = '" + title + '\'' +
                ", identifier ='" + identifier + '\'' +
                ", startDate ='" + startDate + '\'' +
                ", endDate =" + endDate +'\''+

                '}';
    }
}