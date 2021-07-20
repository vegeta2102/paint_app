package jp.co.paint.core.extentions

import android.view.View
import androidx.databinding.BindingAdapter

/**
 * Created by vegeta
 */

@BindingAdapter("bind:visibility")
fun setVisibility(view: View, visibility: Visibility?) {
    view.visibility = when (visibility) {
        Visibility.VISIBLE -> View.VISIBLE
        Visibility.INVISIBLE -> View.INVISIBLE
        else -> View.GONE
    }
}