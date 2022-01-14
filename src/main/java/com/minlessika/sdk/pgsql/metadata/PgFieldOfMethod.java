package com.minlessika.sdk.pgsql.metadata;

import com.minlessika.sdk.metadata.FieldMetadata;
import com.minlessika.sdk.metadata.FieldMetadataWrap;
import com.minlessika.sdk.metadata.MethodReferenceUtils;
import com.minlessika.sdk.metadata.MethodReferenceUtils.MethodRefWithoutArg;
import com.minlessika.sdk.metadata.Recordable;
import com.minlessika.sdk.pgsql.PgBaseScheme;
import com.minlessika.sdk.pgsql.type.PgString;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public final class PgFieldOfMethod extends FieldMetadataWrap {

	public <A1> PgFieldOfMethod(final Class<A1> clazz, final MethodRefWithoutArg<A1> methodRef) throws IOException{
		super(metadata(clazz, MethodReferenceUtils.getReferencedMethod(clazz, methodRef)));
	}
	
	public <A1> PgFieldOfMethod(final Class<A1> clazz, final Method m) throws IOException{
		super(metadata(clazz, m));
	}
	
	private static <A1> FieldMetadata metadata(final Class<A1> clazz, final Method m) throws IOException {
		FieldMetadata metadata = null;
		
		if(!PgBaseScheme.isField(m))
			throw new IllegalArgumentException("Method must be a field !");		
		
		if(!clazz.isAnnotationPresent(Recordable.class))
			throw new IllegalArgumentException("Declaring class must be a recordable !");
		
		try {
			String domainName = PgBaseScheme.nameOfClazz(clazz);
			String fieldName = PgBaseScheme.nameOfMethod(m);
			String fieldLabel = PgBaseScheme.labelOfMethod(m);
			int order = PgBaseScheme.orderOf(m);
						
			if(PgBaseScheme.isKey(m) && PgBaseScheme.hasOne2oneRelation(clazz)) { // possède une relation 1 à 1
				metadata = new PgForeignKeyField(
						new PgLongField(order, PgBaseScheme.nameOfClazz(clazz), fieldName, fieldLabel),
						keyOf(PgBaseScheme.comodel(clazz)) 
				   );
			}else if(m.getReturnType().isEnum() || PgBaseScheme.isList(m) || PgBaseScheme.isMap(m)) { // enum or list of values or map
				metadata = new PgStringField(order, domainName, fieldName, fieldLabel, new PgString(m.getReturnType()));
			} else if(PgBaseScheme.isForeignKey(m)) { // foreign key				
				metadata = new PgForeignKeyField(
								new PgLongField(order, domainName, fieldName, fieldLabel),
								keyOf(m.getReturnType())
						   );
			} else {
				metadata = primitiveFieldOf(clazz, m);				
			}
			
			// is mandatory
			if(PgBaseScheme.isMandatory(m) && !PgBaseScheme.isPartial(clazz)) {
				metadata = new PgMandatoryField(metadata);
			}
			
			// is primary key
			if(PgBaseScheme.isKey(m)) {
				metadata = new PgPrimaryKeyField(metadata);
			}
			
		} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException
				| IllegalArgumentException | InvocationTargetException e) {
			throw new IOException(e);
		}
				
		return metadata;
	}

	public static FieldMetadata keyOf(Class<?> annotatedClass) throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		
		Method[] methods = annotatedClass.getMethods();
		for (Method method : methods) {
			if(PgBaseScheme.isField(method) && PgBaseScheme.isKey(method))
				return primitiveFieldOf(annotatedClass, method);
		}
		
		throw new IllegalArgumentException(String.format("Class %s has not key !", annotatedClass.getSimpleName()));
	}
	
	public static FieldMetadata primitiveFieldOf(final Class<?> clazz, final Method m) throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		
		List<Class<? extends FieldMetadata>> primitiveTypes = 
				Arrays.asList(
						PgStringField.class,
						PgIntegerField.class,
						PgLongField.class,
						PgBooleanField.class,
						PgDateField.class,
						PgDateTimeField.class,
						PgDoubleField.class,
						PgSmallintField.class,
						PgTinyintField.class,
						PgUuidField.class
				);
		
		FieldMetadata metadata = null;
		Class<?> type = m.getReturnType();
		
		for (Class<? extends FieldMetadata> class1 : primitiveTypes) {
			
			String domainName = PgBaseScheme.nameOfClazz(clazz);
			String fieldName = PgBaseScheme.nameOfMethod(m);
			String fieldLabel = PgBaseScheme.labelOfMethod(m);
			int order = PgBaseScheme.orderOf(m);
			
			if((type == Long.class) && PgBaseScheme.isKey(m))
			{				
				if(PgBaseScheme.isAuto(m)) {
					metadata = new PgAutomaticLongField(order, domainName, fieldName, fieldLabel);
				}else {
					metadata = new PgLongField(order, domainName, fieldName, fieldLabel);				
				}
								
				break;
			}
			else {
				Constructor<? extends FieldMetadata> constructor = class1.getConstructor(Integer.class, String.class, String.class, String.class);			
				FieldMetadata fieldProposed = (FieldMetadata)constructor.newInstance(order, domainName, fieldName, fieldLabel);
				if(fieldProposed.belongTo(type))
				{
					metadata = fieldProposed;
					break;
				}
			}			
		}
		
		if(metadata == null)
			throw new IllegalArgumentException(String.format("Type <%s> you specify is not supported !", type.getSimpleName()));
		
		return metadata;
	}
}
