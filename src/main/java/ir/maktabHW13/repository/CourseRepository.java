package ir.maktabHW13.repository;

import ir.maktabHW13.model.Course;

import java.util.List;

public interface CourseRepository extends BaseRepository {

     Course findTitleCourse(String courseTitle);

    void assignTeacherToCourse(String courseId, String teacherId);

    void assignStudentToCourse(String courseId, String studentId);

    void  getDetailCourse(Long courseId);

     List<Course> findCourseByTeacherId(Long teacherId);


}
