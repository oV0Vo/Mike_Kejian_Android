package com.kejian.mike.mike_kejian_android.ui.campus;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kejian.mike.mike_kejian_android.R;

import java.util.ArrayList;

import bl.CampusBLService;
import model.campus.Post;
import model.message.Reply;
import util.DensityUtil;

/**
 * Created by showjoy on 15/9/17.
 */
public class LatestPostListFragment extends Fragment{
    private View view;
    private LinearLayout container;
    private Activity ctx;
    private ArrayList<Post> posts = new ArrayList<Post>();
    private int post_num = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_latest_post_list, null);
        ctx = this.getActivity();
        this.container = (LinearLayout) view.findViewById(R.id.latest_post_container);
        initData();
        initViews();
        return view;

    }

    private void initData(){
        this.posts = CampusBLService.getLatestPostList();
        this.post_num = this.posts.size();
    }
    private void initViews(){
        for(int i = 0;i<this.post_num;i++){
            this.container.addView(this.genLineSplitView());
            this.container.addView(this.genMentionLayout(this.posts.get(i)));
        }

    }

    private View genLineSplitView(){
        View lineView = new View(ctx);
        LinearLayout.LayoutParams layout_line = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,30);
        lineView.setBackgroundResource(R.color.white);
        lineView.setLayoutParams(layout_line);
        lineView.getBackground().setAlpha(0);
        return lineView;
    }




    private LinearLayout genMentionLayout(Post post){
        int dp15 = DensityUtil.dip2px(ctx, 15);
        int dp5 = DensityUtil.dip2px(ctx,5);
        int dp40 = DensityUtil.dip2px(ctx,40);
        int dp10 = DensityUtil.dip2px(ctx,10);

        LinearLayout postLayout = new LinearLayout(ctx);
        postLayout.setOrientation(LinearLayout.HORIZONTAL);
        postLayout.setBackgroundResource(R.drawable.setting_item_selector);
        LinearLayout.LayoutParams layout_linear = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        postLayout.setLayoutParams(layout_linear);

        ImageView userImage = new ImageView(ctx);
        LinearLayout.LayoutParams layout_image = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layout_image.gravity = Gravity.TOP;
        layout_image.setMargins(dp15, dp10, 0, 0);
        userImage.setImageResource(R.drawable.bad_sad);
        userImage.setMaxHeight(dp40);
        userImage.setMaxWidth(dp40);
        userImage.setAdjustViewBounds(true);
        userImage.setLayoutParams(layout_image);

        LinearLayout text = new LinearLayout(ctx);
        LinearLayout.LayoutParams layout_text = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layout_text.gravity = Gravity.RIGHT;
        layout_text.setMargins(dp15, 0, 0, 0);
        text.setOrientation(LinearLayout.VERTICAL);
        text.setLayoutParams(layout_text);

        LinearLayout firstLineStr = new LinearLayout(ctx);
        LinearLayout.LayoutParams layout_firstLine = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        firstLineStr.setOrientation(LinearLayout.HORIZONTAL);
        layout_firstLine.setMargins(0, dp10, 0, 0);
        firstLineStr.setLayoutParams(layout_firstLine);

        TextView title_view = new TextView(ctx);
        LinearLayout.LayoutParams layout_title = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        title_view.setText(post.getTitle());
        title_view.setTextColor(getResources().getColor(R.color.black));
        title_view.setTextSize(18);
        title_view.setLayoutParams(layout_title);

        LinearLayout secondLineStr = new LinearLayout(ctx);
        LinearLayout.LayoutParams layout_secondLine = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        secondLineStr.setOrientation(LinearLayout.HORIZONTAL);
        secondLineStr.setLayoutParams(layout_secondLine);

        TextView time_view = new TextView(ctx);
        LinearLayout.LayoutParams layout_time = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layout_time.gravity = Gravity.LEFT;
        time_view.setText("19:07 today");
        time_view.setTextSize(10);
        time_view.setLayoutParams(layout_time);

        LinearLayout thirdLineStr = new LinearLayout(ctx);
        LinearLayout.LayoutParams layout_thirdLine = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layout_thirdLine.setMargins(0, dp10, 0, 0);
        thirdLineStr.setOrientation(LinearLayout.HORIZONTAL);
        thirdLineStr.setLayoutParams(layout_thirdLine);

        TextView postContent = new TextView(ctx);
        LinearLayout.LayoutParams layout_post = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        postContent.setText(post.getContent());
        postContent.setTextColor(getResources().getColor(R.color.grey));
        postContent.setLayoutParams(layout_post);

        LinearLayout lastLineStr = new LinearLayout(ctx);
        LinearLayout.LayoutParams layout_lastLine = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layout_lastLine.setMargins(0, dp5, 0, 0);
        lastLineStr.setOrientation(LinearLayout.HORIZONTAL);
        lastLineStr.setHorizontalGravity(Gravity.RIGHT);
        lastLineStr.setLayoutParams(layout_lastLine);

        ImageView praise_image_view = new ImageView(ctx);
        LinearLayout.LayoutParams layout_praise_image = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layout_praise_image.gravity = Gravity.CENTER;
        praise_image_view.setLayoutParams(layout_praise_image);
        praise_image_view.setImageResource(R.drawable.up);
        praise_image_view.setMaxHeight(dp15);
        praise_image_view.setMaxWidth(dp15);
        praise_image_view.setAdjustViewBounds(true);

        TextView praise_num_view = new TextView(ctx);
        LinearLayout.LayoutParams layout_praise_num = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layout_praise_num.setMargins(dp5, 0, dp15, 0);
        praise_num_view.setText(Integer.toString(post.getPraise()));
        praise_num_view.setTextColor(getResources().getColor(R.color.grey));
        praise_num_view.setLayoutParams(layout_praise_num);

        ImageView comment_image_view = new ImageView(ctx);
        LinearLayout.LayoutParams layout_comment_image = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layout_comment_image.gravity = Gravity.CENTER;
        comment_image_view.setLayoutParams(layout_comment_image);
        comment_image_view.setImageResource(R.drawable.comment1);
        comment_image_view.setMaxHeight(dp15);
        comment_image_view.setMaxWidth(dp15);
        comment_image_view.setAdjustViewBounds(true);

        TextView comment_num_view = new TextView(ctx);
        LinearLayout.LayoutParams layout_comment_num = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layout_comment_num.setMargins(dp5, 0, dp15, 0);
        comment_num_view.setText(Integer.toString(post.getReplyList().size()));
        comment_num_view.setTextColor(getResources().getColor(R.color.grey));
        comment_num_view.setLayoutParams(layout_comment_num);

        lastLineStr.addView(praise_image_view);
        lastLineStr.addView(praise_num_view);
        lastLineStr.addView(comment_image_view);
        lastLineStr.addView(comment_num_view);


        thirdLineStr.addView(postContent);

        secondLineStr.addView(time_view);

        firstLineStr.addView(title_view);

        text.addView(firstLineStr);
        text.addView(secondLineStr);
        text.addView(thirdLineStr);
        text.addView(lastLineStr);

        postLayout.addView(userImage);
        postLayout.addView(text);
        return postLayout;
    }

}
