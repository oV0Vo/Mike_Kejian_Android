package com.kejian.mike.mike_kejian_android.config.course;

/**
 * Created by violetMoon on 2015/10/13.
 */
public class CourseQuestionArg {
    public static final String URL_PREFIX = "Course";

    public static class GetHistoryQuestions {
        public static final String ACTION_NAME = "getHistoryQuestions";
        public static final String INPUT_COURSE_ID = "courseId";
        public static final String INPUT_STUDENT_ID = "studentId";
        public static final String INPUT_BEGIN_POS = "beginPos";
        public static final String INPUT_NUM = "num";
    }

    public static class GetCurrentQuestions {
        public static final String ACTION_NAME = "getCurrentQuestions";
        public static final String INPUT_COURSE_ID = "courseId";
    }

    public static class AnswerQuestion {
        public static final String ACTION_NAME = "answerQuestion";
        public static final String INPUT_QUESTION_ID = "questionId";
        public static final String INPUT_STUDENT_ID = "studentId";
        public static final String INPUT_STUDENT_NAME = "studentName";
        public static final String INPUT_ANSWER = "answer";
    }

    public static class NetQuestion {
        public static final String ACTION_NAME = "newQuesiton";
        public static final String INPUT_COURSE_ID = "courseId";
        public static final String INPUT_AUTHOR_ID = "authorId";
        public static final String INPUT_QUESTION_TYPE = "questionType";
        public static final String INPUT_CONTENT = "content";
        public static final String INPUT_BEGIN_TIME = "beginTime";
        public static final String INPUT_LAST_TIME = "lastTime";
    }

    public static class GetQuestionStats {

    }
}
