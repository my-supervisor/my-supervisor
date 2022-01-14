package com.minlessika.sdk.metadata;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public final class MethodReferenceUtils {
	
	@FunctionalInterface
    public static interface MethodRefWith1Arg<T, A1> {
        void call(T t, A1 a1);
    }
	
	@FunctionalInterface
    public static interface MethodRefWithoutArg<T> {
        void call(T t) throws IOException;
    }

    public static <T, A1> Method getReferencedMethod(Class<T> clazz, MethodRefWith1Arg<T, A1> methodRef) {
        return findReferencedMethod(clazz, t -> methodRef.call(t, null));
    }
    
    public static <T> Method getReferencedMethod(Class<T> clazz, MethodRefWithoutArg<T> methodRef) {
        return findReferencedMethod(clazz, t -> {
			try {
				methodRef.call(t);
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		});
    }

    @SuppressWarnings("unchecked")
    private static <T> Method findReferencedMethod(Class<T> clazz, Consumer<T> invoker) {
        AtomicReference<Method> ref = new AtomicReference<>();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                ref.set(method);
                return null;
            }
        });
        try {
            invoker.accept((T) enhancer.create());
        } catch (ClassCastException e) {
            throw new IllegalArgumentException(String.format("Invalid method reference on class [%s]", clazz));
        }

        Method method = ref.get();
        if (method == null) {
            throw new IllegalArgumentException(String.format("Invalid method reference on class [%s]", clazz));
        }

        return method;
    }
}
