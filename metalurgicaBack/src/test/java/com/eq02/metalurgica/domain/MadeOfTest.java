package com.eq02.metalurgica.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.eq02.metalurgica.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MadeOfTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MadeOf.class);
        MadeOf madeOf1 = new MadeOf();
        madeOf1.setId(1L);
        MadeOf madeOf2 = new MadeOf();
        madeOf2.setId(madeOf1.getId());
        assertThat(madeOf1).isEqualTo(madeOf2);
        madeOf2.setId(2L);
        assertThat(madeOf1).isNotEqualTo(madeOf2);
        madeOf1.setId(null);
        assertThat(madeOf1).isNotEqualTo(madeOf2);
    }
}
