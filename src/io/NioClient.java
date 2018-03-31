package io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NioClient {
	
	public static int SERVER_PORT =8888;
	
	public static void sendReq() throws IOException{
		Selector selector = Selector.open();
		SocketChannel channel = SocketChannel.open();
		channel.configureBlocking(false);
		channel.connect(new InetSocketAddress(SERVER_PORT));
		channel.register(selector, SelectionKey.OP_CONNECT);
		while (selector.isOpen()){
			selector.select();
			Iterator<SelectionKey> itr = selector.selectedKeys().iterator();
			while (itr.hasNext()){
				SelectionKey key = itr.next();
				if (key.isConnectable()){
					SocketChannel scl = (SocketChannel)key.channel();
					if (scl.finishConnect()){
						scl.write(ByteBuffer.wrap("hello,nio".getBytes()));
						System.out.println("发送请求数据:hello,nio");
					}
					key.interestOps(SelectionKey.OP_READ);
				}else if (key.isReadable()){
					SocketChannel scl = (SocketChannel)key.channel();
					ByteBuffer buffer = ByteBuffer.allocate(128);
					int readed = scl.read(buffer);
					if (readed!=-1){
						buffer.flip();
						System.out.println("接收到服务器响应数据:" + new String(buffer.array()).trim());
					}
					scl.close();
					selector.close();
				}
				itr.remove();
			}
		}
	}
	
	public static void main(String[] args) throws IOException{
		NioClient.sendReq();
	}
}
