package com.arrazyfathan.common_utils

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView


/**
 * Created by Ar Razy Fathan Rabbani on 14/05/23.
 */
class ItemOffsetDecoration(itemOffset: Int) : RecyclerView.ItemDecoration() {
    private val mItemOffset: Int

    init {
        mItemOffset = itemOffset
    }

    constructor(
        context: Context,
        @DimenRes itemOffsetId: Int
    ) : this(context.resources.getDimensionPixelSize(itemOffsetId)) {
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect[mItemOffset, mItemOffset, mItemOffset] = mItemOffset
    }
}