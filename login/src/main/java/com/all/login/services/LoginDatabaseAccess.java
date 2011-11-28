package com.all.login.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Scanner;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.all.core.common.services.ApplicationConfig;
import com.all.login.model.LoginDatabase;
import com.all.shared.json.JsonConverter;

@Service
public class LoginDatabaseAccess {
	private static final Log LOG = LogFactory.getLog(LoginDatabaseAccess.class);
	@Autowired
	private ApplicationConfig appConfig;

	private File file;

	private LoginDatabase db;

	public LoginDatabaseAccess() {
	}

	@PostConstruct
	public void setup() {
		JsonConverter.addJsonReader(LoginDatabase.class, new LoginDatabaseReader());
		file = new File(appConfig.getUserFolder("config"), "ldata.dbg");
	}

	@PreDestroy
	public void shutdown() {
		try {
			save(file, getDB());
		} catch (Exception e) {
			LOG.error(e, e);
		}
	}

	public LoginDatabase getDB() {
		if (db == null) {
			synchronized (this) {
				if (db == null) {
					db = loadDatabase(file);
				}
			}
		}
		return db;
	}

	private static LoginDatabase loadDatabase(File file) {
		LoginDatabase db = null;
		if (file.exists()) {
			Scanner scanner = null;
			try {
				StringBuilder text = new StringBuilder();
				String NL = System.getProperty("line.separator");
				scanner = new Scanner(new FileInputStream(file));
				while (scanner.hasNextLine()) {
					text.append(scanner.nextLine() + NL);
				}
				db = JsonConverter.toBean(text.toString(), LoginDatabase.class);
			} catch (Exception e) {
				LOG.error("Could not deserialize LoginDatabase.", e);
			} finally {
				try {
					scanner.close();
				} catch (Exception e) {
					LOG.error(e, e);
				}
			}
		}
		if(db == null){
			db = new LoginDatabase();
		}
		return db;
	}

	private static void save(File file, LoginDatabase db) {
		Writer out = null;
		try {
			out = new OutputStreamWriter(new FileOutputStream(file));
			out.write(JsonConverter.toJson(db));
		} catch (Exception e) {
			LOG.error("Could not serialize LoginDatabase.", e);
		} finally {
			try {
				out.close();
			} catch (Exception e) {
				LOG.error("Could not close LoginDatabase output stream.", e);
			}
		}
	}

}
