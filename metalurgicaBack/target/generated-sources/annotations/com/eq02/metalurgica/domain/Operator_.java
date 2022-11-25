package com.eq02.metalurgica.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Operator.class)
public abstract class Operator_ {

	public static volatile SetAttribute<Operator, Subscribe> subscribes;
	public static volatile SingularAttribute<Operator, Employee> emloyee;
	public static volatile SingularAttribute<Operator, Long> id;

	public static final String SUBSCRIBES = "subscribes";
	public static final String EMLOYEE = "emloyee";
	public static final String ID = "id";

}

