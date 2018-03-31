package io;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class DownloadFileProcesser {

	private FileChannel fileChannel;
	
	private ByteBuffer buffer = ByteBuffer.allocate(8 * 1024);
	
	public DownloadFileProcesser(String fileName){
		try{
			RandomAccessFile raf = new RandomAccessFile(fileName,"r");
			fileChannel = raf.getChannel();
			System.out.println(fileName+" ,size:" + fileChannel.size());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 每次读取文件的部分数据,直到读完返回0或-1
	 * @return
	 * @throws IOException
	 */
	public int readFile2Buffer() throws IOException{
		buffer.clear();
		int readCount = fileChannel.read(buffer);
		buffer.flip();
		return readCount;
	}
	
	//返回读取的数据
	public ByteBuffer getByteBuffer(){
		return buffer;
	}
	
	public void close() throws IOException {
		fileChannel.close();
	}
}
