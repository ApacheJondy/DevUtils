package dev.utils.common.comparator;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import dev.utils.common.CollectionUtils;
import dev.utils.common.FileUtils;
import dev.utils.common.comparator.sort.DateSort;
import dev.utils.common.comparator.sort.DateSortAsc;
import dev.utils.common.comparator.sort.DateSortDesc;
import dev.utils.common.comparator.sort.DoubleSort;
import dev.utils.common.comparator.sort.DoubleSortAsc;
import dev.utils.common.comparator.sort.DoubleSortDesc;
import dev.utils.common.comparator.sort.FileLastModifiedSortAsc;
import dev.utils.common.comparator.sort.FileLastModifiedSortDesc;
import dev.utils.common.comparator.sort.FileLengthSortAsc;
import dev.utils.common.comparator.sort.FileLengthSortDesc;
import dev.utils.common.comparator.sort.FileNameSortAsc;
import dev.utils.common.comparator.sort.FileNameSortDesc;
import dev.utils.common.comparator.sort.FileSortAsc;
import dev.utils.common.comparator.sort.FileSortDesc;
import dev.utils.common.comparator.sort.FloatSort;
import dev.utils.common.comparator.sort.FloatSortAsc;
import dev.utils.common.comparator.sort.FloatSortDesc;
import dev.utils.common.comparator.sort.IntSort;
import dev.utils.common.comparator.sort.IntSortAsc;
import dev.utils.common.comparator.sort.IntSortDesc;
import dev.utils.common.comparator.sort.LongSort;
import dev.utils.common.comparator.sort.LongSortAsc;
import dev.utils.common.comparator.sort.LongSortDesc;
import dev.utils.common.comparator.sort.StringSort;
import dev.utils.common.comparator.sort.StringSortAsc;
import dev.utils.common.comparator.sort.StringSortDesc;
import dev.utils.common.comparator.sort.StringSortWindowsSimple;
import dev.utils.common.comparator.sort.StringSortWindowsSimple2;
import dev.utils.common.comparator.sort.WindowsExplorerFileSimpleComparator;
import dev.utils.common.comparator.sort.WindowsExplorerFileSimpleComparator2;
import dev.utils.common.comparator.sort.WindowsExplorerStringSimpleComparator;
import dev.utils.common.comparator.sort.WindowsExplorerStringSimpleComparator2;

/**
 * detail: ????????????????????????
 * @author Ttt
 * <pre>
 *     ???????????????????????? List ??????????????? null ??????
 *     {@link #sort(List, Comparator)}
 *     {@link #sortAsc(List)}
 *     {@link #sortDesc(List)}
 *     ????????????????????????????????? null ??????
 *     {@link CollectionUtils#clearNull(Collection)}
 *     <p></p>
 *     File ??????????????????????????????????????? List
 *     {@link FileUtils#listOrEmpty(File)}
 *     {@link FileUtils#listFilesOrEmpty(File)}
 * </pre>
 */
public final class ComparatorUtils {

    private ComparatorUtils() {
    }

    /**
     * List ????????????
     * @param list ??????
     * @return {@code true} success, {@code false} fail
     */
    public static boolean reverse(final List<?> list) {
        if (list != null) {
            Collections.reverse(list);
            return true;
        }
        return false;
    }

    /**
     * List ????????????
     * @param list       ??????
     * @param comparator ???????????????
     * @param <T>        ??????
     * @return {@code true} success, {@code false} fail
     */
    public static <T> boolean sort(
            final List<T> list,
            final Comparator<? super T> comparator
    ) {
        if (list != null) {
            Collections.sort(list, comparator);
            return true;
        }
        return false;
    }

    /**
     * List ????????????
     * @param list ??????
     * @param <T>  ??????
     * @return {@code true} success, {@code false} fail
     */
    public static <T extends Comparable<? super T>> boolean sortAsc(final List<T> list) {
        if (list != null) {
            Collections.sort(list);
            return true;
        }
        return false;
    }

    /**
     * List ????????????
     * @param list ??????
     * @param <T>  ??????
     * @return {@code true} success, {@code false} fail
     */
    public static <T> boolean sortDesc(final List<T> list) {
        return sort(list, Collections.reverseOrder());
    }

    // ========
    // = File =
    // ========

    /**
     * ??????????????????????????????
     * @param list ??????
     * @param <T>  ??????
     * @return {@code true} success, {@code false} fail
     */
    public static <T extends File> boolean sortFileLastModifiedAsc(final List<T> list) {
        return sort(list, new FileLastModifiedSortAsc());
    }

    /**
     * ??????????????????????????????
     * @param list ??????
     * @param <T>  ??????
     * @return {@code true} success, {@code false} fail
     */
    public static <T extends File> boolean sortFileLastModifiedDesc(final List<T> list) {
        return sort(list, new FileLastModifiedSortDesc());
    }

    /**
     * ????????????????????????
     * @param list ??????
     * @param <T>  ??????
     * @return {@code true} success, {@code false} fail
     */
    public static <T extends File> boolean sortFileLengthAsc(final List<T> list) {
        return sort(list, new FileLengthSortAsc());
    }

    /**
     * ????????????????????????
     * @param list ??????
     * @param <T>  ??????
     * @return {@code true} success, {@code false} fail
     */
    public static <T extends File> boolean sortFileLengthDesc(final List<T> list) {
        return sort(list, new FileLengthSortDesc());
    }

    /**
     * ?????????????????????
     * @param list ??????
     * @param <T>  ??????
     * @return {@code true} success, {@code false} fail
     */
    public static <T extends File> boolean sortFileNameAsc(final List<T> list) {
        return sort(list, new FileNameSortAsc());
    }

    /**
     * ?????????????????????
     * @param list ??????
     * @param <T>  ??????
     * @return {@code true} success, {@code false} fail
     */
    public static <T extends File> boolean sortFileNameDesc(final List<T> list) {
        return sort(list, new FileNameSortDesc());
    }

    /**
     * ??????????????????
     * @param list ??????
     * @param <T>  ??????
     * @return {@code true} success, {@code false} fail
     */
    public static <T extends File> boolean sortFileAsc(final List<T> list) {
        return sort(list, new FileSortAsc());
    }

    /**
     * ??????????????????
     * @param list ??????
     * @param <T>  ??????
     * @return {@code true} success, {@code false} fail
     */
    public static <T extends File> boolean sortFileDesc(final List<T> list) {
        return sort(list, new FileSortDesc());
    }

    // ========
    // = Date =
    // ========

    /**
     * Date ????????????
     * @param list ??????
     * @param <T>  ??????
     * @return {@code true} success, {@code false} fail
     */
    public static <T extends DateSort> boolean sortDateAsc(final List<T> list) {
        return sort(list, new DateSortAsc<>());
    }

    /**
     * Date ????????????
     * @param list ??????
     * @param <T>  ??????
     * @return {@code true} success, {@code false} fail
     */
    public static <T extends DateSort> boolean sortDateDesc(final List<T> list) {
        return sort(list, new DateSortDesc<>());
    }

    // =========
    // = Double =
    // =========

    /**
     * Double ????????????
     * @param list ??????
     * @param <T>  ??????
     * @return {@code true} success, {@code false} fail
     */
    public static <T extends DoubleSort> boolean sortDoubleAsc(final List<T> list) {
        return sort(list, new DoubleSortAsc<>());
    }

    /**
     * Double ????????????
     * @param list ??????
     * @param <T>  ??????
     * @return {@code true} success, {@code false} fail
     */
    public static <T extends DoubleSort> boolean sortDoubleDesc(final List<T> list) {
        return sort(list, new DoubleSortDesc<>());
    }

    // =========
    // = Float =
    // =========

    /**
     * Float ????????????
     * @param list ??????
     * @param <T>  ??????
     * @return {@code true} success, {@code false} fail
     */
    public static <T extends FloatSort> boolean sortFloatAsc(final List<T> list) {
        return sort(list, new FloatSortAsc<>());
    }

    /**
     * Float ????????????
     * @param list ??????
     * @param <T>  ??????
     * @return {@code true} success, {@code false} fail
     */
    public static <T extends FloatSort> boolean sortFloatDesc(final List<T> list) {
        return sort(list, new FloatSortDesc<>());
    }

    // =======
    // = Int =
    // =======

    /**
     * Int ????????????
     * @param list ??????
     * @param <T>  ??????
     * @return {@code true} success, {@code false} fail
     */
    public static <T extends IntSort> boolean sortIntAsc(final List<T> list) {
        return sort(list, new IntSortAsc<>());
    }

    /**
     * Int ????????????
     * @param list ??????
     * @param <T>  ??????
     * @return {@code true} success, {@code false} fail
     */
    public static <T extends IntSort> boolean sortIntDesc(final List<T> list) {
        return sort(list, new IntSortDesc<>());
    }

    // ========
    // = Long =
    // ========

    /**
     * Long ????????????
     * @param list ??????
     * @param <T>  ??????
     * @return {@code true} success, {@code false} fail
     */
    public static <T extends LongSort> boolean sortLongAsc(final List<T> list) {
        return sort(list, new LongSortAsc<>());
    }

    /**
     * Long ????????????
     * @param list ??????
     * @param <T>  ??????
     * @return {@code true} success, {@code false} fail
     */
    public static <T extends LongSort> boolean sortLongDesc(final List<T> list) {
        return sort(list, new LongSortDesc<>());
    }

    // =========
    // = String =
    // =========

    /**
     * String ????????????
     * @param list ??????
     * @param <T>  ??????
     * @return {@code true} success, {@code false} fail
     */
    public static <T extends StringSort> boolean sortStringAsc(final List<T> list) {
        return sort(list, new StringSortAsc<>());
    }

    /**
     * String ????????????
     * @param list ??????
     * @param <T>  ??????
     * @return {@code true} success, {@code false} fail
     */
    public static <T extends StringSort> boolean sortStringDesc(final List<T> list) {
        return sort(list, new StringSortDesc<>());
    }

    // =

    /**
     * String Windows ???????????????????????????????????????
     * @param list ??????
     * @param <T>  ??????
     * @return {@code true} success, {@code false} fail
     */
    public static <T extends StringSort> boolean sortStringWindowsSimpleAsc(final List<T> list) {
        return sort(list, new StringSortWindowsSimple<>());
    }

    /**
     * String Windows ???????????????????????????????????????
     * @param list ??????
     * @param <T>  ??????
     * @return {@code true} success, {@code false} fail
     */
    public static <T extends StringSort> boolean sortStringWindowsSimpleDesc(final List<T> list) {
        boolean result = sortStringWindowsSimpleAsc(list);
        if (result) reverse(list);
        return result;
    }

    /**
     * String Windows ??????????????????????????????????????? ( ??????????????? )
     * @param list ??????
     * @param <T>  ??????
     * @return {@code true} success, {@code false} fail
     */
    public static <T extends StringSort> boolean sortStringWindowsSimple2Asc(final List<T> list) {
        return sort(list, new StringSortWindowsSimple2<>());
    }

    /**
     * String Windows ??????????????????????????????????????? ( ??????????????? )
     * @param list ??????
     * @param <T>  ??????
     * @return {@code true} success, {@code false} fail
     */
    public static <T extends StringSort> boolean sortStringWindowsSimple2Desc(final List<T> list) {
        boolean result = sortStringWindowsSimple2Asc(list);
        if (result) reverse(list);
        return result;
    }

    // ====================
    // = Windows Explorer =
    // ====================

    /**
     * Windows ??????????????????????????????
     * @param list ??????
     * @return {@code true} success, {@code false} fail
     */
    public static boolean sortWindowsExplorerFileSimpleComparatorAsc(final List<File> list) {
        return sort(list, new WindowsExplorerFileSimpleComparator());
    }

    /**
     * Windows ??????????????????????????????
     * @param list ??????
     * @return {@code true} success, {@code false} fail
     */
    public static boolean sortWindowsExplorerFileSimpleComparatorDesc(final List<File> list) {
        boolean result = sortWindowsExplorerFileSimpleComparatorAsc(list);
        if (result) reverse(list);
        return result;
    }

    /**
     * Windows ?????????????????????????????? ( ??????????????? )
     * @param list ??????
     * @return {@code true} success, {@code false} fail
     */
    public static boolean sortWindowsExplorerFileSimpleComparator2Asc(final List<File> list) {
        return sort(list, new WindowsExplorerFileSimpleComparator2());
    }

    /**
     * Windows ?????????????????????????????? ( ??????????????? )
     * @param list ??????
     * @return {@code true} success, {@code false} fail
     */
    public static boolean sortWindowsExplorerFileSimpleComparator2Desc(final List<File> list) {
        boolean result = sortWindowsExplorerFileSimpleComparator2Asc(list);
        if (result) reverse(list);
        return result;
    }

    // =

    /**
     * Windows ?????????????????????????????????
     * @param list ??????
     * @return {@code true} success, {@code false} fail
     */
    public static boolean sortWindowsExplorerStringSimpleComparatorAsc(final List<String> list) {
        return sort(list, new WindowsExplorerStringSimpleComparator());
    }

    /**
     * Windows ?????????????????????????????????
     * @param list ??????
     * @return {@code true} success, {@code false} fail
     */
    public static boolean sortWindowsExplorerStringSimpleComparatorDesc(final List<String> list) {
        boolean result = sortWindowsExplorerStringSimpleComparatorAsc(list);
        if (result) reverse(list);
        return result;
    }

    /**
     * Windows ????????????????????????????????? ( ??????????????? )
     * @param list ??????
     * @return {@code true} success, {@code false} fail
     */
    public static boolean sortWindowsExplorerStringSimpleComparator2Asc(final List<String> list) {
        return sort(list, new WindowsExplorerStringSimpleComparator2());
    }

    /**
     * Windows ????????????????????????????????? ( ??????????????? )
     * @param list ??????
     * @return {@code true} success, {@code false} fail
     */
    public static boolean sortWindowsExplorerStringSimpleComparator2Desc(final List<String> list) {
        boolean result = sortWindowsExplorerStringSimpleComparator2Asc(list);
        if (result) reverse(list);
        return result;
    }
}