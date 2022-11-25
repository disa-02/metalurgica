package com.eq02.metalurgica.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Roow.class)
public abstract class Roow_ {

	public static volatile SingularAttribute<Roow, Sale> sale;
	public static volatile SingularAttribute<Roow, Product> product;
	public static volatile SingularAttribute<Roow, Long> id;
	public static volatile SingularAttribute<Roow, Double> subTotal;
	public static volatile SingularAttribute<Roow, Integer> amountProduct;

	public static final String SALE = "sale";
	public static final String PRODUCT = "product";
	public static final String ID = "id";
	public static final String SUB_TOTAL = "subTotal";
	public static final String AMOUNT_PRODUCT = "amountProduct";

}

