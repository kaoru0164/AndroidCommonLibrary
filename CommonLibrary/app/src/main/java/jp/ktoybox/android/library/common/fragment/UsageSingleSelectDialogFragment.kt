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
import jp.ktoybox.android.library.common.dialog.SingleSelectDialogFragment

class UsageSingleSelectDialogFragment : Fragment(), SingleSelectDialogFragment.SingleSelectDialogEventListener {

    companion object {
        fun newInstance(): UsageSingleSelectDialogFragment {
            return UsageSingleSelectDialogFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_usage_single_select_dialog, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        view?.findViewById<View>(R.id.button_create)?.setOnClickListener({ _ ->
            val builder = SingleSelectDialogFragment.Builder()
            val title = view?.findViewById<EditText>(R.id.edit_title)?.text.toString()
            if (title.isNotEmpty()) {
                builder.setTitle(title)
            }
            val itemList = view?.findViewById<EditText>(R.id.edit_items)?.text.toString().split("\n")
            if (itemList.isNotEmpty()) {
                val items = Array(itemList.size, { i -> itemList[i] })
                builder.setItems(items)
            }
            val checked = view?.findViewById<CheckBox>(R.id.check_cancelable)?.isChecked
            if (checked != null) {
                builder.setCancelable(checked)
            }
            builder.create().show(childFragmentManager, "Single Select Dialog")
        })
    }

    override fun onItemSelected(tag: String, itemIndex: Int, itemName: String) {
        Toast.makeText(context, "Item selected\n" + itemIndex + " : " + itemName + "\nTag=" + tag, Toast.LENGTH_SHORT).show()
    }

    override fun onCanceled(tag: String) {
        Toast.makeText(context, "Canceled\nTag=" + tag, Toast.LENGTH_SHORT).show()
    }
}
