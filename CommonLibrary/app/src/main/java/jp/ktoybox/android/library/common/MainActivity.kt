package jp.ktoybox.android.library.common

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import jp.ktoybox.android.library.common.fragment.*

class MainActivity : AppCompatActivity() {

    private var viewPager: ViewPager? = null
    private var tabLayout: TabLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager = findViewById(R.id.main_view_pager)
        viewPager?.adapter = UsageAdapter(supportFragmentManager)

        tabLayout = findViewById(R.id.main_tab)
        tabLayout?.setupWithViewPager(viewPager)
    }

    private companion object {
        const val PAGE_TITLE_MESSAGE_DIALOG = "Message Dialog"
        const val PAGE_TITLE_SINGLE_SELECT_DIALOG = "Single Select Dialog"
        const val PAGE_TITLE_MULTI_SELECT_DIALOG = "Multi Select Dialog"
        const val PAGE_TITLE_COLOR_RESOURCE = "Color Resource"
        const val PAGE_TITLE_DIMENS_RESOURCE = "Dimens Resource"
    }

    private class UsageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> UsageMessageDialogFragment.newInstance()
                1 -> UsageSingleSelectDialogFragment.newInstance()
                2 -> UsageMultiSelectDialogFragment.newInstance()
                3 -> UsageColorResourceFragment.newInstance()
                4 -> UsageDimensResourceFragment.newInstance()
                else -> throw IllegalStateException()
            }
        }

        override fun getCount(): Int {
            return 5
        }

        override fun getPageTitle(position: Int): CharSequence {

            return when (position) {
                0 -> PAGE_TITLE_MESSAGE_DIALOG
                1 -> PAGE_TITLE_SINGLE_SELECT_DIALOG
                2 -> PAGE_TITLE_MULTI_SELECT_DIALOG
                3 -> PAGE_TITLE_COLOR_RESOURCE
                4 -> PAGE_TITLE_DIMENS_RESOURCE
                else -> ""
            }
        }
    }
}
