package com.minlessika.supervisor.xe;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directive;
import org.xembly.Directives;

import com.minlessika.map.CleanMap;
import com.minlessika.supervisor.indicator.BasePeriodicity;
import com.minlessika.supervisor.indicator.ChartCamembert;
import com.minlessika.supervisor.indicator.Indicator;
import com.minlessika.supervisor.indicator.impl.PxIndicatorTemplate;

public final class XeChartCamembertSetting extends XeWrap {

	public XeChartCamembertSetting(final String root, final ChartCamembert item) throws IOException {
		this(transform(root, item));
	}
	
	public XeChartCamembertSetting(final ChartCamembert item) throws IOException {
		this("chart_camembert_setting", item);
	}
	
	public XeChartCamembertSetting(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final ChartCamembert item) throws IOException {
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
	                    .add("sub_label", item.subLabel())
	                    .add("has_periodicity", item.periodicity() != BasePeriodicity.EMPTY)
	                    .add("periodicity_unit", item.periodicity().unit().toString())
	                    .add("periodicity_unit_id", item.periodicity().unit().name())
	                    .add("periodicity_reference", item.periodicity().reference())
	                    .add("periodicity_number", item.periodicity().number())
	                    .add("periodicity_close_interval", item.periodicity().closeInterval())
	                    .add("owner_id", item.ownerId())
	                    .add("description", item.description())
	                    .add("camembert_type_id", item.camembertType().name())
	                    .add("camembert_type", item.camembertType().toString())
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
