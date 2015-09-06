package com.xhqb.profile.spring.aop.method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author shizi
 *
 * @time 2015年9月6日 下午1:40:30
 */
public class MethodInvokeTimeAspect {
	private static Logger log = LoggerFactory.getLogger(MethodInvokeTimeAspect.class);

	public Object profile(ProceedingJoinPoint point) throws Throwable {
		long start = System.currentTimeMillis();

		try {
			return point.proceed();

		} finally {

			long end = System.currentTimeMillis();

			log.info("{} elapsed {} ", point, end - start);
		}

	}

	public void doThrowing(JoinPoint jp, Throwable ex) {
		log.error("{} error {}", jp, ex);
	}
}
