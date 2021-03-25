package com.npst.middleware.hsm;

import java.io.ByteArrayInputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.npst.middleware.util.Util;
import com.safenetinc.luna.LunaSlotManager;

class HSMService
{
	private static final Logger LOG = LoggerFactory.getLogger(HSMService.class);
	private LunaSlotManager hsmConnection;
	private static final String PARTITION_PASSWORD = Util.getProperty("HSM_PARTITION_PASSWORD");
	private static final String HSMSLOT = Util.getProperty("HSM_SLOT");
	private static final String KEYSTORE_INSTANCE = Util.getProperty("HSM_KEYSTORE_INSTANCE");
	private static final String HSM_LABELNAME = Util.getProperty("HSM_LABEL_NAME");
	private static PrivateKey key = null;
	private static Key skey = null;
	private static HSMService instance = null;

	static
	{
		LOG.debug("Inside the static block");
		Security.addProvider(new com.safenetinc.luna.provider.LunaProvider());
		LOG.debug("End the static block");
	}

	public static HSMService getInstance()
	{
		LOG.debug("Starting from getInstance:");
		if (instance == null)
		{
			synchronized (HSMService.class)
			{
				if (instance == null)
				{
					instance = new HSMService();
				}
			}
		}
		LOG.debug("Return success from getInstance:");
		return instance;
	}

	void refreshHsmConnection()
	{
		LOG.debug("Starting from refreshHsmConnection:");
		try
		{
			LOG.trace("Before going to resolveLunaSlotManagerInstance:");
			resolveLunaSlotManagerInstance();
			LOG.trace("Success from resolveLunaSlotManagerInstance:");
			LOG.trace("Before going to hsmConnectionLogin:");
			hsmConnectionLogin();
			LOG.trace("Success from hsmConnectionLogin:");
		}
		catch (Exception e)
		{
			LOG.error(e.getMessage(),e);
		}
	}

	private void hsmConnectionLogin() throws Exception
	{
		LOG.trace("Start the hsmConnectionLogin method");
		synchronized (hsmConnection)
		{
			if (!hsmConnection.isLoggedIn())
			{
				LOG.debug("Before going to hsmConnection.login");
				hsmConnection.login(PARTITION_PASSWORD);
				LOG.debug("Success from hsmConnection.login");
			}
		}
		LOG.trace("End the hsmConnectionLogin method");
	}

	private void resolveLunaSlotManagerInstance() throws Exception
	{
		LOG.trace("Start from resolveLunaSlotManagerInstance");
		hsmConnection = LunaSlotManager.getInstance();
		if (hsmConnection == null)
		{
			throw new Exception("LunaSlotManager did not return an instance.");
		}
		LOG.trace("Success from resolveLunaSlotManagerInstance");
	}

	public synchronized void setKey()
	{
		LOG.trace("Start from setKey");
		try
		{
			if (key != null)
				return;
			if (Security.getProvider("LunaProvider") == null)
			{
				Security.addProvider(new com.safenetinc.luna.provider.LunaProvider());
			}
			LOG.trace("Before going to refreshHsmConnection :");
			refreshHsmConnection();
			LOG.info("End success from refreshHsmConnection :");
			ByteArrayInputStream is1 = new ByteArrayInputStream(HSMSLOT.getBytes());

			KeyStore keyStore = KeyStore.getInstance(KEYSTORE_INSTANCE);
			LOG.trace("Before going to keyStore.load:");
			keyStore.load(is1, PARTITION_PASSWORD.toCharArray());
			LOG.trace("End from keyStore.load:");
			LOG.trace("Before going to set HSM Key:");
			key = (PrivateKey) keyStore.getKey(HSM_LABELNAME, "".toCharArray());
			LOG.trace("End from PrivateKey with key:" + key);
			LOG.trace("Start from skey:");
			skey = (Key) keyStore.getKey("InjectedZPK", "".toCharArray());
			LOG.trace("Start from skey:" + skey);
		}
		catch (Exception e)
		{
			LOG.error(e.getMessage(),e);
		}
		LOG.debug("End from setKey");
	}

	public Key getSKey()
	{
		LOG.trace("Start from getSKey:");
		if (skey == null)
		{
			LOG.trace("Before going to setKey");
			setKey();
			LOG.trace("End from setKey");
		}
		return skey;
	}

	public PrivateKey getKey()
	{
		LOG.trace("Start from getkey method");
		if (key == null)
		{
			LOG.trace("Before going to setkey method");
			setKey();
			LOG.trace("Success from setkey");
		}
		LOG.trace("Return success from getkey method");
		return key;
	}
	public synchronized void reSetHSM()
	{
		resetKey();
	}

	public synchronized void resetKey()
	{
		try
		{
			if (Security.getProvider("LunaProvider") == null)
			{
				Security.addProvider(new com.safenetinc.luna.provider.LunaProvider());
			}
			refreshHsmConnection();
			refreshHsmConnectionRelogin();
			ByteArrayInputStream is1 = new ByteArrayInputStream(HSMSLOT.getBytes());
			KeyStore keyStore = KeyStore.getInstance(KEYSTORE_INSTANCE);
			keyStore.load(is1, PARTITION_PASSWORD.toCharArray());
			key = (PrivateKey) keyStore.getKey(HSM_LABELNAME, "".toCharArray());
			skey = (Key) keyStore.getKey("InjectedZPK", "".toCharArray());
		}
		catch (Exception e)
		{
			LOG.error(e.getMessage(), e);
		}
	}
	private void hsmConnectionReLogin() throws Exception {
				LOG.trace("Start the hsmConnectionReLogin method");
				synchronized (hsmConnection) {
				hsmConnection.login(PARTITION_PASSWORD);
				}
		 }
	void refreshHsmConnectionRelogin() {
				LOG.trace("Starting from refreshHsmConnectionRelogin:");
				try {
					resolveLunaSlotManagerInstance();
					hsmConnection.reinitialize();
					hsmConnectionReLogin();
				} catch (Exception e) {
					LOG.error("Unable to login to the Hardware Storage Module (HSM).", e);
					e.printStackTrace();
				}
				LOG.trace("Success from refreshHsmConnection method:");
		 }
}
