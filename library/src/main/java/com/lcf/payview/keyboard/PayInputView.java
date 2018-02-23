package com.lcf.payview.keyboard;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.lcf.payview.R;


/**
 * 密码输入框
 *
 * @author lcf
 * @date 2018/2/4 21:00
 * @since 1.0
 */
public class PayInputView extends View {
    /**
     * 密码模式，用黑点表示文字
     */
    public static final int MODE_PASSWORD = 0;
    /**
     * 文本模式，直接展示文字
     */
    public static final int MODE_TEXT = 1;

    private static final int DEFAULT_BORDER_WIDTH = 2;
    private static final int DEFAULT_BORDER_COLOR = Color.GRAY;
    private static final int DEFAULT_TEXT_COLOR = Color.BLACK;
    private static final int DEFAULT_TEXT_SIZE = 15;
    private static final int DEFAULT_COUNT = 6;
    private static final int DEFAULT_MODE = MODE_PASSWORD;
    private static final int DEFAULT_POINT_RADIUS = 15;
    private static final int DEFAULT_WIDTH = 48;

    private Paint mPaint = new Paint();
    /**
     * 边框宽度
     */
    private int mBorderWidth;
    /**
     * 边框颜色
     */
    private int mBorderColor;
    /**
     * 圆角大小
     */
    private int mCornerRadius;
    /**
     * 密码文字颜色
     */
    private int mTextColor;
    /**
     * 密码文字大小
     */
    private int mTextSize;
    /**
     * 密码个数
     */
    private int mCount;
    /**
     * 总宽度
     */
    private float mWidth;
    /**
     * 总高度
     */
    private float mHeight;
    /**
     * X轴分隔间距（每个输入框的宽度）
     */
    private float mDividerX;
    /**
     * 密码文字绘制起始点X
     */
    private float mStartX;
    /**
     * 密码文字绘制起始点y
     */
    private float mStartY;
    /**
     * 文字或黑点的模式
     */
    private int mMode;
    /**
     * 密码黑点的半径
     */
    private int mPointRadius;
    /**
     * 密码字符串
     */
    private String mPassword = "";

    private KeyboardView mKeyboardView;
    private OnPassWordInputListener mOnPassWordInputListener;

    public PayInputView(Context context) {
        this(context, null);
    }

    public PayInputView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PayInputView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        resolveAttrs(context, attrs, defStyleAttr);
    }

    private void resolveAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PayInputView, defStyleAttr, 0);
        mBorderWidth = a.getDimensionPixelSize(R.styleable.PayInputView_pay_border_width, DEFAULT_BORDER_WIDTH);
        mBorderColor = a.getColor(R.styleable.PayInputView_pay_border_color, DEFAULT_BORDER_COLOR);
        mCornerRadius = a.getDimensionPixelOffset(R.styleable.PayInputView_pay_corner_radius, -1);
        mPointRadius = a.getDimensionPixelOffset(R.styleable.PayInputView_pay_point_radius, DEFAULT_POINT_RADIUS);
        mTextColor = a.getColor(R.styleable.PayInputView_pay_text_color, DEFAULT_TEXT_COLOR);
        mTextSize = a.getDimensionPixelSize(R.styleable.PayInputView_pay_text_size, DEFAULT_TEXT_SIZE);
        mCount = a.getInt(R.styleable.PayInputView_pay_count, DEFAULT_COUNT);
        mMode = a.getInt(R.styleable.PayInputView_pay_text_mode, DEFAULT_MODE);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;
        int defaultWidth = (int) (getResources().getDisplayMetrics().density * DEFAULT_WIDTH + 0.5f);
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            width = Math.min(defaultWidth, widthSize);
        } else {
            width = defaultWidth;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            height = Math.min(width / mCount, heightSize);
        } else {
            height = width / mCount;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mWidth = getWidth();
        mHeight = getHeight();
        mDividerX = mWidth / mCount;
        mStartX = mDividerX / 2;
        mStartY = mHeight / 2;


        mPaint.setAntiAlias(true);
        drawBorder(canvas);
        drawDivider(canvas);
        drawText(canvas);
    }

    /**
     * 绘制边框
     */
    private void drawBorder(Canvas canvas) {
        mPaint.setColor(mBorderColor);
        mPaint.setStrokeWidth(mBorderWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        if (mCornerRadius > 0) {
            // 画圆角矩形
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                canvas.drawRoundRect(mBorderWidth / 2, mBorderWidth / 2, mWidth - mBorderWidth / 2, mHeight - mBorderWidth / 2, mCornerRadius, mCornerRadius, mPaint);
            } else {
                canvas.drawRoundRect(new RectF(mBorderWidth / 2, mBorderWidth / 2, mWidth - mBorderWidth / 2, mHeight - mBorderWidth / 2), mCornerRadius, mCornerRadius, mPaint);
            }
        } else {
            // 画矩形
            canvas.drawRect(mBorderWidth / 2, mBorderWidth / 2, mWidth - mBorderWidth / 2, mHeight - mBorderWidth / 2, mPaint);
        }
    }

    /**
     * 绘制分割线
     */
    private void drawDivider(Canvas canvas) {
        mPaint.setColor(mBorderColor);
        mPaint.setStrokeWidth(mBorderWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        for (int i = 0; i < mCount - 1; i++) {
            canvas.drawLine(mDividerX * (i + 1), 0, mDividerX * (i + 1), mHeight, mPaint);
        }
    }

    /**
     * 绘制文本（密码用点表示）
     */
    private void drawText(Canvas canvas) {
        int length = mPassword.length();
        if (length > 0) {
            for (int i = 0; i < length; i++) {
                mPaint.setColor(mTextColor);
                mPaint.setStyle(Paint.Style.FILL);
                if (mMode == MODE_PASSWORD) {
                    canvas.drawCircle(mStartX + mDividerX * i, mStartY, mPointRadius, mPaint);
                } else {
                    mPaint.setTextSize(getResources().getDisplayMetrics().scaledDensity * mTextSize);
                    // 测量文字宽高
                    Rect rect = new Rect();
                    String text = String.valueOf(mPassword.charAt(i));
                    mPaint.getTextBounds(text, 0, text.length(), rect);
                    // 绘制文字
                    canvas.drawText(text, mStartX - rect.width() / 2 + mDividerX * i, mStartY + rect.height() / 2, mPaint);
                }
            }
        }
    }

    /**
     * 关联密码输入键盘
     *
     * @param keyboardView
     */
    public void setKeyboardView(KeyboardView keyboardView) {
        this.mKeyboardView = keyboardView;
        if (mKeyboardView != null) {
            mKeyboardView.setOnKeyboardClickListener(new OnKeyboardClickListener() {
                @Override
                public void onTextClick(String text) {
                    int length = mPassword.trim().length();
                    if (length < mCount) {
                        mPassword += text;
                        postInvalidate();
                        // 密码全部输入了
                        if (mPassword.length() == mCount && mOnPassWordInputListener != null) {
                            mOnPassWordInputListener.complete(mPassword);
                        }
                    }
                }

                @Override
                public void onDeleteClick() {
                    if (mPassword.trim().length() > 0) {
                        mPassword = mPassword.substring(0, mPassword.length() - 1);
                        postInvalidate();
                    }
                }
            });
        }
    }

    public OnPassWordInputListener getOnPassWordInputListener() {
        return mOnPassWordInputListener;
    }

    public void setOnPassWordInputListener(OnPassWordInputListener listener) {
        this.mOnPassWordInputListener = listener;
    }

    /**
     * 密码输入回调
     */
    public interface OnPassWordInputListener {
        /**
         * 密码输入完成
         *
         * @param password 密码文本
         */
        void complete(String password);
    }
}
