package com.doctor.business.service.client.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xhqb.business.service.client.impl.DemoClientService;

/**
 * 
 * @author shizi
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/xhqbProfileDemoClientConfig/spring-context.xml")
public class DemoClientServiceTest {

	@Resource(name = "demoClientService")
	private DemoClientService demoService;

	private ExecutorService executorService = Executors.newCachedThreadPool();

	@Test
	public void testGetName() throws InterruptedException {
		for (int i = 0; i < 10; i++) {
			executorService.submit(new Runnable() {

				@Override
				public void run() {
					System.err.println(demoService.getName());

				}
			});
		}
		System.err.println(demoService.getName());
		executorService.shutdown();
	}

}
