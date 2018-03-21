package jp.ktoybox.android.library.common.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog

/**
 * 複数選択ダイアログ.
 *
 * 使用手順
 * 1.Dialogを表示するActivityやFragmentでSingleSelectDialogEventListenerを実装する.
 * 2.Builderクラスを利用してインスタンスを生成する.
 * 3.showメソッドを使用してDialogを表示する.
 *   Activityから表示する場合はgetSupportFragmentManagerメソッドで取得したFragmentManagerを使用する.
 *   Fragmentから表示する場合はgetChildFragmentManagerメソッドで取得したFragmentManagerを使用する.
 */
class MultiSelectDialogFragment : DialogFragment() {
    interface MultiSelectDialogEventListener {
        /**
         * アイテムが選択されたときに呼ばれる.
         *
         * @param tag ダイアログ表示時に指定した文字列.
         * @param selectedStatuses アイテムの選択状態の配列
         * @param itemNames アイテム名の配列
         */
        fun onItemSelected(tag: String, selectedStatuses: BooleanArray, itemNames: Array<String>)

        /**
         * ダイアログがキャンセルされたときに呼ばれる.
         *
         * @param tag ダイアログ表示時に指定した文字列.
         */
        fun onCanceled(tag: String)
    }

    /**
     * MultiSelectDialogを生成するためのビルダークラス.
     */
    class Builder {
        /**
         * ダイアログに設定する引数.
         */
        private var arguments = Bundle()

        /**
         * タイトルを設定する.
         *
         * @param titleString タイトルに表示する文字列.
         * @return 自分のインスタンス
         */
        fun setTitle(titleString: String): Builder {
            arguments.putString(ARGUMENT_KEY_TITLE, titleString)
            return this
        }

        /**
         * アイテム名の配列を設定する.
         *
         * @param itemNames アイテム名の配列.
         * @return 自分のインスタンス
         */
        fun setItems(itemNames: Array<String>): Builder {
            arguments.putStringArray(ARGUMENT_KEY_ITEMS, itemNames)
            return this
        }

        /**
         * ダイアログ外部のタップでキャンセル可能か設定する.
         *
         * @param isCancelable true:キャンセル可能 false:キャンセル不可能
         * @return 自分のインスタンス
         */
        fun setCancelable(isCancelable: Boolean): Builder {
            arguments.putBoolean(ARGUMENT_KEY_CANCELABLE, isCancelable)
            return this
        }

        /**
         * MultiSelectDialogFragmentのインスタンスを生成する.
         *
         * @return MultiSelectDialogFragmentのインスタンス
         */
        fun create(): MultiSelectDialogFragment {
            val fragment = MultiSelectDialogFragment()
            fragment.arguments = arguments
            return fragment
        }
    }

    private companion object {
        /**
         * 引数のキー文字列(タイトル).
         */
        const val ARGUMENT_KEY_TITLE = "Title"
        /**
         * 引数のキー文字列(アイテム).
         */
        const val ARGUMENT_KEY_ITEMS = "Items"
        /**
         * 引数のキー文字列(キャンセル有効).
         */
        const val ARGUMENT_KEY_CANCELABLE = "Cancelable"
    }

    /**
     * ActivityやFragmentにイベントを通知するためのリスナー.
     */
    private var listener: MultiSelectDialogEventListener? = null
    /**
     * アイテムの選択状態を表す配列.
     */
    private var selectedStatusArray: BooleanArray = BooleanArray(0)

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        val fragment = parentFragment
        if (fragment is MultiSelectDialogEventListener) {
            listener = fragment
        } else {
            val activity = activity
            if (activity is MultiSelectDialogEventListener) {
                listener = activity
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context)

        val arguments = arguments

        if (arguments.containsKey(ARGUMENT_KEY_TITLE)) {
            builder.setTitle(arguments.getString(ARGUMENT_KEY_TITLE))
        }

        if (arguments.containsKey(ARGUMENT_KEY_ITEMS)) {
            val itemArray = arguments.getStringArray(ARGUMENT_KEY_ITEMS)
            selectedStatusArray = BooleanArray(itemArray.size, { i -> false })

            builder.setMultiChoiceItems(itemArray
                    , selectedStatusArray
                    , { dialog, which, selected -> selectedStatusArray[which] = selected })
            builder.setPositiveButton("OK"
                    , { dialog, which -> listener?.onItemSelected(tag, selectedStatusArray, itemArray) })
            builder.setNegativeButton("Cancel", null)
        }

        if (arguments.containsKey(ARGUMENT_KEY_CANCELABLE)) {
            isCancelable = arguments.getBoolean(ARGUMENT_KEY_CANCELABLE)
        }

        return builder.create()
    }

    override fun onCancel(dialog: DialogInterface?) {
        super.onCancel(dialog)
        listener?.onCanceled(tag)
    }
}
