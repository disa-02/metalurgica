package com.eq02.metalurgica.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.eq02.metalurgica.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmployeeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Employee.class);
        Employee employee1 = new Employee();
        employee1.setName("id1");
        Employee employee2 = new Employee();
        employee2.setName(employee1.getName());
        assertThat(employee1).isEqualTo(employee2);
        employee2.setName("id2");
        assertThat(employee1).isNotEqualTo(employee2);
        employee1.setName(null);
        assertThat(employee1).isNotEqualTo(employee2);
    }
}
