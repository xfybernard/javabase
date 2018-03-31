package io;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
/**
 * jdk7 try-with-resource以及
 * catch 多个exception
 * @author Administrator
 *
 */
public class TryResource {
	private static final String filePath = "d:\\vm-ware-id.txt";
	
	/**
	 * 通过一个一个字节读取
	 * @param filePath
	 */
	public static void readFileBybyte(String filePath){
		try(InputStream is = new FileInputStream(filePath)){
			int data = is.read();
			while (data!=-1){
				System.out.print((char)data);
				data = is.read();
			}
			throw new NullPointerException();
		}catch(IOException | NullPointerException e){
			e.printStackTrace(System.out);
		}
	}
	
	public static void readFileBybyteArray(String filePath){
		try(InputStream is = new FileInputStream(filePath)){
			byte [] buffer = new byte[1024];
			int data = is.read(buffer);
			while (data!=-1){
				System.out.println(new String(buffer).trim());
				data = is.read(buffer);
			}
		}catch(IOException e){
			e.printStackTrace(System.out);
		}
	}
	
	public static void main(String[] args){
	//	readFileBybyte(filePath);
		readFileBybyteArray(filePath);
	}
}
