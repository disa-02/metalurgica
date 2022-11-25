package com.eq02.metalurgica.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(RawMaterial.class)
public abstract class RawMaterial_ {

	public static volatile SingularAttribute<RawMaterial, String> name;
	public static volatile SingularAttribute<RawMaterial, Long> id;
	public static volatile SingularAttribute<RawMaterial, Integer> stock;
	public static volatile SetAttribute<RawMaterial, MadeOf> madeoves;

	public static final String NAME = "name";
	public static final String ID = "id";
	public static final String STOCK = "stock";
	public static final String MADEOVES = "madeoves";

}

