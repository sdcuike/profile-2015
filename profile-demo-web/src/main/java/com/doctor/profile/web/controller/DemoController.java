package com.doctor.profile.web.controller;

import javax.annotation.Resource;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doctor.profile.dubbo.service.DemoService;

/**
 * @author cuike
 *
 * @time 2015年10月12日
 */

@Controller
public class DemoController {

	@Resource(name = "demoClientService")
	private DemoService demoClientService;

	@RequestMapping("/getName")
	@ResponseBody
	public String getName() {
		return demoClientService.getName(RandomStringUtils.random(6), "man");
	}
}
