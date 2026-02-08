package ir.maktabHW13.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Table(name = "exam")
@Getter
@Setter
@ToString


public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String titleExam;
    private String descriptionExam;
    private LocalDate startExam;



}
