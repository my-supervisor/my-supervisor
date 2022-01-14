package com.minlessika.sdk.validations;

import com.minlessika.sdk.datasource.RecordSet;
import com.minlessika.sdk.datasource.Recordable;
import com.minlessika.sdk.metadata.MethodReferenceUtils.MethodRefWithoutArg;

import java.io.IOException;

public final class MustBeUnique<T extends Recordable> extends AbstractValidator {

	private final Object oldValue;
	private final Object newValue;
	private final Object refValue;
	private final RecordSet<T> source;
	private final MethodRefWithoutArg<T> methodRef;
	private final MethodRefWithoutArg<T> fieldRef;
	
	public MustBeUnique(final RecordSet<T> source, final MethodRefWithoutArg<T> methodRef, final Object newValue, final String msg) {
		this(source, methodRef, null, newValue, null, null, msg);
	}
	
	public MustBeUnique(final RecordSet<T> source, final MethodRefWithoutArg<T> methodRef, final Object newValue, final MethodRefWithoutArg<T> fieldRef, final Object refValue, final String msg) {
		this(source, methodRef, null, newValue, fieldRef, refValue, msg);
	}
	
	public MustBeUnique(final RecordSet<T> source, final MethodRefWithoutArg<T> methodRef, final Object oldValue, final Object newValue, final String msg) {
		this(source, methodRef, oldValue, newValue, null, null, msg);
	}
	
	public MustBeUnique(final RecordSet<T> source, final MethodRefWithoutArg<T> methodRef, final Object oldValue, final Object newValue, final MethodRefWithoutArg<T> fieldRef, final Object refValue, final String msg) {
		super(msg);
		
		this.source = source;
		this.newValue = newValue;
		this.oldValue = oldValue;
		this.methodRef = methodRef;
		this.fieldRef = fieldRef;
		this.refValue = refValue;
	}

	@Override
	protected boolean isNotCorrect() throws IOException {
		
		RecordSet<T> source = this.source.where(methodRef, newValue);
		
		if(fieldRef != null)
			source = source.where(fieldRef, refValue);
		
		boolean isNotCorrect = source.any();
		
		if(oldValue != null)
			isNotCorrect =  !oldValue.equals(newValue) && isNotCorrect;
		
		return isNotCorrect;
	}

}
