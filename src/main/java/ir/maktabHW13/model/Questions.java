package ir.maktabHW13.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.HashSet;
import java.util.Set;

@Table(name = "questions")
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor

@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Questions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String descriptionQuestion;

    @ManyToMany
    @JoinTable(
            name = "course_question",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns =@JoinColumn(name = "course_id")
    )
    private Set<Course> courses=new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Questions questions = (Questions) o;
        return id.equals(questions.id);
    }
    @Override
    public int hashCode() {

        if(id!=null){
            return id.hashCode();
        }
        else {
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
