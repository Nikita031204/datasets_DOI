package org.cross.cite.data

import org.cross.cite.data.mapper.toDomain
import org.cross.cite.domain.DoiRecord
import org.cross.cite.domain.DoiRepository

class DoiRepositoryImpl : DoiRepository {
    private val records: List<DoiRecord> by lazy { mockDoisResponse.toDomain() }

    override suspend fun getDois(): List<DoiRecord> = records

    override suspend fun getDoi(doi: String): DoiRecord? =
        records.firstOrNull { it.doi.equals(doi, ignoreCase = true) }
}
