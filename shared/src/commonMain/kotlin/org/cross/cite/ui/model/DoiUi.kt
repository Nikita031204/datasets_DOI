package org.cross.cite.ui.model

import androidx.compose.runtime.Immutable
import org.cross.cite.domain.DoiRecord
import org.cross.cite.domain.RelatedIdentifier
import org.cross.cite.domain.RelatedIdentifierKind
import org.cross.cite.domain.ResourceType

private const val CARD_CREATORS_LIMIT = 2

@Immutable
data class DoiCardUi(
    val doi: String,
    val title: String,
    val creators: String,
    val moreCreators: Int,
    val publisher: String,
    val year: String,
    val type: ResourceType,
    val typeLabel: String,
)

@Immutable
data class DoiDetailUi(
    val doi: String,
    val title: String,
    val type: ResourceType,
    val typeLabel: String,
    val creators: List<String>,
    val publisher: String?,
    val year: String?,
    val version: String?,
    val license: String?,
    val description: String?,
    val subjects: List<String>,
    val url: String?,
    val related: List<RelatedUi>,
    val citations: String,
    val views: String,
    val downloads: String,
)

@Immutable
data class RelatedUi(
    val value: String,
    val relationType: String,
    val kindLabel: String,
    val isNavigable: Boolean,
)

fun DoiRecord.toCardUi(): DoiCardUi = DoiCardUi(
    doi = doi,
    title = title,
    creators = creators.take(CARD_CREATORS_LIMIT).joinToString("; "),
    moreCreators = (creators.size - CARD_CREATORS_LIMIT).coerceAtLeast(0),
    publisher = publisher.orEmpty(),
    year = publicationYear?.toString().orEmpty(),
    type = resourceType,
    typeLabel = resourceTypeLabel ?: resourceType.name,
)

fun List<DoiRecord>.toCardsUi(): List<DoiCardUi> = map { it.toCardUi() }

fun DoiRecord.toDetailUi(): DoiDetailUi = DoiDetailUi(
    doi = doi,
    title = title,
    type = resourceType,
    typeLabel = resourceTypeLabel ?: resourceType.name,
    creators = creators,
    publisher = publisher,
    year = publicationYear?.toString(),
    version = version,
    license = license,
    description = description,
    subjects = subjects,
    url = url,
    related = related.map { it.toUi() },
    citations = citationCount.toString(),
    views = viewCount.toString(),
    downloads = downloadCount.toString(),
)

private fun RelatedIdentifier.toUi(): RelatedUi = RelatedUi(
    value = value,
    relationType = relationType,
    kindLabel = when (kind) {
        RelatedIdentifierKind.Doi -> "DOI"
        RelatedIdentifierKind.Url -> "URL"
        RelatedIdentifierKind.Other -> "ID"
    },
    isNavigable = kind == RelatedIdentifierKind.Doi,
)
