package com.npst.mobileservice.object;

import java.io.Serializable;

public class ReqResp implements Serializable {
	private static final long serialVersionUID = 1290050204611672411L;

	public String data;
	private String source;
	private String node;
	private String desc;
	
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	@Override
	public String toString() {
		return "ReqResp [data=" + data + ", source=" + source + ", node=" + node + ", desc=" + desc + "]";
	}
}
