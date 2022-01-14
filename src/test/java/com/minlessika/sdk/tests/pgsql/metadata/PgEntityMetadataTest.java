package com.minlessika.sdk.tests.pgsql.metadata;

import com.minlessika.sdk.pgsql.metadata.PgEntityMetadata;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.io.IOException;

public final class PgEntityMetadataTest {
	
	@Test
	public void scriptTest() throws IOException {
		final String expected = "CREATE TABLE person ("
								+ "id bigserial NOT NULL, "
								+ "guid uuid NOT NULL, "
								+ "creation_date timestamp without time zone NOT NULL, "
								+ "creator_id bigint NOT NULL, "
								+ "owner_id bigint NOT NULL, "
								+ "last_modification_date timestamp without time zone NOT NULL, "								
								+ "last_modifier_id bigint NOT NULL, "
								+ "tag character varying, "
								+ "CONSTRAINT person_pkey PRIMARY KEY (id)"								
								+ ") "
								+ "WITH (OIDS=FALSE); "
								+ "COMMENT ON COLUMN person.id IS 'ID'; "
								+ "COMMENT ON COLUMN person.guid IS 'GUID'; "
								+ "COMMENT ON COLUMN person.creation_date IS 'Date de création'; "
								+ "COMMENT ON COLUMN person.creator_id IS 'Créateur'; "
								+ "COMMENT ON COLUMN person.owner_id IS 'Propriétaire'; "
								+ "COMMENT ON COLUMN person.last_modification_date IS 'Date de dernière modification'; "								
								+ "COMMENT ON COLUMN person.last_modifier_id IS 'Dernier modificateur'; "
								+ "COMMENT ON COLUMN person.tag IS 'Tag';";
		
		MatcherAssert.assertThat(
				new PgEntityMetadata("person", "Personne").script(), 
				Matchers.equalTo(expected)
		);
	}
}
