package io.github.obuiron.kotlinrepo.core.apollo

import com.apollographql.apollo.api.CustomTypeAdapter
import com.apollographql.apollo.api.CustomTypeValue
import kotlinx.datetime.*

object LocalDateTimeAdapter : CustomTypeAdapter<LocalDateTime> {
    override fun decode(value: CustomTypeValue<*>): LocalDateTime {
        return Instant.parse(value.value.toString()).toLocalDateTime(TimeZone.UTC)
    }

    override fun encode(value: LocalDateTime): CustomTypeValue<*> {
        return CustomTypeValue.GraphQLString(value.toInstant(TimeZone.UTC).toString())
    }
}
