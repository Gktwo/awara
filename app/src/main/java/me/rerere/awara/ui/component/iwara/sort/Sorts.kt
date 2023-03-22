package me.rerere.awara.ui.component.iwara.sort

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.LocalFireDepartment
import androidx.compose.material.icons.outlined.RemoveRedEye
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import me.rerere.awara.ui.component.iwara.SortOption

val MediaSortOptions = listOf(
    SortOption(
        name = "date",
        label = {
            Text("最新")
        },
        icon = {
            Icon(Icons.Outlined.CalendarMonth, null)
        }
    ),
    SortOption(
        name = "trending",
        label = {
            Text("流行")
        },
        icon = {
            Icon(Icons.Outlined.LocalFireDepartment, null)
        }
    ),
    SortOption(
        name = "popularity",
        label = {
            Text("人气")
        },
        icon = {
            Icon(Icons.Outlined.Star, null)
        }
    ),
    SortOption(
        name = "views",
        label = {
            Text("观看")
        },
        icon = {
            Icon(Icons.Outlined.RemoveRedEye, null)
        }
    ),
    SortOption(
        name = "likes",
        label = {
            Text("喜欢")
        },
        icon = {
            Icon(Icons.Outlined.Favorite, null)
        }
    )
)