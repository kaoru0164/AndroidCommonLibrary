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
import jp.ktoybox.android.library.common.dialog.MessageDialogFragment

class UsageMessageDialogFragment : Fragment(), MessageDialogFragment.MessageDialogEventListener {

    companion object {
        fun newInstance(): UsageMessageDialogFragment {
            return UsageMessageDialogFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_usage_message_dialog, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        view?.findViewById<View>(R.id.button_create)?.setOnClickListener({ _ ->
            val builder = MessageDialogFragment.Builder()
            val title = view?.findViewById<EditText>(R.id.edit_title)?.text.toString()
            if (title.isNotEmpty()) {
                builder.setTitle(title)
            }
            val message = view?.findViewById<EditText>(R.id.edit_message)?.text.toString()
            if (message.isNotEmpty()) {
                builder.setMessage(message)
            }
            val positive = view?.findViewById<EditText>(R.id.edit_positive_button)?.text.toString()
            if (positive.isNotEmpty()) {
                builder.setPositiveButton(positive)
            }
            val negative = view?.findViewById<EditText>(R.id.edit_negative_button)?.text.toString()
            if (negative.isNotEmpty()) {
                builder.setNegativeButton(negative)
            }
            val neutral = view?.findViewById<EditText>(R.id.edit_neutral_button)?.text.toString()
            if (neutral.isNotEmpty()) {
                builder.setNeutralButton(neutral)
            }
            val checked = view?.findViewById<CheckBox>(R.id.check_cancelable)?.isChecked
            if (checked != null) {
                builder.setCancelable(checked)
            }
            builder.create().show(childFragmentManager, "Message Dialog")
        })
    }

    override fun onPositiveButtonClicked(tag: String) {
        Toast.makeText(context, "Positive button clicked\nTag=" + tag, Toast.LENGTH_SHORT).show()
    }

    override fun onNegativeButtonClicked(tag: String) {
        Toast.makeText(context, "Negative button clicked\nTag=" + tag, Toast.LENGTH_SHORT).show()
    }

    override fun onNeutralButtonClicked(tag: String) {
        Toast.makeText(context, "Neutral button clicked\nTag=" + tag, Toast.LENGTH_SHORT).show()
    }

    override fun onCanceled(tag: String) {
        Toast.makeText(context, "Canceled\nTag=" + tag, Toast.LENGTH_SHORT).show()
    }
}
