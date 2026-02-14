package ir.maktabHW13.dto;

import ir.maktabHW13.model.Course;
import ir.maktabHW13.model.MultiOption;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class MultipleChoiceQuestionsDTO {

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
    @NotBlank(message = "options cannot empty")
    private List<String> options;

    @Setter
    @Getter
    @NotBlank(message = "answers cannot empty")
    private int correctAnswer;

    @Setter
    @Getter
    @NotBlank(message = "Course ID cannot empty")
    private Course course;
}
