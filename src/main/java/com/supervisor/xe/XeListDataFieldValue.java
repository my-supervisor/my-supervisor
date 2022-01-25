package com.supervisor.xe;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directive;
import org.xembly.Directives;

import com.minlessika.map.CleanMap;
import com.supervisor.domain.bi.BiResult;
import com.supervisor.domain.bi.BiRow;

public final class XeListDataFieldValue extends XeWrap {
	
	public XeListDataFieldValue(final String key, final BiResult result) {
		this(
				new Joined<>(
            		new Mapped<BiRow, Iterable<Directive>>(
    			            item -> transform("predefined_value", key, item),
    			            result.rows()
        		    )
                )
		);
	}
	
	private XeListDataFieldValue(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final String key, final BiRow item) throws IOException {
		return new Directives()
                .add(root)
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", (String)item.get("REF").value())
	                    .add("value", valueOf(item.get(key).value()))           
                )                
                .up();
	}
	
	private static Object valueOf(final Object data) {
		if(data instanceof java.sql.Date) {
			final LocalDate date = ((java.sql.Date)data).toLocalDate();
			return date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
		} else {
			return data;
		}
	}
}
