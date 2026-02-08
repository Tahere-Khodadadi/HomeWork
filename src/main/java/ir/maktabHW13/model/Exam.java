package ir.maktabHW13.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;


@Entity
@Table(name = "exam")
@Getter
@Setter
@ToString
@NoArgsConstructor

public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title_Exam;

    @Column(nullable = false,columnDefinition = "Text")
    private String description_Exam;

    @Column(nullable = false)
    private Integer  duration_Exam;

    @ManyToOne
    @JoinColumn(name = "course_id",nullable = false)
    private Course course;


}
