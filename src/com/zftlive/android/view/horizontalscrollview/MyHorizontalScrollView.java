package com.zftlive.android.view.horizontalscrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.nineoldandroids.view.ViewHelper;

public class MyHorizontalScrollView extends HorizontalScrollView {

    // 在HorizontalScrollView有个LinearLayout
    private LinearLayout linearLayout;
    // 菜单，内容页
    private ViewGroup myMenu;
    private ViewGroup myContent;
    //菜单宽度
    private int myMenuWidth;

    // 屏幕宽度
    private int screenWidth;
    // 菜单与屏幕右侧的距离(dp)
    private int myMenuPaddingRight = 50;

    // 避免多次调用onMeasure的标志
    private boolean once = false;

    /**
     * 自定义View需要实现带有Context、AttributeSet这2个参数的构造方法,否则自定义参数会出错
     * 当使用了自定义属性时，会调用此构造方法
     * 
     * @param context
     * @param attrs
     */
    public MyHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 获取屏幕宽度
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        screenWidth = outMetrics.widthPixels;// 屏幕宽度

        // 将dp转换px
        myMenuPaddingRight = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 100, context.getResources()
                        .getDisplayMetrics());

    }

    /**
     * 设置子View的宽高，决定自身View的宽高，每次启动都会调用此方法
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (!once) {//使其只调用一次
            // this指的是HorizontalScrollView，获取各个元素
            linearLayout = (LinearLayout) this.getChildAt(0);// 第一个子元素
            myMenu = (ViewGroup) linearLayout.getChildAt(0);// HorizontalScrollView下LinearLayout的第一个子元素
            myContent = (ViewGroup) linearLayout.getChildAt(1);// HorizontalScrollView下LinearLayout的第二个子元素

            // 设置子View的宽高，高于屏幕一致
            myMenuWidth=myMenu.getLayoutParams().width = screenWidth - myMenuPaddingRight;// 菜单的宽度=屏幕宽度-右边距
            myContent.getLayoutParams().width = screenWidth;// 内容宽度=屏幕宽度
            // 决定自身View的宽高，高于屏幕一致
            // 由于这里的LinearLayout里只包含了Menu和Content所以就不需要额外的去指定自身的宽
            once = true;
        }
    }

    //设置View的位置，首先，先将Menu隐藏（在eclipse中ScrollView的画面内容（非滚动条）正数表示向左移，向上移）
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //刚载入界面的时候隐藏Menu菜单也就是ScrollView向左滑动菜单自身的大小
        if(changed){
            this.scrollTo(myMenuWidth, 0);//向左滑动，相当于把右边的内容页拖到正中央，菜单隐藏    
        }
    
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action=ev.getAction();
        switch (action) {
        case MotionEvent.ACTION_UP:
            int scrollX=this.getScrollX();//滑动的距离scrollTo方法里，也就是onMeasure方法里的向左滑动那部分
            if(scrollX>=myMenuWidth/2){
                this.smoothScrollTo(myMenuWidth,0);//向左滑动展示内容
            }else{
                this.smoothScrollTo(0, 0);
            }
            return true;
        }
        return super.onTouchEvent(ev);
    }
    
    
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        Log.i("tuzi",l+"");
        float scale = l * 1.0f / myMenuWidth; // 1 ~ 0
        float rightScale = 0.7f + 0.3f * scale;
        float leftScale = 1.0f - scale * 0.3f;
        float leftAlpha = 0.6f + 0.4f * (1 - scale);

        // 调用属性动画，设置TranslationX
        ViewHelper.setTranslationX(myMenu, myMenuWidth * scale * 0.8f);
        
        ViewHelper.setScaleX(myMenu, leftScale);
        ViewHelper.setScaleY(myMenu, leftScale);
        ViewHelper.setAlpha(myMenu, leftAlpha);
        // 设置内容缩放的中心点
        ViewHelper.setPivotX(myContent, 0);
        ViewHelper.setPivotY(myContent, myContent.getHeight() / 2);
        ViewHelper.setScaleX(myContent, rightScale);
        ViewHelper.setScaleY(myContent, rightScale);
    }

}