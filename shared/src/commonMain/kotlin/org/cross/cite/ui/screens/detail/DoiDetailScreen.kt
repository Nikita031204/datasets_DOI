package org.cross.cite.ui.screens.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.cross.cite.detail.DoiDetailIntent
import org.cross.cite.detail.DoiDetailState
import org.cross.cite.resources.Res
import org.cross.cite.resources.detail_loading
import org.cross.cite.resources.detail_not_found_body
import org.cross.cite.resources.detail_not_found_title
import org.cross.cite.resources.field_license
import org.cross.cite.resources.field_publisher
import org.cross.cite.resources.field_version
import org.cross.cite.resources.field_year
import org.cross.cite.resources.metric_citations
import org.cross.cite.resources.metric_downloads
import org.cross.cite.resources.metric_views
import org.cross.cite.resources.section_creators
import org.cross.cite.resources.section_description
import org.cross.cite.resources.section_metrics
import org.cross.cite.resources.section_related
import org.cross.cite.resources.section_subjects
import org.cross.cite.ui.components.ResourceTypeChip
import org.cross.cite.ui.components.Section
import org.cross.cite.ui.model.DoiDetailUi
import org.jetbrains.compose.resources.stringResource

@Composable
fun DoiDetailScreen(
    state: DoiDetailState,
    onIntent: (DoiDetailIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (state) {
        DoiDetailState.Loading -> LoadingContent(modifier)
        is DoiDetailState.NotFound -> NotFoundContent(doi = state.doi, modifier = modifier)
        is DoiDetailState.Content -> RecordContent(
            record = state.record,
            onRelatedClick = { doi -> onIntent(DoiDetailIntent.RelatedClicked(doi)) },
            modifier = modifier,
        )
    }
}

@Composable
private fun LoadingContent(modifier: Modifier) {
    Box(modifier, contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
            CircularProgressIndicator()
            Text(stringResource(Res.string.detail_loading), color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun NotFoundContent(doi: String, modifier: Modifier) {
    Box(modifier.padding(24.dp), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = stringResource(Res.string.detail_not_found_title),
                style = MaterialTheme.typography.titleLarge,
            )
            Text(
                text = stringResource(Res.string.detail_not_found_body, doi),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun RecordContent(
    record: DoiDetailUi,
    onRelatedClick: (String) -> Unit,
    modifier: Modifier,
) {
    Box(modifier.verticalScroll(rememberScrollState()), contentAlignment = Alignment.TopCenter) {
        Column(
            modifier = Modifier.widthIn(max = 840.dp).fillMaxWidth().padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                ResourceTypeChip(type = record.type, label = record.typeLabel)
                Text(text = record.title, style = MaterialTheme.typography.headlineSmall)
                Text(
                    text = record.doi,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary,
                )
            }

            FactsBlock(record)

            Section(stringResource(Res.string.section_creators)) {
                Text(record.creators.joinToString("; "), style = MaterialTheme.typography.bodyMedium)
            }

            record.description?.let { description ->
                Section(stringResource(Res.string.section_description)) {
                    Text(description, style = MaterialTheme.typography.bodyMedium)
                }
            }

            if (record.subjects.isNotEmpty()) {
                Section(stringResource(Res.string.section_subjects)) {
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        record.subjects.forEach { subject ->
                            Surface(
                                shape = MaterialTheme.shapes.small,
                                color = MaterialTheme.colorScheme.secondaryContainer,
                                contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                            ) {
                                Text(
                                    text = subject,
                                    style = MaterialTheme.typography.labelLarge,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                )
                            }
                        }
                    }
                }
            }

            Section(stringResource(Res.string.section_related)) {
                RelatedList(related = record.related, onClick = onRelatedClick)
            }

            Section(stringResource(Res.string.section_metrics)) {
                Row(horizontalArrangement = Arrangement.spacedBy(32.dp)) {
                    Metric(stringResource(Res.string.metric_citations), record.citations)
                    Metric(stringResource(Res.string.metric_views), record.views)
                    Metric(stringResource(Res.string.metric_downloads), record.downloads)
                }
            }
        }
    }
}

@Composable
private fun FactsBlock(record: DoiDetailUi) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
        record.publisher?.let { Fact(stringResource(Res.string.field_publisher), it) }
        record.year?.let { Fact(stringResource(Res.string.field_year), it) }
        record.version?.let { Fact(stringResource(Res.string.field_version), it) }
        record.license?.let { Fact(stringResource(Res.string.field_license), it) }
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
    }
}

@Composable
private fun Fact(label: String, value: String) {
    Row {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.widthIn(min = 120.dp),
        )
        Text(text = value, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
private fun Metric(label: String, value: String) {
    Column {
        Text(text = value, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}
