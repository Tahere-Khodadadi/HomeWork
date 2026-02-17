package ir.maktabHW13.service;

import ir.maktabHW13.model.Course;

import java.util.List;

public interface CourseService {

    void addCourse(Course course);

    void removeCourses(Long courseId);

    Course updateCourse(Course course);

    void addTeacherToCourse(String courseId, String teacherId);

    List<Course> findAll();

    void addStudentToCourse(String courseId, String teacherId);

    void showDetailCourse(Long courseId);

    void showTeacherCourse(Long teacherId );

}