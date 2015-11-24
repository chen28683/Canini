package com.server.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import com.server.processor.ServletProcessor;
import com.server.processor.StaticReourceProcessor;

public class Server {
	public static int default_port = 8080;
	public static String default_ip = "127.0.0.1";
	private ServerSocket serverSocket = null;
	private boolean shutdown = true;
	public static String WEB_ROOT = System.getProperty("user.dir");

	public boolean init(int port, String ip) {
		if (serverSocket != null) {
			return false;
		}
		try {
			serverSocket = new ServerSocket(port, 1, InetAddress.getByName(ip));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		shutdown = false;
		return true;
	}

	public void start() {
		while (!shutdown) {
			Socket socket = null;
			InputStream input = null;
			OutputStream output = null;
			try {
				socket = serverSocket.accept();
				input = socket.getInputStream();
				output = socket.getOutputStream(); // create Request object and
				// parse
				Request request = new Request(input);
				request.parse(); // create Response object
				String uri = request.getUri();
				Response response = new Response(output);
				response.setRequest(request);
				if (uri != null) {
					if (uri.indexOf("/servlet/") != -1) {
						ServletProcessor processor = new ServletProcessor();
						processor.process(request, response);
					} else {
						StaticReourceProcessor processor = new StaticReourceProcessor();
						processor.process(request, response);
					}
				}
				socket.close(); // check if the previous URI is a shutdown
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
	}

	public void shutdown() {
		shutdown = true;
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Server server = new Server();
		server.init(8080, "127.0.0.1");
		server.start();
	}

}
