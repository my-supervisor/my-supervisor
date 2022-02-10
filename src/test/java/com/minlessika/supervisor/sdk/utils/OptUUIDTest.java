package com.minlessika.supervisor.sdk.utils;

import com.supervisor.sdk.utils.OptUUID;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

final class OptUUIDTest {

    @Test
    void checksPresence() {
        MatcherAssert.assertThat(
            new OptUUID(
                UUID.randomUUID()
            ).isPresent(),
            Matchers.is(true)
        );
    }

    @Test
    void checksValue() {
        final UUID expected = UUID.randomUUID();
        MatcherAssert.assertThat(
            new OptUUID(
                expected
            ).toString(),
            Matchers.is(expected.toString())
        );
    }

    @ParameterizedTest
    @ValueSource(strings = { "", "0"})
    void tryToGetAbsentValue(final String string) {
        final IllegalArgumentException thrown = Assertions.assertThrows(
            IllegalArgumentException.class, () -> new OptUUID(string).get()
        );
        MatcherAssert.assertThat(
            thrown.getMessage(),
            Matchers.is("UUID is not present !")
        );
    }

    @ParameterizedTest
    @ValueSource(strings = { "", "0"})
    void checksAbsence(final String string) {
        MatcherAssert.assertThat(
            new OptUUID(string).isPresent(),
            Matchers.is(false)
        );
    }
}
