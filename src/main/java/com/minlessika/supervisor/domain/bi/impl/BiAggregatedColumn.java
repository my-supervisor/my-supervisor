package com.minlessika.supervisor.domain.bi.impl;

import com.minlessika.supervisor.domain.DataFieldType;
import com.minlessika.supervisor.domain.bi.AggregateFunc;
import com.minlessika.supervisor.domain.bi.BiColumn;

public final class BiAggregatedColumn extends BiColumnWrap implements BiColumn {

	public BiAggregatedColumn(final String name, final AggregateFunc aggregate, final BiColumn origin) {
		super(
			new BiSimpleColumn(
					String.format("\"%s\"", name), 
					PgDataWarehouse.aggregateColumn(
							origin.name(), 
							DataFieldType.NONE, 
							aggregate
					), 
					aggregate
			)
		);
	}
}
