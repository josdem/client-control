package com.all.client.itunes;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ByteObject implements Serializable {
	
	private static final long serialVersionUID = 3255983749937922660L;
	private static final int SIZE_IN_KB = 1024;

	private static Log log = LogFactory.getLog(ByteObject.class);

	private byte[] content;
	private boolean compressed;
	
	private ByteObject(byte[] content, boolean compressed){
		this.content = content;
		this.compressed = compressed;
	}
	
	public ByteObject(Serializable object){
		content = toBytes(object);
	}
	
	public Serializable readObject() {
		if(compressed){
			throw new IllegalStateException("The byte content is compressed. Uncompress the content before reading an Object.");
		}
		if (content == null){
			return null;
		}
		ByteArrayInputStream bais = new ByteArrayInputStream(content);
		ObjectInputStream ois;
		Serializable result = null;
		try {
			ois = new ObjectInputStream(bais);
			result = (Serializable) ois.readObject();
			ois.close();
		} catch (Exception e) {
			return null;
		}
		return result;
	}
	
	public byte[] getBytes(){
		return Arrays.copyOf(content, content.length);
	}
	
	public static byte[] toBytes(Serializable content) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutput out;
		try {
			out = new ObjectOutputStream(bos);
			out.writeObject(content);
			out.close();
		} catch (Exception e) {
			log.error(e,e);
			return null;
		}
		return bos.toByteArray();
	}
	
	public static ByteObject fromBytes(byte[] content, boolean compressed) {
		return content != null ? new ByteObject(content, compressed) : null;
	}
	
	public static double getSizeInKBytes(Serializable obj) {
		return (double) toBytes(obj).length / SIZE_IN_KB;
	}

	public static double getSizeInKBytes(byte[] bytes) {
		return (double) bytes.length / SIZE_IN_KB;
	}
}
