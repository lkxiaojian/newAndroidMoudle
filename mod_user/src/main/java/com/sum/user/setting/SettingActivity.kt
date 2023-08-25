package com.sum.user.setting

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import com.azhon.appupdate.listener.OnButtonClickListener
import com.azhon.appupdate.manager.DownloadManager
import com.sum.common.constant.USER_ACTIVITY_SETTING
import com.sum.common.dialog.MessageDialog
import com.sum.common.provider.LoginServiceProvider
import com.sum.common.provider.SearchServiceProvider
import com.sum.common.provider.UserServiceProvider
import com.sum.framework.base.BaseDataBindActivity
import com.sum.framework.ext.gone
import com.sum.framework.ext.onClick
import com.sum.framework.ext.visible
import com.sum.framework.toast.TipsToast
import com.sum.framework.manager.AppManager
import com.sum.framework.utils.ViewUtils
import com.sum.framework.utils.dpToPx
import com.sum.framework.utils.getColorFromResource
import com.sum.framework.utils.getStringFromResource
import com.sum.network.manager.CookiesManager
import com.sum.user.R
import com.sum.user.about.AboutUsActivity
import com.sum.user.databinding.ActivitySettingBinding
import com.sum.user.dialog.LogoutTipsDialog
import com.sum.user.info.UserInfoActivity
import com.sum.common.manager.FileManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @author mingyan.su
 * @date   2023/3/23 12:43
 * @desc   设置
 */
@Route(path = USER_ACTIVITY_SETTING)
class SettingActivity : BaseDataBindActivity<ActivitySettingBinding>(),
    OnButtonClickListener {
    var allCacheDir = arrayOfNulls<String>(2)
    private var manager: DownloadManager? = null
    private val url = "http://s.duapps.com/apks/own/ESFileExplorer-cn.apk"
    private val apkName = "appupdate.apk"
    override fun initView(savedInstanceState: Bundle?) {
        ViewUtils.setClipViewCornerRadius(mBinding.tvLogout, dpToPx(8))
        mBinding.tvCurrentVersion.text = String.format(
            getString(
                R.string.setting_current_version
            ), AppManager.getAppVersionName(this)
        )
        if (UserServiceProvider.isLogin()) {
            mBinding.tvLogout.visible()
        } else {
            mBinding.tvLogout.gone()
        }
        val rootDir = FileManager.getAppRootDir()
        val imageDir = FileManager.getImageDirectory(this)
        allCacheDir = arrayOf(rootDir, imageDir.absolutePath)
        lifecycleScope.launch(Dispatchers.IO) {
            updateCacheSize()
        }
        initListener()
    }

    /**
     * 更新缓存大小
     */
    private fun updateCacheSize() {
        val size = FileManager.getTotalCacheSize(this, *allCacheDir)
        mBinding.tvCache.text = size
    }

    private fun initListener() {
        mBinding.clUserInfo.onClick {
            UserInfoActivity.start(this)
        }
        mBinding.clAccountSafe.onClick {
            TipsToast.showWarningTips(com.sum.common.R.string.default_developing)
        }
        mBinding.clCurrentVersion.onClick {
//            TipsToast.showWarningTips(R.string.setting_newest_version)
            manager = DownloadManager.Builder(this).run {
                apkUrl(url)
                apkName(apkName)
                smallIcon(R.mipmap.wechat)
                showNewerToast(true)
                apkVersionCode(2)
                apkVersionName("v4.2.1")
                apkSize("7.7MB")
                apkDescription(getString(R.string.dialog_msg))
                enableLog(true)
                jumpInstallPage(true)
                dialogButtonTextColor(Color.WHITE)
                showNotification(true)
                showBgdToast(false)
                forcedUpgrade(false)

//                onDownloadListener(listenerAdapter)
//            apkMD5("DC501F04BBAA458C9DC33008EFED5E7F")
//            httpManager()
//            dialogImage(R.drawable.ic_dialog)
//            dialogButtonColor(Color.parseColor("#E743DA"))
//            dialogProgressBarColor(Color.parseColor("#E743DA"))
//            notificationChannel()
//            notifyId(1011)
                onButtonClickListener(this@SettingActivity)
                build()
            }
            manager?.download()
        }
        mBinding.clPrivacyPolicy.onClick {
            LoginServiceProvider.readPolicy(this)
        }
        mBinding.clClearCache.onClick {
            showClearCacheDialog()
        }
        mBinding.clAboutUs.onClick {
            AboutUsActivity.start(this)
        }
        mBinding.tvLogout.onClick {
            LogoutTipsDialog.Builder(this, mButtonClickListener = {
                showLoading()
                LoginServiceProvider.logout(context = this, lifecycleOwner = this) {
                    CookiesManager.clearCookies()
                    UserServiceProvider.clearUserInfo()
                    SearchServiceProvider.clearSearchHistoryCache()
                    dismissLoading()
                }
            }).show()
        }
    }

    /**
     * 清理缓存弹框
     */
    private fun showClearCacheDialog() {
        MessageDialog.Builder(this).setTitle(getStringFromResource(com.sum.common.R.string.dialog_tips_title))
                .setMessage(getStringFromResource(R.string.setting_clear_cache_tips))
                .setConfirm(getStringFromResource(com.sum.common.R.string.default_confirm))
                .setConfirmTxtColor(getColorFromResource(com.sum.common.R.color.color_0165b8))
                .setCancel(getString(com.sum.common.R.string.default_cancel))
                .setonCancelListener {
                    it?.dismiss()
                }
                .setonConfirmListener {
                    clearCache()
                    it?.dismiss()
                }.create().show()
    }

    /**
     * 清理缓存
     */
    private fun clearCache() {
        showLoading("正在清理...")
        lifecycleScope.launch {
            allCacheDir.forEach { filesDir ->
                filesDir?.let { FileManager.delAllFile(it) }
            }
            delay(500)
            dismissLoading()
            updateCacheSize()
        }
    }



    override fun onButtonClick(id: Int) {
        Log.e(TAG, "onButtonClick: $id")
    }
}