package ir.maktabHW13.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "teachers")

public class Teacher  extends User{

    private String TeacherId;

    @OneToMany(mappedBy = "teacher",fetch = FetchType.EAGER)
    private List<Course> teacherCourse=new ArrayList<>();


    @Override
    public String toString() {
        return super.toString();
    }
}
