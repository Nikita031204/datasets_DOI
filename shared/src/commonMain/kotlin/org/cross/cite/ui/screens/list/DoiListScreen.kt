package org.cross.cite.ui.screens.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.cross.cite.list.DoiListIntent
import org.cross.cite.list.DoiListState
import org.cross.cite.resources.Res
import org.cross.cite.resources.list_empty
import org.cross.cite.resources.list_total
import org.cross.cite.resources.search_no_results
import org.cross.cite.resources.search_placeholder
import org.jetbrains.compose.resources.stringResource

@Composable
fun DoiListScreen(
    state: DoiListState,
    onIntent: (DoiListIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val isSearching = state.searchQuery.isNotBlank()
    val isEmpty = state.items.isEmpty()
    
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item(key = "search") {
            OutlinedTextField(
                value = state.searchQuery,
                onValueChange = { onIntent(DoiListIntent.SearchQueryChanged(it)) },
                placeholder = { Text(stringResource(Res.string.search_placeholder)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
            )
        }
        
        if (isEmpty && !isSearching) {
            item(key = "empty") {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(top = 32.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = stringResource(Res.string.list_empty),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        } else if (isEmpty && isSearching) {
            item(key = "no_results") {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(top = 32.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = stringResource(Res.string.search_no_results),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        } else {
            item(key = "total") {
                Text(
                    text = stringResource(Res.string.list_total, state.total),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            items(state.items, key = { it.doi }) { item ->
                DoiCard(
                    item = item,
                    onClick = { onIntent(DoiListIntent.CardClicked(item.doi)) },
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}
