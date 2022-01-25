package com.minlessika.sdk.tests.metadata;

import com.supervisor.sdk.datasource.BaseScheme;
import com.supervisor.sdk.metadata.FieldMetadata;
import com.supervisor.sdk.pgsql.PgBaseScheme;
import com.supervisor.sdk.pgsql.metadata.PgFieldOfMethod;
import com.supervisor.sdk.pgsql.metadata.PgForeignKeyField;
import com.supervisor.sdk.pgsql.metadata.PgLongField;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PgBaseSchemeTest {

	@Rule
    public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void fieldOfMethodMethodNotAFieldTest() throws NoSuchMethodException, SecurityException, IOException {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Method must be a field !");
		
		BaseScheme scheme = new PgBaseScheme();
		Method m = MyDomain.class.getMethod("isNotAField", new Class<?>[0]);
		scheme.fieldOf(MyDomain.class, m);
	}
	
	@Test
	public void fieldOfMethodClassNotARecordableTest() throws NoSuchMethodException, SecurityException, IOException {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Declaring class must be a recordable !");
		
		BaseScheme scheme = new PgBaseScheme();
		Method m = NotADomain.class.getMethod("myField", new Class<?>[0]);
		scheme.fieldOf(NotADomain.class, m);
	}
	
	@Test
	public void fieldOfMethodFieldIsMandatoryTest() throws NoSuchMethodException, SecurityException, IOException {
		BaseScheme scheme = new PgBaseScheme();
		Method m = MyDomain.class.getMethod("name", new Class<?>[0]);
		FieldMetadata metadata = scheme.fieldOf(MyDomain.class, m);
		
		MatcherAssert.assertThat(
				metadata.definitionScript(), 
				Matchers.equalTo("name character varying NOT NULL")
		);
	}
	
	@Test
	public void fieldOfMethodFieldIsPrimaryKeyTest() throws NoSuchMethodException, SecurityException, IOException {
		BaseScheme scheme = new PgBaseScheme();
		Method m = MyDomain.class.getMethod("id", new Class<?>[0]);
		FieldMetadata metadata = scheme.fieldOf(MyDomain.class, m);
		
		MatcherAssert.assertThat(
				metadata.definitionScript(), 
				Matchers.equalTo("id bigserial NOT NULL")
		);
		
		MatcherAssert.assertThat(
				metadata.constraintScript(), 
				Matchers.equalTo("CONSTRAINT test_my_domain_pkey PRIMARY KEY (id)")
		);
	}
	
	@Test
	public void fieldOfMethodFieldIsForeignKeyTest() throws NoSuchMethodException, SecurityException, IOException {
		BaseScheme scheme = new PgBaseScheme();
		Method m = MyDomain.class.getMethod("myModule", new Class<?>[0]);
		FieldMetadata metadata = scheme.fieldOf(MyDomain.class, m);
		
		MatcherAssert.assertThat(
				metadata.constraintScript(), 
				Matchers.equalTo("CONSTRAINT test_my_domain_my_module_id_fkey FOREIGN KEY (my_module_id) "
						       + "REFERENCES my_module (id) MATCH SIMPLE "
						       + "ON UPDATE NO ACTION ON DELETE CASCADE")
		);
	}
	
	@Test
	public void hasComodelTest() throws NoSuchMethodException, SecurityException, IOException {

		MatcherAssert.assertThat(
				PgBaseScheme.hasComodel(MyCoDomain.class), 
				Matchers.is(true)
		);
		
		MatcherAssert.assertThat(
				PgBaseScheme.hasComodel(MyDomain.class), 
				Matchers.is(false)
		);
	}
	
	@Test
	public void isPartialTest() throws NoSuchMethodException, SecurityException, IOException {

		MatcherAssert.assertThat(
				PgBaseScheme.isPartial(MyPartialDomain.class), 
				Matchers.is(true)
		);
		
		MatcherAssert.assertThat(
				PgBaseScheme.isPartial(MyCoDomain.class), 
				Matchers.is(false)
		);
	}
	
	@Test
	public void comodelTest() throws NoSuchMethodException, SecurityException, IOException {

		MatcherAssert.assertThat(
				PgBaseScheme.comodel(MyPartialDomain.class), 
				Matchers.equalTo(MyDomain.class) 
		);
		
		MatcherAssert.assertThat(
				PgBaseScheme.comodel(MyDomain.class), 
				Matchers.equalTo(Object.class) 
		);
	}
	
	@Test
	public void hasOne2oneRelationTest() throws NoSuchMethodException, SecurityException, IOException {

		MatcherAssert.assertThat(
				PgBaseScheme.hasOne2oneRelation(MyCoDomain.class), 
				Matchers.is(true) 
		);
		
		MatcherAssert.assertThat(
				PgBaseScheme.hasOne2oneRelation(MyPartialDomain.class), 
				Matchers.is(false) 
		);
	}
	
	@Test
	public void foreignKeyOne2oneRelationTest() throws NoSuchMethodException, SecurityException, IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		FieldMetadata metadata = new PgForeignKeyField(
				new PgLongField(1, PgBaseScheme.nameOfClazz(MyCoDomain.class), "id", "ID"),
				PgFieldOfMethod.keyOf(PgBaseScheme.comodel(MyCoDomain.class)) 
		   );
		
		MatcherAssert.assertThat(
				metadata.constraintScript(), 
				Matchers.equalTo(
						"CONSTRAINT test_my_co_domain_id_fkey FOREIGN KEY (id) "
						+ "REFERENCES test_my_domain (id) MATCH SIMPLE " 
						+ "ON UPDATE NO ACTION ON DELETE CASCADE"
				) 
		);
		
		MatcherAssert.assertThat(
				PgBaseScheme.hasOne2oneRelation(MyPartialDomain.class), 
				Matchers.is(false) 
		);
	}
}
