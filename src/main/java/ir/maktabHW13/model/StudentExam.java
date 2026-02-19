package ir.maktabHW13.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalTime;
import java.util.List;


@Entity
@Table(name = "student_exam")
@Getter
@Setter
@ToString
@NoArgsConstructor

public class StudentExam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User student;

    @ManyToOne
    @JoinColumn(name = "exam_id")
    private Exam exam;


    private LocalTime startExamTime;
    private LocalTime endExamTime;
    private LocalTime submitDate;

    @Enumerated(EnumType.STRING)
    private ExamStatus examStatus = ExamStatus.NotStarted;

    private int totalScoreExam;
    private Integer currentQuestionIndex = 0; //  default for first Question


    @OneToMany(mappedBy = "answer")
    private List<Answer> answers;

    @OneToMany(mappedBy = "exam")
    private List<Questions> questions;


    public Questions getNextQuestion() {
        if (currentQuestionIndex + 1 < questions.size()) {
            Questions next = questions.get(currentQuestionIndex + 1);//return next question

            return questions.get(currentQuestionIndex + 1);
        }
        return null;

    }

    public Questions getPreviousQuestion() {
        if (currentQuestionIndex - 1 >= 0) {
            Questions previous = questions.get(currentQuestionIndex - 1);
            return questions.get(currentQuestionIndex - 1);
        }
        return null;
    }

    public void moveNextQuestion() {
        if (currentQuestionIndex + 1 < questions.size()) {
            currentQuestionIndex++;
        }

    }

    public void movePreviousQuestion() {
        if (currentQuestionIndex - 1 >= 0) {
            currentQuestionIndex--;
        }
    }

    public void calculateTotalScore() {
        int totalScore = 0;
        if (answers == null || answers.isEmpty()) {
            this.totalScoreExam = 0;
            return;
        }
        for (Answer answer : answers) {
            Questions question = answer.getQuestion();
            if (question instanceof MultipleChoiceQuestion) {
                String selectedOption = answer.getAnswerStudent();

                int point=((MultipleChoiceQuestion) question).
                        calculateCorrectAnswer(selectedOption);
                answer.setScoreStudent(point);
                totalScore+=point;

            }
            else if (question instanceof DescriptionQuestion) {
                if(answer.getAnswerStudent()!=null) {
                    totalScore+=answer.getScoreStudent();
                }

            }
        }
      this.totalScoreExam = totalScore;
    }
}


