package dev.base.able

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * detail: 基类 ViewModel 接口
 * @author Ttt
 */
interface IDevBaseViewModel<VDB : ViewModel> {

    /**
     * 初始化 [ViewModel]
     */
    fun initViewModel()

    // ========================
    // = Application Provider =
    // ========================

    /**
     * 获取 Application ViewModel
     * @param application [Application]
     * @param modelClass [ViewModel]
     */
    fun <T : ViewModel?> getAppViewModel(
        application: Application?,
        modelClass: Class<T>
    ): T?

    /**
     * 获取 Application ViewModelProvider
     * @param application [Application]
     * @return [ViewModelProvider]
     */
    fun getAppViewModelProvider(application: Application?): ViewModelProvider?

    /**
     * 获取 Application AndroidViewModel Factory
     * @param application [Application]
     * @return [ViewModelProvider.Factory]
     */
    fun getAppFactory(application: Application?): ViewModelProvider.Factory?
}