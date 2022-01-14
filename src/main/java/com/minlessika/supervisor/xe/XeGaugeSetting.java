package com.minlessika.supervisor.xe;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directive;
import org.xembly.Directives;

import com.minlessika.map.CleanMap;
import com.minlessika.supervisor.indicator.BasePeriodicity;
import com.minlessika.supervisor.indicator.Gauge;
import com.minlessika.supervisor.indicator.Indicator;
import com.minlessika.supervisor.indicator.impl.PxIndicatorTemplate;

public final class XeGaugeSetting extends XeWrap {

	public XeGaugeSetting(final String root, final Gauge item) throws IOException {
		this(transform(root, item));
	}
	
	public XeGaugeSetting(final Gauge item) throws IOException {
		this("gauge_setting", item);
	}
	
	public XeGaugeSetting(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final Gauge item) throws IOException {
		return new Directives()
                .add(root)
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", item.id())
	                    .add("name", nameOf(item))
	                    .add("tags", tagsOf(item))
	                    .add("code", item.code())
	                    .add("type", item.type().toString())
	                    .add("type_id", item.type().name())
	                    .add("short_name", item.type().shortName())
	                    .add("label", item.label())
	                    .add("has_periodicity", item.periodicity() != BasePeriodicity.EMPTY)
	                    .add("periodicity_unit", item.periodicity().unit().toString())
	                    .add("periodicity_unit_id", item.periodicity().unit().name())
	                    .add("periodicity_reference", item.periodicity().reference())
	                    .add("periodicity_number", item.periodicity().number())
	                    .add("periodicity_close_interval", item.periodicity().closeInterval())
	                    .add("owner_id", item.ownerId())
	                    .add("description", item.description())
	                    .add("unity_symbol", item.unitySymbol())
	                    .add("symbol_position", item.symbolPosition().name())
	                    .add("minor_ticks", item.minorTicks())
	                    .add("major_ticks", item.majorTicks())
	                    .add("gauge_type", item.gaugeType().toString())
	                    .add("gauge_type_id", item.gaugeType().name())
	                    .add("min", item.min())
	                    .add("max", item.max())
	                    .add("is_template", item.isTemplate())     
                )                
                .up();
	}

	private static String nameOf(final Indicator item) throws IOException {
		if(item.isTemplate())
			return new PxIndicatorTemplate(item).name();
		else
			return item.name();
	}
	
	private static String tagsOf(final Indicator item) throws IOException {
		if(item.isTemplate()) {
			return StringUtils.join(new PxIndicatorTemplate(item).tags(), ',');
		}
		else
			return StringUtils.EMPTY;
	}
}
