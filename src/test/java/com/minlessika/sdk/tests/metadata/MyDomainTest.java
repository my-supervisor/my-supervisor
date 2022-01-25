package com.minlessika.sdk.tests.metadata;

import com.google.common.base.CaseFormat;
import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.MethodReferenceUtils;
import com.supervisor.sdk.metadata.Recordable;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public class MyDomainTest {

	@Test
	public void test() throws NoSuchMethodException, SecurityException {
		
		MatcherAssert.assertThat(
			MyDomain.class.getDeclaredMethod("names").getReturnType(), 
			Matchers.equalTo(List.class)
		);
		
		MatcherAssert.assertThat(
			(Class<?>)((ParameterizedType)MyDomain.class.getDeclaredMethod("names").getGenericReturnType()).getActualTypeArguments()[0], 
			Matchers.equalTo(String.class)
		);
		
		MatcherAssert.assertThat(
			MyDomain.class.isAnnotationPresent(Recordable.class), 
			Matchers.is(true)
		);
		
		Recordable rec = MyDomain.class.getAnnotation(Recordable.class);
		
		MatcherAssert.assertThat(
				rec.name(), 
				Matchers.equalTo("test_my_domain")
	    );
		
		MatcherAssert.assertThat(
				rec.label(), 
				Matchers.equalTo("Mon domaine")
	    );
		
		MatcherAssert.assertThat(
				CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, "myDomain"), 
				Matchers.equalTo("my_domain")
		);
		
		Method m = MethodReferenceUtils.getReferencedMethod(MyDomain.class, MyDomain::fullName);
		
		MatcherAssert.assertThat(
				m.getAnnotation(Field.class).name(), 
				Matchers.equalTo("full_name")
		);
		
		MatcherAssert.assertThat(
				m.getDeclaringClass(), 
				Matchers.equalTo(MyDomain.class)
		);
	}

}
