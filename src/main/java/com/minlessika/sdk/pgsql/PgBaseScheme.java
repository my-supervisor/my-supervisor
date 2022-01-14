package com.minlessika.sdk.pgsql;

import com.google.common.base.CaseFormat;
import com.minlessika.sdk.datasource.BaseScheme;
import com.minlessika.sdk.metadata.Field;
import com.minlessika.sdk.metadata.FieldMetadata;
import com.minlessika.sdk.metadata.MethodReferenceUtils;
import com.minlessika.sdk.metadata.MethodReferenceUtils.MethodRefWithoutArg;
import com.minlessika.sdk.metadata.Recordable;
import com.minlessika.sdk.metadata.Relation;
import com.minlessika.sdk.pgsql.metadata.PgFieldOfMethod;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

public final class PgBaseScheme implements BaseScheme {
	
	private static final Random random = new Random();
	
	@Override
	public <T> String scriptOf(Class<T> clazz) throws IOException {
		
		List<FieldMetadata> fields = fieldsOf(clazz);
		String definition = StringUtils.EMPTY;
		for (FieldMetadata field : fields) {
			if(StringUtils.isBlank(definition))
				definition = field.definitionScript();
			else
				definition = String.format("%s, %s", definition, field.definitionScript());
		}
		
		for (FieldMetadata field : fields) {
			String constraintScript = field.constraintScript();
			if (!StringUtils.isBlank(constraintScript))
				definition = String.format("%s, %s", definition, constraintScript);
		}
		
		definition = String.format("CREATE TABLE IF NOT EXISTS %s (%s) \r\n"
							    + "WITH (OIDS=FALSE); \r\n"
							    + "COMMENT ON TABLE %s IS '%s';", 
							    nameOfClazz(clazz), 
							    definition,
							    nameOfClazz(clazz),
							    StringEscapeUtils.escapeSql(labelOfClazz(clazz))
					);
		
		for (FieldMetadata field : fields) {
			String commentScript = field.commentScript();
			if (!StringUtils.isBlank(commentScript))
				definition = String.format("%s %s;", definition, commentScript);
		}
		
		return definition;
	}

	@Override
	public <T> FieldMetadata fieldOf(Class<T> clazz, MethodRefWithoutArg<T> methodRef) throws IOException {
		return new PgFieldOfMethod(clazz, methodRef);
	}

	@Override
	public <T> FieldMetadata fieldOf(Class<T> clazz, Method m) throws IOException {
		return new PgFieldOfMethod(clazz, m);
	}

	public static String userNameOfClazz(Class<?> annotatedClass) {
		
		Recordable rec = annotatedClass.getAnnotation(Recordable.class);
		return rec.name();
	}
	
	public static String nameOfClazz(Class<?> annotatedClass) {
		
		if(isPartial(annotatedClass))
			return nameOfClazz(comodel(annotatedClass));
		else {
			String name = userNameOfClazz(annotatedClass);
			if(name.equals("NONE"))
				name = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, annotatedClass.getSimpleName());
			
			return name;
		}	
	}
	
	private static String labelOfClazz(Class<?> annotatedClass) {
		
		if(isPartial(annotatedClass))
			return labelOfClazz(comodel(annotatedClass));
		else {
			Recordable rec = annotatedClass.getAnnotation(Recordable.class);
			String label = rec.label();
			if(label.equals("NONE"))
				label = nameOfClazz(annotatedClass);
			
			return label;
		}
	}
	
	public static String nameOfMethod(Method m) {
		
		Field rec = m.getAnnotation(Field.class);
		String name = rec.name();
		if(name.equals("NONE")) {
			name = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, m.getName());
			if(isForeignKey(m))
				name = String.format("%s_id", name);
		}
						
		return name;
	}
	
	public static int orderOf(Method m) {
		
		Field rec = m.getAnnotation(Field.class);
		int order = rec.order();
		if(order == -1)
			order = randomNumberInRange(10, 100);
		
		return order;
	}
	
	public static String labelOfMethod(Method m) {
		
		Field rec = m.getAnnotation(Field.class);
		String label = rec.label();
		if(label.equals("NONE"))
			label = nameOfMethod(m);
		
		return label;
	}
	
	public static boolean isKey(Method m) {
		
		Field rec = m.getAnnotation(Field.class);
		return rec.isKey();
	}
	
	public static boolean isAuto(Method m) {
		
		Field rec = m.getAnnotation(Field.class);
		return rec.isAuto();
	}
	
	public static boolean isField(Method m) {
		return m.isAnnotationPresent(Field.class);
	}	
	
	public static boolean isMandatory(Method m) {
		Field rec = m.getAnnotation(Field.class);
		return rec.isMandatory();
	}
	
	private static boolean forceInherit(Method m) {
		Field rec = m.getAnnotation(Field.class);
		return rec.forceInherit();
	}
	
	private static boolean ignore(Method m) {
		Field rec = m.getAnnotation(Field.class);
		return rec.ignore();
	}
	
	private static boolean inheritFields(Class<?> clazz) {
		Recordable clazzR = clazz.getAnnotation(Recordable.class);
		return clazzR.inheritFields();
	}
	
	public static boolean isForeignKey(Method m) {
		Field rec = m.getAnnotation(Field.class);
		return rec.rel() == Relation.MANY2ONE;
	}
	
	public static boolean hasOne2oneRelation(Class<?> clazz) {
		return !userNameOfClazz(clazz).equals("NONE") && hasComodel(clazz);
	}
	
	public static boolean isPartial(Class<?> clazz) {
		return userNameOfClazz(clazz).equals("NONE") && hasComodel(clazz);
	}
	
	public static boolean hasComodel(Class<?> clazz) {
		return comodel(clazz) != Object.class;
	}
	
	public static Class<?> comodel(Class<?> clazz) {
		Recordable rec = clazz.getAnnotation(Recordable.class);
		return rec.comodel();
	}
	
	public static boolean isList(Method m) {
		return m.getReturnType() == List.class;
	}
	
	public static boolean isMap(Method m) {
		return m.getReturnType() == Map.class;
	}
	
	private static int randomNumberInRange(int min, int max) {
		return random.ints(min, (max + 1)).findFirst().getAsInt();
	}
	
	private static List<FieldMetadata> fieldsOfClazz(Class<?> clazz, boolean own) throws IOException{
		List<FieldMetadata> fields = new ArrayList<>();
		Method[] methods = clazz.getMethods();
		List<Method> declaredMethods = Arrays.asList(clazz.getDeclaredMethods());
		for (Method method : methods) {		
			if (isField(method)) {				
				if(ignore(method) || method.isDefault())
					continue;
				
				boolean isClassField = declaredMethods.stream()
						                              .anyMatch(
						                            		  c -> 
						                            		  c.getDeclaringClass().isAssignableFrom(method.getDeclaringClass())  
						                            		  && 
						                            		  c.getReturnType().isAssignableFrom(method.getReturnType())
						                              ); 
				boolean isClassInheritFields = inheritFields(clazz);
				boolean isFieldforceInherit = forceInherit(method);
				boolean additionnal = false;
				if(!isClassField && !comodel(clazz).isAssignableFrom(method.getDeclaringClass()) && (isClassInheritFields || isFieldforceInherit)) {
					for (Class<?> i : clazz.getInterfaces()) {
						if(i.isAssignableFrom(method.getDeclaringClass())) {
							additionnal = true;
							break;
						}
					}
				}
				
				if(isClassField || (isFieldforceInherit && !own) || (!own && isClassInheritFields && !hasComodel(clazz)) || additionnal)
					fields.add(new PgFieldOfMethod(clazz, method));
			}
		}
		
		fields.sort((c1, c2) -> c1.order().compareTo(c2.order()));
		
		return fields;
	}

	@Override
	public <T> String nameOf(Class<T> clazz) throws IOException {
		return nameOfClazz(clazz);
	}

	@Override
	public <T> List<FieldMetadata> fieldsOf(Class<T> clazz) throws IOException {
		return fieldsOfClazz(clazz, false);
	}

	@Override
	public <T> String labelOf(Class<T> clazz) throws IOException {
		return labelOfClazz(clazz);
	}

	@Override
	public <T> String nameOf(Class<T> clazz, MethodRefWithoutArg<T> methodRef) throws IOException {
		Method m = MethodReferenceUtils.getReferencedMethod(clazz, methodRef);
		return nameOfMethod(m);
	}

	@Override
	public <T> String labelOf(Class<T> clazz, MethodRefWithoutArg<T> methodRef) throws IOException {
		Method m = MethodReferenceUtils.getReferencedMethod(clazz, methodRef);
		return labelOfMethod(m);
	}

	@Override
	public <T> List<FieldMetadata> ownFieldsOf(Class<T> clazz) throws IOException {
		return fieldsOfClazz(clazz, true);
	}
}
