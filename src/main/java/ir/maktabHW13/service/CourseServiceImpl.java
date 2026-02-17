package ir.maktabHW13.service;

import ir.maktabHW13.model.Course;

import ir.maktabHW13.repository.CourseRepository;
import ir.maktabHW13.repository.UserRepository;

import java.util.List;

public class CourseServiceImpl implements CourseService {
    UserRepository userRepository;
    CourseRepository courseRepository;


    public CourseServiceImpl(UserRepository userRepository, CourseRepository courseRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }
    @Override
    public void addCourse(Course course) {
        userRepository.save(course);
    }

    @Override
    public void removeCourses(Long courseId) {
        Course course1 = courseRepository.findById(Course.class, courseId);
        if (course1 == null) {
            System.out.println("Course not found");
            return;
        }
        try {
            courseRepository.remove(courseId);
            System.out.println("Course removed successfully");
        } catch (Exception e) {
            throw new RuntimeException("Course not removed successfully");
        }
    }

    @Override
    public Course updateCourse(Course course) {
        try {


            Course existingCourse = userRepository.findById(Course.class, course.getId());
            if (existingCourse != null) {
                existingCourse.setTitle(course.getTitle());
                existingCourse.setIdentifier(course.getIdentifier());
                existingCourse.setStartDate(course.getStartDate());
                existingCourse.setEndDate(course.getEndDate());

                return courseRepository.update(existingCourse);

            }
        } catch (Exception e) {
            throw new RuntimeException(" update course failed  ");
        }
        return course;
    }


    @Override
    public void addTeacherToCourse(String courseId, String teacherId) {

        if (courseId == null && teacherId == null) {
            System.out.println("not exist course or teacher");
        } else {
            courseRepository.assignTeacherToCourse(courseId, teacherId);
        }
    }

    @Override
    public List<Course> findAll() {
        return userRepository.findAll(Course.class);
    }

    @Override
    public void addStudentToCourse(String courseId, String studentId) {
        if (courseId == null && studentId == null) {
            throw new RuntimeException("not exist course or student");
        } else {
            courseRepository.assignStudentToCourse(courseId, studentId);
        }
    }

    @Override
    public void showDetailCourse(Long courseId) {
        Course course = courseRepository.findById(Course.class, courseId);
        if (course == null) {
            System.out.println("Course not found");
        }

        else  {
              courseRepository.getDetailCourse(courseId);
            System.out.println("Show detail course successfully");
        }

    }
    @Override
    public void showTeacherCourse(Long teacherId) {

        List<Course> listCourses = courseRepository.findCourseByTeacherId(teacherId);

        if(listCourses.isEmpty()) {

            System.out.println(" no courses for this teacher ");
        }

    }

}
