package bl.course;

import java.util.ArrayList;

import dataType.course.CourseBriefInfo;
import dataType.course.CourseType;

/**
 * Created by violetMoon on 2015/9/29.
 */
public class CourseBriefFilter {

    public static ArrayList<CourseBriefInfo> filterByCourseType(ArrayList<CourseBriefInfo> courseBriefs,
                                                                CourseType courseType) {
        ArrayList<CourseBriefInfo> results = new ArrayList<CourseBriefInfo>();
        for(CourseBriefInfo course: courseBriefs)
            if(courseType == course.getCourseType())
                results.add(course);
        //@mock
        if(results.size() == 0)
            return courseBriefs;
        return results;
    }

    public static ArrayList<CourseBriefInfo> filterByAcademyName(ArrayList<CourseBriefInfo> courseBriefs,
                                                                 CharSequence academyName) {
        ArrayList<CourseBriefInfo> results = new ArrayList<CourseBriefInfo>();
        for(CourseBriefInfo course: courseBriefs)
            if(academyName.equals(course.getAcademyName()))
                results.add(course);
        //@mock
        if(results.size() == 0)
            return courseBriefs;
        return results;
    }
}
