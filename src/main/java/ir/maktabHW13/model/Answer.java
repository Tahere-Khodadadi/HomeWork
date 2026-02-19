package ir.maktabHW13.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "answers")
@Setter
@Getter
@NoArgsConstructor
@ToString

public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "student_exam_id")
    private StudentExam studentExam;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Questions question;

    @Column(nullable = false)
    private String answerStudent;

    private Integer scoreStudent;




}
