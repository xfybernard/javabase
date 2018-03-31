package io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NioFileServer {
	
	public static int SERVERPORT=8888;
	
	private static String filePath;
	
	public static void handleSelectionKey(SelectionKey key) throws IOException{
		if (key.isAcceptable()){
			ServerSocketChannel sskChannel = (ServerSocketChannel)key.channel();
			SocketChannel client = sskChannel.accept();
			if (client!=null){
				System.out.println("�µĿ���������:" + client.getRemoteAddress());
				client.configureBlocking(false);
				client.register(key.selector(), SelectionKey.OP_READ);
			}
		}else if(key.isReadable()){
			SocketChannel client = (SocketChannel)key.channel();
			ByteBuffer buffer = ByteBuffer.allocate(8*1024);
			client.read(buffer);
			byte  []buffer1 = new byte[buffer.position()];
			buffer.flip();
			buffer.get(buffer1);
			System.out.println("���ܵ��ͻ���" +client.getRemoteAddress()+" ��Ϣ:" +new String(buffer1));
			key.interestOps(SelectionKey.OP_WRITE);
			DownloadFileProcesser downloadFileProcesser = new DownloadFileProcesser(filePath);
		    key.attach(downloadFileProcesser);
		}else if (key.isWritable()){
			SocketChannel client = (SocketChannel)key.channel();
			DownloadFileProcesser process = (DownloadFileProcesser)key.attachment();
			int count = process.readFile2Buffer();
			if (count<=0){
				System.out.println("�ͻ���" +client.getRemoteAddress()+ "�������...");
				client.close();
				process.close();
			}else{
				client.write(process.getByteBuffer());
			}
		}
	}
	
	public static void main(String[] args) throws IOException{
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
	    serverSocketChannel.configureBlocking(false);
	    serverSocketChannel.socket().bind(new InetSocketAddress(SERVERPORT));
	    Selector selector = Selector.open();
	    serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
	    System.out.println("�ļ�����������,�˿�:" + SERVERPORT);
	    filePath = "e:\\pdf\\SpringԴ����Ƚ��� [�¼ѱ���][�����ʵ������][2013.09][386ҳ].pdf";
	    while (true){
	    	selector.select();
	    	Iterator<SelectionKey> itr = selector.selectedKeys().iterator();
	    	while (itr.hasNext()){
	    		handleSelectionKey(itr.next());
	    		itr.remove();
	    	}
	    }
	}
}
