package com.npst.mobileservice.service;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.log4j.Logger;

import com.npst.mobileservice.dao.MobupireqrespjsonidHome;
import com.npst.upi.hor.Mobupireqrespjson;
import com.npst.upi.hor.Mobupireqrespjsonid;

public class MobupireqrespjsonidService {
	private static MobupireqrespjsonidHome mobupireqrespjsonidHome = null;
	private static final Logger log = Logger.getLogger(MobupireqrespjsonidService.class);

	public Mobupireqrespjsonid findById(int parseInt) {
		try {
			if (null == mobupireqrespjsonidHome) {
				mobupireqrespjsonidHome = new MobupireqrespjsonidHome();
			}
			return mobupireqrespjsonidHome.findById(parseInt);
		} catch (Exception e) {

			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);

		}
		return null;

	}
	
	public Mobupireqrespjsonid findByTpId(String tpId) {
		try {
			if (null == mobupireqrespjsonidHome) {
				mobupireqrespjsonidHome = new MobupireqrespjsonidHome();
			}
			return mobupireqrespjsonidHome.findByTpId(tpId);
		} catch (Exception e) {

			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);

		}
		return null;

	}

	public void save(Mobupireqrespjson mobupireqrespjson) {
		log.info("mobupireqrespjson[" + mobupireqrespjson.toString() + "]");
		try {
			if (null == mobupireqrespjsonidHome) {
				mobupireqrespjsonidHome = new MobupireqrespjsonidHome();
			}
			Mobupireqrespjsonid mobupireqrespjsonid = new Mobupireqrespjsonid();
			mobupireqrespjsonid.setFlag(mobupireqrespjson.getFlag());
			mobupireqrespjsonid.setIdmobreqrespjsonid(mobupireqrespjson.getIdPk());
			mobupireqrespjsonid.setTpId(mobupireqrespjson.getTpId());
			mobupireqrespjsonidHome.save(mobupireqrespjsonid);
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);

		}

	}

}
