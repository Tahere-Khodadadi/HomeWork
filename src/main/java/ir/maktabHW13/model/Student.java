package ir.maktabHW13.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "students")
@Setter
@Getter

public class Student extends User{

    private String StudentId;


    @ManyToMany(mappedBy = "students",fetch = FetchType.EAGER)
    private List<Course> courses;



}
