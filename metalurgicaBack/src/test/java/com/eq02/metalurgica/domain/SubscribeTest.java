package com.eq02.metalurgica.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.eq02.metalurgica.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SubscribeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Subscribe.class);
        Subscribe subscribe1 = new Subscribe();
        subscribe1.setId(1L);
        Subscribe subscribe2 = new Subscribe();
        subscribe2.setId(subscribe1.getId());
        assertThat(subscribe1).isEqualTo(subscribe2);
        subscribe2.setId(2L);
        assertThat(subscribe1).isNotEqualTo(subscribe2);
        subscribe1.setId(null);
        assertThat(subscribe1).isNotEqualTo(subscribe2);
    }
}
