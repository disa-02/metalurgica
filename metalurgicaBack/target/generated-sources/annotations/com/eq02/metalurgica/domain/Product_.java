package com.eq02.metalurgica.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Product.class)
public abstract class Product_ {

	public static volatile SingularAttribute<Product, Double> buyPrice;
	public static volatile SetAttribute<Product, Roow> roows;
	public static volatile SingularAttribute<Product, String> name;
	public static volatile SingularAttribute<Product, Double> sellPrice;
	public static volatile SingularAttribute<Product, Long> id;
	public static volatile SingularAttribute<Product, Integer> stock;
	public static volatile SetAttribute<Product, MadeOf> madeoves;

	public static final String BUY_PRICE = "buyPrice";
	public static final String ROOWS = "roows";
	public static final String NAME = "name";
	public static final String SELL_PRICE = "sellPrice";
	public static final String ID = "id";
	public static final String STOCK = "stock";
	public static final String MADEOVES = "madeoves";

}

