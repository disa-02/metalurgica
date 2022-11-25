package com.eq02.metalurgica.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(MadeOf.class)
public abstract class MadeOf_ {

	public static volatile SingularAttribute<MadeOf, Double> amountMaterial;
	public static volatile SingularAttribute<MadeOf, Product> product;
	public static volatile SingularAttribute<MadeOf, RawMaterial> rawMaterial;
	public static volatile SingularAttribute<MadeOf, Long> id;

	public static final String AMOUNT_MATERIAL = "amountMaterial";
	public static final String PRODUCT = "product";
	public static final String RAW_MATERIAL = "rawMaterial";
	public static final String ID = "id";

}

