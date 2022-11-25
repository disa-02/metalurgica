package com.eq02.metalurgica.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.eq02.metalurgica.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SalesPersonTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SalesPerson.class);
        SalesPerson salesPerson1 = new SalesPerson();
        salesPerson1.setId(1L);
        SalesPerson salesPerson2 = new SalesPerson();
        salesPerson2.setId(salesPerson1.getId());
        assertThat(salesPerson1).isEqualTo(salesPerson2);
        salesPerson2.setId(2L);
        assertThat(salesPerson1).isNotEqualTo(salesPerson2);
        salesPerson1.setId(null);
        assertThat(salesPerson1).isNotEqualTo(salesPerson2);
    }
}
