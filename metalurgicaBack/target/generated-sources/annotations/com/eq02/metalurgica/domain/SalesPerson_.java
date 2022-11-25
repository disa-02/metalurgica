package com.eq02.metalurgica.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(SalesPerson.class)
public abstract class SalesPerson_ {

	public static volatile SingularAttribute<SalesPerson, Employee> emloyee;
	public static volatile SingularAttribute<SalesPerson, Long> id;
	public static volatile SetAttribute<SalesPerson, Sale> sales;

	public static final String EMLOYEE = "emloyee";
	public static final String ID = "id";
	public static final String SALES = "sales";

}

