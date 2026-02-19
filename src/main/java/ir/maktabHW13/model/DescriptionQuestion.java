package ir.maktabHW13.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "descriptive_questions")
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class DescriptionQuestion extends  Questions {


    @Override
    public int calculateCorrectAnswer(String answerStudent) {
        return 0;
    }
}
