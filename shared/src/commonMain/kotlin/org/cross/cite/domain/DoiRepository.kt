package org.cross.cite.domain

interface DoiRepository {
    suspend fun getDois(): List<DoiRecord>
    suspend fun getDoi(doi: String): DoiRecord?
}
