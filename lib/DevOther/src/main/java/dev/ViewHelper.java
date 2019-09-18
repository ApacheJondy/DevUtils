package dev;

import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.view.View;
import android.widget.TextView;

import dev.utils.app.TextViewUtils;

/**
 * detail: View 链式调用快捷设置 Helper 类
 * @author Ttt
 * <pre>
 *     通过 DevApp 工具类快捷实现
 *     <p></p>
 *     DevApp Api
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevApp/README.md"/>
 * </pre>
 */
public final class ViewHelper {

    // ViewHelper
    private static final ViewHelper HELPER = new ViewHelper();

    /**
     * 获取单例 ViewHelper
     * @return {@link ViewHelper}
     */
    public static ViewHelper get() {
        return HELPER;
    }

    // ========
    // = Text =
    // ========

    /**
     * 设置文本
     * @param view {@link TextView}
     * @param text TextView text
     * @return {@link ViewHelper}
     */
    public ViewHelper setText(final View view, final String text) {
        TextViewUtils.setText(view, text);
        return this;
    }

    /**
     * 设置多个 TextView 文本
     * @param text  TextView text
     * @param views View(TextView)[]
     * @return {@link ViewHelper}
     */
    public ViewHelper setTexts(final String text, final View... views) {
        TextViewUtils.setTexts(text, views);
        return this;
    }

    /**
     * 设置 Html 内容
     * @param view    {@link TextView}
     * @param content Html content
     * @return {@link ViewHelper}
     */
    public ViewHelper setHtmlText(final View view, final String content) {
        TextViewUtils.setHtmlText(view, content);
        return this;
    }

    /**
     * 设置多个 TextView Html 内容
     * @param content Html content
     * @param views   View(TextView)[]
     * @return {@link ViewHelper}
     */
    public ViewHelper setHtmlTexts(final String content, final View... views) {
        TextViewUtils.setHtmlTexts(content, views);
        return this;
    }

    /**
     * 设置字体颜色
     * @param view  {@link TextView}
     * @param color R.color.id
     * @return {@link ViewHelper}
     */
    public ViewHelper setTextColor(final View view, @ColorInt final int color) {
        TextViewUtils.setTextColor(view, color);
        return this;
    }

    /**
     * 设置多个 TextView 字体颜色
     * @param color R.color.id
     * @param views View(TextView)[]
     * @return {@link ViewHelper}
     */
    public ViewHelper setTextColors(@ColorInt final int color, final View... views) {
        TextViewUtils.setTextColors(color, views);
        return this;
    }

    /**
     * 设置字体
     * @param view     {@link TextView}
     * @param typeface {@link Typeface} 字体样式
     * @return {@link ViewHelper}
     */
    public ViewHelper setTypeface(final View view, final Typeface typeface) {
        TextViewUtils.setTypeface(view, typeface);
        return this;
    }

    /**
     * 设置字体大小 - px 像素
     * @param view {@link TextView}
     * @param size 字体大小
     * @return {@link ViewHelper}
     */
    public ViewHelper setTextSizeByPx(final View view, final float size) {
        TextViewUtils.setTextSizeByPx(view, size);
        return this;
    }

    /**
     * 设置字体大小 - sp 缩放像素
     * @param view {@link TextView}
     * @param size 字体大小
     * @return {@link ViewHelper}
     */
    public ViewHelper setTextSizeBySp(final View view, final float size) {
        TextViewUtils.setTextSizeBySp(view, size);
        return this;
    }

    /**
     * 设置字体大小 - dp 与设备无关的像素
     * @param view {@link TextView}
     * @param size 字体大小
     * @return {@link ViewHelper}
     */
    public ViewHelper setTextSizeByDp(final View view, final float size) {
        TextViewUtils.setTextSizeByDp(view, size);
        return this;
    }

    /**
     * 设置字体大小 - inches 英寸
     * @param view {@link TextView}
     * @param size 字体大小
     * @return {@link ViewHelper}
     */
    public ViewHelper setTextSizeByIn(final View view, final float size) {
        TextViewUtils.setTextSizeByIn(view, size);
        return this;
    }

    /**
     * 设置字体大小
     * @param view {@link TextView}
     * @param unit 字体参数类型
     * @param size 字体大小
     * @return {@link ViewHelper}
     */
    public ViewHelper setTextSize(final View view, final int unit, final float size) {
        TextViewUtils.setTextSize(view, unit, size);
        return this;
    }

    /**
     * 设置多个 TextView 字体大小
     * @param views View(TextView)[]
     * @param unit  参数类型
     * @param size  字体大小
     * @return {@link ViewHelper}
     */
    public ViewHelper setTextSizes(final View[] views, final int unit, final float size) {
        TextViewUtils.setTextSizes(views, unit, size);
        return this;
    }

    /**
     * 清空 flags
     * @param view {@link TextView}
     * @return {@link ViewHelper}
     */
    public ViewHelper clearFlags(final View view) {
        TextViewUtils.clearFlags(view);
        return this;
    }

    /**
     * 设置 TextView 是否加粗
     * @param view   {@link TextView}
     * @param isBold {@code true} yes, {@code false} no
     * @return {@link ViewHelper}
     */
    public ViewHelper setBold(final View view, final boolean isBold) {
        TextViewUtils.setBold(view, isBold);
        return this;
    }

    /**
     * 设置 TextView 是否加粗
     * @param view     {@link TextView}
     * @param typeface {@link Typeface} 字体样式
     * @param isBold   {@code true} yes, {@code false} no
     * @return {@link ViewHelper}
     */
    public ViewHelper setBold(final View view, final Typeface typeface, final boolean isBold) {
        TextViewUtils.setBold(view, typeface, isBold);
        return this;
    }

    /**
     * 设置下划线
     * @param view {@link TextView}
     * @return {@link ViewHelper}
     */
    public ViewHelper setUnderlineText(final View view) {
        TextViewUtils.setUnderlineText(view);
        return this;
    }

    /**
     * 设置下划线并加清晰
     * @param view        {@link TextView}
     * @param isAntiAlias 是否消除锯齿
     * @return {@link ViewHelper}
     */
    public ViewHelper setUnderlineText(final View view, final boolean isAntiAlias) {
        TextViewUtils.setUnderlineText(view, isAntiAlias);
        return this;
    }

    /**
     * 设置中划线
     * @param view {@link TextView}
     * @return {@link ViewHelper}
     */
    public ViewHelper setStrikeThruText(final View view) {
        TextViewUtils.setStrikeThruText(view);
        return this;
    }

    /**
     * 设置中划线并加清晰
     * @param view        {@link TextView}
     * @param isAntiAlias 是否消除锯齿
     * @return {@link ViewHelper}
     */
    public ViewHelper setStrikeThruText(final View view, final boolean isAntiAlias) {
        TextViewUtils.setStrikeThruText(view, isAntiAlias);
        return this;
    }

    /**
     * 设置文字水平间距
     * @param view          {@link TextView}
     * @param letterSpacing 文字水平间距值
     * @return {@link ViewHelper}
     */
    public ViewHelper setLetterSpacing(final View view, final float letterSpacing) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            TextViewUtils.setLetterSpacing(view, letterSpacing);
        }
        return this;
    }

    /**
     * 设置文字行间距 ( 行高 )
     * @param view        {@link TextView}
     * @param lineSpacing 文字行间距 ( 行高 ), android:lineSpacingExtra
     * @return {@link ViewHelper}
     */
    public ViewHelper setLineSpacing(final View view, final float lineSpacing) {
        TextViewUtils.setLineSpacing(view, lineSpacing);
        return this;
    }

    /**
     * 设置文字行间距 ( 行高 )、行间距倍数
     * @param view        {@link TextView}
     * @param lineSpacing 文字行间距 ( 行高 ), android:lineSpacingExtra
     * @param multiplier  行间距倍数, android:lineSpacingMultiplier
     * @return {@link ViewHelper}
     */
    public ViewHelper setLineSpacingAndMultiplier(final View view, final float lineSpacing, final float multiplier) {
        TextViewUtils.setLineSpacingAndMultiplier(view, lineSpacing, multiplier);
        return this;
    }

    /**
     * 设置字体水平方向的缩放
     * @param view {@link TextView}
     * @param size 缩放比例
     * @return {@link ViewHelper}
     */
    public ViewHelper setTextScaleX(final View view, final float size) {
        TextViewUtils.setTextScaleX(view, size);
        return this;
    }

    /**
     * 设置是否保留字体留白间隙区域
     * @param view       {@link TextView}
     * @param includepad 是否保留字体留白间隙区域
     * @return {@link ViewHelper}
     */
    public ViewHelper setIncludeFontPadding(final View view, final boolean includepad) {
        TextViewUtils.setIncludeFontPadding(view, includepad);
        return this;
    }
}
