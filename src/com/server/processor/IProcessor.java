package com.server.processor;

import com.server.http.Request;
import com.server.http.Response;

public interface IProcessor {
	public boolean process(Request request, Response response);
}
