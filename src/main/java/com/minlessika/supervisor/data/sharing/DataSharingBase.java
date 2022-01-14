package com.minlessika.supervisor.data.sharing;

import java.io.IOException;

import com.minlessika.sdk.datasource.Record;
import com.minlessika.supervisor.domain.Activity;

public abstract class DataSharingBase<T extends Sharable, A extends DataShared<T>> implements DataSharing<T> {

	private final Class<A> dataSharedClazz;
	private final Class<T> dataClazz;
	protected final WriterAction action;
	protected final T source;
	protected final Activity targetActivity;
	
	public DataSharingBase(final Class<T> dataClazz, final Class<A> dataSharedClazz, final T concrete) {
		this(dataClazz, dataSharedClazz, concrete, Activity.EMPTY, WriterAction.TEMPLATING);
	}
	
	public DataSharingBase(final Class<T> dataClazz, final Class<A> dataSharedClazz, final T template, final Activity concreteActivity) {
		this(dataClazz, dataSharedClazz, template, concreteActivity, WriterAction.GENERATING);
	}

	private DataSharingBase(final Class<T> dataClazz, final Class<A> dataSharedClazz, final T source, final Activity targetActivity, final WriterAction action) {
		
		this.dataClazz = dataClazz;
		this.dataSharedClazz = dataSharedClazz;
		this.source = source;
		this.targetActivity = targetActivity;
		this.action = action;
		
		if(!(action == WriterAction.GENERATING || action == WriterAction.TEMPLATING)) {
			throw new IllegalArgumentException(String.format("DataSharing : action %s is not supported !", action.toString()));
		}
	}
	
	@Override
	public boolean isEmpty() throws IOException {
		return !any();
	}
	
	@Override
	public boolean any() throws IOException {
		if(action == WriterAction.TEMPLATING) {
			return source.listOf(dataSharedClazz)
				         .contains(source.id());
		} else {
			return !source.listOf(dataSharedClazz)
					      .where(A::template, source.id())
					      .where(A::activity, targetActivity.id())
					      .isEmpty();
		}
	}
	
	@Override
	public T counterpart() throws IOException {
		if(action == WriterAction.TEMPLATING) {
			return template();
		} else {
			return concrete();
		}
	}
	
	protected Record<T> concreteRecord() throws IOException {
		
		final Long concreteId;
		if(action == WriterAction.TEMPLATING) {			
			concreteId = source.id();
		} else {
			concreteId = source.listOf(dataSharedClazz)
							   .where(A::template, source.id())
							   .where(A::activity, targetActivity.id())
							   .first()
							   .valueOf(A::id);						
		}
		
		return source.listOf(dataClazz).get(concreteId);
	}
	
	protected Record<T> templateRecord() throws IOException {
		
		final Long templateId;
		if(action == WriterAction.TEMPLATING) {
			templateId = source.listOf(dataSharedClazz)
			          		   .get(source.id())
			                   .valueOf(A::template);			
		} else {
			templateId = source.id();
		}
		
		return source.listOf(dataClazz).get(templateId);
	}
	
	@Override
	public void linkTo(T counterpart) throws IOException {
		
		if(any())
			return;
		
		if(action == WriterAction.TEMPLATING) {
			source.listOf(dataSharedClazz)
			      .entryOf(A::id, source.id())
			      .entryOf(A::activity, source.activity().id())
			      .entryOf(A::template, counterpart.id())
			      .add();
		} else {
			source.listOf(dataSharedClazz)
			      .entryOf(A::id, counterpart.id())
			      .entryOf(A::activity, targetActivity.id())
			      .entryOf(A::template, source.id())
			      .add();
		}	
	}
}
