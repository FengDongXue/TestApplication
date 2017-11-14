package com.lanwei.governmentstar.view;

import android.animation.ArgbEvaluator;
import android.animation.FloatEvaluator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 *
 * 2017/1/18  9:30
 */
//自定义的侧拉菜单
//因为内容页覆盖在菜单页上面的，所以可以直接继承Framlayout
public class SlidingMenu extends FrameLayout {

    private View menu;
    private View main;
    private ViewDragHelper viewDragHelper;
    /**
     * 滑动的最大范围
     */
    private int maxLeft;
    //private Scroller scroller;

    public SlidingMenu(Context context) {
        //super(context);
        this(context,null);
    }

    public SlidingMenu(Context context, AttributeSet attrs) {
        //super(context, attrs);
        this(context,attrs,-1);
    }

    public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //在布局文件中加载控件的时候，只要加载到控件的头标签就会调用控件的构造函数，所以这个时候是不知道控件有几个子控件的

        //2.1.创建ViewDrageHelper
        //参数1：子控件的父控件
        //参数2：触摸事件的回调
        viewDragHelper = ViewDragHelper.create(this,callback);

    }

    //1.获取自定义控件的子控件，方便实现滑动效果
    /**
     * 当加载完布局文件中的控件的结束标签的时候，调用的方法，表示布局文件加载完控件，
     * 这个时候是没有执行测量排版操作的，所以是获取不到宽高
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ////根据子控件在布局文件中的索引，获取子控件的view对象
        menu = getChildAt(0);
        main = getChildAt(1);
    }
    //2.实现滑动内容页效果，viewDrageHelper
    //2.2.创建ViewDrageHelper触摸事件回调
    ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {

        /**
         * 设置是否可以捕获view的触摸事件
         * @param child  触摸的控件的view对象
         * @param pointerId 多点触摸的索引
         * @return  true:捕获  false:不捕获
         */
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return true;
        }

        /**
         * 捕获触摸事件调用的方法
         * @param capturedChild  捕获触摸事件的控件的view对象
         * @param activePointerId 捕获的多点触摸的索引
         */
        @Override
        public void onViewCaptured(View capturedChild, int activePointerId) {
            super.onViewCaptured(capturedChild, activePointerId);
            Log.e("AAA", "被触摸了...");
        }

        /**
         * 鸡肋方法，设置是否强制进行水平滑动，如果要强制水平滑动，返回大于0任意值
         * @param child
         * @return
         */
        @Override
        public int getViewHorizontalDragRange(View child) {
            return 1;
        }

        /**
         * 水平滑动view的时候调用的方法
         * @param child  滑动的控件的view对象
         * @param left   viewDrageHelper认为的我们想要将控件移动的距离；计算方式：chid.getleft+dx
         * @param dx    手指滑动的距离
         * @return  实际我们想让控件移动的距离
         */
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {

            //2.5.控制控件的滑动范围
            //判断是否滑动的首页
            if (child == main){
                left = getLeft(left);
            }

            return left;
        }
        /**
         * 垂直滑动view的时候调用的方法
         * @param child  滑动的控件的view对象
         * @param top   viewDrageHelper认为的我们想要将控件移动的距离；计算方式：chid.getTop+dy
         * @param dy    手指滑动的距离
         * @return  实际我们想让控件移动的距离
         */
        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return 0;
        }

        /**
         * 当控件的位置改变的时候调用的方法
         * @param changedView  本次改变位置的控件的view对象
         * @param left     本地控件改变位置之后距离父控件左边的距离
         * @param top      本地控件改变位置之后距离父控件顶边的距离
         * @param dx       本次水平移动的距离
         * @param dy       本次垂直移动的距离
         */
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);

            //2.4.当滑动内容页的时候，可以滑动，但是当滑动菜单页的时候，菜单页的位置不改变，只是缩放，更改的时候内容页位置
            //判断如果是滑动菜单页，菜单页位置固定不动，去更改内容页的位置
            if (changedView == menu){
                //设置菜单页的位置不能改变
                //需要在自定义控件中对菜单页的子控件进行重新排版，使位置固定
                //l,t,r,b:距离父控件原点的距离
                menu.layout(0,0,menu.getMeasuredWidth(),menu.getMeasuredHeight());

                //更改内容页位置
                //获取内容页位置改变之后的l的值，现在的l的值 = 原来的l的值+移动的距离
                int newLeft = main.getLeft()+dx;

                //2.5.2.当滑动菜单页的时候，菜单位置不同，移动的是首页，所以在这个地方，首页也进行了移动操作，也要进行范围控制
                newLeft = getLeft(newLeft);

                main.layout(newLeft,0,newLeft+main.getMeasuredWidth(),main.getMeasuredHeight());
            }

            //2.pic7.根据移动距离求出控件缩放的百分比0-1f(0%-100%)
            float fraction = main.getLeft() * 1f/ maxLeft;
            //根据缩放的百分比进行缩放效果的实现
            execAnim(fraction);
            //通过回调传递给activity百分比
            if (listener != null){
                listener.rotate(fraction);
                //根据不同的情况，设置到底是打开还是关闭操作
                //通过百分比确定是打开还是关闭
                if (fraction == 0f){
                    listener.isOpen(false);
                }else if(fraction == 1f){
                    listener.isOpen(true);
                }
            }
        }

        /**
         * 手指抬起的方法
         * @param releasedChild  触摸抬起控件的view对象
         * @param xvel        x轴移动的速度
         * @param yvel        y轴移动的速度
         */
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            //2.6.当松开手指的时候，自动回弹操作
            //通过判断首页的距离父控件左边的距离，如果大于最大滑动距离的一半，打开操作，如果小于，关闭操作
            if (main.getLeft() > maxLeft /2){
                //打开
                //操作：平滑的自动回弹的效果
                /*scroller = new Scroller(getContext());
                scroller.startScroll();
                invalidate();*/
                //ViewDragHelper已经封装了scroller操作
                //参数1：移动的控件
                //参数2：距离父控件左边的距离
                //参数3：距离父控件顶部的距离
                viewDragHelper.smoothSlideViewTo(main,maxLeft,0);
                //ViewCompat ： android提供的兼容性的方法，效果跟invalidate()是一样
                ViewCompat.postInvalidateOnAnimation(SlidingMenu.this);
            }else{
                //关闭
                viewDragHelper.smoothSlideViewTo(main,0,0);
                //ViewCompat ： android提供的兼容性的方法，效果跟invalidate()是一样
                ViewCompat.postInvalidateOnAnimation(SlidingMenu.this);
            }

        }

    };

    //float类型的估值器：根据百分比计算对应的值
    FloatEvaluator floatEvaluator = new FloatEvaluator();
    //颜色的估值器
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    /**
     * 2.pic7.根据缩放的百分比进行缩放操作
     * @param fraction
     */
    private void execAnim(float fraction) {

        //缩放百分比：0-1f;
        //缩放的值：0.0f - 1.0f
        //首页的缩放的值：1.0f -> 0.8f
        //如何根据缩放的百分比求出缩放的值
        //缩放的值 = 开始的值+（结束的值 - 开始的值）*缩放的百分比
        //参数1：百分比
        //参数2：开始的值
        //参数3：结束的值
//        Float evaluate = floatEvaluator.evaluate(fraction, 1.0f, 0.8f);

        //首页实现缩放操作
//        main.setScaleX(evaluate);//x轴缩放多少
//        main.setScaleY(evaluate);//x轴缩放多少

        //菜单页缩放操作，0.3f -> 1.0f
//        menu.setScaleX(floatEvaluator.evaluate(fraction,0.3f,1.0f));
//        menu.setScaleY(floatEvaluator.evaluate(fraction,0.3f,1.0f));
        //菜单页除了缩放，还要随着移动的距离，有相应比例的偏移操作
        menu.setTranslationX(floatEvaluator.evaluate(fraction,-menu.getMeasuredWidth()/2,0));

        //设置背景的滤镜的渐变操作
        if (getBackground() != null){
            int color = (int) argbEvaluator.evaluate(fraction, Color.BLACK, Color.TRANSPARENT);
            //设置滤镜效果
            //参数1：滤镜颜色
            //参数2：覆盖的类型
            getBackground().setColorFilter(color, PorterDuff.Mode.SRC_OVER);//设置类型为在图片上方遮罩
        }

    }

    //2.3.viewDrageHelper必须得到一个触摸事件，才能进行工作，没有是不能实现触摸滑动效果
    //根据事件传递机制，发现事件会传递给onInterceptTouchEvent和onTouchEvent方法，如果onInterceptTouchEvent拦截事件，onTouchEvent是获取不到事件
    //所以为了保证触摸事件可以完全传递到viewDrageHelper,所以需要onInterceptTouchEvent和onTouchEvent方法都进行传递触摸事件
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //拦截，放开触摸事件
        //将触摸事件传递给viewDragHelper，并通过viewDragHelper进行拦截事件的判断
        boolean result = viewDragHelper.shouldInterceptTouchEvent(ev);

        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //需要将触摸事件传递给ViewDragHelper
        viewDragHelper.processTouchEvent(event);
        return true;
    }


    //2.5.1.因为最大的宽度是控件宽度的60%,所以需要获取控件的宽度，可以在测量和排版方法中进行操作，但是如果仅仅只是为了获取宽度就重写测量和排版的方法，浪费资源
    /**
     * 当测量控件完毕，调用此方法，可以对原来的宽高和现在的宽高进行处理
     * @param w  现在的宽
     * @param h     现在的高
     * @param oldw  以前的宽
     * @param oldh  以前的高
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        maxLeft = (int) (getMeasuredWidth() * 0.6f);
    }

    /**
     * 2.5.3.判断滑动范围方法抽取
     * @param left
     * @return
     */
    private int getLeft(int left) {
        if (left < 0){
            left = 0;
        }else if(left > maxLeft){
            left = maxLeft;
        }
        return left;
    }

    /**
     * 2.6.1.平滑自动回弹实现
     */
    @Override
    public void computeScroll() {
        super.computeScroll();
        //判断是否还需要移动
        /*if (scroller.computeScrollOffset()){
            scrollTo();
            invalidate();
        }*/
        //判断是否可以继续滑动
        if (viewDragHelper.continueSettling(true)){
            ViewCompat.postInvalidateOnAnimation(SlidingMenu.this);
        }
    }


    //3.创建回调实现将百分比传递给activity使用
    onSlidingMenuListener listener;
    public void setOnSlidingMenuListener(onSlidingMenuListener listener){
        this.listener = listener;
    }
    public interface  onSlidingMenuListener{
        /**
         * 旋转操作
         * @param fraction
         */
        public void rotate(float fraction);

        /**
         * 设置是否打开操作
         * @param isopen
         */
        public void isOpen(boolean isopen);
    }
}
