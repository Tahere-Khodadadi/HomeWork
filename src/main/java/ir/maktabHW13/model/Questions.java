package ir.maktabHW13.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
    private String descriptionQuestion;

    @ManyToOne
    @JoinTable(
            name = "course_question",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Course course;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
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
                ", description='" + descriptionQuestion + '\'' +
                '}';
    }
}
