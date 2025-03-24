package com.farmer.be.domain;

import static com.farmer.be.domain.SessionContextTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SessionContextTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SessionContext.class);
        SessionContext sessionContext1 = getSessionContextSample1();
        SessionContext sessionContext2 = new SessionContext();
        assertThat(sessionContext1).isNotEqualTo(sessionContext2);

        sessionContext2.setId(sessionContext1.getId());
        assertThat(sessionContext1).isEqualTo(sessionContext2);

        sessionContext2 = getSessionContextSample2();
        assertThat(sessionContext1).isNotEqualTo(sessionContext2);
    }
}
