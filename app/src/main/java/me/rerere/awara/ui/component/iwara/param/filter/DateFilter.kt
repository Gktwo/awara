package me.rerere.awara.ui.component.iwara.param.filter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.rerere.awara.ui.component.iwara.param.FilterValue
import java.util.Calendar

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DateFilter(
    values: List<FilterValue>,
    onValueAdd: (FilterValue) -> Unit,
    onValueRemove: (FilterValue) -> Unit
) {
    val currentValue: FilterValue? by remember {
        derivedStateOf { values.firstOrNull { it.key == "date" } }
    }
    val pickedYear: Int? by remember {
        derivedStateOf {
            currentValue?.value?.split("-")?.get(0)?.toIntOrNull()
        }
    }
    val pickedMonth: Int? by remember {
        derivedStateOf {
            currentValue?.value?.split("-")?.getOrNull(1)?.toIntOrNull()
        }
    }
    val currentYear: Int = remember {
        Calendar.getInstance().get(Calendar.YEAR)
    }
    val monthOfPickedYear: Int by remember {
        derivedStateOf {
            if (pickedYear != null) {
                if (pickedYear == currentYear) {
                    Calendar.getInstance().get(Calendar.MONTH) + 1
                } else {
                    12
                }
            } else {
                0
            }
        }
    }

    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        for (year in 2014..currentYear) {
            if (year == pickedYear) {
                FilterChip(
                    selected = true,
                    onClick = {
                        currentValue?.let(onValueRemove)
                    },
                    label = {
                        Text(year.toString())
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                    )
                )

                for (month in 1..monthOfPickedYear) {
                    FilterChip(
                        selected = month == pickedMonth,
                        onClick = {
                            if (month != pickedMonth) {
                                currentValue?.let(onValueRemove)
                                onValueAdd(FilterValue("date", "$year-$month"))
                            } else {
                                currentValue?.let(onValueRemove)
                            }
                        },
                        label = {
                            Text("$year-$month")
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                }
            } else {
                FilterChip(
                    selected = false,
                    onClick = {
                        currentValue?.let(onValueRemove)
                        onValueAdd(FilterValue("date", "$year"))
                    },
                    label = {
                        Text(year.toString())
                    }
                )
            }
        }
    }
}