package io;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class DefaultProtocolImpl implements Protocol{

	private int bufferSize;
	
	public DefaultProtocolImpl(int bufferSize){
		this.bufferSize = bufferSize;
	}
	
	@Override
	public void handleAcceptable(SelectionKey key) throws IOException {
		SocketChannel clientChannel = ((ServerSocketChannel)key.channel()).accept();
		clientChannel.configureBlocking(false);
		clientChannel.register(key.selector(), 
				SelectionKey.OP_READ,ByteBuffer.allocate(bufferSize));
	}

	@Override
	public void handleReadable(SelectionKey key) throws IOException{
		SocketChannel clientChannel =  (SocketChannel)key.channel();
		Object attchment = key.attachment();
		if (attchment!=null){
			ByteBuffer attachment = (ByteBuffer)key.attachment();
			int readed = clientChannel.read(attachment);
			ByteBuffer buf = attachment.duplicate();
			System.out.println("接收客户端 :" +clientChannel.getRemoteAddress()+ "数据:" + new String(buf.array()).trim());
			// 如果read()方法返回-1，则表示底层连接已经关闭，此时需要关闭信道。
			//关闭信道时，将从选择器的各种集合中移除与该信道关联的键。
			if (readed==-1){
				System.out.println("客户端:" +clientChannel.getRemoteAddress()+ "主动断开连接...");
				clientChannel.close();
			}else if (readed>0){
				key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
				//key.interestOps(SelectionKey.OP_WRITE);
			}
		}else {
			ByteBuffer buffer = ByteBuffer.allocate(bufferSize);
			int readed = clientChannel.read(buffer);
			if (readed==-1){
				System.out.println("客户端 :" +clientChannel.getRemoteAddress()+ "主动断开连接...");
				clientChannel.close();
			}
		}
	}

	@Override
	public void handleWriteable(SelectionKey key) throws IOException{
		SocketChannel clientChannel =  (SocketChannel)key.channel();
		ByteBuffer buffer = (ByteBuffer)key.attachment();
		buffer.flip();
		while (buffer.hasRemaining()){
			clientChannel.write(buffer);
		}
		key.attach(null);
		key.interestOps(SelectionKey.OP_READ);
	}
}
