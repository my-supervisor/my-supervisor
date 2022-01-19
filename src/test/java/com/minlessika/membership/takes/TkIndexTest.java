/*
 * Copyright (c) 2018-2022 Minlessika
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to read
 * the Software only. Permissions is hereby NOT GRANTED to use, copy, modify,
 * merge, publish, distribute, sublicense, and/or sell copies of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.minlessika.membership.takes;

import org.hamcrest.MatcherAssert;
import org.hamcrest.core.StringContains;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.takes.rq.RqFake;
import org.takes.rs.RsPrint;

/**
 * Test case for {@link TkIndex}
 * @since 0.1
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
