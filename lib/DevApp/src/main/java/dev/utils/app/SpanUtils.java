package dev.utils.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.AlignmentSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.CharacterStyle;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.LeadingMarginSpan;
import android.text.style.LineHeightSpan;
import android.text.style.MaskFilterSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.ReplacementSpan;
import android.text.style.ScaleXSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.text.style.UpdateAppearance;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.FloatRange;
import androidx.annotation.IntDef;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.io.InputStream;
import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;

import dev.DevUtils;
import dev.utils.DevFinal;
import dev.utils.LogPrintUtils;
import dev.utils.common.CloseUtils;

/**
 * detail: SpannableString ?????????
 * @author Blankj
 * @author Ttt
 * <pre>
 *     SpannableStringBuilder ????????? ( ?????????????????????????????????????????????Typeface?????????????????????????????????????????????????????????????????????????????? )
 *     <p></p>
 *     Android ?????? SpannableString ??? SpannableStringBuilder ???????????????
 *     @see <a href="https://blog.csdn.net/baidu_31956557/article/details/78339071"/>
 *     <p></p>
 *     Android ????????? SpannableStringBuilder
 *     @see <a href="https://www.jianshu.com/p/f004300c6920"/>
 *     <p></p>
 *     SpannableString ????????????????????????
 *     @see <a href="https://www.cnblogs.com/qynprime/p/8026672.html"/>
 *     <p></p>
 *     SpannableString ??? SpannableStringBuilder ??????????????? String ??? StringBuilder ??????
 *     <p></p>
 *     setSpan() - int flags
 *     <p></p>
 *     Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
 *     ?????????????????? ( ???????????? [start, end) ??????????????????, ???????????????????????????????????????????????? )
 *     <p></p>
 *     Spannable.SPAN_EXCLUSIVE_INCLUSIVE
 *     ???????????????, ???????????? ( ???????????? [start, end) ???????????????, ????????????????????????????????????????????????, ?????????????????????????????????????????? what ?????? )
 *     <p></p>
 *     Spannable.SPAN_INCLUSIVE_EXCLUSIVE
 *     ????????????, ??????????????? ( ???????????? [start, end) ???????????????, ????????????????????????????????????????????????, ????????????????????????????????????????????? what ?????? )
 *     <p></p>
 *     Spannable.SPAN_INCLUSIVE_INCLUSIVE
 *     ??????????????? ?????????????????? ( ???????????? [start, end) ??????????????????, ??????????????????????????????????????? )
 * </pre>
 */
public final class SpanUtils {

    // ?????? TAG
    private static final String TAG = SpanUtils.class.getSimpleName();

    // ????????????
    public static final int ALIGN_BOTTOM   = 0;
    public static final int ALIGN_BASELINE = 1;
    public static final int ALIGN_CENTER   = 2;
    public static final int ALIGN_TOP      = 3;

    @IntDef({ALIGN_BOTTOM, ALIGN_BASELINE, ALIGN_CENTER, ALIGN_TOP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Align {
    }

    // ?????? SpannableString ?????????
    private final SerializableSpannableStringBuilder mBuilder;
    // TextView create setText
    private       TextView                           mTextView;
    // ????????????
    private       CharSequence                       mText;

    // ????????????????????????
    private              int mType;
    private static final int TYPE_CHAR_SEQUENCE = 0;
    private static final int TYPE_IMAGE         = 1;
    private static final int TYPE_SPACE         = 2;

    // ????????????
    private static final int                 COLOR_DEFAULT = Color.WHITE;
    // ????????????
    private              int                 flag; // setSpan flag
    private              int                 foregroundColor; // ?????????
    private              int                 backgroundColor; // ?????????
    private              int                 lineHeight; // ??????
    private              int                 alignLine; // ???????????????
    private              int                 quoteColor; // ???????????????
    private              int                 stripeWidth; // ????????????
    private              int                 quoteGapWidth; // ????????????
    private              int                 first; // ???????????????
    private              int                 rest; // ???????????????
    private              int                 bulletColor; // ???????????????
    private              int                 bulletRadius; // ???????????????
    private              int                 bulletGapWidth; // ?????????????????????
    private              int                 fontSize; // ???????????? px
    private              boolean             fontSizeIsDp; // ???????????????????????? dp
    private              float               proportion; // ??????????????????
    private              float               xProportion; // ????????????????????????
    private              boolean             isStrikethrough; // ???????????????
    private              boolean             isUnderline; // ???????????????
    private              boolean             isSuperscript; // ????????????
    private              boolean             isSubscript; // ????????????
    private              boolean             isBold; // ????????????
    private              boolean             isItalic; // ????????????
    private              boolean             isBoldItalic; // ???????????????
    private              String              fontFamily; // ????????????
    private              Typeface            typeface; // ??????
    private              Layout.Alignment    alignment; // ???????????? ( ???????????? )
    private              int                 verticalAlign; // ??????????????????
    private              ClickableSpan       clickSpan; // ????????????
    private              String              url; // Url
    private              float               blurRadius; // ????????????
    private              BlurMaskFilter.Blur style; // ????????????
    private              Shader              shader; // ??????
    private              float               shadowRadius; // ????????????
    private              float               shadowDx; // X ??????????????????
    private              float               shadowDy; // Y ??????????????????
    private              int                 shadowColor; // ????????????
    private              int                 spaceSize; // ????????????
    private              int                 spaceColor; // ????????????
    private              Object[]            spans; // ????????? setSpan ??????

    // ????????????
    private Bitmap   imageBitmap; // Bitmap
    private Drawable imageDrawable; // Drawable
    private Uri      imageUri; // Uri
    private int      imageResourceId; // resource id
    private int      alignImage; // ??????????????????

    // ==========
    // = ???????????? =
    // ==========

    /**
     * ????????????
     * @param textView {@link TextView}
     */
    private SpanUtils(TextView textView) {
        this();
        mTextView = textView;
    }

    /**
     * ????????????
     */
    public SpanUtils() {
        mBuilder = new SerializableSpannableStringBuilder();
        mText    = "";
        mType    = -1;
        setDefault();
    }

    /**
     * ???????????? TextView SpannableString Utils
     * @param textView {@link TextView}
     * @return {@link SpanUtils}
     */
    public static SpanUtils with(final TextView textView) {
        return new SpanUtils(textView);
    }

    // ==========
    // = ???????????? =
    // ==========

    /**
     * ????????????
     * @param flag ??????
     *             <ul>
     *             <li>{@link Spanned#SPAN_INCLUSIVE_EXCLUSIVE}</li>
     *             <li>{@link Spanned#SPAN_INCLUSIVE_INCLUSIVE}</li>
     *             <li>{@link Spanned#SPAN_EXCLUSIVE_EXCLUSIVE}</li>
     *             <li>{@link Spanned#SPAN_EXCLUSIVE_INCLUSIVE}</li>
     *             </ul>
     * @return {@link SpanUtils}
     */
    public SpanUtils setFlag(final int flag) {
        this.flag = flag;
        return this;
    }

    /**
     * ???????????????
     * @param color ?????????
     * @return {@link SpanUtils}
     */
    public SpanUtils setForegroundColor(@ColorInt final int color) {
        this.foregroundColor = color;
        return this;
    }

    /**
     * ???????????????
     * @param color ?????????
     * @return {@link SpanUtils}
     */
    public SpanUtils setBackgroundColor(@ColorInt final int color) {
        this.backgroundColor = color;
        return this;
    }

    /**
     * ????????????
     * @param lineHeight ??????
     * @return {@link SpanUtils}
     */
    public SpanUtils setLineHeight(@IntRange(from = 0) final int lineHeight) {
        return setLineHeight(lineHeight, ALIGN_CENTER);
    }

    /**
     * ????????????
     * @param lineHeight ??????
     * @param align      ???????????????
     *                   <ul>
     *                   <li>{@link Align#ALIGN_TOP   }</li>
     *                   <li>{@link Align#ALIGN_CENTER}</li>
     *                   <li>{@link Align#ALIGN_BOTTOM}</li>
     *                   </ul>
     * @return {@link SpanUtils}
     */
    public SpanUtils setLineHeight(
            @IntRange(from = 0) final int lineHeight,
            @Align final int align
    ) {
        this.lineHeight = lineHeight;
        this.alignLine  = align;
        return this;
    }

    /**
     * ????????????????????????
     * @param color ???????????????
     * @return {@link SpanUtils}
     */
    public SpanUtils setQuoteColor(@ColorInt final int color) {
        return setQuoteColor(color, 2, 2);
    }

    /**
     * ????????????????????????
     * @param color       ???????????????
     * @param stripeWidth ????????????
     * @param gapWidth    ????????????
     * @return {@link SpanUtils}
     */
    public SpanUtils setQuoteColor(
            @ColorInt final int color,
            @IntRange(from = 1) final int stripeWidth,
            @IntRange(from = 0) final int gapWidth
    ) {
        this.quoteColor    = color;
        this.stripeWidth   = stripeWidth;
        this.quoteGapWidth = gapWidth;
        return this;
    }

    /**
     * ????????????
     * @param first ???????????????????????????
     * @param rest  ???????????????????????????
     * @return {@link SpanUtils}
     */
    public SpanUtils setLeadingMargin(
            @IntRange(from = 0) final int first,
            @IntRange(from = 0) final int rest
    ) {
        this.first = first;
        this.rest  = rest;
        return this;
    }

    /**
     * ??????????????????
     * @param gapWidth ??????????????????
     * @return {@link SpanUtils}
     */
    public SpanUtils setBullet(@IntRange(from = 0) final int gapWidth) {
        return setBullet(0, 3, gapWidth);
    }

    /**
     * ??????????????????
     * @param color    ????????????
     * @param radius   ????????????
     * @param gapWidth ??????????????????
     * @return {@link SpanUtils}
     */
    public SpanUtils setBullet(
            @ColorInt final int color,
            @IntRange(from = 0) final int radius,
            @IntRange(from = 0) final int gapWidth
    ) {
        this.bulletColor    = color;
        this.bulletRadius   = radius;
        this.bulletGapWidth = gapWidth;
        return this;
    }

    /**
     * ??????????????????
     * @param size ????????????
     * @return {@link SpanUtils}
     */
    public SpanUtils setFontSize(@IntRange(from = 0) final int size) {
        return setFontSize(size, false);
    }

    /**
     * ??????????????????
     * @param size ????????????
     * @param isDp ???????????? dp
     * @return {@link SpanUtils}
     */
    public SpanUtils setFontSize(
            @IntRange(from = 0) final int size,
            final boolean isDp
    ) {
        this.fontSize     = size;
        this.fontSizeIsDp = isDp;
        return this;
    }

    /**
     * ??????????????????
     * @param proportion ????????????
     * @return {@link SpanUtils}
     */
    public SpanUtils setFontProportion(final float proportion) {
        this.proportion = proportion;
        return this;
    }

    /**
     * ????????????????????????
     * @param proportion ??????????????????
     * @return {@link SpanUtils}
     */
    public SpanUtils setFontXProportion(final float proportion) {
        this.xProportion = proportion;
        return this;
    }

    // =

    /**
     * ???????????????
     * @return {@link SpanUtils}
     */
    public SpanUtils setStrikethrough() {
        this.isStrikethrough = true;
        return this;
    }

    /**
     * ???????????????
     * @return {@link SpanUtils}
     */
    public SpanUtils setUnderline() {
        this.isUnderline = true;
        return this;
    }

    /**
     * ????????????
     * @return {@link SpanUtils}
     */
    public SpanUtils setSuperscript() {
        this.isSuperscript = true;
        return this;
    }

    /**
     * ????????????
     * @return {@link SpanUtils}
     */
    public SpanUtils setSubscript() {
        this.isSubscript = true;
        return this;
    }

    /**
     * ????????????
     * @return {@link SpanUtils}
     */
    public SpanUtils setBold() {
        isBold = true;
        return this;
    }

    /**
     * ????????????
     * @return {@link SpanUtils}
     */
    public SpanUtils setItalic() {
        isItalic = true;
        return this;
    }

    /**
     * ???????????????
     * @return {@link SpanUtils}
     */
    public SpanUtils setBoldItalic() {
        isBoldItalic = true;
        return this;
    }

    /**
     * ??????????????????
     * @param fontFamily ????????????
     *                   <ul>
     *                   <li>monospace</li>
     *                   <li>serif</li>
     *                   <li>sans-serif</li>
     *                   </ul>
     * @return {@link SpanUtils}
     */
    public SpanUtils setFontFamily(@NonNull final String fontFamily) {
        this.fontFamily = fontFamily;
        return this;
    }

    /**
     * ????????????
     * @param typeface {@link Typeface}
     * @return {@link SpanUtils}
     */
    public SpanUtils setTypeface(@NonNull final Typeface typeface) {
        this.typeface = typeface;
        return this;
    }

    /**
     * ??????????????????
     * @param alignment ????????????
     *                  <ul>
     *                  <li>{@link Layout.Alignment#ALIGN_NORMAL  }</li>
     *                  <li>{@link Layout.Alignment#ALIGN_OPPOSITE}</li>
     *                  <li>{@link Layout.Alignment#ALIGN_CENTER  }</li>
     *                  </ul>
     * @return {@link SpanUtils}
     */
    public SpanUtils setHorizontalAlign(@NonNull final Layout.Alignment alignment) {
        this.alignment = alignment;
        return this;
    }

    /**
     * ??????????????????
     * @param align ????????????
     *              <ul>
     *              <li>{@link Align#ALIGN_TOP     }</li>
     *              <li>{@link Align#ALIGN_CENTER  }</li>
     *              <li>{@link Align#ALIGN_BASELINE}</li>
     *              <li>{@link Align#ALIGN_BOTTOM  }</li>
     *              </ul>
     * @return {@link SpanUtils}
     */
    public SpanUtils setVerticalAlign(@Align final int align) {
        this.verticalAlign = align;
        return this;
    }

    /**
     * ??????????????????
     * <pre>
     *     ????????? {@code view.setMovementMethod(LinkMovementMethod.getInstance())}
     * </pre>
     * @param clickSpan {@link ClickableSpan}
     * @return {@link SpanUtils}
     */
    public SpanUtils setClickSpan(@NonNull final ClickableSpan clickSpan) {
        if (mTextView != null && mTextView.getMovementMethod() == null) {
            mTextView.setMovementMethod(LinkMovementMethod.getInstance());
        }
        this.clickSpan = clickSpan;
        return this;
    }

    /**
     * ???????????????
     * <pre>
     *     ????????? {@code view.setMovementMethod(LinkMovementMethod.getInstance())}
     * </pre>
     * @param url ?????????
     * @return {@link SpanUtils}
     */
    public SpanUtils setUrl(@NonNull final String url) {
        if (mTextView != null && mTextView.getMovementMethod() == null) {
            mTextView.setMovementMethod(LinkMovementMethod.getInstance());
        }
        this.url = url;
        return this;
    }

    /**
     * ????????????
     * @param radius ????????????
     * @param style  ????????????
     *               <ul>
     *               <li>{@link BlurMaskFilter.Blur#NORMAL}</li>
     *               <li>{@link BlurMaskFilter.Blur#SOLID}</li>
     *               <li>{@link BlurMaskFilter.Blur#OUTER}</li>
     *               <li>{@link BlurMaskFilter.Blur#INNER}</li>
     *               </ul>
     * @return {@link SpanUtils}
     */
    public SpanUtils setBlur(
            @FloatRange(from = 0, fromInclusive = false) final float radius,
            final BlurMaskFilter.Blur style
    ) {
        this.blurRadius = radius;
        this.style      = style;
        return this;
    }

    /**
     * ???????????????
     * @param shader {@link Shader}
     * @return {@link SpanUtils}
     */
    public SpanUtils setShader(@NonNull final Shader shader) {
        this.shader = shader;
        return this;
    }

    /**
     * ????????????
     * @param radius      ????????????
     * @param dx          X ??????????????????
     * @param dy          Y ??????????????????
     * @param shadowColor ????????????
     * @return {@link SpanUtils}
     */
    public SpanUtils setShadow(
            @FloatRange(from = 0, fromInclusive = false) final float radius,
            final float dx,
            final float dy,
            final int shadowColor
    ) {
        this.shadowRadius = radius;
        this.shadowDx     = dx;
        this.shadowDy     = dy;
        this.shadowColor  = shadowColor;
        return this;
    }

    // =

    /**
     * ????????? setSpan ??????
     * @param spans span ????????????
     * @return {@link SpanUtils}
     */
    public SpanUtils setSpans(@NonNull final Object... spans) {
        if (spans.length > 0) {
            this.spans = spans;
        }
        return this;
    }

    /**
     * ????????????
     * @param text ????????????
     * @return {@link SpanUtils}
     */
    public SpanUtils append(@NonNull final CharSequence text) {
        apply(TYPE_CHAR_SEQUENCE);
        mText = text;
        return this;
    }

    /**
     * ????????????
     * @return {@link SpanUtils}
     */
    public SpanUtils appendLine() {
        apply(TYPE_CHAR_SEQUENCE);
        mText = DevFinal.SYMBOL.NEW_LINE;
        return this;
    }

    /**
     * ???????????? ( ?????? )
     * @param text ????????????
     * @return {@link SpanUtils}
     */
    public SpanUtils appendLine(@NonNull final CharSequence text) {
        apply(TYPE_CHAR_SEQUENCE);
        mText = text + DevFinal.SYMBOL.NEW_LINE;
        return this;
    }

    // =

    /**
     * ?????? Image
     * @param bitmap {@link Bitmap} image
     * @return {@link SpanUtils}
     */
    public SpanUtils appendImage(@NonNull final Bitmap bitmap) {
        return appendImage(bitmap, ALIGN_BOTTOM);
    }

    /**
     * ?????? Image
     * @param bitmap {@link Bitmap} image
     * @param align  ????????????
     *               <ul>
     *               <li>{@link Align#ALIGN_TOP     }</li>
     *               <li>{@link Align#ALIGN_CENTER  }</li>
     *               <li>{@link Align#ALIGN_BASELINE}</li>
     *               <li>{@link Align#ALIGN_BOTTOM  }</li>
     *               </ul>
     * @return {@link SpanUtils}
     */
    public SpanUtils appendImage(
            @NonNull final Bitmap bitmap,
            @Align final int align
    ) {
        apply(TYPE_IMAGE);
        this.imageBitmap = bitmap;
        this.alignImage  = align;
        return this;
    }

    /**
     * ?????? Image
     * @param drawable {@link Drawable} image
     * @return {@link SpanUtils}
     */
    public SpanUtils appendImage(@NonNull final Drawable drawable) {
        return appendImage(drawable, ALIGN_BOTTOM);
    }

    /**
     * ?????? Image
     * @param drawable {@link Drawable} image
     * @param align    ????????????
     *                 <ul>
     *                 <li>{@link Align#ALIGN_TOP     }</li>
     *                 <li>{@link Align#ALIGN_CENTER  }</li>
     *                 <li>{@link Align#ALIGN_BASELINE}</li>
     *                 <li>{@link Align#ALIGN_BOTTOM  }</li>
     *                 </ul>
     * @return {@link SpanUtils}
     */
    public SpanUtils appendImage(
            @NonNull final Drawable drawable,
            @Align final int align
    ) {
        apply(TYPE_IMAGE);
        this.imageDrawable = drawable;
        this.alignImage    = align;
        return this;
    }

    /**
     * ?????? Image
     * @param uri {@link Uri} image
     * @return {@link SpanUtils}
     */
    public SpanUtils appendImage(@NonNull final Uri uri) {
        return appendImage(uri, ALIGN_BOTTOM);
    }

    /**
     * ?????? Image
     * @param uri   {@link Uri} image
     * @param align ????????????
     *              <ul>
     *              <li>{@link Align#ALIGN_TOP     }</li>
     *              <li>{@link Align#ALIGN_CENTER  }</li>
     *              <li>{@link Align#ALIGN_BASELINE}</li>
     *              <li>{@link Align#ALIGN_BOTTOM  }</li>
     *              </ul>
     * @return {@link SpanUtils}
     */
    public SpanUtils appendImage(
            @NonNull final Uri uri,
            @Align final int align
    ) {
        apply(TYPE_IMAGE);
        this.imageUri   = uri;
        this.alignImage = align;
        return this;
    }

    /**
     * ?????? Image
     * @param resourceId The resource id of image
     * @return {@link SpanUtils}
     */
    public SpanUtils appendImage(@DrawableRes final int resourceId) {
        return appendImage(resourceId, ALIGN_BOTTOM);
    }

    /**
     * ?????? Image
     * @param resourceId The resource id of image
     * @param align      ????????????
     *                   <ul>
     *                   <li>{@link Align#ALIGN_TOP     }</li>
     *                   <li>{@link Align#ALIGN_CENTER  }</li>
     *                   <li>{@link Align#ALIGN_BASELINE}</li>
     *                   <li>{@link Align#ALIGN_BOTTOM  }</li>
     *                   </ul>
     * @return {@link SpanUtils}
     */
    public SpanUtils appendImage(
            @DrawableRes final int resourceId,
            @Align final int align
    ) {
        apply(TYPE_IMAGE);
        this.imageResourceId = resourceId;
        this.alignImage      = align;
        return this;
    }

    // =

    /**
     * ????????????
     * @param size ????????????
     * @return {@link SpanUtils}
     */
    public SpanUtils appendSpace(@IntRange(from = 0) final int size) {
        return appendSpace(size, Color.TRANSPARENT);
    }

    /**
     * ????????????
     * @param size  ????????????
     * @param color ????????????
     * @return {@link SpanUtils}
     */
    public SpanUtils appendSpace(
            @IntRange(from = 0) final int size,
            @ColorInt final int color
    ) {
        apply(TYPE_SPACE);
        this.spaceSize  = size;
        this.spaceColor = color;
        return this;
    }

    // =

    /**
     * ?????? SpannableStringBuilder
     * @return {@link SpannableStringBuilder}
     */
    public SpannableStringBuilder get() {
        return mBuilder;
    }

    /**
     * ?????? SpannableStringBuilder
     * @return {@link SpannableStringBuilder}
     */
    public SpannableStringBuilder create() {
        applyLast();
        if (mTextView != null) {
            mTextView.setText(mBuilder);
        }
        return mBuilder;
    }

    // ==========
    // = ???????????? =
    // ==========

    /**
     * ????????????
     * @param type ?????? ( ?????? ) ??????
     */
    private void apply(final int type) {
        applyLast();
        mType = type;
    }

    /**
     * ????????????, ???????????????
     */
    private void applyLast() {
        if (mType == TYPE_CHAR_SEQUENCE) {
            updateCharCharSequence();
        } else if (mType == TYPE_IMAGE) {
            updateImage();
        } else if (mType == TYPE_SPACE) {
            updateSpace();
        }
        setDefault();
    }

    /**
     * ?????? CharSequence ??????
     */
    private void updateCharCharSequence() {
        if (mText.length() == 0) return;
        int start = mBuilder.length();
        if (start == 0 && lineHeight != -1) { // bug of LineHeightSpan when first line
            mBuilder.append(Character.toString((char) 2))
                    .append(DevFinal.SYMBOL.NEW_LINE)
                    .setSpan(new AbsoluteSizeSpan(0), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            start = 2;
        }
        mBuilder.append(mText);
        int end = mBuilder.length();
        if (verticalAlign != -1) {
            mBuilder.setSpan(new VerticalAlignSpan(verticalAlign), start, end, flag);
        }
        if (foregroundColor != COLOR_DEFAULT) {
            mBuilder.setSpan(new ForegroundColorSpan(foregroundColor), start, end, flag);
        }
        if (backgroundColor != COLOR_DEFAULT) {
            mBuilder.setSpan(new BackgroundColorSpan(backgroundColor), start, end, flag);
        }
        if (first != -1) {
            mBuilder.setSpan(new LeadingMarginSpan.Standard(first, rest), start, end, flag);
        }
        if (quoteColor != COLOR_DEFAULT) {
            mBuilder.setSpan(new CustomQuoteSpan(quoteColor, stripeWidth, quoteGapWidth), start, end, flag);
        }
        if (bulletColor != COLOR_DEFAULT) {
            mBuilder.setSpan(new CustomBulletSpan(bulletColor, bulletRadius, bulletGapWidth), start, end, flag);
        }
        if (fontSize != -1) {
            mBuilder.setSpan(new AbsoluteSizeSpan(fontSize, fontSizeIsDp), start, end, flag);
        }
        if (proportion != -1) {
            mBuilder.setSpan(new RelativeSizeSpan(proportion), start, end, flag);
        }
        if (xProportion != -1) {
            mBuilder.setSpan(new ScaleXSpan(xProportion), start, end, flag);
        }
        if (lineHeight != -1) {
            mBuilder.setSpan(new CustomLineHeightSpan(lineHeight, alignLine), start, end, flag);
        }
        if (isStrikethrough) {
            mBuilder.setSpan(new StrikethroughSpan(), start, end, flag);
        }
        if (isUnderline) {
            mBuilder.setSpan(new UnderlineSpan(), start, end, flag);
        }
        if (isSuperscript) {
            mBuilder.setSpan(new SuperscriptSpan(), start, end, flag);
        }
        if (isSubscript) {
            mBuilder.setSpan(new SubscriptSpan(), start, end, flag);
        }
        if (isBold) {
            mBuilder.setSpan(new StyleSpan(Typeface.BOLD), start, end, flag);
        }
        if (isItalic) {
            mBuilder.setSpan(new StyleSpan(Typeface.ITALIC), start, end, flag);
        }
        if (isBoldItalic) {
            mBuilder.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), start, end, flag);
        }
        if (fontFamily != null) {
            mBuilder.setSpan(new TypefaceSpan(fontFamily), start, end, flag);
        }
        if (typeface != null) {
            mBuilder.setSpan(new CustomTypefaceSpan(typeface), start, end, flag);
        }
        if (alignment != null) {
            mBuilder.setSpan(new AlignmentSpan.Standard(alignment), start, end, flag);
        }
        if (clickSpan != null) {
            mBuilder.setSpan(clickSpan, start, end, flag);
        }
        if (url != null) {
            mBuilder.setSpan(new URLSpan(url), start, end, flag);
        }
        if (blurRadius != -1) {
            mBuilder.setSpan(new MaskFilterSpan(new BlurMaskFilter(blurRadius, style)), start, end, flag);
        }
        if (shader != null) {
            mBuilder.setSpan(new ShaderSpan(shader), start, end, flag);
        }
        if (shadowRadius != -1) {
            mBuilder.setSpan(new ShadowSpan(shadowRadius, shadowDx, shadowDy, shadowColor), start, end, flag);
        }
        if (spans != null) {
            for (Object span : spans) {
                mBuilder.setSpan(span, start, end, flag);
            }
        }
    }

    /**
     * ?????? Image
     */
    private void updateImage() {
        int start = mBuilder.length();
        mText = "<img>";
        updateCharCharSequence();
        int end = mBuilder.length();
        if (imageBitmap != null) {
            mBuilder.setSpan(new CustomImageSpan(imageBitmap, alignImage), start, end, flag);
        } else if (imageDrawable != null) {
            mBuilder.setSpan(new CustomImageSpan(imageDrawable, alignImage), start, end, flag);
        } else if (imageUri != null) {
            mBuilder.setSpan(new CustomImageSpan(imageUri, alignImage), start, end, flag);
        } else if (imageResourceId != -1) {
            mBuilder.setSpan(new CustomImageSpan(imageResourceId, alignImage), start, end, flag);
        }
    }

    /**
     * ?????? Span
     */
    private void updateSpace() {
        int start = mBuilder.length();
        mText = "< >";
        updateCharCharSequence();
        int end = mBuilder.length();
        mBuilder.setSpan(new SpaceSpan(spaceSize, spaceColor), start, end, flag);
    }

    /**
     * ??????????????????
     */
    private void setDefault() {
        flag            = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;
        foregroundColor = COLOR_DEFAULT;
        backgroundColor = COLOR_DEFAULT;
        lineHeight      = -1;
        quoteColor      = COLOR_DEFAULT;
        first           = -1;
        bulletColor     = COLOR_DEFAULT;
        fontSize        = -1;
        proportion      = -1;
        xProportion     = -1;
        isStrikethrough = false;
        isUnderline     = false;
        isSuperscript   = false;
        isSubscript     = false;
        isBold          = false;
        isItalic        = false;
        isBoldItalic    = false;
        fontFamily      = null;
        typeface        = null;
        alignment       = null;
        verticalAlign   = -1;
        clickSpan       = null;
        url             = null;
        blurRadius      = -1;
        shader          = null;
        shadowRadius    = -1;
        spaceSize       = -1;
        spans           = null;

        imageBitmap     = null;
        imageDrawable   = null;
        imageUri        = null;
        imageResourceId = -1;
    }

    // ========================
    // = ??????????????? Span ???????????? =
    // ========================

    /**
     * detail: ???????????? Span
     * @author Ttt
     */
    static class VerticalAlignSpan
            extends ReplacementSpan {

        static final int ALIGN_CENTER = 2;
        static final int ALIGN_TOP    = 3;

        final int mVerticalAlignment;

        VerticalAlignSpan(int verticalAlignment) {
            mVerticalAlignment = verticalAlignment;
        }

        @Override
        public int getSize(
                @NonNull Paint paint,
                CharSequence text,
                int start,
                int end,
                @Nullable Paint.FontMetricsInt fm
        ) {
            text = text.subSequence(start, end);
            return (int) paint.measureText(text.toString());
        }

        @Override
        public void draw(
                @NonNull Canvas canvas,
                CharSequence text,
                int start,
                int end,
                float x,
                int top,
                int y,
                int bottom,
                @NonNull Paint paint
        ) {
            text = text.subSequence(start, end);
            Paint.FontMetricsInt fm = paint.getFontMetricsInt();
            canvas.drawText(
                    text.toString(), x,
                    y - ((y + fm.descent + y + fm.ascent) / 2 - (bottom + top) / 2),
                    paint
            );
        }
    }

    /**
     * detail: ?????? Span
     * @author Ttt
     */
    static class CustomLineHeightSpan
            implements LineHeightSpan {

        private final int height;

        static final int ALIGN_CENTER = 2;
        static final int ALIGN_TOP    = 3;

        final  int                  mVerticalAlignment;
        static Paint.FontMetricsInt sfm;

        CustomLineHeightSpan(
                int height,
                int verticalAlignment
        ) {
            this.height        = height;
            mVerticalAlignment = verticalAlignment;
        }

        @Override
        public void chooseHeight(
                final CharSequence text,
                final int start,
                final int end,
                final int spanstartv,
                final int v,
                final Paint.FontMetricsInt fm
        ) {
            if (sfm == null) {
                sfm         = new Paint.FontMetricsInt();
                sfm.top     = fm.top;
                sfm.ascent  = fm.ascent;
                sfm.descent = fm.descent;
                sfm.bottom  = fm.bottom;
                sfm.leading = fm.leading;
            } else {
                fm.top     = sfm.top;
                fm.ascent  = sfm.ascent;
                fm.descent = sfm.descent;
                fm.bottom  = sfm.bottom;
                fm.leading = sfm.leading;
            }
            int need = height - (v + fm.descent - fm.ascent - spanstartv);
            if (need > 0) {
                if (mVerticalAlignment == ALIGN_TOP) {
                    fm.descent += need;
                } else if (mVerticalAlignment == ALIGN_CENTER) {
                    fm.descent += need / 2;
                    fm.ascent -= need / 2;
                } else {
                    fm.ascent -= need;
                }
            }
            need = height - (v + fm.bottom - fm.top - spanstartv);
            if (need > 0) {
                if (mVerticalAlignment == ALIGN_TOP) {
                    fm.bottom += need;
                } else if (mVerticalAlignment == ALIGN_CENTER) {
                    fm.bottom += need / 2;
                    fm.top -= need / 2;
                } else {
                    fm.top -= need;
                }
            }
            if (end == ((Spanned) text).getSpanEnd(this)) {
                sfm = null;
            }
        }
    }

    /**
     * detail: ?????? Span
     * @author Ttt
     */
    static class SpaceSpan
            extends ReplacementSpan {

        private final int   width;
        private final Paint paint = new Paint();

        private SpaceSpan(final int width) {
            this(width, Color.TRANSPARENT);
        }

        private SpaceSpan(
                final int width,
                final int color
        ) {
            super();
            this.width = width;
            paint.setColor(color);
            paint.setStyle(Paint.Style.FILL);
        }

        @Override
        public int getSize(
                @NonNull final Paint paint,
                final CharSequence text,
                @IntRange(from = 0) final int start,
                @IntRange(from = 0) final int end,
                @Nullable final Paint.FontMetricsInt fm
        ) {
            return width;
        }

        @Override
        public void draw(
                @NonNull final Canvas canvas,
                final CharSequence text,
                @IntRange(from = 0) final int start,
                @IntRange(from = 0) final int end,
                final float x,
                final int top,
                final int y,
                final int bottom,
                @NonNull final Paint paint
        ) {
            canvas.drawRect(x, top, x + width, bottom, this.paint);
        }
    }

    /**
     * detail: ????????????????????? Span
     * @author Ttt
     */
    static class CustomQuoteSpan
            implements LeadingMarginSpan {

        private final int color;
        private final int stripeWidth;
        private final int gapWidth;

        private CustomQuoteSpan(
                final int color,
                final int stripeWidth,
                final int gapWidth
        ) {
            super();
            this.color       = color;
            this.stripeWidth = stripeWidth;
            this.gapWidth    = gapWidth;
        }

        public int getLeadingMargin(final boolean first) {
            return stripeWidth + gapWidth;
        }

        public void drawLeadingMargin(
                final Canvas c,
                final Paint p,
                final int x,
                final int dir,
                final int top,
                final int baseline,
                final int bottom,
                final CharSequence text,
                final int start,
                final int end,
                final boolean first,
                final Layout layout
        ) {
            Paint.Style style = p.getStyle();
            int         color = p.getColor();

            p.setStyle(Paint.Style.FILL);
            p.setColor(this.color);

            c.drawRect(x, top, x + dir * stripeWidth, bottom, p);

            p.setStyle(style);
            p.setColor(color);
        }
    }

    /**
     * detail: ???????????????????????? Span
     * @author Ttt
     */
    static class CustomBulletSpan
            implements LeadingMarginSpan {

        private final int color;
        private final int radius;
        private final int gapWidth;

        private Path sBulletPath = null;

        private CustomBulletSpan(
                final int color,
                final int radius,
                final int gapWidth
        ) {
            this.color    = color;
            this.radius   = radius;
            this.gapWidth = gapWidth;
        }

        public int getLeadingMargin(final boolean first) {
            return 2 * radius + gapWidth;
        }

        public void drawLeadingMargin(
                final Canvas c,
                final Paint p,
                final int x,
                final int dir,
                final int top,
                final int baseline,
                final int bottom,
                final CharSequence text,
                final int start,
                final int end,
                final boolean first,
                final Layout l
        ) {
            if (((Spanned) text).getSpanStart(this) == start) {
                Paint.Style style = p.getStyle();
                int         oldColor;
                oldColor = p.getColor();
                p.setColor(color);
                p.setStyle(Paint.Style.FILL);
                if (c.isHardwareAccelerated()) {
                    if (sBulletPath == null) {
                        sBulletPath = new Path();
                        // Bullet is slightly better to avoid aliasing artifacts on mdpi devices.
                        sBulletPath.addCircle(0.0F, 0.0F, radius, Path.Direction.CW);
                    }
                    c.save();
                    c.translate(x + dir * radius, (top + bottom) / 2.0F);
                    c.drawPath(sBulletPath, p);
                    c.restore();
                } else {
                    c.drawCircle(x + dir * radius, (top + bottom) / 2.0F, radius, p);
                }
                p.setColor(oldColor);
                p.setStyle(style);
            }
        }
    }

    /**
     * detail: ????????????????????? Span
     * @author Ttt
     */
    @SuppressLint("ParcelCreator")
    static class CustomTypefaceSpan
            extends TypefaceSpan {

        private final Typeface newType;

        private CustomTypefaceSpan(final Typeface type) {
            super("");
            newType = type;
        }

        @Override
        public void updateDrawState(final TextPaint textPaint) {
            apply(textPaint, newType);
        }

        @Override
        public void updateMeasureState(final TextPaint paint) {
            apply(paint, newType);
        }

        private void apply(
                final Paint paint,
                final Typeface tf
        ) {
            int      oldStyle;
            Typeface old = paint.getTypeface();
            if (old == null) {
                oldStyle = 0;
            } else {
                oldStyle = old.getStyle();
            }

            int fake = oldStyle & ~tf.getStyle();
            if ((fake & Typeface.BOLD) != 0) {
                paint.setFakeBoldText(true);
            }

            if ((fake & Typeface.ITALIC) != 0) {
                paint.setTextSkewX(-0.25F);
            }
//            paint.getShader();
            paint.setTypeface(tf);
        }
    }

    /**
     * detail: ??????????????????????????? Span
     * @author Ttt
     */
    static class CustomImageSpan
            extends CustomDynamicDrawableSpan {

        private Drawable mDrawable;
        private Uri      mContentUri;
        private int      mResourceId;

        private CustomImageSpan(
                final Bitmap bitmap,
                final int verticalAlignment
        ) {
            super(verticalAlignment);
            mDrawable = new BitmapDrawable(getContext().getResources(), bitmap);
            mDrawable.setBounds(0, 0, mDrawable.getIntrinsicWidth(), mDrawable.getIntrinsicHeight());
        }

        private CustomImageSpan(
                final Drawable drawable,
                final int verticalAlignment
        ) {
            super(verticalAlignment);
            mDrawable = drawable;
            mDrawable.setBounds(0, 0, mDrawable.getIntrinsicWidth(), mDrawable.getIntrinsicHeight());
        }

        private CustomImageSpan(
                final Uri uri,
                final int verticalAlignment
        ) {
            super(verticalAlignment);
            mContentUri = uri;
        }

        private CustomImageSpan(
                @DrawableRes final int resourceId,
                final int verticalAlignment
        ) {
            super(verticalAlignment);
            mResourceId = resourceId;
        }

        @Override
        public Drawable getDrawable() {
            Drawable drawable = null;
            if (mDrawable != null) {
                drawable = mDrawable;
            } else if (mContentUri != null) {
                Bitmap bitmap;
                try {
                    InputStream is = getContext().getContentResolver().openInputStream(mContentUri);
                    bitmap   = BitmapFactory.decodeStream(is);
                    drawable = new BitmapDrawable(getContext().getResources(), bitmap);
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                    CloseUtils.closeIOQuietly(is);
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, e, "Failed to loaded content %s", mContentUri);
                }
            } else {
                try {
                    drawable = ContextCompat.getDrawable(getContext(), mResourceId);
                    if (drawable != null) {
                        drawable.setBounds(
                                0, 0, drawable.getIntrinsicWidth(),
                                drawable.getIntrinsicHeight()
                        );
                    }
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, e, "Unable to find resource: %s", mResourceId);
                }
            }
            return drawable;
        }
    }

    /**
     * detail: ????????? Drawable ???????????? Span
     * @author Ttt
     */
    static abstract class CustomDynamicDrawableSpan
            extends ReplacementSpan {

        static final int ALIGN_BOTTOM   = 0;
        static final int ALIGN_BASELINE = 1;
        static final int ALIGN_CENTER   = 2;
        static final int ALIGN_TOP      = 3;
        final        int mVerticalAlignment;

        private CustomDynamicDrawableSpan() {
            mVerticalAlignment = ALIGN_BOTTOM;
        }

        private CustomDynamicDrawableSpan(final int verticalAlignment) {
            mVerticalAlignment = verticalAlignment;
        }

        public abstract Drawable getDrawable();

        @Override
        public int getSize(
                @NonNull final Paint paint,
                final CharSequence text,
                final int start,
                final int end,
                final Paint.FontMetricsInt fm
        ) {
            Drawable drawable = getCachedDrawable();
            Rect     rect     = drawable.getBounds();
            if (fm != null) {
                int lineHeight = fm.bottom - fm.top;
                if (lineHeight < rect.height()) {
                    if (mVerticalAlignment == ALIGN_TOP) {
                        fm.bottom = rect.height() + fm.top;
                    } else if (mVerticalAlignment == ALIGN_CENTER) {
                        fm.top    = -rect.height() / 2 - lineHeight / 4;
                        fm.bottom = rect.height() / 2 - lineHeight / 4;
                    } else {
                        fm.top = -rect.height() + fm.bottom;
                    }
                    fm.ascent  = fm.top;
                    fm.descent = fm.bottom;
                }
            }
            return rect.right;
        }

        @Override
        public void draw(
                @NonNull final Canvas canvas,
                final CharSequence text,
                final int start,
                final int end,
                final float x,
                final int top,
                final int y,
                final int bottom,
                @NonNull final Paint paint
        ) {
            Drawable drawable = getCachedDrawable();
            Rect     rect     = drawable.getBounds();
            canvas.save();
            float transY;
            int   lineHeight = bottom - top;
            if (rect.height() < lineHeight) {
                if (mVerticalAlignment == ALIGN_TOP) {
                    transY = top;
                } else if (mVerticalAlignment == ALIGN_CENTER) {
                    transY = (bottom + top - rect.height()) / 2;
                } else if (mVerticalAlignment == ALIGN_BASELINE) {
                    transY = y - rect.height();
                } else {
                    transY = bottom - rect.height();
                }
                canvas.translate(x, transY);
            } else {
                canvas.translate(x, top);
            }
            drawable.draw(canvas);
            canvas.restore();
        }

        private Drawable getCachedDrawable() {
            WeakReference<Drawable> wr       = mDrawableRef;
            Drawable                drawable = null;
            if (wr != null) {
                drawable = wr.get();
            }
            if (drawable == null) {
                drawable     = getDrawable();
                mDrawableRef = new WeakReference<>(drawable);
            }
            return drawable;
        }

        private WeakReference<Drawable> mDrawableRef;
    }

    /**
     * detail: ???????????? Span
     * @author Ttt
     */
    static class ShaderSpan
            extends CharacterStyle
            implements UpdateAppearance {
        private final Shader mShader;

        private ShaderSpan(final Shader shader) {
            this.mShader = shader;
        }

        @Override
        public void updateDrawState(final TextPaint tp) {
            tp.setShader(mShader);
        }
    }

    /**
     * detail: ???????????? Span
     * @author Ttt
     */
    static class ShadowSpan
            extends CharacterStyle
            implements UpdateAppearance {
        private final float radius;
        private final float dx;
        private final float dy;
        private final int   shadowColor;

        private ShadowSpan(
                final float radius,
                final float dx,
                final float dy,
                final int shadowColor
        ) {
            this.radius      = radius;
            this.dx          = dx;
            this.dy          = dy;
            this.shadowColor = shadowColor;
        }

        @Override
        public void updateDrawState(final TextPaint tp) {
            tp.setShadowLayer(radius, dx, dy, shadowColor);
        }
    }

    // ==========
    // = ???????????? =
    // ==========

    /**
     * detail: SpannableStringBuilder ?????????, ???????????????????????????
     * @author Ttt
     */
    private static class SerializableSpannableStringBuilder
            extends SpannableStringBuilder
            implements Serializable {

        private static final long serialVersionUID = 2514562037168478805L;
    }

    /**
     * ?????? Context
     * @return {@link Context}
     */
    private static Context getContext() {
        return DevUtils.getContext();
    }
}