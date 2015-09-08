package model.course;

import java.util.ArrayList;

/**
 * Created by violetMoon on 2015/9/8.
 */
public class CourseModel {
    private static ArrayList<Course> courses;

    static {
        courses = new ArrayList<Course>();
        courses.add(getCourseMock1());
        courses.add(getCourseMock2());
        courses.add(getCourseMock1());
        courses.add(getCourseMock2());
        courses.add(getCourseMock1());
        courses.add(getCourseMock2());
    }

    public static Course getCourse(int courseId) {
        for(Course course: courses) {
            if(course.getCourseId() == courseId)
                return course;
        }
        return null;
    }

    public static void setCourses(ArrayList<Course> courses) {
        courses = courses;
    }

    private static Course getCourseMock1() {
        Course course = new Course();
        course.setAcademyName("软件学院");
        course.setAnnoucement("隔壁老王说再也不偷情了");
        course.setBriefIntro("与隔壁王太太偷情时被王先生抓奸在床，王先生揍了我一顿，对我说：“说好只爱我一个人的呢？”");
        course.setName("软件需求工程");
        course.setProgressWeek(1);
        course.setTeachContent("专业美容学校，学美容必选的化妆名校");
        course.setTeacherName("麦乐鸡");
        ArrayList<String> references = new ArrayList<String>();
        references.add("java虚拟机规范");
        references.add("java并发编程");
        course.setReferences(references);
        return course;
    }

    private static Course getCourseMock2() {
        Course course = new Course();
        course.setAcademyName("新东方烹饪与理发技术学院");
        course.setAnnoucement("大哥来份大宝剑不");
        course.setBriefIntro("我妈要是有你这儿媳妇就不愁她孙子长得丑了");
        course.setName("j2ee于中间件");
        course.setProgressWeek(11);
        course.setTeachContent("惊了个呆 男子嫖娼被抓才知3名“卖淫女”皆为男性");
        course.setTeacherName("唐马儒");
        course.setReferences(new ArrayList<String>());
        return course;
    }
}
