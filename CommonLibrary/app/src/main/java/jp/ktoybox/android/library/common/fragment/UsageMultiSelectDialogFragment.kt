package jp.ktoybox.android.library.common.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast

import jp.ktoybox.android.library.common.R
import jp.ktoybox.android.library.common.dialog.MultiSelectDialogFragment

class UsageMultiSelectDialogFragment : Fragment(), MultiSelectDialogFragment.MultiSelectDialogEventListener {

    companion object {
        fun newInstance(): UsageMultiSelectDialogFragment {
            return UsageMultiSelectDialogFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_usage_multi_select_dialog, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        view?.findViewById<View>(R.id.button_create)?.setOnClickListener({ _ ->
            val builder = MultiSelectDialogFragment.Builder()
            val title = view?.findViewById<EditText>(R.id.edit_title)?.text.toString()
            if (title.isNotEmpty()) {
                builder.setTitle(title)
            }
            val itemList = view?.findViewById<EditText>(R.id.edit_items)?.text.toString().split("\n")
            if (itemList.isNotEmpty()) {
                val items = Array<String>(itemList.size, { i -> itemList[i] })
                builder.setItems(items)
            }
            val checked = view?.findViewById<CheckBox>(R.id.check_cancelable)?.isChecked
            if (checked != null) {
                builder.setCancelable(checked)
            }
            builder.create().show(childFragmentManager, "Multi Select Dialog")
        })
    }

    override fun onItemSelected(tag: String, selectedStatuses: BooleanArray, itemNames: Array<String>) {
        val builder = StringBuilder()
        builder.append("Item selected\n")
        selectedStatuses.forEachIndexed({ index, checked -> builder.append("" + index + " : " + itemNames[index] + " : " + checked + "\n") })
        builder.append("Tag=" + tag)
        Toast.makeText(context, builder.toString(), Toast.LENGTH_LONG).show()
    }

    override fun onCanceled(tag: String) {
        Toast.makeText(context, "Canceled\nTag=" + tag, Toast.LENGTH_SHORT).show()
    }
}
