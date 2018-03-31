package io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

public class NioServer {
	
	private static final int BUFSIZE = 256;

    private static final int TIMEOUT = 30000;

    private static final int PORT = 8888;
    
    private static DefaultProtocolImpl protocol = new DefaultProtocolImpl(BUFSIZE);
	
	public static void main(String[] args) throws IOException{
		Selector selector = Selector.open();
		ServerSocketChannel scl = ServerSocketChannel.open();
		scl.configureBlocking(false);
		scl.register(selector, SelectionKey.OP_ACCEPT);
		scl.bind(new InetSocketAddress(PORT));
		System.out.println("服务已启动:" + PORT);
		while (true){
			if (selector.select(TIMEOUT)==0){
				continue;
			}

			Iterator<SelectionKey> itr = selector.selectedKeys().iterator();
			try{
				while (itr.hasNext()){
					SelectionKey key = itr.next();
					if (key.isAcceptable()){
						protocol.handleAcceptable(key);
					}else if (key.isReadable()){
						protocol.handleReadable(key);
					}else if (key.isWritable()){
						protocol.handleWriteable(key);
					}
					itr.remove();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
