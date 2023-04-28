package me.rerere.awara.ui.component.iwara.param.filter

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import kotlinx.coroutines.launch
import me.rerere.awara.data.entity.Tag
import me.rerere.awara.data.repo.MediaRepo
import me.rerere.awara.data.source.onError
import me.rerere.awara.data.source.runAPICatching
import me.rerere.awara.data.source.stringResource
import me.rerere.awara.ui.LocalMessageProvider
import me.rerere.awara.ui.component.iwara.param.FilterChipCloseIcon
import me.rerere.awara.ui.component.iwara.param.FilterValue
import org.koin.androidx.compose.get

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagFilter(
    values: List<FilterValue>,
    onValueAdd: (FilterValue) -> Unit,
    onValueRemove: (FilterValue) -> Unit
) {
    val currentTags by remember {
        derivedStateOf {
            values.filter { it.key == "tags" }
        }
    }

    // query
    val message = LocalMessageProvider.current
    val mediaRepo = get<MediaRepo>()
    val scope = rememberCoroutineScope()
    var query by remember {
        mutableStateOf("")
    }
    var queryLoading by remember {
        mutableStateOf(false)
    }
    var queryActive by remember {
        mutableStateOf(false)
    }
    val queryResult = remember {
        mutableStateListOf<Tag>()
    }
    val search = {
        scope.launch {
            queryLoading = true
            queryResult.clear()
            runAPICatching {
                val result = mediaRepo.getTagsSuggestions(query)
                queryResult.addAll(result.results)
            }.onError {
                message.error {
                    Text(stringResource(error = it))
                }
            }
            queryLoading = false
        }
    }

    Column {
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            currentTags.fastForEach {
                FilterChip(
                    selected = true,
                    onClick = {
                        onValueRemove(it)
                    },
                    label = {
                        Text(it.value)
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                        selectedLeadingIconColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    leadingIcon = FilterChipCloseIcon
                )
            }
            DockedSearchBar(
                query = query,
                onQueryChange = { query = it },
                onSearch = {
                    search()
                },
                active = queryActive,
                onActiveChange = {
                    queryActive = it
                },
                modifier = Modifier
                    .fillMaxWidth(),
                trailingIcon = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (queryActive) {
                            IconButton(
                                onClick = { queryActive = false }
                            ) {
                                Icon(Icons.Outlined.Close, null)
                            }
                        }

                        if(!queryLoading) {
                            IconButton(
                                onClick = {
                                    search()
                                }
                            ) {
                                Icon(Icons.Outlined.Search, null)
                            }
                        } else {
                            CircularProgressIndicator()
                        }
                    }
                }
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(queryResult) {
                        Text(
                            text = it.id,
                            modifier = Modifier
                                .clickable {
                                    onValueAdd(FilterValue("tags", it.id))
                                    queryActive = false
                                    query = ""
                                }
                                .padding(8.dp)
                                .fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}