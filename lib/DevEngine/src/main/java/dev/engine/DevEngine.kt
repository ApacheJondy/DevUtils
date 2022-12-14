package dev.engine

import android.content.Context
import com.tencent.mmkv.MMKV
import dev.DevUtils
import dev.engine.analytics.DevAnalyticsEngine
import dev.engine.barcode.DevBarCodeEngine
import dev.engine.barcode.ZXingEngineImpl
import dev.engine.cache.CacheConfig
import dev.engine.cache.DevCacheEngine
import dev.engine.cache.DevCacheEngineImpl
import dev.engine.compress.DevCompressEngine
import dev.engine.compress.LubanEngineImpl
import dev.engine.image.DevImageEngine
import dev.engine.image.GlideEngineImpl
import dev.engine.json.DevJSONEngine
import dev.engine.json.GsonEngineImpl
import dev.engine.keyvalue.*
import dev.engine.log.DevLogEngine
import dev.engine.log.DevLoggerEngineImpl
import dev.engine.media.DevMediaEngine
import dev.engine.media.PictureSelectorEngineImpl
import dev.engine.permission.DevPermissionEngine
import dev.engine.permission.DevPermissionEngineImpl
import dev.engine.push.DevPushEngine
import dev.engine.share.DevShareEngine
import dev.engine.storage.DevMediaStoreEngineImpl
import dev.engine.storage.DevStorageEngine
import dev.utils.app.cache.DevCache
import dev.utils.app.logger.LogConfig
import dev.utils.common.cipher.Cipher

/**
 * detail: DevEngine
 * @author Ttt
 * <p></p>
 * GitHub
 * @see https://github.com/afkT/DevUtils
 * DevApp Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevApp/README.md
 * DevAssist Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/README.md
 * DevBase README
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevBase/README.md
 * DevBaseMVVM README
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevBaseMVVM/README.md
 * DevMVVM README
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevMVVM/README.md
 * DevEngine README
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevEngine/README.md
 * DevHttpCapture Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevHttpCapture/README.md
 * DevHttpManager Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevHttpManager/README.md
 * DevRetrofit Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevRetrofit/README.md
 * DevWidget Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/README.md
 * DevEnvironment Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/Environment
 * DevJava Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevJava/README.md
 */
object DevEngine {

    // ============
    // = ??????????????? =
    // ============

    /**
     * ?????? DevEngine ?????????
     * @return DevEngine versionCode
     */
    fun getDevEngineVersionCode(): Int {
        return BuildConfig.DevEngine_VersionCode
    }

    /**
     * ?????? DevEngine ??????
     * @return DevEngine versionName
     */
    fun getDevEngineVersion(): String {
        return BuildConfig.DevEngine_Version
    }

    /**
     * ?????? DevApp ?????????
     * @return DevApp versionCode
     */
    fun getDevAppVersionCode(): Int {
        return BuildConfig.DevApp_VersionCode
    }

    /**
     * ?????? DevApp ??????
     * @return DevApp versionName
     */
    fun getDevAppVersion(): String {
        return BuildConfig.DevApp_Version
    }

    // ==========
    // = ???????????? =
    // ==========

    /**
     * ?????? MMKV Config
     * @param cipher ??????????????????
     * @param mmkv [MMKV]
     * @return [MMKVConfig]
     * <p></p>
     * ???????????? [defaultMMKVInitialize]
     */
    fun createMMKVConfig(
        cipher: Cipher? = null,
        mmkv: MMKV
    ): MMKVConfig {
        return MMKVConfig(cipher, mmkv)
    }

    // ============
    // = ??????????????? =
    // ============

    /**
     * ??????????????? ( ???????????????????????????????????????????????? )
     * @param context Context
     */
    fun completeInitialize(context: Context) {
        defaultMMKVInitialize(context)
        // ?????? MMKV ?????? null ??????????????????
        MMKVUtils.defaultHolder().mmkv?.let { mmkv ->
            defaultEngine(createMMKVConfig(cipher = null, mmkv = mmkv))
            return
        }
        defaultEngine()
    }

    // =

    /**
     * ?????? DevEngine ????????????????????? MMKV ?????????
     * @param context Context?
     * @return DevEngine
     */
    fun defaultMMKVInitialize(context: Context?): DevEngine {
        DevUtils.getContext(context)?.let {
            MMKVUtils.initialize(it.applicationContext)
        }
        return this
    }

    /**
     * ?????? DevEngine ????????????????????? Engine
     * @param keyValueConfig Key-Value Engine Config
     * @param logConfig Log Config
     * ???????????? MMKV ??????????????? [defaultMMKVInitialize]
     * ????????????????????? [defaultEngine] ?????? [MMKVConfig] or [createMMKVConfig]
     */
    fun defaultEngine(
        keyValueConfig: IKeyValueEngine.EngineConfig? = null
    ) {
        defaultEngine(
            CacheConfig(null, DevCache.newCache()),
            keyValueConfig
        )
    }

    /**
     * ?????? DevEngine ????????????????????? Engine
     * @param cacheConfig Cache Engine Config
     * @param keyValueConfig Key-Value Engine Config
     * @param logConfig Log Config
     * <p><p>
     * ?????? Key-Value ??? MMKV Engine ?????????????????? MMKV.initialize()
     */
    fun defaultEngine(
        cacheConfig: CacheConfig?,
        keyValueConfig: IKeyValueEngine.EngineConfig?,
        logConfig: LogConfig? = null
    ) {

        // ========================
        // = BarCode Engine ????????? =
        // ========================

        // ????????? ZXing BarCode Engine ??????
        DevBarCodeEngine.setEngine(ZXingEngineImpl())

        // ===============
        // = JSON Engine =
        // ===============

        // ????????? Gson JSON Engine ??????
        DevJSONEngine.setEngine(GsonEngineImpl())
//        // ????????? Fastjson JSON Engine ??????
//        DevJSONEngine.setEngine(FastjsonEngineImpl())

        // ==============================
        // = Cache Engine ???????????????????????? =
        // ==============================

        cacheConfig?.apply {
            // ????????? DevCache ( DevUtils ) Cache Engine ??????
            DevCacheEngine.setEngine(DevCacheEngineImpl(this))
        }

        // ================================
        // = Image Compress Engine ???????????? =
        // ================================

        // ????????? Luban Image Compress Engine ??????
        DevCompressEngine.setEngine(LubanEngineImpl())

        // ====================================
        // = Image Engine ???????????????????????????????????? =
        // ====================================

        // ????????? Glide Image Engine ??????
        DevImageEngine.setEngine(GlideEngineImpl())

        // ============================
        // = KeyValue Engine ??????????????? =
        // ============================

        keyValueConfig?.apply {
            if (this is MMKVConfig) {
                // ????????? MMKV Key-Value Engine ??????
                DevKeyValueEngine.setEngine(MMKVKeyValueEngineImpl(this))
            } else if (this is SPConfig) {
                // ????????? SharedPreferences Key-Value Engine ??????
                DevKeyValueEngine.setEngine(SPKeyValueEngineImpl(this))
            }
        }

        // =====================
        // = Log Engine ???????????? =
        // =====================

        // ????????? DevLogger Log Engine ??????
        DevLogEngine.setEngine(object : DevLoggerEngineImpl(logConfig) {
            override fun isPrintLog(): Boolean {
                // ?????? Debug ?????????????????????
                return DevUtils.isDebug()
            }
        })

        // =====================================
        // = Media Selector Engine ????????????????????? =
        // =====================================

        // ????????? PictureSelector Media Selector Engine ??????
        DevMediaEngine.setEngine(PictureSelectorEngineImpl())

        // ============================
        // = Permission Engine ???????????? =
        // ============================

        // ????????? DevUtils Permission Engine ??????
        DevPermissionEngine.setEngine(DevPermissionEngineImpl())

        // =================================
        // = Storage Engine ??????????????????????????? =
        // =================================

        // ????????? DevUtils MediaStore Engine ??????
        DevStorageEngine.setEngine(DevMediaStoreEngineImpl())
    }

    // ==============
    // = Engine get =
    // ==============

    /**
     * ?????? Analytics Engine
     * @return Analytics Engine
     */
    fun getAnalytics() = DevAnalyticsEngine.getEngine()

    /**
     * ?????? BarCode Engine
     * @return BarCode Engine
     */
    fun getBarCode() = DevBarCodeEngine.getEngine()

    /**
     * ?????? Cache Engine
     * @return Cache Engine
     */
    fun getCache() = DevCacheEngine.getEngine()

    /**
     * ?????? Compress Engine
     * @return Compress Engine
     */
    fun getCompress() = DevCompressEngine.getEngine()

    /**
     * ?????? Image Engine
     * @return Image Engine
     */
    fun getImage() = DevImageEngine.getEngine()

    /**
     * ?????? JSON Engine
     * @return JSON Engine
     */
    fun getJSON() = DevJSONEngine.getEngine()

    /**
     * ?????? KeyValue Engine
     * @return KeyValue Engine
     */
    fun getKeyValue() = DevKeyValueEngine.getEngine()

    /**
     * ?????? Log Engine
     * @return Log Engine
     */
    fun getLog() = DevLogEngine.getEngine()

    /**
     * ?????? Media Engine
     * @return Media Engine
     */
    fun getMedia() = DevMediaEngine.getEngine()

    /**
     * ?????? Permission Engine
     * @return Permission Engine
     */
    fun getPermission() = DevPermissionEngine.getEngine()

    /**
     * ?????? Push Engine
     * @return Push Engine
     */
    fun getPush() = DevPushEngine.getEngine()

    /**
     * ?????? Share Engine
     * @return Share Engine
     */
    fun getShare() = DevShareEngine.getEngine()

    /**
     * ?????? Storage Engine
     * @return Storage Engine
     */
    fun getStorage() = DevStorageEngine.getEngine()

    // ==================
    // = Engine Key get =
    // ==================

    /**
     * ?????? Analytics Engine
     * @return Analytics Engine
     */
    fun getAnalytics(key: String?) = DevAnalyticsEngine.getEngine(key)

    /**
     * ?????? BarCode Engine
     * @return BarCode Engine
     */
    fun getBarCode(key: String?) = DevBarCodeEngine.getEngine(key)

    /**
     * ?????? Cache Engine
     * @return Cache Engine
     */
    fun getCache(key: String?) = DevCacheEngine.getEngine(key)

    /**
     * ?????? Compress Engine
     * @return Compress Engine
     */
    fun getCompress(key: String?) = DevCompressEngine.getEngine(key)

    /**
     * ?????? Image Engine
     * @return Image Engine
     */
    fun getImage(key: String?) = DevImageEngine.getEngine(key)

    /**
     * ?????? JSON Engine
     * @return JSON Engine
     */
    fun getJSON(key: String?) = DevJSONEngine.getEngine(key)

    /**
     * ?????? KeyValue Engine
     * @return KeyValue Engine
     */
    fun getKeyValue(key: String?) = DevKeyValueEngine.getEngine(key)

    /**
     * ?????? Log Engine
     * @return Log Engine
     */
    fun getLog(key: String?) = DevLogEngine.getEngine(key)

    /**
     * ?????? Media Engine
     * @return Media Engine
     */
    fun getMedia(key: String?) = DevMediaEngine.getEngine(key)

    /**
     * ?????? Permission Engine
     * @return Permission Engine
     */
    fun getPermission(key: String?) = DevPermissionEngine.getEngine(key)

    /**
     * ?????? Push Engine
     * @return Push Engine
     */
    fun getPush(key: String?) = DevPushEngine.getEngine(key)

    /**
     * ?????? Share Engine
     * @return Share Engine
     */
    fun getShare(key: String?) = DevShareEngine.getEngine(key)

    /**
     * ?????? Storage Engine
     * @return Storage Engine
     */
    fun getStorage(key: String?) = DevStorageEngine.getEngine(key)

    // =================
    // = Engine Assist =
    // =================

    /**
     * ?????? Analytics Engine Generic Assist
     * @return Analytics Engine Generic Assist
     */
    fun getAnalyticsAssist() = DevAnalyticsEngine.getAssist()

    /**
     * ?????? BarCode Engine Generic Assist
     * @return BarCode Engine Generic Assist
     */
    fun getBarCodeAssist() = DevBarCodeEngine.getAssist()

    /**
     * ?????? Cache Engine Generic Assist
     * @return Cache Engine Generic Assist
     */
    fun getCacheAssist() = DevCacheEngine.getAssist()

    /**
     * ?????? Compress Engine Generic Assist
     * @return Compress Engine Generic Assist
     */
    fun getCompressAssist() = DevCompressEngine.getAssist()

    /**
     * ?????? Image Engine Generic Assist
     * @return Image Engine Generic Assist
     */
    fun getImageAssist() = DevImageEngine.getAssist()

    /**
     * ?????? JSON Engine Generic Assist
     * @return JSON Engine Generic Assist
     */
    fun getJSONAssist() = DevJSONEngine.getAssist()

    /**
     * ?????? KeyValue Engine Generic Assist
     * @return KeyValue Engine Generic Assist
     */
    fun getKeyValueAssist() = DevKeyValueEngine.getAssist()

    /**
     * ?????? Log Engine Generic Assist
     * @return Log Engine Generic Assist
     */
    fun getLogAssist() = DevLogEngine.getAssist()

    /**
     * ?????? Media Engine Generic Assist
     * @return Media Engine Generic Assist
     */
    fun getMediaAssist() = DevMediaEngine.getAssist()

    /**
     * ?????? Permission Engine Generic Assist
     * @return Permission Engine Generic Assist
     */
    fun getPermissionAssist() = DevPermissionEngine.getAssist()

    /**
     * ?????? Push Engine Generic Assist
     * @return Push Engine Generic Assist
     */
    fun getPushAssist() = DevPushEngine.getAssist()

    /**
     * ?????? Share Engine Generic Assist
     * @return Share Engine Generic Assist
     */
    fun getShareAssist() = DevShareEngine.getAssist()

    /**
     * ?????? Storage Engine Generic Assist
     * @return Storage Engine Generic Assist
     */
    fun getStorageAssist() = DevStorageEngine.getAssist()
}