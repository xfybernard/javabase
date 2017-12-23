package com.xfy.bernard.classloader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MyClassloader extends ClassLoader {

	private String classDir;

	public MyClassloader() {
	}

	public MyClassloader(String classDir) {
		this.classDir = classDir;
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		String classFileName = classDir + File.separator + name + ".class";
		FileInputStream fis = null;
		ByteArrayOutputStream bos = null;
		try {
			fis = new FileInputStream(classFileName);
			bos = new ByteArrayOutputStream();
			cypher(fis, bos);
			byte[] byteAry = bos.toByteArray();
			return defineClass(null, byteAry, 0, byteAry.length);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public static void cypher(InputStream ism, OutputStream osm) throws IOException {
		int b = -1;
		while ((b = ism.read()) != -1) {
			osm.write(b ^ 0xff);
		}
	}

	public static void main(String[] args) throws FileNotFoundException, IOException {
		String srcDirection = "D:\\workspace\\javabase\\bin\\com\\xfy\\bernard\\classloader";
		String destDirection = "D:\\workspace\\javabase\\cypherclass";
		String srcPath = srcDirection + "\\MyList.class";
		String destPath = destDirection + "\\MyList.class";
		InputStream fisrc = null;
		OutputStream fidest = null;
		try {
			fisrc = new FileInputStream(srcPath);
			fidest = new FileOutputStream(destPath);
			cypher(fisrc, fidest);
		} finally {
			if (fisrc != null) {
				fisrc.close();
			}
			if (fidest != null) {
				fidest.close();
			}
		}
	}

}
