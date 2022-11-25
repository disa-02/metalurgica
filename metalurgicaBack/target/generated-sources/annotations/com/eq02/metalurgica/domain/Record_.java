package com.eq02.metalurgica.domain;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Record.class)
public abstract class Record_ {

	public static volatile SingularAttribute<Record, Double> amount;
	public static volatile SingularAttribute<Record, LocalDate> dateRange;
	public static volatile SingularAttribute<Record, Long> id;
	public static volatile SetAttribute<Record, Sale> sales;

	public static final String AMOUNT = "amount";
	public static final String DATE_RANGE = "dateRange";
	public static final String ID = "id";
	public static final String SALES = "sales";

}

