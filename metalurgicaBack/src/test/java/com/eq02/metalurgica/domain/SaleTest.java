package com.eq02.metalurgica.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.eq02.metalurgica.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SaleTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sale.class);
        Sale sale1 = new Sale();
        sale1.setSaleCode("id1");
        Sale sale2 = new Sale();
        sale2.setSaleCode(sale1.getSaleCode());
        assertThat(sale1).isEqualTo(sale2);
        sale2.setSaleCode("id2");
        assertThat(sale1).isNotEqualTo(sale2);
        sale1.setSaleCode(null);
        assertThat(sale1).isNotEqualTo(sale2);
    }
}
