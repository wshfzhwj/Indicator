package com.aniu.indicatorlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class LineIndicator extends View {
    public static final String TAG = LineIndicator.class.getSimpleName();

    private Path mPath;
    /**
     * 选中画笔颜色
     */
    private Paint mPaintSelect;
    /**
     * 普通画笔颜色
     */
    private Paint mPaintDefault;
    /**
     * 绘制个数
     */
    private int mNum;
    /**
     * 绘制半径
     */
    private float mRadius;
    /**
     * 选中颜色
     */
    private int mSelectedColor;
    /**
     * 默认颜色
     */
    private int mDefaultColor;
    /**
     * 间距
     */
    private float mDistance;
    /**
     * 选中pos
     */
    private int mPosition;
    /**
     * 是否无限循环
     */
    private boolean mIsInfiniteCircle;

    public LineIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        setStyleable(context, attrs);
        mPaintDefault = new Paint();
        mPaintSelect = new Paint();
        mPath = new Path();
        initPaint();
    }


    private void initPaint(){
        mPaintSelect.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaintSelect.setColor(mSelectedColor);
        mPaintSelect.setAntiAlias(true);
        mPaintSelect.setStrokeWidth(3);

        mPaintDefault.setStyle(Paint.Style.FILL);
        mPaintDefault.setColor(mDefaultColor);
        mPaintDefault.setAntiAlias(true);
        mPaintDefault.setStrokeWidth(3);
    }



    /**
     * 绘制   invalidate()后 执行
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if ((mNum <= 0)) {
            return;
        }
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        canvas.translate(mRadius, height / 2);

        for (int i = 0; i < mNum; i++) {
            if (mPosition == i) {
                float leftClose = i * (mDistance + 2 * mRadius);
                float rightClose = leftClose + 4 * mRadius;
                float topClose = -mRadius;
                float bottomClose = mRadius;
                RectF rectClose = new RectF(leftClose, topClose, rightClose, bottomClose);// 设置个新的长方形
                canvas.drawRoundRect(rectClose, mRadius, mRadius, mPaintSelect);
            } else {
                if (i < mPosition) {
                    canvas.drawCircle(mRadius + i * (mDistance + 2 * mRadius), 0, mRadius, mPaintDefault);
                } else {
                    canvas.drawCircle(mRadius + i * (mDistance + 2 * mRadius) + 2 * mRadius, 0, mRadius, mPaintDefault);
                }
            }
        }
    }

    /**
     * xml 参数设置  选中颜色 默认颜色  点大小 长度 距离 距离类型 类型 真实个数(轮播)
     *
     * @param context
     * @param attrs
     */
    private void setStyleable(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LineIndicator);
        mSelectedColor = array.getColor(R.styleable.LineIndicator_selected_color, 0xffffffff);
        mDefaultColor = array.getColor(R.styleable.LineIndicator_default_color, 0xffcdcdcd);
        mRadius = array.getDimension(R.styleable.LineIndicator_radius, 20);//px
        mDistance = array.getDimension(R.styleable.LineIndicator_distance, 3 * mRadius);//px
        mNum = array.getInteger(R.styleable.LineIndicator_num, 0);
        array.recycle();
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.e(TAG, "onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        switch (widthMode) {
            case MeasureSpec.EXACTLY:
                break;
            case MeasureSpec.AT_MOST:
                //
                widthSize = (int) ((mNum + 1) * mRadius * 2 + (mNum - 1) * mDistance + 2 * mRadius);
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
        }

        setMeasuredDimension(MeasureSpec.makeMeasureSpec(widthSize, widthMode), heightMeasureSpec);

    }

    /**
     * @param viewpager        适配的viewpager
     * @param cycleNumber      真/伪无限循环都必须输入
     * @param isInfiniteCircle 真无限循环 配合Banner
     * @return
     */
    public LineIndicator setViewPager(ViewPager viewpager, int cycleNumber, boolean isInfiniteCircle) {
        Log.e(TAG, "setViewPager");
        mNum = cycleNumber;
        requestLayout();
        mIsInfiniteCircle = isInfiniteCircle;
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (mNum > 0) {
                    if (!mIsInfiniteCircle) {
                        mPosition = position % mNum;
                    } else {
                        if (position == 0) {
                            mPosition = mNum - 1;
                        } else if (position == mNum + 1) {
                            mPosition = 0;
                        } else {
                            mPosition--;
                        }
                    }
                    invalidate();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return this;
    }
}
