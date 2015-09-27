package model.course.data;

import java.util.ArrayList;
import java.util.Date;

import model.campus.Post;
import model.campus.Reply;

/**
 * Created by violetMoon on 2015/9/12.
 */
public class PostMocks {
    private static Post post1;
    private static Post post2;
    private static Post post3;
    private static Post post4;

    static {
        init();
    }

    private static void init() {
        initPost1();
        initPost2();
        initPost3();
        initPost4();
    }

    private static void initPost1() {
        post1 = new Post();
        post1.setDate(new Date(115, 8, 11, 15, 12, 8));
        post1.setContent("女神：用一句话来形容我的长相。");
        post1.setPraise(1553);

        ArrayList<Reply> replys = new ArrayList<Reply>();

        Reply reply1 = new Reply();
        reply1.setUserId(PersonMocks.id4);
        reply1.setPraise(111);
        reply1.setContent("已撸");
        reply1.setDate(new Date(115, 8, 11, 15, 14, 12));
        reply1.setSubReplyList(null);
        replys.add(reply1);

        Reply reply2 = new Reply();
        reply2.setUserId(PersonMocks.id5);
        reply2.setPraise(128);
        reply2.setContent("我石更了");
        reply2.setDate(new Date(115, 8, 11, 16, 14, 27));
        reply2.setSubReplyList(null);
        replys.add(reply2);

        Reply reply3 = new Reply();
        reply3.setUserId(PersonMocks.id6);
        reply3.setPraise(64);
        reply3.setContent("人的身上有206根骨头，跟你在一起我有207根");
        reply3.setDate(new Date(115, 8, 12));
        reply3.setSubReplyList(null);
        replys.add(reply3);

        post1.setReplyList(replys);
        post1.setTitle("屌丝们快进来");
        post1.setUserId(PersonMocks.name9);
        post1.setAuthorName(PersonMocks.name1);
    }

    private static void initPost2() {
        post2 = new Post();
        post2.setDate(new Date(115, 8, 11, 11, 10, 5));
        post2.setContent("鸭怎么叫啊");
        post2.setPraise(1224);

        ArrayList<Reply> replys = new ArrayList<Reply>();

        Reply reply1 = new Reply();
        reply1.setUserId(PersonMocks.id11);
        reply1.setPraise(11);
        reply1.setContent("嘎嘎嘎嘎");
        reply1.setDate(new Date(115, 8, 11, 21, 14, 15));
        reply1.setSubReplyList(null);
        replys.add(reply1);

        Reply reply2 = new Reply();
        reply2.setUserId(PersonMocks.id1);
        reply2.setPraise(18);
        reply2.setContent("羊呢？");
        reply2.setDate(new Date(115, 8, 11, 23, 24, 11));
        reply2.setSubReplyList(null);
        replys.add(reply2);

        Reply reply3 = new Reply();
        reply3.setUserId(PersonMocks.id11);
        reply3.setPraise(43);
        reply3.setContent("咩咩咩咩");
        reply3.setDate(new Date(115, 8, 13, 8, 14, 55));
        reply3.setSubReplyList(null);
        replys.add(reply3);

        Reply reply4 = new Reply();
        reply4.setUserId(PersonMocks.id1);
        reply4.setPraise(54);
        reply4.setContent("鸡呢？");
        reply4.setDate(new Date(115, 8, 13, 13, 14, 41));
        reply4.setSubReplyList(null);
        replys.add(reply4);

        Reply reply5 = new Reply();
        reply5.setUserId(PersonMocks.id11);
        reply5.setPraise(233);
        reply5.setContent("啊！啊！不要！啊！就是这样！");
        reply5.setDate(new Date(115, 8, 13, 13, 44, 2));
        reply5.setSubReplyList(null);
        replys.add(reply5);

        Reply reply6 = new Reply();
        reply6.setUserId(PersonMocks.id1);
        reply6.setPraise(33);
        reply6.setContent("你给我滚出去!");
        reply6.setDate(new Date(115, 8, 13, 13, 44, 12));
        reply6.setSubReplyList(null);
        replys.add(reply5);

        post2.setReplyList(replys);
        post2.setTitle("小明滚进来!!!");
        post2.setUserId(PersonMocks.id1);
        post2.setAuthorName(PersonMocks.name1);
    }

    private static void initPost3() {
        post3 = new Post();
        post3.setDate(new Date(115, 8, 13, 11, 10, 5));
        post3.setContent("我叫李晓明，不是那个小明，你们这群渣渣.");
        post3.setPraise(1131);

        ArrayList<Reply> replys = new ArrayList<Reply>();

        Reply reply1 = new Reply();
        reply1.setUserId(PersonMocks.id7);
        reply1.setPraise(11);
        reply1.setContent("被洪水卷走的一瞬间");
        reply1.setDate(new Date(115, 8, 13, 11, 14, 15));
        reply1.setSubReplyList(null);
        replys.add(reply1);

        Reply reply2 = new Reply();
        reply2.setUserId(PersonMocks.id8);
        reply2.setPraise(11);
        reply2.setContent("被洪水卷走的一瞬间");
        reply2.setDate(new Date(115, 8, 13, 11, 24, 15));
        reply2.setSubReplyList(null);
        replys.add(reply2);

        Reply reply3 = new Reply();
        reply3.setUserId(PersonMocks.id9);
        reply3.setPraise(233);
        reply3.setContent("小明拉住了本已脱险的女友...");
        reply3.setDate(new Date(115, 8, 13, 13, 24, 11));
        reply3.setSubReplyList(null);
        replys.add(reply3);

        post3.setReplyList(replys);
        post3.setTitle("你们这群渣渣");
        post3.setUserId(PersonMocks.id11);
        post3.setAuthorName(PersonMocks.name11);
    }

    private static void initPost4() {
        post4 = new Post();
        post4.setContent("那人怎么样啊？");
        post4.setPraise(2333);

        ArrayList<Reply> replys = new ArrayList<Reply>();

        Reply reply1 = new Reply();
        reply1.setUserId(PersonMocks.id7);
        reply1.setPraise(11);
        reply1.setContent("长得不行");
        reply1.setDate(new Date(115, 8, 13, 12, 14, 15));
        reply1.setSubReplyList(null);
        replys.add(reply1);

        Reply reply2 = new Reply();
        reply2.setUserId(PersonMocks.id6);
        reply2.setPraise(11);
        reply2.setContent("强势插入!");
        reply2.setDate(new Date(115, 8, 13, 12, 24, 15));
        reply2.setSubReplyList(null);
        replys.add(reply2);

        Reply reply3 = new Reply();
        reply3.setUserId(PersonMocks.id10);
        reply3.setPraise(233);
        reply3.setContent("那你怎么不分手啊...");
        reply3.setDate(new Date(115, 8, 13, 12, 26, 11));
        reply3.setSubReplyList(null);
        replys.add(reply3);

        Reply reply4 = new Reply();
        reply4.setUserId(PersonMocks.id7);
        reply4.setPraise(2333);
        reply4.setContent("哎呀，他真的长得不行！");
        reply4.setDate(new Date(115, 8, 13, 13, 24, 11));
        reply4.setSubReplyList(null);
        replys.add(reply4);

        Reply reply5 = new Reply();
        reply5.setUserId(PersonMocks.id9);
        reply5.setPraise(43);
        reply5.setContent("233333....");
        reply5.setDate(new Date(115, 8, 13, 14, 25, 31));
        reply5.setSubReplyList(null);
        replys.add(reply5);

        post4.setReplyList(replys);
        post4.setTitle("乖孩子召唤闺蜜好孩子");
        post4.setUserId(PersonMocks.id10);
        post4.setAuthorName(PersonMocks.name10);
        post4.setDate(new Date(115, 8, 13, 11, 25, 24));
    }

    public static Post getPost1() {
        return post1;
    }

    public static Post getPost2() {
        return post2;
    }

    public static Post getPost3() {
        return post3;
    }

    public static Post getPost4() {
        return post4;
    }

    public static ArrayList<Post> getAllPosts() {
        ArrayList<Post> posts = new ArrayList<Post>();
        posts.add(post1);
        posts.add(post2);
        posts.add(post3);
        posts.add(post4);
        return posts;
    }
}
