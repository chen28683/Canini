package com.server.processor;

import com.server.http.Request;
import com.server.http.Response;
import com.server.servlet.Servlet;

public class ServletProcessor implements IProcessor {

	@Override
	public boolean process(Request request, Response response) {
		String uri = request.getUri();
		String servletName = uri.substring(uri.lastIndexOf("/") + 1);
		try {
			Class servletClass = ClassLoader.getSystemClassLoader().loadClass("com.server.servlet" + servletName);
			Servlet servlet = (Servlet) servletClass.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
