package dev.utils.app.logger;

import android.text.TextUtils;

import java.io.File;

import dev.utils.LogPrintUtils;

/**
 * detail: 日志操作工具类
 * @author Ttt
 */
public final class DevLoggerUtils {

    private DevLoggerUtils() {
    }

    // 日志 TAG
    private static final String TAG = DevLoggerUtils.class.getSimpleName();

    /**
     * 初始化调用方法 ( 内部已调用 )
     */
    public static void init() {
        // 保存 APP 版本信息
        Utils.init();
    }

    // ========================
    // = 快速初始化 LogConfig =
    // ========================

    /**
     * 获取 Release Log 配置 ( 打印线程信息、显示方法总数 3、从 0 开始、不进行排序、默认只打印 ERROR 级别日志 )
     * @param tag 日志 TAG
     * @return {@link LogConfig} 日志配置
     */
    public static LogConfig getReleaseLogConfig(final String tag) {
        return getLogConfig(tag, 3, 0, false, true, false, LogLevel.ERROR);
    }

    /**
     * 获取 Release Log 配置 ( 打印线程信息、显示方法总数 3、从 0 开始、不进行排序 )
     * @param tag      日志 TAG
     * @param logLevel 日志级别
     * @return {@link LogConfig} 日志配置
     */
    public static LogConfig getReleaseLogConfig(final String tag, final LogLevel logLevel) {
        return getLogConfig(tag, 3, 0, false, true, false, logLevel);
    }

    // =

    /**
     * 获取 Debug Log 配置 ( 打印线程信息、显示方法总数 3、从 0 开始、不进行排序、默认只打印 ERROR 级别日志 )
     * @param tag 日志 TAG
     * @return {@link LogConfig} 日志配置
     */
    public static LogConfig getDebugLogConfig(final String tag) {
        return getLogConfig(tag, 3, 0, false, true, false, LogLevel.DEBUG);
    }

    /**
     * 获取 Debug Log 配置 ( 打印线程信息、显示方法总数 3、从 0 开始、进行排序 )
     * @param tag      日志 TAG
     * @param logLevel 日志级别
     * @return {@link LogConfig} 日志配置
     */
    public static LogConfig getDebugLogConfig(final String tag, final LogLevel logLevel) {
        return getLogConfig(tag, 3, 0, false, true, false, logLevel);
    }

    // =

    /**
     * 获取 Log 配置 ( 打印线程信息、显示方法总数 3、从 0 开始、并且美化日志信息、默认打印 DEBUG 级别及以上日志 )
     * @param tag 日志 TAG
     * @return {@link LogConfig} 日志配置
     */
    public static LogConfig getSortLogConfig(final String tag) {
        return getLogConfig(tag, 3, 0, false, true, true, LogLevel.DEBUG);
    }

    /**
     * 获取 Log 配置 ( 打印线程信息、显示方法总数 3、从 0 开始、并且美化日志信息 )
     * @param tag      日志 TAG
     * @param logLevel 日志级别
     * @return {@link LogConfig} 日志配置
     */
    public static LogConfig getSortLogConfig(final String tag, final LogLevel logLevel) {
        return getLogConfig(tag, 3, 0, false, true, true, logLevel);
    }

    // =

    /**
     * 获取 Log 配置
     * @param tag        日志 TAG
     * @param count      显示的方法总数 ( 推荐 3)
     * @param offset     方法偏移索引 ( 从第几个方法开始打印, 默认推荐 0)
     * @param allMethod  是否打印全部方法
     * @param threadInfo 是否显示线程信息
     * @param sortLog    是否排序日志 ( 美化 )
     * @param logLevel   日志级别
     * @return {@link LogConfig} 日志配置
     */
    public static LogConfig getLogConfig(final String tag, final int count, final int offset, final boolean allMethod, final boolean threadInfo, final boolean sortLog, final LogLevel logLevel) {
        // 生成默认配置信息
        LogConfig logConfig = new LogConfig();
        // 堆栈方法总数 ( 显示经过的方法 )
        logConfig.methodCount = count;
        // 堆栈方法索引偏移 (0 = 最新经过调用的方法信息, 偏移则往上推, 如 1 = 倒数第二条经过调用的方法信息 )
        logConfig.methodOffset = offset;
        // 是否输出全部方法 ( 在特殊情况下, 如想要打印全部经过的方法, 但是不知道经过的总数 )
        logConfig.outputMethodAll = allMethod;
        // 显示日志线程信息 ( 特殊情况, 显示经过的线程信息, 具体情况如上 )
        logConfig.displayThreadInfo = threadInfo;
        // 是否排序日志 ( 格式化 )
        logConfig.sortLog = sortLog;
        // 日志级别
        logConfig.logLevel = logLevel;
        // 设置 TAG ( 特殊情况使用, 不使用全部的 TAG 时, 如单独输出在某个 TAG 下 )
        logConfig.tag = tag;
        // 返回日志配置
        return logConfig;
    }

    // ================
    // = 错误日志处理 =
    // ================

    /**
     * 保存异常日志
     * @param ex       错误信息
     * @param filePath 保存路径 + 文件名 ( 含后缀 )
     * @return {@code true} 保存成功, {@code false} 保存失败
     */
    public static boolean saveErrorLog(final Throwable ex, final String filePath) {
        return saveErrorLog(ex, null, null, filePath);
    }

    /**
     * 保存异常日志
     * @param ex       错误信息
     * @param head     顶部标题
     * @param bottom   底部内容
     * @param filePath 保存路径 + 文件名 ( 含后缀 )
     * @return {@code true} 保存成功, {@code false} 保存失败
     */
    public static boolean saveErrorLog(final Throwable ex, final String head, final String bottom, final String filePath) {
        if (TextUtils.isEmpty(filePath)) return false;
        try {
            File file = new File(filePath);
            // 判断是否属于文件夹
            if (file.isDirectory()) {
                // 进行保存
                return saveErrorLog(ex, head, bottom, filePath, System.currentTimeMillis() + ".log");
            } else {
                // 获取文件名
                String fileName = file.getName();
                // 重新裁剪
                String tempPath = filePath.substring(0, filePath.length() - fileName.length());
                // 进行保存
                return saveErrorLog(ex, head, bottom, tempPath, fileName);
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "saveErrorLog");
        }
        return false;
    }

    // =

    /**
     * 保存异常日志
     * @param ex       错误信息
     * @param filePath 保存路径
     * @param fileName 文件名 ( 含后缀 )
     * @return {@code true} 保存成功, {@code false} 保存失败
     */
    public static boolean saveErrorLog(final Throwable ex, final String filePath, final String fileName) {
        return saveErrorLog(ex, null, null, filePath, fileName);
    }

    /**
     * 保存异常日志
     * @param ex       错误信息
     * @param head     顶部标题
     * @param bottom   底部内容
     * @param filePath 保存路径
     * @param fileName 文件名 ( 含后缀 )
     * @return {@code true} 保存成功, {@code false} 保存失败
     */
    public static boolean saveErrorLog(final Throwable ex, final String head, final String bottom, final String filePath, final String fileName) {
        if (TextUtils.isEmpty(filePath)) {
            return false;
        } else if (TextUtils.isEmpty(fileName)) {
            return false;
        }
        return Utils.saveErrorLog(ex, head, bottom, filePath, fileName, true);
    }

    // ============
    // = 日志处理 =
    // ============

    /**
     * 保存日志
     * @param log      日志信息
     * @param filePath 保存路径 + 文件名 ( 含后缀 )
     * @return {@code true} 保存成功, {@code false} 保存失败
     */
    public static boolean saveLog(final String log, final String filePath) {
        return saveLog(log, null, null, filePath);
    }

    /**
     * 保存日志
     * @param log      日志信息
     * @param head     顶部标题
     * @param bottom   底部内容
     * @param filePath 保存路径 + 文件名 ( 含后缀 )
     * @return {@code true} 保存成功, {@code false} 保存失败
     */
    public static boolean saveLog(final String log, final String head, final String bottom, final String filePath) {
        if (TextUtils.isEmpty(filePath)) return false;
        try {
            File file = new File(filePath);
            // 判断是否属于文件夹
            if (file.isDirectory()) {
                // 进行保存
                return saveLog(log, head, bottom, filePath, System.currentTimeMillis() + ".log");
            } else {
                // 获取文件名
                String fileName = file.getName();
                // 重新裁剪
                String tempPath = filePath.substring(0, filePath.length() - fileName.length());
                // 进行保存
                return saveLog(log, head, bottom, tempPath, fileName);
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "saveLog");
        }
        return false;
    }

    // =

    /**
     * 保存日志
     * @param log      日志信息
     * @param filePath 保存路径
     * @param fileName 文件名 ( 含后缀 )
     * @return {@code true} 保存成功, {@code false} 保存失败
     */
    public static boolean saveLog(final String log, final String filePath, final String fileName) {
        return saveLog(log, null, null, filePath, fileName);
    }

    /**
     * 保存日志
     * @param log      日志信息
     * @param head     顶部标题
     * @param bottom   底部内容
     * @param filePath 保存路径
     * @param fileName 文件名 ( 含后缀 )
     * @return {@code true} 保存成功, {@code false} 保存失败
     */
    public static boolean saveLog(final String log, final String head, final String bottom,
                                  final String filePath, final String fileName) {
        if (TextUtils.isEmpty(filePath)) {
            return false;
        } else if (TextUtils.isEmpty(fileName)) {
            return false;
        }
        return Utils.saveLog(log, head, bottom, filePath, fileName, true);
    }
}