package org.cross.cite.data.mapper

import org.cross.cite.data.dto.DoiAttributesDto
import org.cross.cite.data.dto.JsonApiListDto
import org.cross.cite.data.dto.JsonApiResourceDto
import org.cross.cite.data.dto.RelatedIdentifierDto
import org.cross.cite.domain.DoiRecord
import org.cross.cite.domain.RelatedIdentifier
import org.cross.cite.domain.RelatedIdentifierKind
import org.cross.cite.domain.ResourceType

fun JsonApiListDto<DoiAttributesDto>.toDomain(): List<DoiRecord> = data.map { it.toDomain() }

fun JsonApiResourceDto<DoiAttributesDto>.toDomain(): DoiRecord = with(attributes) {
    DoiRecord(
        doi = doi.ifBlank { id }.lowercase(),
        title = titles.firstOrNull { it.titleType == null }?.title
            ?: titles.firstOrNull()?.title
            ?: doi,
        creators = creators.map { it.name },
        publisher = publisher,
        publicationYear = publicationYear,
        resourceType = resourceTypeOf(types?.resourceTypeGeneral),
        resourceTypeLabel = types?.resourceType?.takeIf { it.isNotBlank() } ?: types?.resourceTypeGeneral,
        subjects = subjects.map { it.subject }.distinct(),
        description = descriptions.firstOrNull { it.descriptionType == "Abstract" }?.description
            ?: descriptions.firstOrNull()?.description,
        license = rightsList.firstOrNull()?.rights,
        version = version,
        url = url,
        related = relatedIdentifiers.map { it.toDomain() },
        citationCount = citationCount ?: 0,
        viewCount = viewCount ?: 0,
        downloadCount = downloadCount ?: 0,
    )
}

private fun RelatedIdentifierDto.toDomain(): RelatedIdentifier {
    val kind = when (relatedIdentifierType.uppercase()) {
        "DOI" -> RelatedIdentifierKind.Doi
        "URL" -> RelatedIdentifierKind.Url
        else -> RelatedIdentifierKind.Other
    }
    return RelatedIdentifier(
        value = if (kind == RelatedIdentifierKind.Doi) normalizeDoi(relatedIdentifier) else relatedIdentifier,
        kind = kind,
        relationType = relationType,
    )
}

private fun normalizeDoi(raw: String): String =
    raw.trim()
        .removePrefix("https://doi.org/")
        .removePrefix("http://doi.org/")
        .removePrefix("doi:")
        .lowercase()

private fun resourceTypeOf(general: String?): ResourceType = when (general) {
    "Dataset" -> ResourceType.Dataset
    "Software" -> ResourceType.Software
    "Text" -> ResourceType.Text
    "JournalArticle" -> ResourceType.JournalArticle
    "Collection" -> ResourceType.Collection
    "Image" -> ResourceType.Image
    else -> ResourceType.Other
}
