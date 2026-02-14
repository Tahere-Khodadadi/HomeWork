package ir.maktabHW13.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "exam")
@Getter
@Setter
@ToString
@NoArgsConstructor

@Inheritance(strategy = InheritanceType.JOINED)

public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title_Exam;

    @Column(nullable = false,columnDefinition = "Text")
    private String description_Exam;

    @Column(nullable = false)
    private int duration_Exam;

    @ManyToOne
    @JoinColumn(name = "course_id",nullable = true)
    private Course course;

   @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
   @JoinTable(
                   name = "exam_question",
           joinColumns=@JoinColumn(name="exam_id"),
           inverseJoinColumns = @JoinColumn(name = "question_id")
            )
   private List<Questions> questions=new ArrayList<>();


}
