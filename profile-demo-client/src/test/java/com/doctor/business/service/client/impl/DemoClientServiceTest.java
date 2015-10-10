package com.doctor.business.service.client.impl;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.MDC;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.doctor.profile.constant.DubboUrlParameter;
import com.doctor.profile.constant.WebConstant;

/**
 * 
 * @author cuike
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/profileDemoClientConfig/spring-context.xml")
public class DemoClientServiceTest {

	@Resource(name = "demoClientService")
	private DemoClientService demoService;

	private ExecutorService executorService = Executors.newCachedThreadPool();

	@Test
	public void testGetName() throws InterruptedException {
		String uuid = UUID.randomUUID().toString();
		MDC.put(DubboUrlParameter.uuid, uuid);
		MDC.put(WebConstant.uuid, uuid);
		System.err.println(demoService.getName("ss", "man"));

	}
}
