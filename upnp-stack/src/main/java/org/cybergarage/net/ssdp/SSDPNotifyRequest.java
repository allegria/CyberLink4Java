/******************************************************************
*
*	CyberUPnP for Java
*
*	Copyright (C) Satoshi Konno 2002
*
*	File: SSDPMSearchRequest.java
*
*	Revision;
*
*	01/14/03
*		- first revision.
*	
******************************************************************/

package org.cybergarage.net.ssdp;

import org.cybergarage.net.http.HTTP;

public class SSDPNotifyRequest extends SSDPRequest
{
	////////////////////////////////////////////////
	//	Constructor
	////////////////////////////////////////////////
	
	public SSDPNotifyRequest()
	{
		setMethod(HTTP.NOTIFY);
		setURI("*");
	}
}
