package com.minlessika.sdk.tests.pgsql.metadata;

import com.minlessika.sdk.pgsql.metadata.PgForeignKeyField;
import com.minlessika.sdk.pgsql.metadata.PgIntegerField;
import com.minlessika.sdk.pgsql.metadata.PgPrimaryKeyField;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.io.IOException;

public final class PgForeignKeyFieldTest {
	
	@Test
	public void simpleConstraintScriptTest() throws IOException {
		MatcherAssert.assertThat(
				new PgForeignKeyField(
					new PgIntegerField(1, "membership_user", "person_id", "Identité de l'utilisateur"),
					new PgIntegerField(1, "base_person", "id", "Personne")
				).constraintScript(), 
				Matchers.equalTo("CONSTRAINT membership_user_person_id_fkey FOREIGN KEY (person_id) "
						       + "REFERENCES base_person (id) MATCH SIMPLE "
						       + "ON UPDATE NO ACTION ON DELETE CASCADE")
		);
	}
	
	@Test
	public void composedConstraintScriptTest() throws IOException {
		MatcherAssert.assertThat(
				new PgForeignKeyField(
					new PgPrimaryKeyField(
						new PgIntegerField(1, "membership_user", "id", "ID")
					),					
					new PgIntegerField(1, "base_person", "id", "ID")				
				).constraintScript(), 
				Matchers.equalTo("CONSTRAINT membership_user_pkey PRIMARY KEY (id), "
						       + "CONSTRAINT membership_user_id_fkey FOREIGN KEY (id) "
						       + "REFERENCES base_person (id) MATCH SIMPLE "
						       + "ON UPDATE NO ACTION ON DELETE CASCADE")
		);
	}
}
