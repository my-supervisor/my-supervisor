package com.supervisor.xe;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directive;
import org.xembly.Directives;

import com.minlessika.map.CleanMap;
import com.supervisor.indicator.BasePeriodicity;
import com.supervisor.indicator.DynamicTable2Col;
import com.supervisor.indicator.Indicator;
import com.supervisor.indicator.impl.PxIndicatorTemplate;

public final class XeDynamicTable2ColSetting extends XeWrap {

	public XeDynamicTable2ColSetting(final String root, final DynamicTable2Col item) throws IOException {
		this(transform(root, item));
	}
	
	public XeDynamicTable2ColSetting(final DynamicTable2Col item) throws IOException {
		this("dynamic_table_2_col_setting", item);
	}
	
	public XeDynamicTable2ColSetting(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final DynamicTable2Col item) throws IOException {
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
	                    .add("ordering_mode_id", item.orderingMode().name())
	                    .add("ordering_mode", item.orderingMode().toString())
	                    .add("manage_evolution_percent", item.manageEvolutionPercent())
	                    .add("unity_symbol", item.unitySymbol())
	                    .add("symbol_position", item.symbolPosition().name())
	                    .add("nb_max_of_elements_to_show", item.nbMaxOfElementsToShow())
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
