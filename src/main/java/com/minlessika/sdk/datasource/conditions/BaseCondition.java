package com.minlessika.sdk.datasource.conditions;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.IOException;

public abstract class BaseCondition implements Condition {

	@Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof Condition)) {
            return false;
        }

        Condition item = (Condition) o;

        try {
			return new EqualsBuilder()
			        .append(toScritpt(), item.toScritpt())
			        .append(parameters(), item.parameters())
			        .isEquals();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
    }

    @Override
    public int hashCode() {
        try {
			return new HashCodeBuilder(17, 37)
			        .append(toScritpt())
			        .append(parameters())
			        .toHashCode();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
    }
}
