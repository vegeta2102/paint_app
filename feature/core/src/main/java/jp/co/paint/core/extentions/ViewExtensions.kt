package jp.co.paint.core.extentions

import android.view.View
import android.view.ViewGroup

/**
 * Created by vegeta
 */

fun View.margin(
    left: Float? = null,
    top: Float? = null,
    right: Float? = null,
    bottom: Float? = null
) {
    layoutParams<ViewGroup.MarginLayoutParams> {
        left?.run { leftMargin = this.toInt() }
        top?.run { topMargin = this.toInt() }
        right?.run { rightMargin = this.toInt() }
        bottom?.run { bottomMargin = this.toInt() }
    }
}

inline fun <reified T : ViewGroup.LayoutParams> View.layoutParams(block: T.() -> Unit) {
    if (layoutParams is T) block(layoutParams as T)
}