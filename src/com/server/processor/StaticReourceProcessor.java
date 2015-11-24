package com.server.processor;

import java.io.IOException;

import com.server.http.Request;
import com.server.http.Response;

public class StaticReourceProcessor implements IProcessor {

	@Override
	public boolean process(Request request, Response response) {
		try {
			response.sendStaticResource();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

}
