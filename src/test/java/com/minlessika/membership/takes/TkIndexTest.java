package com.minlessika.membership.takes;

import org.hamcrest.MatcherAssert;
import org.hamcrest.core.StringContains;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.takes.rq.RqFake;
import org.takes.rs.RsPrint;

/**
 * Test case for {@link TkIndex}
 */
final class TkIndexTest {

    /**
     * @todo #9:30min Fix TkIndex and enable this test.
     *  This test fails because we aren't able to locate xsl file
     *  {@code /com/membership/xsl/index/page_en_US.xsl}. To fix it,
     *  we wrote firstly this test to reproduce the bug. Now we should
     *  fix it and enable this test.
    **/
    @Test
    @Disabled
    void returnsHttpResponse() throws Exception {
        MatcherAssert.assertThat(
            new RsPrint(
                new TkIndex(new FakeBase()).act(new RqFake("GET", "/"))
            ).printBody(),
            new StringContains("Home")
        );
    }
}
