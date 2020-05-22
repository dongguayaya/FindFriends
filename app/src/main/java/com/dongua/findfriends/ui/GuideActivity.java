package com.dongua.findfriends.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.dongua.findfriends.R;
import com.dongua.findfriends.test.TestActivity;
import com.dongua.framework.base.BasePageAdapter;
import com.dongua.framework.manager.MediaPlayerManager;
import com.dongua.framework.utils.AnimUtils;

import java.util.ArrayList;
import java.util.List;


    /**
      * 引导页
           * 1.ViewPager:适配器
           * 2.小圆点的逻辑
           * 3.歌曲的播放
           * 4.属性动画旋转
           * 5.跳转
           * @param
     */



public class GuideActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager vpScoll;
    private TextView tvGuideSkip;
    private ImageView iconMusic;
    private ImageView ivGuidePoint1;
    private ImageView ivGuidePoint2;
    private ImageView ivGuidePoint3;
    private BasePageAdapter mPageAdapter;
    private ImageView iv_guide_star;
    private ImageView iv_guide_night;
    private ImageView iv_guide_smile;
    private MediaPlayerManager mGuideMusic;
    private ObjectAnimator mAnim;
    private View view1;
    private View view2;
    private View view3;
    private List<View> mPageList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        initView();
    }

    private void initView() {
        vpScoll = (ViewPager) findViewById(R.id.vp_scoll);
        tvGuideSkip = (TextView) findViewById(R.id.tv_guide_skip);
        ivGuidePoint1 = (ImageView) findViewById(R.id.iv_guide_point_1);
        ivGuidePoint2 = (ImageView) findViewById(R.id.iv_guide_point_2);
        ivGuidePoint3 = (ImageView) findViewById(R.id.iv_guide_point_3);
        iconMusic=findViewById(R.id.iv_icon_music);

        iconMusic.setOnClickListener(this);
        tvGuideSkip.setOnClickListener(this);


        view1=View.inflate(this,R.layout.layout_pager_guide_1,null);
        view2=View.inflate(this,R.layout.layout_pager_guide_2,null);
        view3=View.inflate(this,R.layout.layout_pager_guide_3,null);
        mPageList.add(view1);
        mPageList.add(view2);
        mPageList.add(view3);

        //预加载
        vpScoll.setOffscreenPageLimit(mPageList.size());

        mPageAdapter=new BasePageAdapter(mPageList);
        vpScoll.setAdapter(mPageAdapter);

        //帧动画
        iv_guide_star=view1.findViewById(R.id.iv_guide_star);
        iv_guide_night=view2.findViewById(R.id.iv_guide_night);
        iv_guide_smile=view3.findViewById(R.id.iv_guide_smile);
        ivGuidePoint1.setImageResource(R.drawable.pointer_pink);
        //播放帧动画
        AnimationDrawable animStar=(AnimationDrawable)iv_guide_star.getBackground();
        animStar.start();
        AnimationDrawable animNight=(AnimationDrawable)iv_guide_night.getBackground();
        animNight.start();
        AnimationDrawable animSmile=(AnimationDrawable)iv_guide_smile.getBackground();
        animSmile.start();

        //小圆点的逻辑
        vpScoll.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                seletePoint(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //歌曲的逻辑
        startMusic();
    }

    /**
     * 播放音乐
     */
    private void startMusic() {
        mGuideMusic=new MediaPlayerManager();
        mGuideMusic.setLooping(true);
        AssetFileDescriptor file=getResources().openRawResourceFd(R.raw.guide);
        mGuideMusic.startPlay(file);
        //旋转动画
        mAnim= AnimUtils.rotation(iconMusic);
        mAnim.start();
    }

    /**
     * 动态选择小圆点
     * @param position
     */
    private void seletePoint(int position) {
        switch (position){
            case 0:
                ivGuidePoint1.setImageResource(R.drawable.pointer_pink);
                ivGuidePoint2.setImageResource(R.drawable.pointer);
                ivGuidePoint3.setImageResource(R.drawable.pointer);
                break;
            case 1:
                ivGuidePoint1.setImageResource(R.drawable.pointer);
                ivGuidePoint2.setImageResource(R.drawable.pointer_pink);
                ivGuidePoint3.setImageResource(R.drawable.pointer);
                break;
            case 2:
                ivGuidePoint1.setImageResource(R.drawable.pointer);
                ivGuidePoint2.setImageResource(R.drawable.pointer);
                ivGuidePoint3.setImageResource(R.drawable.pointer_pink);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_icon_music:
                if(mGuideMusic.MEDIA_STATUS==MediaPlayerManager.MEDIA_STATUS_PAUSE){
                    mAnim.start();
                    mGuideMusic.continuePlay();
                    iconMusic.setImageResource(R.drawable.music);
                }else if(mGuideMusic.MEDIA_STATUS==MediaPlayerManager.MEDIA_STATUS_PLAY){
                    mAnim.pause();
                    mGuideMusic.pausePlay();
                    iconMusic.setImageResource(R.drawable.musicblack);
                }
                break;
            case R.id.tv_guide_skip:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
        }
    }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            mGuideMusic.stopPlay();
        }
    }