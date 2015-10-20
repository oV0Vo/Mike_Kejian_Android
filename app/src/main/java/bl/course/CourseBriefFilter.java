package bl.course;

import java.util.ArrayList;

import com.kejian.mike.mike_kejian_android.dataType.course.CourseBriefInfo;
import com.kejian.mike.mike_kejian_android.dataType.course.CourseType;

/**
 * Created by violetMoon on 2015/9/29.
 */
public class CourseBriefFilter {

    public static ArrayList<CourseBriefInfo> filterByCourseType(ArrayList<CourseBriefInfo> courseBriefs,
                                                                CharSequence courseType) {
        ArrayList<CourseBriefInfo> results = new ArrayList<CourseBriefInfo>();
        for(CourseBriefInfo course: courseBriefs)
            if(courseType.equals(course.getCourseType()))
                results.add(course);
        return results;
    }

    public static ArrayList<CourseBriefInfo> filterByAcademyName(ArrayList<CourseBriefInfo> courseBriefs,
                                                                 CharSequence academyName) {
        ArrayList<CourseBriefInfo> results = new ArrayList<CourseBriefInfo>();
        for(CourseBriefInfo course: courseBriefs)
            if(academyName.equals(course.getAcademyName()))
                results.add(course);
        return results;
    }
}
