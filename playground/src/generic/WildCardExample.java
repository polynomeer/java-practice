package generic;

import javaclass.superkeyword.Student;

import java.util.Arrays;

public class WildCardExample {
    public static void registerCourse(Course<?> course){
        System.out.println(course.getName() + " student: " +
                Arrays.toString(course.getStudents()));
    }

    public static void registerCourseStudent(Course<? extends Student> course) {
        System.out.println(course.getName() + " student: " +
                Arrays.toString(course.getStudents()));
    }
}
