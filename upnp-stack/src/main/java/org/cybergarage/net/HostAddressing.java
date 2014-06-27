/*********************************************************************
*
*	CyberLink UPnP for Java
*	Inspired by: Satoshi Konno
*	File: HostAddressing.java
*
*	UPnP Specification 1.1 - Addressing is Step 0 of UPnP networking.
*	CyberLink UPnP for Java libraries are intended to run on top of a
*	host that is managing the networking stack and the net libraries
*	will primarily be used to interrogate the running stack not to
*	initiate it. 
*
**********************************************************************/
package org.cybergarage.net;

import java.net.NetworkInterface;
import java.net.InetAddress;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;



/*
 * pass in an interface name or an ip address
 * check for if ipv4 or ipv4 + ipv6
 * check if interface / address exists and is active
 * if don't pass in then get all active with at least ipv4
 * TODO work out if possible to receive request on the
 * loopback address e.g. local control point and if can
 * might be optimal to have some method to use it like the 
 * original c4j does.
 * 
 * TODO work out what security controls over the sockets
 * e.g. handling of proxies needs to be implemented including
 * test suite
 */


public class HostAddressing {

	/*
	 *	UPnP Specification
	 *	Devices and control points MUST support IPv4-only, and MAY also support IPv6. 
	 *	IPv6-only devices and control points are NOT allowed in UPnP, since these cannot
	 *	interoperate with IPv4-only control points and devices. 
	 *	All IPv6 devices and control points are therefore inherently multi-homed and must
	 *	adhere to all multi-homed behaviors.
	 */
	public static boolean USE_ONLY_IPV4_ADDR = false;
	
		
	
	
}
