package com.xhqb.business.service.client.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xhqb.profile.dubbo.service.DemoService;

/**
 * 
 * @author shizi
 *
 * @time 2015年9月6日 下午1:50:41
 */
@Service("demoClientService")
public class DemoClientService implements DemoService {
	private static final Logger log = LoggerFactory.getLogger(DemoClientService.class);

	@Resource(name = "demoService")
	private DemoService demoService;

	@Override
	public String getName() {
		log.info("DemoClientService log");
		log.error("DemoClientService test error log file");
		return demoService.getName();
	}

}
