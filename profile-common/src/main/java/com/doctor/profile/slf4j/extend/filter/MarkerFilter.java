package com.doctor.profile.slf4j.extend.filter;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.AbstractMatcherFilter;
import ch.qos.logback.core.spi.FilterReply;

/**
 * 
 * @author shizi
 *
 * @time 2015年9月6日 下午1:40:25
 */
public class MarkerFilter extends AbstractMatcherFilter<ILoggingEvent> {

	private Marker markerToMatch = null;

	@Override
	public void start() {
		if (null != this.markerToMatch)
			super.start();
		else
			addError("!!! no marker yet !!!");
	}

	@Override
	public FilterReply decide(ILoggingEvent event) {
		Marker marker = event.getMarker();
		if (!isStarted())
			return FilterReply.NEUTRAL;
		if (null == marker)
			return onMismatch;
		if (markerToMatch.contains(marker))
			return onMatch;
		return onMismatch;
	}

	public void setMarker(String markerStr) {
		if (null != markerStr)
			markerToMatch = MarkerFactory.getMarker(markerStr);
	}

}
