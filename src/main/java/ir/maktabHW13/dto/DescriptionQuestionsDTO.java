package ir.maktabHW13.dto;

import ir.maktabHW13.model.Course;
import ir.maktabHW13.model.Questions;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

public class DescriptionQuestionsDTO extends Questions {
    @Setter
    @Getter
    @NotBlank(message = "title cannot empty")
    private String title;
    @Setter
    @Getter
    @NotBlank(message = " Question Text cannot empty")
    private String questionText;


    @Setter
    @Getter
    @NotBlank(message = " Course ID cannot empty")
    private Course course;
}
