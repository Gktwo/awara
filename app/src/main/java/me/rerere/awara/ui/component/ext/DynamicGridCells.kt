package me.rerere.awara.ui.component.ext

import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp

@Stable
class DynamicStaggeredGridCells(
    val minSize: Dp,
    private val min: Int = 1,
    private val max: Int = Int.MAX_VALUE,
) : StaggeredGridCells {
    init {
        require(minSize.value > 0)
        require(min > 0)
        require(max > 0)
        require(min <= max)
    }

    override fun Density.calculateCrossAxisCellSizes(
        availableSize: Int,
        spacing: Int
    ): List<Int> {
        val count = maxOf((availableSize + spacing) / (minSize.roundToPx() + spacing), 1)
        val countClamped = count.coerceIn(min, max)
        return calculateCellsCrossAxisSizeImpl(availableSize, countClamped, spacing)
    }

    override fun hashCode(): Int {
        return minSize.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return other is DynamicStaggeredGridCells && minSize == other.minSize
    }
}

private fun calculateCellsCrossAxisSizeImpl(
    gridSize: Int,
    slotCount: Int,
    spacing: Int
): List<Int> {
    val gridSizeWithoutSpacing = gridSize - spacing * (slotCount - 1)
    val slotSize = gridSizeWithoutSpacing / slotCount
    val remainingPixels = gridSizeWithoutSpacing % slotCount
    return List(slotCount) {
        slotSize + if (it < remainingPixels) 1 else 0
    }
}