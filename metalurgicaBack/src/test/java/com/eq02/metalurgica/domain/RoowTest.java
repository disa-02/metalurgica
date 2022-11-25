package com.eq02.metalurgica.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.eq02.metalurgica.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RoowTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Roow.class);
        Roow roow1 = new Roow();
        roow1.setId(1L);
        Roow roow2 = new Roow();
        roow2.setId(roow1.getId());
        assertThat(roow1).isEqualTo(roow2);
        roow2.setId(2L);
        assertThat(roow1).isNotEqualTo(roow2);
        roow1.setId(null);
        assertThat(roow1).isNotEqualTo(roow2);
    }
}
