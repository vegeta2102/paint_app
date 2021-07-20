package jp.co.paint.core.extentions

import android.content.res.Resources

/**
 * Created by vegeta
 */

fun Float.toDp(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()
fun Float.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()