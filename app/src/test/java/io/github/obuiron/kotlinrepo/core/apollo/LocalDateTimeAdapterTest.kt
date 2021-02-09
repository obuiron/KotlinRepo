package io.github.obuiron.kotlinrepo.core.apollo

import com.apollographql.apollo.api.CustomTypeValue.GraphQLString
import kotlinx.datetime.toLocalDateTime
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should be instance of`
import org.junit.Test

class LocalDateTimeAdapterTest {

    @Test
    fun `should decode a valid ISO-8601 date`() {
        // Given
        val graphqlString = GraphQLString("2016-02-05T13:42:07Z")

        // When
        val datetime = LocalDateTimeAdapter.decode(graphqlString)

        // Then
        with(datetime) {
            year `should be equal to` 2016
            monthNumber `should be equal to` 2
            dayOfMonth `should be equal to` 5
            hour `should be equal to` 13
            minute `should be equal to` 42
            second `should be equal to` 7
        }
    }

    @Test
    fun `should encore datetime to graphql string as ISO-8601 date`() {
        // Given
        val datetime = "2016-02-05T13:42:07".toLocalDateTime()

        // When
        val graphqlType = LocalDateTimeAdapter.encode(datetime)

        // Then
        graphqlType `should be instance of` GraphQLString::class
        graphqlType.value.toString() `should be equal to` "2016-02-05T13:42:07Z"
    }
}
