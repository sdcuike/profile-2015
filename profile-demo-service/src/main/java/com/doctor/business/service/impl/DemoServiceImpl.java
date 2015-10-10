package com.doctor.business.service.impl;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.doctor.profile.dubbo.service.DemoService;

/**
 * 
 * @author shizi
 *
 * @time 2015年9月6日 下午1:44:15
 */
@Service("demoService")
public class DemoServiceImpl implements DemoService {

	private static Logger log = LoggerFactory.getLogger(DemoServiceImpl.class);

	@Override
	public String getName(String name, String sex) {
		log.info("模拟 业务日志");
		log.error("模拟异常日志", new RuntimeException("模拟异常日志"));
		try {
			TimeUnit.SECONDS.sleep(10);
		} catch (InterruptedException e) {
			log.error("error", e);
		}
		return "返回值";
	}

}
