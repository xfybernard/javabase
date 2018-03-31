package io;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NioServerClient implements Runnable{
	private static int  SERVERPORT = 8888;
	private String name;
    private FileChannel fileChannel;
    private ByteBuffer buffer = ByteBuffer.allocate(8 * 1024);
    public NioServerClient(String name , RandomAccessFile randomAccessFile){
    	this.name = name;
    	this.fileChannel = randomAccessFile.getChannel(); 
    }
    
    public void handleSelectionKey(SelectionKey key) throws IOException{
    	if (key.isConnectable()){
    		System.out.println("客户端[" + name + "]已经连接上了服务器...");
    		SocketChannel scl = (SocketChannel)key.channel();
    		if (scl.isConnectionPending() && scl.finishConnect()){
    			scl.configureBlocking(false);
    			String msg = "Thread-" + name + " send message!";
    	        scl.write(ByteBuffer.wrap(msg.getBytes()));
    	        System.out.println("客户端[" + name + "]给服务器端发送文本消息完毕:" + msg);
    	        scl.register(key.selector(), SelectionKey.OP_READ);
    		}
    	}else if (key.isReadable()) {
    		SocketChannel scl = (SocketChannel)key.channel();
    		buffer.clear();
    		int readed = scl.read(buffer);
    		if (readed<=0){
    			scl.close();
    			key.selector().close();
    	        System.out.println("Thread " + name + " 下载完毕...");
    	        fileChannel.close();
    		}
    		while (readed>0){
    			buffer.flip();
    			fileChannel.write(buffer);
    			readed = scl.read(buffer);
    		}
    	}
    }
    
	@Override
	public void run() {
		try{
			SocketChannel sc = SocketChannel.open();
		    Selector selector = Selector.open();
		    sc.configureBlocking(false);
		    sc.register(selector, SelectionKey.OP_CONNECT);
		    sc.connect(new InetSocketAddress(SERVERPORT));
		    while (selector.isOpen()){
		    	selector.select();
		    	Iterator<SelectionKey> itr = selector.selectedKeys().iterator();
		    	while (itr.hasNext()){
		    		handleSelectionKey(itr.next());
		    		itr.remove();
		    	}
		    }
		}catch(IOException e){
			e.printStackTrace();
		}
	}



	public static void main(String[] args) throws Exception {
		for(int i=0;i<10;i++){
			RandomAccessFile raf = new RandomAccessFile("e:\\pdfio\\niodown_"+i+".pdf","rw");
			new Thread(new NioServerClient(String.valueOf(i),raf)).start();
		}
	}
}
