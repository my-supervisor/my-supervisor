package com.minlessika.supervisor.xe;

import java.io.IOException;
import java.util.List;

import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directive;
import org.xembly.Directives;

import com.minlessika.map.CleanMap;
import com.minlessika.supervisor.domain.DataFieldOfSheet;
import com.minlessika.supervisor.domain.DataFieldOfSheets;
import com.minlessika.supervisor.domain.DataFieldStyle;
import com.minlessika.supervisor.domain.ListDataField;
import com.minlessika.supervisor.domain.ListDataFieldOfSheet;
import com.minlessika.supervisor.domain.SimpleDataField;
import com.minlessika.supervisor.domain.SimpleDataFieldOfSheet;
import com.minlessika.supervisor.domain.TableDataField;
import com.minlessika.supervisor.domain.bi.BiResult;
import com.minlessika.supervisor.domain.impl.PxTableDataField;

public final class XeDataFieldOfSheet extends XeWrap {

	public XeDataFieldOfSheet(final String root, final DataFieldOfSheet field) throws IOException {
		this(transform(root, false, field));
	}
	
	public XeDataFieldOfSheet(final DataFieldOfSheet field) throws IOException {
		this("data_field_of_sheet", field);
	}
	
	public XeDataFieldOfSheet(final DataFieldOfSheets fields) throws IOException {
		this(fields.items());
	}
	
	public XeDataFieldOfSheet(String root, boolean showTableFields, final List<DataFieldOfSheet> fields) {
		this(
			new Directives()
        		.add(root)
				.append(
                    new Joined<>(
                		new Mapped<DataFieldOfSheet, Iterable<Directive>>(
        			            item -> transform("data_field_of_sheet", showTableFields, item),
        			            fields
            		    )
                    )
                 ).up()
    	);
	}
	
	public XeDataFieldOfSheet(final List<DataFieldOfSheet> fields) {
		this("data_field_of_sheets", false, fields);
	}
	
	public XeDataFieldOfSheet(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final boolean showTableFields, final DataFieldOfSheet field) throws IOException {
		
		Directives raw = new Directives()
				                .add(root)
				                .add(
			                    	new CleanMap<String, Object>()
				                    	.add("id", field.id())
						                .add("code", field.code())
						                .add("origin_id", field.origin().id())
						                .add("model_id", field.model().id())
						                .add("name", field.name())
						                .add("type", field.type().toString())
						                .add("type_id", field.type().name())                
						                .add("style", field.style().toString())
						                .add("style_id", field.style().name())
						                .add("description", field.description())
						                .add("order", field.order())
						                .add("is_mandatory", field.isMandatory())
						                .add("column_view", field.columnView())
						                .add("value", field.value())
						                .add("is_table", field.model().isTable())       
			                    );
		
		if(field.model().isTable() && showTableFields) {
			final TableDataField table = new PxTableDataField(field.model());
			raw = raw.add("table_id").set(table.id()).up()
					 .add("table_code").set(table.code()).up()
					 .add("table").set(table.name()).up();
		}
		
		if(field.style() == DataFieldStyle.LIST) {
			final ListDataFieldOfSheet list = (ListDataFieldOfSheet)field;
			final ListDataField listField = (ListDataField)list.origin();
			raw = raw.add("key").set(list.sheetToRefer().reference()).up()
					 .add("is_read_only").set(listField.isReadOnly()).up()
					 .add("must_edit_once").set(listField.mustEditOnce()).up()
					 .add("can_be_updated").set(listField.canBeUpdated()).up();
			
			if(!listField.canBeUpdated() && listField.sources().any()) {
				BiResult result = listField.values();
				raw = raw.append(
							new XeListDataFieldValue(
								listField.fieldToDisplay().code(), 
								result
							).toXembly()
						  );
			}
		}
		
		if(field.style() == DataFieldStyle.SIMPLE) {
			final SimpleDataFieldOfSheet simple = (SimpleDataFieldOfSheet)field;
			final SimpleDataField simpleField = (SimpleDataField)simple.origin();
			raw = raw.add("is_read_only").set(simpleField.isReadOnly()).up()
					 .add("must_edit_once").set(simpleField.mustEditOnce()).up();
		}
		
		return raw.up();
	}

}
