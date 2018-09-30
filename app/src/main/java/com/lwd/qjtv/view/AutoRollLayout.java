package com.lwd.qjtv.view;
/*
 需求
 A 轮播图的作用就是显示图片和标题
 B 显示图片
 C 设置标题
 D 设置圆点
 E 定时滚动
 F 对外暴露方法，让外界控制是否滚动
 G 让外部知道当前被点击的条目的角标
 分析
 A 要提供方法，让外界传入要显示的内容的集合 // 使用接口
 B ViewPager 设置adapter
 C 可以对viewpager设置变化监听，在监听中改变textview的标题
 D 在setItems方法中往容器里添加一些圆点，在opcl中改变圆点的enable状态
 E 使用handler，postDelay，在Runnable中改变viewpager的当前条目
 F 添加一个方法和成员变量，在runnable中滚动逻辑进行判断，
 G 通过手势识别器，可以获得点击事件，在点击事件中，可以获得viewpager的当前角标，需要先定义回调接口，让外界事件接口，并传入接口对象，在合适的时机调用对象上的方法即可
 实现
 A
 定义一个接口IShowItem出来。有两个方法：获取图片路径、获取标题
 setItems(List<IShowItem> items) //
 B 
 看代码
 C
 1 可以对viewpager设置变化监听
 2 在监听的方法中改变textview的标题

 D 
 1  在setItems方法中往容器里添加一些圆点
 2 在opcl中改变圆点的enable状态

 E
 1 定义handler
 2 定义runnable
 3 在init方法时候触发
 F
 1 添加成员量和方法
 2 修改runnable的逻辑

 // Universal Image Loader的使用
 1 构造对象 ImageLoader.getInstance()
 2 配置ImageLoader (imageLoader.init(ImageLoaderConfiguration.createDefault(getContext()));)
 3 displayImage
G 
1 定义回调接口 OnItemClickListener
2 提供设置接口对象的方法 setOnItemClickListener
3 在合适的时机调用对象上的方法  onSingleTapUp
4 在外界实现逻辑

 对触摸事件机制的回顾：
 如果对一个view设置了onTouchListener
 1 如果onTouch方法返回了true，view自身的onTouchEvent方法不会被执行

 */

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.lidroid.xutils.BitmapUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.lwd.qjtv.R;
import com.webber.autorolllayout.DecoratorViewPager;
import com.webber.autorolllayout.TouchDownOrUpListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class AutoRollLayout extends FrameLayout {
    private BitmapUtils bitmapUtils;
    private TouchDownOrUpListener listener;

    public static interface OnItemClickListener {
        void onItemClick(int index);
    }

    public static interface IShowItem {
        // 将会被显示到viewpager上面
        String getImageUrl();

        // 将会被显示到textview上面
        String getTitle();
    }

    protected static final long ROLL_DELAY = 3000;

    List<? extends IShowItem> item;
    List<IShowItem> items = new ArrayList<>();
    private DecoratorViewPager viewpager;
    private TextView titleTv;
    private LinearLayout dotContainer;
    private static Handler handler = new Handler();
    boolean allowAutoRoll = true;
    OnItemClickListener oicl;
    private Context mContext;

    private boolean isInit = true;

    private ImageLoader imageLoader;

    private DisplayImageOptions options;

    public AutoRollLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        /*bitmapUtils=new_pic BitmapUtils(context);*/
        initImageloader();
        init();
    }

    float y;
    float x;

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                    y = event.getY();
//                    x = event.getY();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                float moveX = event.getX();
//                float moveY = event.getY();
//                float  dx  = moveX-x;
//                float dY  = moveY-y;
//                if(Math.abs(dY)>Math.abs(dx)&&Math.abs(dY)>50){
//                    return  true;
//                }
//                break;
//            case MotionEvent.ACTION_UP:
//                break;
//            default:
//        }
//
//        return super.onTouchEvent(event);
//    }

    private void initImageloader() {
        imageLoader = ImageLoader.getInstance();
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration
                .createDefault(mContext);
        imageLoader.init(configuration);
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.video_place_holder) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.video_place_holder) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.video_place_holder) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
                // .displayer(new_pic RoundedBitmapDisplayer(20)) // 设置成圆角图片
                .build();

    }

    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    private static class AnimateFirstDisplayListener extends
            SimpleImageLoadingListener {

        static final List<String> displayedImages = Collections
                .synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view,
                                      Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                // 是否第一次显示
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    // 图片淡入效果
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                }
            }
        }
    }

    public AutoRollLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public AutoRollLayout(Context context) {
        this(context, null);

    }

    public void setOnItemClickListener(OnItemClickListener oicl) {
        this.oicl = oicl;
    }

    private void init() {
        View.inflate(getContext(), R.layout.layout_auto_roll, this);
        viewpager = (DecoratorViewPager) findViewById(R.id.vp);
        titleTv = (TextView) findViewById(R.id.tv_info_title);
        dotContainer = (LinearLayout) findViewById(R.id.ll_bottom);

        viewpager.setAdapter(pagerAdapter);
        viewpager.setOnTouchListener(viewPageOtl);
        viewpager.setOnPageChangeListener(opcl);
        gestureDetector = new GestureDetector(getContext(), ogl);
    }


    public void setItems(List<IShowItem> items) {
        this.items = items;
        int dotWithd = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 6, getResources()
                        .getDisplayMetrics());
        // 添加前先移除所有的

        dotContainer.removeAllViews();
        // 添加点
        if (items == null) {
            return;
        }
        for (IShowItem iShowItem : items) {
            View dot = new View(getContext());
            dot.setBackgroundResource(R.drawable.dot);
            // 指定view的宽高
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    dotWithd, dotWithd);
            // 指定view的右边距
            lp.rightMargin = dotWithd;
            dotContainer.addView(dot, lp);
        }
        allowAutoRoll = true;
        pagerAdapter.notifyDataSetChanged();
        if (isInit) {
            viewpager.setCurrentItem(Integer.MAX_VALUE / 2);
            isInit = false;
        }
        opcl.onPageSelected(Integer.MAX_VALUE / 2);
        handler.postDelayed(rollRunnable, ROLL_DELAY);
    }

    public void setAllowAutoRoll(boolean allowAutoRoll) {
        this.allowAutoRoll = allowAutoRoll;
        handler.postDelayed(rollRunnable, ROLL_DELAY);
    }

    Runnable rollRunnable = new Runnable() {
        @Override
        public void run() {
            // 避免重复调用，只保证有一个正在被执行，把所有没有来得及执行任务的都删除掉
            handler.removeCallbacks(this);

            // 如果发现不允许自动滚动了就直接返回
            if (!allowAutoRoll) {
                return;
            }
            // 获取viewpager的当前页面角标
            int currentIndex = viewpager.getCurrentItem();
            // 角标+1，如果已经是最后一个了，就是0
            int next = 0;
            if (currentIndex == pagerAdapter.getCount() - 1) {
                next = 0;
            } else {
                next = currentIndex + 1;
            }
            // 改变viewpager的当前条目
            viewpager.setCurrentItem(next, true);
            // 发布延时任务，再次改变当前页面
            handler.postDelayed(this, ROLL_DELAY);

        }
    };

    public List<IShowItem> getItemList() {
        items = new ArrayList<>();
        return items;
    }

    public void notifyPage() {
        if (pagerAdapter != null) {
            pagerAdapter.notifyDataSetChanged();
            this.setItems(items);
        }
    }

    private PagerAdapter pagerAdapter = new PagerAdapter() {

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public int getCount() {
            return items == null || items.size() == 0 ? 0 : Integer.MAX_VALUE;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // 需要构造出ImageView？
            ImageView imageView = new ImageView(container.getContext());
//            RoundImageViewTo iv = new_pic RoundImageViewTo(container.getContext());
//            iv.setType(RoundImageViewTo.TYPE_ROUND);
//            iv.setBorderRadius(5);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//            Glide.with(mContext).load(items.get(position % items.size()).getImageUrl())
//                    .placeholder(R.drawable.loding_auto)
//                    .into(iv);
            imageLoader.displayImage(items.get(position % items.size()).getImageUrl(),
                    imageView, options, animateFirstListener);
            //bitmapUtils.display(iv, items.get(position%items.size()).getImageUrl());
            container.addView(imageView);
            return imageView;
        }

    };

    private ViewPager.OnPageChangeListener opcl = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            if (items.size() == 0)
                return;
            titleTv.setText(items.get(position % items.size()).getTitle());
            // 把当前圆点设置成红的,要把原来的设置成白色的
            for (int i = 0; i < items.size(); i++) {
                View view = dotContainer.getChildAt(i);
                if (view != null) {
                    view.setEnabled(i != position % items.size());
                }
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    // 不用去继承VIewpager，就可以重写它的onTouchEvent方法
    private OnTouchListener viewPageOtl = new OnTouchListener() {

        // v 就是被触摸的哪个view，也就是被设置OnTouchListener的view --》viewpager
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            // 把触摸时间交给内奸分析
            gestureDetector.onTouchEvent(event);
            // 可以获得到viewpager的所有触摸事件
            // 根据不同的触摸事件类型，进行不同的操作
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: // 用户开始触摸了 ，，应该停止自动滚动
                    handler.removeCallbacks(rollRunnable);
                    if (listener != null) {
                        listener.onDownListener(event);
                    }
                    break;
                case MotionEvent.ACTION_UP: // 用户手指离开屏幕,, 应该恢复自动滚动（原来是停的，你摸完之后，）
                    handler.postDelayed(rollRunnable, ROLL_DELAY);
                    if (listener != null) {
                        listener.onUpListener(event);
                    }
                    break;
                case MotionEvent.ACTION_CANCEL:
                    if (listener != null) {
                        listener.onUpListener(event);
                    }
                    break;
            }

            // 原来触摸事件怎么走就怎么走
            // v.onTouchEvent(event);
            // return true;
            // 上面的代码和下面的意思是一样的
            return false;
        }
    };

    public void setOnTouchDownOrUpListener(TouchDownOrUpListener listener) {
        this.listener = listener;
    }

    private GestureDetector gestureDetector;

    private GestureDetector.OnGestureListener ogl = new GestureDetector.OnGestureListener() {

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
//			Toast.makeText(getContext(), "onSingleTapUp",0).show();
            // 检查当前viewpager显示的页面的角标
            // 想方设法通知外界
            if (oicl != null) {
                oicl.onItemClick(viewpager.getCurrentItem());
            }
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                                float distanceY) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            return false;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }
    };

    public void setNestedpParent(ViewGroup parent) {
        viewpager.setNestedpParent(parent);
    }

    public void onDestroy() {
        this.mContext = null;
    }
}
