package com.npst.middleware.util;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.jpos.core.Configurable;
import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;
import org.jpos.util.Log;
import org.jpos.util.LogEvent;
import org.jpos.util.LogListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JPOSListener implements LogListener , Configurable{
	private static final   Logger   JPOSLOGLISTENER = LoggerFactory.getLogger(JPOSListener.class);
	public JPOSListener() {
	}
	
	@Override
	public void setConfiguration(Configuration cfg)
			throws ConfigurationException {
	}

	private String getLogString(LogEvent ev) {
		ByteArrayOutputStream w = new ByteArrayOutputStream();
		PrintStream p = new PrintStream(w);
		ev.dump(p, "");
		return w.toString();

	}

	public LogEvent log(LogEvent ev) {

		String category =ev.getRealm().replace('/', '.'); 
		
		Logger logger = LoggerFactory
				.getLogger(category);
		String   level = Log.DEBUG;
		if(ev.getTag()!= null && !ev.getTag().isEmpty())
			level = ev.getTag();
			
		if (level.equalsIgnoreCase(Log.TRACE)) {
			if (logger.isTraceEnabled())
				logger.trace(getLogString(ev));
		} else if (level.equalsIgnoreCase(Log.DEBUG)) {
			if (logger.isDebugEnabled())
				logger.debug(getLogString(ev));
		} else if (level.equalsIgnoreCase(Log.INFO)) {
			if (logger.isInfoEnabled())
				logger.info(getLogString(ev));
		} else if (level.equalsIgnoreCase(Log.ERROR)
				|| level.equalsIgnoreCase(Log.FATAL)) {
			if (logger.isErrorEnabled())
				logger.error(getLogString(ev));
		} else if (level.equalsIgnoreCase(Log.WARN)) {
			if (logger.isWarnEnabled())
				logger.warn(getLogString(ev));
		}
		else
		{
			logger = LoggerFactory.getLogger(level+"."+category);
			if (logger.isDebugEnabled())
				logger.debug(getLogString(ev));
			else
			{
				if(JPOSLOGLISTENER.isDebugEnabled())
					JPOSLOGLISTENER.debug("level="+level+"text="+ getLogString(ev));
			}
		}
		return ev;

	}
}