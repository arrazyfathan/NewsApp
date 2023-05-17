package com.arrazyfathan.common_ui.recyclerview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.RecyclerView.Orientation
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * Give equal margin around each [RecyclerView] items, adaptively. Supports known direct subclasses of
 * [RecyclerView.LayoutManager] such as: [LinearLayoutManager], [GridLayoutManager], and [StaggeredGridLayoutManager]
 *
 * Created by Fatah F on Fri, 14 Jan 2022
 */
class AdaptiveSpacingItemDecoration(
    private val size: Int,
    private val edgeEnabled: Boolean = false
) : ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        // Separate layout type
        when (val layoutManager = parent.layoutManager) {
            is GridLayoutManager -> {
                makeGridSpacing(
                    outRect,
                    parent.getChildAdapterPosition(view),
                    state.itemCount,
                    layoutManager.orientation,
                    layoutManager.spanCount,
                    layoutManager.reverseLayout
                )
            }

            is LinearLayoutManager -> {
                val linearOrientation = layoutManager.orientation

                // Flag whether item positioning is reversed (more like flipped) or not. So, if normally item is
                // written from left to right (horizontally), then it will be right to left (whatever item index is)
                // and if item is written from top to bottom (vertically), then it will be from bottom to top.
                val isReversed = layoutManager.reverseLayout xor layoutManager.stackFromEnd

                makeLinearSpacing(
                    outRect,
                    parent.getChildAdapterPosition(view),
                    state.itemCount,
                    linearOrientation,
                    isReversed
                )
            }

            is StaggeredGridLayoutManager -> { // Equals to GridLayoutManager but isn't the same on function level
                makeGridSpacing(
                    outRect,
                    parent.getChildAdapterPosition(view),
                    state.itemCount,
                    layoutManager.orientation,
                    layoutManager.spanCount,
                    layoutManager.reverseLayout
                )
            }
        }
    }

    private fun makeLinearSpacing(
        outRect: Rect,
        position: Int,
        itemCount: Int,
        @RecyclerView.Orientation orientation: Int,
        isReversed: Boolean
    ) {
        // Basic item positioning
        val isLastPosition = position == (itemCount - 1)
        val isFirstPosition = position == 0

        // Saved size
        val sizeBasedOnEdge = if (edgeEnabled) size else NO_SPACING
        val sizeBasedOnFirstPosition = if (isFirstPosition) sizeBasedOnEdge else size
        val sizeBasedOnLastPosition = if (isLastPosition) sizeBasedOnEdge else NO_SPACING

        when (orientation) {
            RecyclerView.HORIZONTAL -> {
                with(outRect) {
                    left = if (isReversed) sizeBasedOnLastPosition else sizeBasedOnFirstPosition
                    top = sizeBasedOnEdge
                    right = if (isReversed) sizeBasedOnFirstPosition else sizeBasedOnLastPosition
                    bottom = sizeBasedOnEdge
                }
            }

            RecyclerView.VERTICAL -> {
                with(outRect) {
                    left = sizeBasedOnEdge
                    top = if (isReversed) sizeBasedOnLastPosition else sizeBasedOnFirstPosition
                    right = sizeBasedOnEdge
                    bottom = if (isReversed) sizeBasedOnFirstPosition else sizeBasedOnLastPosition
                }
            }
        }
    }

    private fun makeGridSpacing(
        outRect: Rect,
        position: Int,
        itemCount: Int,
        @RecyclerView.Orientation orientation: Int,
        spanCount: Int,
        isReversed: Boolean
    ) {
        // Basic item positioning
        val isLastPosition = position == (itemCount - 1)
        val sizeBasedOnEdge = if (edgeEnabled) size else NO_SPACING
        val sizeBasedOnLastPosition = if (isLastPosition) sizeBasedOnEdge else size

        // Opposite of spanCount (find layout depth)
        val subsideCount = if (itemCount % spanCount == 0) {
            itemCount / spanCount
        } else {
            (itemCount / spanCount) + 1
        }

        // Grid position. Imagine all items ordered in x/y axis
        val xAxis =
            if (orientation == RecyclerView.HORIZONTAL) position / spanCount else position % spanCount
        val yAxis =
            if (orientation == RecyclerView.HORIZONTAL) position % spanCount else position / spanCount

        // Conditions in row and column
        val isFirstColumn = xAxis == 0
        val isFirstRow = yAxis == 0
        val isLastColumn =
            if (orientation == RecyclerView.HORIZONTAL) xAxis == subsideCount - 1 else xAxis == spanCount - 1
        val isLastRow =
            if (orientation == RecyclerView.HORIZONTAL) yAxis == spanCount - 1 else yAxis == subsideCount - 1

        // Saved size
        val sizeBasedOnFirstColumn = if (isFirstColumn) sizeBasedOnEdge else NO_SPACING
        val sizeBasedOnLastColumn = if (!isLastColumn) sizeBasedOnLastPosition else sizeBasedOnEdge
        val sizeBasedOnFirstRow = if (isFirstRow) sizeBasedOnEdge else NO_SPACING
        val sizeBasedOnLastRow = if (!isLastRow) size else sizeBasedOnEdge

        when (orientation) {
            RecyclerView.HORIZONTAL -> { // Row fixed. Number of rows is spanCount
                with(outRect) {
                    left = if (isReversed) sizeBasedOnLastColumn else sizeBasedOnFirstColumn
                    top =
                        if (edgeEnabled) size * (spanCount - yAxis) / (spanCount) else size * yAxis / spanCount
                    right = if (isReversed) sizeBasedOnFirstColumn else sizeBasedOnLastColumn
                    bottom = if (edgeEnabled) {
                        size * (yAxis + 1) / spanCount
                    } else {
                        size * (spanCount - (yAxis + 1)) / spanCount
                    }
                }
            }

            RecyclerView.VERTICAL -> { // Column fixed. Number of columns is spanCount
                with(outRect) {
                    left =
                        if (edgeEnabled) size * (spanCount - xAxis) / (spanCount) else size * xAxis / spanCount
                    top = if (isReversed) sizeBasedOnLastRow else sizeBasedOnFirstRow
                    right = if (edgeEnabled) {
                        size * (xAxis + 1) / spanCount
                    } else {
                        size * (spanCount - (xAxis + 1)) / spanCount
                    }
                    bottom = if (isReversed) sizeBasedOnFirstRow else sizeBasedOnLastRow
                }
            }
        }
    }

    companion object {
        private const val NO_SPACING = 0
    }
}