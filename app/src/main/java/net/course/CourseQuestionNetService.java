package net.course;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.kejian.mike.mike_kejian_android.dataType.course.PersonMocks;
import com.kejian.mike.mike_kejian_android.dataType.course.question.BasicQuestion;
import com.kejian.mike.mike_kejian_android.dataType.course.question.CommitAnswerResultMessage;
import com.kejian.mike.mike_kejian_android.dataType.course.question.CurrentQuestion;
import com.kejian.mike.mike_kejian_android.dataType.course.question.QuestionAnswer;
import com.kejian.mike.mike_kejian_android.dataType.course.question.QuestionShowAnswer;
import com.kejian.mike.mike_kejian_android.dataType.course.question.QuestionStats;
import com.kejian.mike.mike_kejian_android.dataType.course.question.SingleChoiceQuestion;
import util.NetOperateResultMessage;

/**
 * Created by violetMoon on 2015/10/6.
 */
public class CourseQuestionNetService {

    public static ArrayList<BasicQuestion> getHistroryQuestions(String courseId, int beginPos, int num,
                                                                int time, TimeUnit timeUnit) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return getHistoryQuestionMocks();
    }

    public static ArrayList<CurrentQuestion> getCurrentQuestions(String courseId, int beginPos,
                                                                 int num, int time, TimeUnit timeUnit) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return getCurrentQuestionMocks();
    }

    public static NetOperateResultMessage addNewQuestion(CurrentQuestion question) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<QuestionShowAnswer> getQuestionAnswer(String questionId, int beginPos,
                                                              int num) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return questionAnswerMocks();
    }

    public static QuestionStats getQuestionStats(String questionId) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return questionStatsMock();
    }

    public static CommitAnswerResultMessage commitAnswer(QuestionAnswer answer) {
        int choice = (int)(System.currentTimeMillis() % 3);
        switch(choice) {
            case 0:
                return CommitAnswerResultMessage.NET_ERROR;
            case 1:
                return CommitAnswerResultMessage.QUESITON_TIME_OUT;
            case 2:
                return CommitAnswerResultMessage.SUCCESS;
            default:
                return null;
        }
    }

    private static QuestionStats questionStatsMock() {
        QuestionStats mock = new QuestionStats();

        ArrayList<Integer> d = new ArrayList<Integer>();
        d.add(10);
        d.add(12);
        d.add(23);
        d.add(45);
        mock.setChoiceDistribute(d);
        mock.setCorrectAnswerNum(42);
        mock.setQuestionId("sdfsd");
        mock.setTotalAnswerNum(113);
        return mock;
    }

    private static ArrayList<QuestionShowAnswer> questionAnswerMocks() {
        ArrayList<QuestionShowAnswer> mocks = new ArrayList<QuestionShowAnswer>();
        mocks.add(questionAnswerMock());
        mocks.add(questionAnswerMock());
        mocks.add(questionAnswerMock());
        mocks.add(questionAnswerMock());
        mocks.add(questionAnswerMock());
        return mocks;
    }

    private static QuestionShowAnswer questionAnswerMock() {
        QuestionShowAnswer mock = new QuestionShowAnswer();
        mock.setAnswer("有一美人兮，见之不忘。\n" +
                "一日不见兮，思之如狂。\n" +
                "凤飞翱翔兮，四海求凰。\n" +
                "无奈佳人兮，不在东墙。\n" +
                "将琴代语兮，聊写衷肠。\n" +
                "何日见许兮，慰我彷徨。\n" +
                "愿言配德兮，携手相将。\n" +
                "不得於飞兮，使我沦亡。\n" +
                "凤兮凤兮归故乡，遨游四海求其凰。\n" +
                "时未遇兮无所将，何悟今兮升斯堂！\n" +
                "有艳淑女在闺房，室迩人遐毒我肠。\n" +
                "何缘交颈为鸳鸯，胡颉颃兮共翱翔！\n" +
                "凰兮凰兮从我栖，得托孳尾永为妃。\n" +
                "交情通意心和谐，中夜相从知者谁？\n" +
                "双翼俱起翻高飞，无感我思使余悲。");
        mock.setHeadIconUrl("");
        mock.setStudentId("131250012");
        mock.setStudentName("黄圣达");

        return mock;
    }

    public static boolean shutDownQuestion(String questionId) {
        return true;
    }

    private static ArrayList<BasicQuestion> getHistoryQuestionMocks() {
        ArrayList<BasicQuestion> mocks = new ArrayList<BasicQuestion>();
        mocks.add(getQuestionMock());
        mocks.add(getQuestionMock());
        mocks.add(getQuestionMock());
        mocks.add(getQuestionMock());

        return mocks;
    }

    private static ArrayList<CurrentQuestion> getCurrentQuestionMocks() {
        ArrayList<CurrentQuestion> currentQuestions = new ArrayList<CurrentQuestion>();
        Random random = new Random(System.currentTimeMillis());
        int limit = 1000;
        currentQuestions.add(new CurrentQuestion(getQuestionMock(), random.nextInt(limit) * 1000));
        return currentQuestions;
    }

    private static BasicQuestion getQuestionMock() {
        SingleChoiceQuestion q = new SingleChoiceQuestion();
        q.setCorrectChoice(1);
        q.setAuthorId(PersonMocks.id9);
        ArrayList<String> contents = new ArrayList<String>();
        contents.add("为了更接地气,微软在中国用百度取代了“亲儿子”必应");
        contents.add("为了更接地气,微软在中国用百度取代了“亲儿子”必应");
        contents.add("为了更接地气,微软在中国用百度取代了“亲儿子”必应");
        contents.add("为了更接地气,微软在中国用百度取代了“亲儿子”必应");
        q.setChoiceContents(contents);
        q.setContent("百度已经是中国用户上网操作的使用习惯,甚至是不少中国用户为数不多的几个入口之一。");
        q.setCourseId("zhe bu shi yi ge id");
        q.setQuestionDate(new Date(System.currentTimeMillis()));
        return q;
    }
}
