package ir.maktabHW13.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "multiple_choice_questions")

@Setter
@Getter
@ToString(callSuper = true)
@NoArgsConstructor

public class MultipleChoiceQuestion extends Questions {

    // if add 1 question
    @OneToMany
            (mappedBy = "questions", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MultiOption> options = new ArrayList<>();

    @Column(name = "correct_option_index")
    private int correctOptionIndex;

    @Override
    public int calculateCorrectAnswer(String selectedAnswer) {
        if (selectedAnswer.equals(correctOptionIndex)) {
            return 1;
        }
        return 0;
    }
}


