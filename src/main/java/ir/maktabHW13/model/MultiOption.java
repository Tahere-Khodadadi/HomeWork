package ir.maktabHW13.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "multi_options")
@Getter
@Setter

@NoArgsConstructor
public class MultiOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "option_text")
    private String optionText;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private MultipleChoiceQuestion questions;
}

