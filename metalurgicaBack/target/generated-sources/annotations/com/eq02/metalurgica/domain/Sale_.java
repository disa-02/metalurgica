package com.eq02.metalurgica.domain;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Sale.class)
public abstract class Sale_ {

	public static volatile SingularAttribute<Sale, LocalDate> date;
	public static volatile SingularAttribute<Sale, Double> total;
	public static volatile SetAttribute<Sale, Roow> roows;
	public static volatile SingularAttribute<Sale, Record> record;
	public static volatile SingularAttribute<Sale, String> saleCode;
	public static volatile SingularAttribute<Sale, SalesPerson> salesPerson;

	public static final String DATE = "date";
	public static final String TOTAL = "total";
	public static final String ROOWS = "roows";
	public static final String RECORD = "record";
	public static final String SALE_CODE = "saleCode";
	public static final String SALES_PERSON = "salesPerson";

}

