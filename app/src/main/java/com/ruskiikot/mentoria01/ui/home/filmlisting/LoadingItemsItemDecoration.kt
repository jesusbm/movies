package com.ruskiikot.mentoria01.ui.home.filmlisting

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.ruskiikot.mentoria01.R

class LoadingItemsItemDecoration : RecyclerView.ItemDecoration() {

    private val bounds = Rect()

    private var myDrawable: Drawable? = null

    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        canvas.save()

        if (myDrawable == null) {
            myDrawable = ContextCompat.getDrawable(parent.context, R.drawable.dots_horizontal_circle_outline_icon)
        }

        for (child in parent.children) {
            val childPositionInAdapter = parent.getChildAdapterPosition(child)
            if (childPositionInAdapter == state.itemCount - 1) {

                parent.getDecoratedBoundsWithMargins(child, bounds)

                val drawableWidth = myDrawable?.intrinsicWidth ?: 0
                val drawableHeight = myDrawable?.intrinsicHeight ?: 0

                val left = parent.width / 2 - drawableWidth / 2
                val bottom = parent.height / 2 - drawableHeight / 2//bounds.bottom + Math.round(child.translationY)
                val top = bottom - drawableHeight
                val right = left + drawableWidth
                myDrawable?.setBounds(left, top, right, bottom)
                myDrawable?.draw(canvas)
            }
        }
        canvas.restore()
    }

    override fun getItemOffsets(rect: Rect, child: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(rect, child, parent, state)
        val childPositionInAdapter = parent.getChildAdapterPosition(child)
        if (childPositionInAdapter == state.itemCount - 1 || childPositionInAdapter == state.itemCount - 2) {
            //rect.set(0, 0, 0, myDrawable?.intrinsicHeight ?: 0)
            rect.set(0, 0, 0, 0)
        } else {
            rect.set(0, 0, 0, 0)
        }
    }
}

