package com.supervisor.sharing;

import java.io.IOException;

public interface DataSharing<T extends Sharable> {
	boolean isEmpty() throws IOException;
	boolean any() throws IOException;
	T counterpart() throws IOException;
	T concrete() throws IOException;
	T template() throws IOException;
	void linkTo(T counterpart) throws IOException;
}
