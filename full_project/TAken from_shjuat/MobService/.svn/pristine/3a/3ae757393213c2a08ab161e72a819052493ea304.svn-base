package com.npst.mobileservice.util;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateListener {
	private static final SessionFactory sessionFactory = buildSessionFactory();
	static Logger log = Logger.getLogger(HibernateListener.class);

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	// added PasswordHistory entity 05/07/2017
	private static SessionFactory buildSessionFactory() {
		{
			Configuration configuration = new Configuration().configure();
			configuration.setProperty("hibernate.hikari.dataSource.password",
					AESEncryptionUtility.decrypt(configuration.getProperty("hibernate.hikari.dataSource.password"),
							AESEncryptionUtility.secretKeys));
			return configuration.buildSessionFactory();
		}
	}
}