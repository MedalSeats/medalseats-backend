package com.medalseats.adapter.r2dbc.common

abstract class DefaultSqlQueries {

    fun limit(limit: Int?) =
        limit?.let {
            """
           LIMIT :limit
        """
        }

    fun offset(offset: Int?) =
        offset?.let {
            """
           OFFSET :offset
        """
        }

    fun sortingBy(field: String, asc: Boolean = true) =
        """
        ORDER BY $field ${if (asc) "ASC" else "DESC"}
        """
}
