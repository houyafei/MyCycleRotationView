package com.cyclerotationview.hyf.application20161228;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/28.
 */
public class CycleRotationView extends RelativeLayout {


    /*
* Viewpager
* */
    private ViewPager mViewPager ;

    /*
    * text
    * show the prompt or title of per image
    * */
    private TextView mTextView ;

    /*
    * linearLayout
    * show the points
    * */
    private LinearLayout mLinearlayout ;


    /*
    * added image
    * */
    private int[] mImages ;

    /*
    * save the imageView
    * */
    private ArrayList<ImageView> mImageViewsList = new ArrayList<>();

    /*
    * context
    * */
    private Context mContext ;

    private static int pointWidth = 20 ;
    private static int pointheight = 20 ;

    /*
    * 异步线程
    * */
    private Handler mHandler;

    public CycleRotationView(Context context) {
        super(context);

    }

    public CycleRotationView(Context context, AttributeSet attrs) {
        super(context,attrs);
        mContext = context ;
        mHandler = new Handler();
        initView(context);
    }



    public void openCycleView() {
        //添加图片
        addImageView(mImages,mContext);

        //添加小圆点
        addPointGroup(mLinearlayout, mContext, mImages);

        viewPagerAddAdapter(mViewPager);
    }

    /**
     *
     * @param mViewPager
     */
    private void viewPagerAddAdapter(ViewPager mViewPager) {
        if(mViewPager==null) return;
        mViewPager.setAdapter(new CycleAdapter());

        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            int lastPosition = 0;

            @Override
            public void onPageSelected(int position) {
                position = position % mImageViewsList.size();

                mLinearlayout.getChildAt(position).setSelected(true);

                mLinearlayout.getChildAt(lastPosition).setSelected(false);

                lastPosition = position;

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }

    /**
     * added the point groups
    **/
    private void addPointGroup(LinearLayout mLinearlayout, Context context, int[] mImages) {
        if(mLinearlayout==null ||
                context==null ||
                mImages==null) return ;

        for (int i = 0; i < mImages.length; i++) {
            ImageView point = new ImageView(context);
            point.setImageResource(R.drawable.shape_selector);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dp2px(pointWidth),dp2px(pointheight));

            if(i>0){
                params.leftMargin = dp2px(4);
                point.setSelected(false);
            }else{
                point.setSelected(true);
            }

            point.setLayoutParams(params);
            mLinearlayout.addView(point);
        }
    }

    /**
     *
      * @param mImages
     * @param context
     */
    private void addImageView(int[] mImages,Context context) {

        if(mImages==null) return ;

        for (int i=0;i<mImages.length;i++){
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(mImages[i]);
            mImageViewsList.add(imageView);
        }

    }

    public void setmImages(int[] mImages) {
        this.mImages = mImages;
    }

    public void startAutoCycle(){
        if(mImages==null||
                mContext==null) return ;
        timerTask() ;
    }
    /*
    * initial the views
    * */
    private void initView(Context mContext) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.cycle_view_layout, this, true);

        mViewPager = (ViewPager) view.findViewById(R.id.id_viewpager);

        mTextView = (TextView) view.findViewById(R.id.id_text_title);

        mLinearlayout = (LinearLayout) view.findViewById(R.id.id_layout_point);
    }
    /**
     * 定时任务
     */
    private void timerTask() {

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                int currentItem = mViewPager.getCurrentItem();

                if(currentItem==mViewPager.getAdapter().getCount()-1){
                    mViewPager.setCurrentItem(1);

                }else{
                    mViewPager.setCurrentItem(currentItem+1);
                }

                mHandler.postDelayed(this,3000);
                // 不断给自己发消息
               // mHandler.postDelayed(this, 3000);
            }
        }, 3000);
    }

    private class CycleAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            position = position % mImageViewsList.size();

            final View view = mImageViewsList.get(position);

            if(view.getParent()!=null){
                container.removeView(view);
            }
            container.addView(mImageViewsList.get(position));
            return mImageViewsList.get(position);

        }

        @Override
        public void destroyItem(View container, int position, Object object) {
            //super.destroyItem(container, position, object);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }
    /**
     * 将 dp 转换成 px
     * @param dp
     * @return
     */
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }


}
