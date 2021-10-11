package com.mrtrakwon.msa.util.http;


import java.net.InetAddress;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ServiceUtil {
	private static final Logger logger = LoggerFactory.getLogger(ServiceUtil.class);

	private final String port;
	private String serviceAddress = null;

	@Autowired
	private ServiceUtil(@Value("${server.port}") String port){
		this.port = port;
	}

	public String getServiceAddress() {
		if (serviceAddress == null) {
			serviceAddress = findMyHostName() + "/" + findMyIpAddress() + ":" + port;
		}
		return serviceAddress;
	}

	private String findMyIpAddress() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			return "unknown IP address";
		}
	}

	private String findMyHostName() {
		try {
			return InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			return "unknown host name";
		}
	}
}
