package org.cross.cite.data.dto

data class JsonApiListDto<A>(
    val data: List<JsonApiResourceDto<A>>,
    val meta: JsonApiMetaDto? = null,
)

data class JsonApiSingleDto<A>(
    val data: JsonApiResourceDto<A>,
)

data class JsonApiResourceDto<A>(
    val id: String,
    val type: String,
    val attributes: A,
)

data class JsonApiMetaDto(
    val total: Int,
    val totalPages: Int,
    val page: Int,
)

data class DoiAttributesDto(
    val doi: String,
    val prefix: String? = null,
    val suffix: String? = null,
    val creators: List<CreatorDto> = emptyList(),
    val titles: List<TitleDto> = emptyList(),
    val publisher: String? = null,
    val publicationYear: Int? = null,
    val subjects: List<SubjectDto> = emptyList(),
    val language: String? = null,
    val types: TypesDto? = null,
    val relatedIdentifiers: List<RelatedIdentifierDto> = emptyList(),
    val version: String? = null,
    val rightsList: List<RightsDto> = emptyList(),
    val descriptions: List<DescriptionDto> = emptyList(),
    val url: String? = null,
    val state: String? = null,
    val viewCount: Int? = null,
    val downloadCount: Int? = null,
    val citationCount: Int? = null,
)

data class CreatorDto(
    val name: String,
    val nameType: String? = null,
    val givenName: String? = null,
    val familyName: String? = null,
    val affiliation: List<String> = emptyList(),
)

data class TitleDto(
    val title: String,
    val titleType: String? = null,
    val lang: String? = null,
)

data class SubjectDto(
    val subject: String,
)

data class TypesDto(
    val resourceTypeGeneral: String? = null,
    val resourceType: String? = null,
)

data class RelatedIdentifierDto(
    val relatedIdentifier: String,
    val relatedIdentifierType: String,
    val relationType: String,
    val resourceTypeGeneral: String? = null,
)

data class RightsDto(
    val rights: String,
    val rightsUri: String? = null,
)

data class DescriptionDto(
    val description: String,
    val descriptionType: String? = null,
)
