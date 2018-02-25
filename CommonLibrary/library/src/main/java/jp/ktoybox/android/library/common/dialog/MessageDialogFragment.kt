package jp.ktoybox.android.library.common.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog

/**
 * メッセージ表示用ダイアログ.
 *
 * 使用手順
 * 1.Dialogを表示するActivityやFragmentでMessageDialogEventListenerを実装する.
 * 2.Builderクラスを利用してインスタンスを生成する.
 * 3.showメソッドを使用してDialogを表示する.
 *   Activityから表示する場合はgetSupportFragmentManagerメソッドで取得したFragmentManagerを使用する.
 *   Fragmentから表示する場合はgetChildFragmentManagerメソッドで取得したFragmentManagerを使用する.
 */
class MessageDialogFragment : DialogFragment() {
    /**
     * MessageDialogのイベントを取得するためのリスナー.
     */
    interface MessageDialogEventListener {
        /**
         * PositiveButtonがクリックされたときに呼ばれる.
         *
         * @param tag ダイアログ表示時に指定した文字列.
         */
        fun onPositiveButtonClicked(tag: String)

        /**
         * NegativeButtonがクリックされたときに呼ばれる.
         *
         * @param tag ダイアログ表示時に指定した文字列.
         */
        fun onNegativeButtonClicked(tag: String)

        /**
         * NeutralButtonがクリックされたときに呼ばれる.
         *
         * @param tag ダイアログ表示時に指定した文字列.
         */
        fun onNeutralButtonClicked(tag: String)

        /**
         * ダイアログがキャンセルされたときに呼ばれる.
         *
         * @param tag ダイアログ表示時に指定した文字列.
         */
        fun onCanceled(tag: String)
    }

    /**
     * MessageDialogを生成するためのビルダークラス.
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
         * メッセージを設定する.
         *
         * @param messageString メッセージに表示する文字列.
         * @return 自分のインスタンス
         */
        fun setMessage(messageString: String): Builder {
            arguments.putString(ARGUMENT_KEY_MESSAGE, messageString)
            return this
        }

        /**
         * PositiveButtonを設定する.
         *
         * @param buttonString ボタンに表示する文字列.
         * @return 自分のインスタンス
         */
        fun setPositiveButton(buttonString: String): Builder {
            arguments.putString(ARGUMENT_KEY_POSITIVE_BUTTON, buttonString)
            return this
        }

        /**
         * NegativeButtonを設定する.
         *
         * @param buttonString ボタンに表示する文字列.
         * @return 自分のインスタンス
         */
        fun setNegativeButton(buttonString: String): Builder {
            arguments.putString(ARGUMENT_KEY_NEGATIVE_BUTTON, buttonString)
            return this
        }

        /**
         * NeutralButtonを設定する.
         *
         * @param buttonString ボタンに表示する文字列.
         * @return 自分のインスタンス
         */
        fun setNeutralButton(buttonString: String): Builder {
            arguments.putString(ARGUMENT_KEY_NEUTRAL_BUTTON, buttonString)
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
         * MessageDialogFragmentのインスタンスを生成する.
         *
         * @return MessageDialogFragmentのインスタンス
         */
        fun create(): MessageDialogFragment {
            val fragment = MessageDialogFragment()
            fragment.arguments = arguments
            return fragment
        }
    }

    private companion object {
        /**
         * 引数のキー文字列(タイトル).
         */
        val ARGUMENT_KEY_TITLE = "Title"
        /**
         * 引数のキー文字列(メッセージ).
         */
        val ARGUMENT_KEY_MESSAGE = "Message"
        /**
         * 引数のキー文字列(ポジティブボタン).
         */
        val ARGUMENT_KEY_POSITIVE_BUTTON = "PositiveButton"
        /**
         * 引数のキー文字列(ネガティブボタン).
         */
        val ARGUMENT_KEY_NEGATIVE_BUTTON = "NegativeButton"
        /**
         * 引数のキー文字列(ニュートラルボタン).
         */
        val ARGUMENT_KEY_NEUTRAL_BUTTON = "NeutralButton"
        /**
         * 引数のキー文字列(キャンセル有効).
         */
        val ARGUMENT_KEY_CANCELABLE = "Cancelable"
    }

    /**
     * ActivityやFragmentにイベントを通知するためのリスナー.
     */
    private var listener: MessageDialogEventListener? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        val fragment = parentFragment
        if (fragment is MessageDialogEventListener) {
            listener = fragment
        } else {
            val activity = activity
            if (activity is MessageDialogEventListener) {
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

        if (arguments.containsKey(ARGUMENT_KEY_MESSAGE)) {
            builder.setMessage(arguments.getString(ARGUMENT_KEY_MESSAGE))
        }

        if (arguments.containsKey(ARGUMENT_KEY_POSITIVE_BUTTON)) {
            builder.setPositiveButton(arguments.getString(ARGUMENT_KEY_POSITIVE_BUTTON)
                    , { dialog, which -> listener?.onPositiveButtonClicked(tag) })
        }

        if (arguments.containsKey(ARGUMENT_KEY_NEGATIVE_BUTTON)) {
            builder.setNegativeButton(arguments.getString(ARGUMENT_KEY_NEGATIVE_BUTTON)
                    , { dialog, which -> listener?.onNegativeButtonClicked(tag) })
        }

        if (arguments.containsKey(ARGUMENT_KEY_NEUTRAL_BUTTON)) {
            builder.setNeutralButton(arguments.getString(ARGUMENT_KEY_NEUTRAL_BUTTON)
                    , { dialog, which -> listener?.onNeutralButtonClicked(tag) })
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