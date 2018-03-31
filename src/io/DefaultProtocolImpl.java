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
			System.out.println("���տͻ��� :" +clientChannel.getRemoteAddress()+ "����:" + new String(buf.array()).trim());
			// ���read()��������-1�����ʾ�ײ������Ѿ��رգ���ʱ��Ҫ�ر��ŵ���
			//�ر��ŵ�ʱ������ѡ�����ĸ��ּ������Ƴ�����ŵ������ļ���
			if (readed==-1){
				System.out.println("�ͻ���:" +clientChannel.getRemoteAddress()+ "�����Ͽ�����...");
				clientChannel.close();
			}else if (readed>0){
				key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
				//key.interestOps(SelectionKey.OP_WRITE);
			}
		}else {
			ByteBuffer buffer = ByteBuffer.allocate(bufferSize);
			int readed = clientChannel.read(buffer);
			if (readed==-1){
				System.out.println("�ͻ��� :" +clientChannel.getRemoteAddress()+ "�����Ͽ�����...");
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
