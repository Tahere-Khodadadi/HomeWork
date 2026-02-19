package ir.maktabHW13.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "questions")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@ToString
@NoArgsConstructor

public abstract class Questions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String questionText;


    @ManyToMany(mappedBy = "questions")
    private List<Exam> exam = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;//check the refrence Identifier in heap
        if (o == null || getClass() != o.getClass()) return false;
        Questions questions = (Questions) o;
        return id.equals(questions.id);
    }


    @Override
    public int hashCode() {

        if (id != null) {
            return id.hashCode();
        } else {
            return super.hashCode();
        }
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", questionText='" + questionText + '\'' +
                '}';
    }

    @OneToMany(mappedBy = "question")
    private List<Answer> answers = new ArrayList<>();


    @ManyToOne
    @JoinColumn(name = "previous_question_id")
    private Questions previousQuestion;

    @ManyToOne
    @JoinColumn(name = "next_question_id")
    private Questions nextQuestion;

public abstract  int calculateCorrectAnswer(String answerStudent);
}
