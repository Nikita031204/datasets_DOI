package org.cross.cite.domain

data class DoiRecord(
    val doi: String,
    val title: String,
    val creators: List<String>,
    val publisher: String?,
    val publicationYear: Int?,
    val resourceType: ResourceType,
    val resourceTypeLabel: String?,
    val subjects: List<String>,
    val description: String?,
    val license: String?,
    val version: String?,
    val url: String?,
    val related: List<RelatedIdentifier>,
    val citationCount: Int,
    val viewCount: Int,
    val downloadCount: Int,
)

data class RelatedIdentifier(
    val value: String,
    val kind: RelatedIdentifierKind,
    val relationType: String,
)

enum class RelatedIdentifierKind { Doi, Url, Other }

enum class ResourceType {
    Dataset,
    Software,
    Text,
    JournalArticle,
    Collection,
    Image,
    Other,
}
