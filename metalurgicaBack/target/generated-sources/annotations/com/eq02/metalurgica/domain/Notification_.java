package com.eq02.metalurgica.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Notification.class)
public abstract class Notification_ {

	public static volatile SingularAttribute<Notification, String> descripcion;
	public static volatile SingularAttribute<Notification, Integer> tipo;
	public static volatile SetAttribute<Notification, Subscribe> subscribes;
	public static volatile SingularAttribute<Notification, Long> id;

	public static final String DESCRIPCION = "descripcion";
	public static final String TIPO = "tipo";
	public static final String SUBSCRIBES = "subscribes";
	public static final String ID = "id";

}

