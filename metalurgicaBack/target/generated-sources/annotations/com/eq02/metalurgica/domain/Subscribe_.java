package com.eq02.metalurgica.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Subscribe.class)
public abstract class Subscribe_ {

	public static volatile SingularAttribute<Subscribe, Notification> notification;
	public static volatile SingularAttribute<Subscribe, Long> id;
	public static volatile SingularAttribute<Subscribe, Operator> operator;

	public static final String NOTIFICATION = "notification";
	public static final String ID = "id";
	public static final String OPERATOR = "operator";

}

