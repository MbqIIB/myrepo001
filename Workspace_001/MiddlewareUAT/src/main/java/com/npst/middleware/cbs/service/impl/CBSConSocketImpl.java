package com.npst.middleware.cbs.service.impl;

import java.io.IOException;
import java.util.Date;

import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOUtil;
import org.jpos.iso.channel.ASCIIChannel;
import org.jpos.iso.channel.NACChannel;
import org.jpos.util.LogSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.npst.middleware.cbs.service.CBSConSocket;
import com.npst.middleware.iso.ATMPackager;
import com.npst.middleware.iso.CBSPackager;
import com.npst.middleware.util.JPOSListener;
import com.npst.middleware.util.Util;

@Service
public class CBSConSocketImpl implements CBSConSocket
{
	private static final Logger LOG = LoggerFactory.getLogger(CBSConSocketImpl.class);
	private static final String CBSHEADER = "ISO015000010";
	private static final String CBSHEADER_RESPONSE = "ISOISOISOISOISOISOISOISO";

	String serverName;
	int port;
	String atmServerIP;
	int atmPort;
	String cbsMessageHeader;
	String atmMessageHeader;

	public CBSConSocketImpl()
	{
		super();
		// CBS server Config
		serverName = Util.getProperty("CBS_IP");
		String portStr = Util.getProperty("CBS_PORT");
		port = Integer.parseInt(portStr);
		cbsMessageHeader = Util.getProperty("CBS_MESSAGE_HEADER");

		// ATM server Config
		atmServerIP = Util.getProperty("ATM_IP");
		String atmPortStr = Util.getProperty("ATM_PORT");
		atmPort = Integer.parseInt(atmPortStr);
		atmMessageHeader = Util.getProperty("ATM_MESSAGE_HEADER");
	}

	@Override
	public ISOMsg send(final ISOMsg isoObj) throws Exception
	{
		/*
		 * LOG.trace("Starting send..." + isoObj); String response = ""; try {
		 * LOG.debug("Connecting to " + serverName + " on port " + port); Socket
		 * client = new Socket(serverName, port); LOG.debug("Just connected to "
		 * + client.getRemoteSocketAddress()); OutputStream outToServer =
		 * client.getOutputStream(); DataOutputStream out = new
		 * DataOutputStream(outToServer);
		 * out.write(Util.isoToStr(isoObj).getBytes()); InputStream inFromServer
		 * = client.getInputStream(); DataInputStream in = new
		 * DataInputStream(inFromServer); response = in.readLine();
		 * client.close(); } catch (Exception e) {
		 * LOG.error("Exception in Send Method ..." + e); e.printStackTrace(); }
		 * LOG.info("End Send Method ...." + response.toString()); return
		 * Util.strToIso(response);
		 */
		return null;
	}

	@Override
	public ISOMsg sendChannel(ISOMsg isoObj) throws Exception
	{
		CBSPackager packager = new CBSPackager();
		NACChannel channel = null;
		ISOMsg responseMsg = null;
		org.jpos.util.Logger logger = new org.jpos.util.Logger();
		try
		{
			LOG.debug("Connecting to server {} on port {} ",serverName,port);
			channel = new NACChannel();
			channel.setTimeout(31000);
			channel.setPort(port);
			channel.setHost(serverName);
			channel.setHeader("ISOISOISOISOISOISOISOISO");
			channel.setPackager(packager);
			logger.addListener(new JPOSListener());
			((LogSource) channel).setLogger(logger, "test-channel");
			byte[] data = isoObj.pack();
			LOG.info("Request String : {} " ,new String(data));
			LOG.info("\n\nRequest HEX String : {} ",ISOUtil.hexdump(data));
			channel.connect();
			LOG.info("Starting req at {} ",new Date());
			channel.send(isoObj);
			LOG.info("Got Response at {} ",new Date());
			
			LOG.info("main() receiving......{} : " , channel.isConnected());
			responseMsg = channel.receive();
			LOG.info("message received......." ,new String(responseMsg.pack()));
			if (responseMsg != null)
			{
				responseMsg.setPackager(packager);
				byte[] image_out = responseMsg.pack();
			}
		}
		catch (IOException io)
		{
			LOG.error(io.getMessage(),io);
			if (io.getMessage().indexOf("Unable to connect") >= 0)
			{
				responseMsg = new ISOMsg();
				responseMsg.set(39, "DWN");
			}
		}
		catch (Exception e)
		{
			LOG.error(e.getMessage(),e);
		}
		finally
		{
			if (channel != null)
				channel.disconnect();
		}
		LOG.info("Returning from CBSConSocketImpl with responseMsg  as {} " , responseMsg);
		return responseMsg;
	}

	@Override
	public ISOMsg sendATMChannel(ISOMsg isoObj) throws Exception
	{
		ATMPackager packager = new ATMPackager();
		ASCIIChannel channel = null;
		ISOMsg responseMsg = null;
		org.jpos.util.Logger logger = new org.jpos.util.Logger();
		try
		{
			LOG.info("Connecting to ATM at IP {} and port {} " ,atmServerIP,atmPort);
			channel = new ASCIIChannel(atmServerIP, atmPort, packager);
			logger.addListener(new JPOSListener());
			((LogSource) channel).setLogger(logger, "test-channel");
			byte[] data = isoObj.pack();
			LOG.info("Request String : {} " ,new String(data));
			LOG.info("\n\nRequest HEX String : {} ",ISOUtil.hexdump(data));
			//channel.setTimeout(20000);
			channel.connect();
			LOG.info("Connecting server {}  on port {} at {}",serverName,port,new Date());
			channel.send(isoObj);
			responseMsg = channel.receive();
			LOG.info("Response received as {} with {} " ,new String(responseMsg.pack()),new Date());
			if (responseMsg != null)
			{
				responseMsg.setPackager(packager);
				byte[] image_out = responseMsg.pack();
				LOG.info("main() Out Byte Hex :::: " , ISOUtil.hexdump(image_out));
				LOG.info("main() Field 39 (Response code) :: " ,responseMsg.getString(39));
			}
		}
		catch (Exception e)
		{
			LOG.error(e.getMessage(),e);
		}
		finally
		{
			if (channel != null)
				channel.disconnect();
		}
		LOG.info("Return from CBSConsocket with responseMsg as {} ",responseMsg);
		return responseMsg;
	}

}
