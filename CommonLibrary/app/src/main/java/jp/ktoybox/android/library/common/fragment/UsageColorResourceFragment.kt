package jp.ktoybox.android.library.common.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import jp.ktoybox.android.library.common.R


class UsageColorResourceFragment : Fragment() {

    companion object {
        fun newInstance(): UsageColorResourceFragment {
            return UsageColorResourceFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_usage_color_resource, container, false)
    }

}
