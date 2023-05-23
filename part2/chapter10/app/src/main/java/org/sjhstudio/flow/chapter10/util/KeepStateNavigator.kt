package org.sjhstudio.flow.chapter10.util

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator

@Navigator.Name("KeepStateFragment")
class KeepStateNavigator(
    private val context: Context,
    private val fragmentManager: FragmentManager,
    private val containerId: Int
) : FragmentNavigator(context, fragmentManager, containerId) {

    companion object {
        private const val LOG = "KeepStateNavigator"
    }

    init {
        Log.e(LOG, "init KeepStateNavigator")
    }

    override fun navigate(
        destination: Destination,
        args: Bundle?,
        navOptions: NavOptions?,
        navigatorExtras: Navigator.Extras?
    ): NavDestination? {
        Log.e(LOG, "navigate()")
        val tag = destination.id.toString()
        val transaction = fragmentManager.beginTransaction()
        var initialNavigate = false
        val currentFragment = fragmentManager.primaryNavigationFragment

        // primaryNavigationFragment 가 존재하면, 기존 fragment 는 hide 처리
        // 그렇지 않는 경우, 첫 Navigate 상태
        if (currentFragment != null) {
            Log.e(LOG, "currentFragment 존재")
            transaction.hide(currentFragment)
        } else {
            Log.e(LOG, "currentFragment 없음")
            initialNavigate = true
        }

        // 생성할 fragment
        var fragment = fragmentManager.findFragmentByTag(tag)

        // fragment 를 이전에 생성하지 않는 경우, add 로 추가
        // fragment 를 이전에 생성한 경우, show 처리
        if (fragment == null) {
            val className = destination.className
            fragment = fragmentManager.fragmentFactory.instantiate(context.classLoader, className)
            transaction.add(containerId, fragment, tag)
        } else {
            transaction.show(fragment)
        }

        transaction.setPrimaryNavigationFragment(fragment)  // destination fragment 를 primary 로 설정
        // transaction 관련 fragment 상태 변경 최적화
        transaction.setReorderingAllowed(true)
        transaction.commitNow()

        return if (initialNavigate) destination else null
    }
}