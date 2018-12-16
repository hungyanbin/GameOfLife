package com.yanbin.gameoflife

import android.content.res.Resources


fun Int.dpToPx(): Int {
    val density = Resources.getSystem().displayMetrics.density
    return Math.round(this * density)
}