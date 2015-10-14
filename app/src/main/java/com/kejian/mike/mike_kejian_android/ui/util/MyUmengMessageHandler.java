package com.kejian.mike.mike_kejian_android.ui.util;

import android.content.Context;
import android.util.Log;

import com.umeng.message.UmengMessageHandler;
import com.umeng.message.entity.UMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import com.kejian.mike.mike_kejian_android.dataType.pushMessage.AnnoucPublishPushMessage;
import com.kejian.mike.mike_kejian_android.dataType.pushMessage.AtPushMessage;
import com.kejian.mike.mike_kejian_android.dataType.pushMessage.PushMessage;
import com.kejian.mike.mike_kejian_android.dataType.pushMessage.QuestionNoticePushMessage;
import com.kejian.mike.mike_kejian_android.dataType.pushMessage.IdolQuestionPublishPM;
import com.kejian.mike.mike_kejian_android.dataType.pushMessage.QuestionReplyPushMessage;
import com.kejian.mike.mike_kejian_android.dataType.pushMessage.ReceiverInfo;
import com.kejian.mike.mike_kejian_android.dataType.pushMessage.ReplyCommentPushMessage;
import com.kejian.mike.mike_kejian_android.dataType.pushMessage.ReplyInvitePushMessage;
import com.kejian.mike.mike_kejian_android.dataType.pushMessage.ReplyPraisePushMessage;
import com.kejian.mike.mike_kejian_android.dataType.pushMessage.SenderInfo;
import com.kejian.mike.mike_kejian_android.dataType.pushMessage.UserNoticePushMessage;

/**
 * Created by violetMoon on 2015/10/11.
 */
public class MyUmengMessageHandler extends UmengMessageHandler{

    private static final String TAG = "MyUmengMH";

    private static final String ARG_SENDER_INFO_KEY = "sender_info";

    private static final String ARG_RECEIVER_INFO_KEY = "receiver_info";

    private static final String ARG_TIME = "time";

    private static final String ARG_INF_TYPE = "inf_type";

    private static final String ARG_CONTENT = "content";


//    public void dealWithCustomMessage(Context context, UMessage msg) {
//        SenderInfo senderInfo = null;
//        ReceiverInfo receiverInfo = null;
//        long time = -1l;
//        InfoType infoType = null;
//        String messageContent = null;
//
//        Map<String, String> args = msg.extra;
//        if(args == null) {
//            return;
//        }
//
//        for(Map.Entry<String, String> entry: args.entrySet()) {
//            String value = entry.getValue();
//            switch (entry.getKey()) {
//                case ARG_SENDER_INFO_KEY:
//                    senderInfo = parseSenderInfo(value);
//                    break;
//                case ARG_RECEIVER_INFO_KEY:
//                    receiverInfo = parseReceiverInfo(value);
//                    break;
//                case ARG_TIME:
//                    time = parseTime(value);
//                    break;
//                case ARG_INF_TYPE:
//                    infoType = parseInfoType(value);
//                    break;
//                case ARG_CONTENT:
//                    //有可能因为参数顺序不对然后infoType没有从而不能解析，所以解析决定留到后面
//                    messageContent = value;
//                    break;
//                default:
//                    Log.e(TAG, "unable to parse key " + entry.getKey());
//                    break;
//
//            }
//        }
//
//        PushMessage pushMessage = parseMessageContent(messageContent, infoType);
//        if(pushMessage == null || receiverInfo == null || senderInfo == null) {
//            return;
//        }
//        pushMessage.setReceiverInfo(receiverInfo);
//        pushMessage.setSenderInfo(senderInfo);
//        pushMessage.setTime(time);
//    }
//
//    private SenderInfo parseSenderInfo(String value) {
//        try {
//            JSONObject jObj = new JSONObject(value);
//            String id = jObj.getString(ARG_SENDER_INFO.ID);
//            String name = jObj.getString(ARG_SENDER_INFO.NAME);
//            SenderInfo senderInfo = new SenderInfo();
//            senderInfo.setId(id);
//            senderInfo.setName(name);
//            return senderInfo;
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    private ReceiverInfo parseReceiverInfo(String value) {
//        try {
//            JSONObject jObj = new JSONObject(value);
//            String id = jObj.getString(ARG_RECEIVER_INFO.ID);
//            String name = jObj.getString(ARG_RECEIVER_INFO.NAME);
//            ReceiverInfo receiverInfo = new ReceiverInfo();
//            receiverInfo.setId(id);
//            receiverInfo.setName(name);
//            return receiverInfo;
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return null;
//        }
//
//    }
//
//    private Long parseTime(String valueStr) {
//        try {
//            Long value = Long.parseLong(valueStr);
//            return value;
//        } catch (NumberFormatException e) {
//            return null;
//        }
//    }
//
//    private InfoType parseInfoType(String value) {
//        try {
//            InfoType infoType = InfoType.valueOf(value);
//            return infoType;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    private PushMessage parseMessageContent(String content, InfoType infoType) {
//        if(infoType == null) {
//            Log.e(TAG, "infoType null!");
//            return null;
//        }
//
//        PushMessage pushMessage = null;
//        switch(infoType) {
//            case AT:
//                pushMessage = parseAtPM(content);
//                break;
//            case ANNOUCE_TE:
//                pushMessage = parseAnnoucPublishPM(content);
//                break;
//            case ANNOUCE_AD:
//                //@here
//                pushMessage = parseAnnoucPublishPM(content);
//                break;
//            case ATTENTION:
//                pushMessage = parseQuestionNoticePM(content);
//                break;
//            case COMMENT:
//                pushMessage = parseReplyCommentPM(content);
//                break;
//            case FOLLOW:
//                pushMessage = parseUserNoticePM();
//                break;
//            case LIKE:
//                pushMessage = parseReplyPraisePM(content);
//                break;
//            case IDOL_POST:
//                pushMessage = parseIdolQuestionPublishPM(content);
//                break;
//            case REPLY:
//                pushMessage = parseQuesitonReplyPM(content);
//                break;
//            case INVITE:
//                pushMessage = parseReplyInvitePM(content);
//                break;
//            default:
//                Log.e(TAG, "invalid INF_TYPE " + infoType.toString());
//                return null;
//        }
//
//        return pushMessage;
//    }
//
//    private AtPushMessage parseAtPM(String content) {
//        try {
//            JSONObject jObj = new JSONObject(content);
//            String postId = jObj.getString(ARG_POST_INFO.ID);
//            String postContent = jObj.getString(ARG_POST_INFO.CONTENT);
//            AtPushMessage m = new AtPushMessage();
//            m.setPostContent(postContent);
//            m.setPostId(postId);
//            return m;
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    private AnnoucPublishPushMessage parseAnnoucPublishPM(String content) {
//        try {
//            JSONObject jObj = new JSONObject(content);
//            String annoucId = jObj.getString(ARG_ANNOUC_CONTENT.ID);
//            String annoucContent = jObj.getString(ARG_ANNOUC_CONTENT.CONTENT);
//            String courseId = jObj.getString(ARG_COURSE_INFO.ID);
//            String courseName = jObj.getString(ARG_COURSE_INFO.NAME);
//            AnnoucPublishPushMessage m = new AnnoucPublishPushMessage();
//            m.setAnnoucContent(annoucContent);
//            m.setAnnoucId(annoucId);
//            m.setCourseId(courseId);
//            m.setCourseName(courseName);
//            return m;
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    private QuestionNoticePushMessage parseQuestionNoticePM(String content) {
//        try {
//            JSONObject jObj = new JSONObject(content);
//            String questionTitle = jObj.getString(ARG_QUESTION_INFO.TITLE);
//            String questionContent = jObj.getString(ARG_QUESTION_INFO.CONTENT);
//            String questionId = jObj.getString(ARG_QUESTION_INFO.ID);
//            QuestionNoticePushMessage m = new QuestionNoticePushMessage();
//            m.setQuestionTitle(questionTitle);
//            m.setQuestionContent(questionContent);
//            m.setQuestionId(questionId);
//            return m;
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    private ReplyCommentPushMessage parseReplyCommentPM(String content) {
//        try {
//            JSONObject jObj = new JSONObject(content);
//            String commentId = jObj.getString(ARG_COMMENT.ID);
//            String commentTitle = jObj.getString(ARG_COMMENT.TITLE);
//            String commentContent = jObj.getString(ARG_COMMENT.CONTENT);
//            String questionId = jObj.getString(ARG_QUESTION_INFO.ID);
//            String questionName = jObj.getString(ARG_QUESTION_INFO.NAME);
//            ReplyCommentPushMessage m = new ReplyCommentPushMessage();
//            m.setCommentContent(commentContent);
//            m.setCommentId(commentId);
//            m.setCommentTitle(commentTitle);
//            m.setQuesitonId(questionId);
//            m.setQuestionName(questionName);
//            return m;
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return null;
//        }
//
//    }
//
//    private UserNoticePushMessage parseUserNoticePM() {
//        UserNoticePushMessage m = new UserNoticePushMessage();
//        return m;
//    }
//
//    private ReplyPraisePushMessage parseReplyPraisePM(String content) {
//        try {
//            JSONObject jObj = new JSONObject(content);
//            String replyId = jObj.getString(ARG_REPLY_INFO.ID);
//            String replyName = jObj.getString(ARG_REPLY_INFO.NAME);
//            ReplyPraisePushMessage m = new ReplyPraisePushMessage();
//            m.setReplyId(replyId);
//            m.setReplyName(replyName);
//            return m;
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    private IdolQuestionPublishPM parseIdolQuestionPublishPM(String content) {
//        try {
//            JSONObject jObj = new JSONObject(content);
//            String questionId = jObj.getString(ARG_QUESTION_INFO.ID);
//            String questionTitle = jObj.getString(ARG_QUESTION_INFO.TITLE);
//            String questionContent = jObj.getString(ARG_QUESTION_INFO.CONTENT);
//            IdolQuestionPublishPM m = new IdolQuestionPublishPM();
//            m.setQuestionId(questionId);
//            m.setQuestionContent(questionContent);
//            m.setQuestionTitle(questionTitle);
//            return m;
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    private QuestionReplyPushMessage parseQuesitonReplyPM(String content) {
//        try {
//            JSONObject jObj = new JSONObject(content);
//            String questionId = jObj.getString(ARG_QUESTION_INFO.ID);
//            String questionTitle = jObj.getString(ARG_QUESTION_INFO.TITLE);
//            String questionContent = jObj.getString(ARG_QUESTION_INFO.CONTENT);
//            String replyId = jObj.getString(ARG_REPLY_INFO.ID);
//            String replyContent = jObj.getString(ARG_REPLY_INFO.CONTENT);
//            QuestionReplyPushMessage m = new QuestionReplyPushMessage();
//            m.setQuestionId(questionId);
//            m.setQuestionContent(questionContent);
//            m.setQuestionTitle(questionTitle);
//            m.setReplyContent(replyContent);
//            m.setReplyId(replyId);
//            return m;
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    private ReplyInvitePushMessage parseReplyInvitePM(String content) {
//        try {
//            JSONObject jObj = new JSONObject(content);
//            String id = jObj.getString(ARG_POST_INFO.ID);
//            String name = jObj.getString(ARG_POST_INFO.NAME);
//            ReplyInvitePushMessage m = new ReplyInvitePushMessage();
//            m.setQuestionId(id);
//            m.setQuestionName(name);
//            return m;
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    private static class ARG_SENDER_INFO {
//        public static final String ID = "id";
//        public static final String NAME = "name";
//    }
//
//    private static class ARG_RECEIVER_INFO {
//        public static final String ID = "id";
//        public static final String NAME = "name";
//    }
//
//    private static class ARG_POST_INFO {
//        public static final String ID = "id";
//        public static final String CONTENT = "content";
//        public static final String NAME = "name";
//    }
//
//    private static class ARG_COURSE_INFO {
//        public static final String ID = "id";
//        public static final String NAME = "name";
//    }
//
//    private static class ARG_COMMENT {
//        public static final String ID = "id";
//        public static final String TITLE = "title";
//        public static final String CONTENT = "content";
//    }
//
//    private static class ARG_ANNOUC_CONTENT {
//        public static final String ID = "id";
//        public static final String CONTENT = "content";
//    }
//
//    private static class ARG_QUESTION_INFO {
//        public static final String ID = "id";
//        public static final String TITLE = "title";
//        public static final String CONTENT = "content";
//        public static final String NAME = "name";
//    }
//
//    private static class ARG_REPLY_INFO {
//        public static final String ID = "id";
//        public static final String NAME = "name";
//        public static final String CONTENT = "content";
//    }
//
//    private static enum InfoType {
//        AT, ANNOUCE_TE, ANNOUCE_AD, ATTENTION, COMMENT, FOLLOW, LIKE, IDOL_POST, REPLY,
//        INVITE
//    }
}
