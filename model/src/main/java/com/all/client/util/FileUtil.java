package com.all.client.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FileUtil {
	private static final Log LOG = LogFactory.getLog(FileUtil.class);

	public static String getExtension(File file) {
		if (file == null) {
			throw new IllegalArgumentException("can not get file extension from 'null' file");
		} else {
			String fileName = file.getName();
			if (fileName == null) {
				return "";// it should make more sense to return null, but for compatibility it remains this way
			} else {
				fileName = fileName.trim();
				int index = fileName.lastIndexOf('.');
				if (index == -1) {
					return fileName;// it should make more sense to return null, but for compatibility it remains this way
				} else if (index + 1 == fileName.length()) {
					return "";
				} else {
					return fileName.substring(index + 1).toUpperCase();
				}
			}
		}
	}

	public static List<String> readLinesToList(String path) {
		List<String> list = new ArrayList<String>();
		BufferedReader bf = null;
		Reader reader = null;

		try {
			InputStream is = FileUtil.class.getClassLoader().getResourceAsStream(path);
			if (is == null) {
				reader = new FileReader(path);
			} else {
				reader = new InputStreamReader(is);
			}
			bf = new BufferedReader(reader);
			String line;
			while ((line = bf.readLine()) != null) {
				list.add(line);
			}
		} catch (IOException e) {
			LOG.error(e, e);
			return null;
		} finally {
			closeReader(bf);
			closeReader(reader);
		}

		return list;
	}

	private static void closeReader(Reader reader) {
		if (reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
				LOG.info(e, e);
			}
		}
	}

	public void writeLinesToFile(List<String> stringList, String path) {
		FileWriter fileWriter = null;
		BufferedWriter bw = null;
		try {
			fileWriter = new FileWriter(path);
			bw = new BufferedWriter(fileWriter);

			for (String line : stringList) {
				bw.write(line);
				bw.newLine();
			}
			bw.flush();

		} catch (IOException e) {
			LOG.error(e, e);
		} finally {
			closeWriter(bw);
			closeWriter(fileWriter);
		}
	}

	private void closeWriter(Writer writer) {
		if (writer != null) {
			try {
				writer.close();
				writer = null;
			} catch (IOException e) {
				LOG.info(e, e);
			}
		}
	}

	public static File copy(File source, File destinationFolder, FileCopyObserver observer) throws InterruptedException {
		if (observer == null) {
			observer = FileCopyObserver.EMPTY;
		}
		boolean error = false;
		long currentBytes = 0;
		long totalBytes = source.length();
		File destination = new File(destinationFolder, source.getName());
		int t = 1;
		while (destination.exists()) {
			String name = source.getName();
			int lastIndexOf = name.lastIndexOf('.');
			if (lastIndexOf == -1) {
				name += "(" + t + ")";
			} else {
				name = name.substring(0, lastIndexOf) + "(" + t + ")" + name.substring(lastIndexOf, name.length());
			}
			destination = new File(destinationFolder, name);
			t++;
		}
		byte[] buffer = new byte[1024 * 1024 * 2];
		int read = 0;
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new FileInputStream(source);
			out = new FileOutputStream(destination);
			while ((read = in.read(buffer)) > 0) {
				out.write(buffer, 0, read);
				currentBytes += read;
				observer.copyProgress(currentBytes, totalBytes);
				observer.checkInterrupt();
			}
		} catch (IOException ex) {
			error = true;
			observer.deviceFull();
		} catch (InterruptedException ex) {
			error = true;
		} finally {
			try {
				if(in != null) {
					in.close();
				}
			} catch (IOException e) {
				LOG.error(e, e);
			}
			try {
				if(out != null) {
					out.close();
				}
			} catch (IOException e) {
				LOG.error(e, e);
			}
			if (error) {
				destination.delete();
			}
		}
		observer.complete(totalBytes);
		return destination;
	}

	public interface FileCopyObserver {
		FileCopyObserver EMPTY = new FileCopyObserver() {
			@Override
			public void deviceFull() {
			}

			@Override
			public void copyProgress(long currentFileBytes, long totalFileBytes) {
			}

			@Override
			public void complete(long totalFileBytes) {
			}

			@Override
			public void checkInterrupt() throws InterruptedException {
			}
		};

		void checkInterrupt() throws InterruptedException;

		void copyProgress(long currentFileBytes, long totalFileBytes);

		void complete(long totalFileBytes);

		void deviceFull();

	}
}
