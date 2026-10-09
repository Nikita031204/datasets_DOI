package org.cross.cite.ui.screens.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import org.cross.cite.resources.Res
import org.cross.cite.resources.ic_arrow_back
import org.cross.cite.resources.related_empty
import org.cross.cite.resources.related_open
import org.cross.cite.ui.model.RelatedUi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun RelatedList(
    related: List<RelatedUi>,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (related.isEmpty()) {
        Text(
            text = stringResource(Res.string.related_empty),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = modifier,
        )
        return
    }
    Column(modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        related.forEach { item ->
            RelatedRow(item = item, onClick = { onClick(item.value) })
        }
    }
}

@Composable
private fun RelatedRow(item: RelatedUi, onClick: () -> Unit) {
    OutlinedCard(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(enabled = item.isNavigable, onClick = onClick)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(
                text = item.kindLabel,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.widthIn(min = 36.dp),
            )
            Column(Modifier.weight(1f)) {
                Text(
                    text = item.relationType,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.tertiary,
                )
                Text(
                    text = item.value,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (item.isNavigable) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSurface
                    },
                )
            }
            if (item.isNavigable) {
                Icon(
                    painter = painterResource(Res.drawable.ic_arrow_back),
                    contentDescription = stringResource(Res.string.related_open),
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.rotate(180f),
                )
            }
        }
    }
}
