package io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class BioSocket {
	
	private static final int PORT = 8888;

	public static void main(String[] args) throws IOException {
		Socket socket = new Socket();
		socket.connect(new InetSocketAddress(PORT));
		OutputStream os = socket.getOutputStream();
		InputStream is = socket.getInputStream();
		os.write("hello,server".getBytes());
		byte[] buffer = new byte[128];
		is.read(buffer);
		System.out.print(new String(buffer).trim());
		socket.close();
	}
}
