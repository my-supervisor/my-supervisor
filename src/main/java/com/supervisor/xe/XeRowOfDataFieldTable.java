package com.supervisor.xe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directive;
import org.xembly.Directives;

import com.minlessika.map.CleanMap;
import com.supervisor.domain.UserScope;
import com.supervisor.domain.DataFieldOfSheet;
import com.supervisor.domain.DataFieldStyle;
import com.supervisor.domain.ListDataField;
import com.supervisor.domain.ListDataFieldOfSheet;
import com.supervisor.domain.SimpleDataField;
import com.supervisor.domain.SimpleDataFieldOfSheet;
import com.supervisor.domain.TableDataFieldOfSheetRow;
import com.supervisor.domain.bi.BiResult;

public final class XeRowOfDataFieldTable extends XeWrap {

	public XeRowOfDataFieldTable(final String root, final TableDataFieldOfSheetRow field) throws IOException {
		this(transform(root, field));
	}
	
	public XeRowOfDataFieldTable(final TableDataFieldOfSheetRow field) throws IOException {
		this("row", field);
	}
	
	public XeRowOfDataFieldTable(final List<TableDataFieldOfSheetRow> fields) {
		this(new Directives()
                		.add("rows")
    					.append(
    	                    new Joined<>(
	                    		new Mapped<TableDataFieldOfSheetRow, Iterable<Directive>>(
	            			            item -> transform("row", item),
	            			            fields
    	            		    )
                            )
    	                 ).up());
	}
	
	public XeRowOfDataFieldTable(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final TableDataFieldOfSheetRow field) throws IOException {
		
		final List<DataFieldOfSheet> cells = new ArrayList<>();
		
		for (DataFieldOfSheet cell : field.fields().where(DataFieldOfSheet::userScope, UserScope.USER).items()) {
			cells.add(cell);
		}
		
		return  new Directives()
                .add(root)
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", field.id())
	                    .add("table", field.table().name())
	                    .add("table_code", field.table().code())
	                    .add("table_id", field.table().id())           
                )                              
                .append(
                		 new Joined<>(
 	                    		new Mapped<DataFieldOfSheet, Iterable<Directive>>(
            			            item ->transform("cell", item),
            			            cells
     	            		    )
                             )
                ).up();
	}
	
	private static Iterable<Directive> transform(final String root, final DataFieldOfSheet field) throws IOException {
		
		Directives raw =  new Directives()
				                .add(root)
				                .add(
			                    	new CleanMap<String, Object>()
						                .add("id", field.id())
						                .add("code", field.code())
						                .add("origin_id", field.origin().id())
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
			                    );
		
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
