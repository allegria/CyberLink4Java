/*********************************************************************
*
*	CyberLink UPnP for Java
*	Inspired by: Satoshi Konno
*	File: HostInterface.java
*
*	UPnP Specification 1.1 - Addressing is Step 0 of UPnP networking.
*	CyberLink UPnP for Java libraries are intended to run on top of a
*	host that is managing the networking stack and the net libraries
*	will primarily be used to interrogate the running stack not to
*	initiate it. 
*
**********************************************************************/

package org.cybergarage.net;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;

import org.cybergarage.util.Debug;

public class HostInterface
{
	////////////////////////////////////////////////
	//	Constants
	////////////////////////////////////////////////
	
	/*
	 *	UPnP Specification
	 *	Devices and control points MUST support IPv4-only, and MAY also support IPv6. 
	 *	IPv6-only devices and control points are NOT allowed in UPnP, since these cannot
	 *	interoperate with IPv4-only control points and devices. 
	 *	All IPv6 devices and control points are therefore inherently multi-homed and must
	 *	adhere to all multi-homed behaviors.
	 */
	
	public static boolean USE_LOOPBACK_ADDR = false;
	public static boolean USE_ONLY_IPV4_ADDR = false;
	public static boolean USE_ONLY_IPV6_ADDR = false;

	
	
	/*
	 * 	TODO include the following classes' networking aspects in this class as makes sense:
	 * 	org.cybergarage.upnp.std.av.renderer.MediaRenderer
	 * 	org.cybergarage.upnp.std.av.server.MediaServer
	 * 	org.cybergarage.upnp.Device
	 * 	org.cybergarage.http.HTTPServer
	 * 	org.cybergarage.http.HTTPServerList
	 * 	org.cybergarage.http.HTTPPacket
	 * 	org.cybergarage.upnp.ControlPoint
	 *	org.cybergarage.upnp.ssdp.HTTPMUSocket
	 * 	org.cybergarage.upnp.ssdp.HTTPUSocket
	 * 	org.cybergarage.upnp.ssdp.SSDPNotifySocket
	 * 	org.cybergarage.upnp.ssdp.SSDPNotifySocketList
	 * 	org.cybergarage.upnp.ssdp.SSDPPacket
	 * 	org.cybergarage.upnp.ssdp.SSDPSearchRequest
	 * 	org.cybergarage.upnp.ssdp.SSDPSearchResponseSocket
	 * 	org.cybergarage.upnp.ssdp.SSDPSearchResponseSocketList
	 * 	org.cybergarage.upnp.ssdp.SSDPSearchSocket
	 * 	org.cybergarage.upnp.ssdp.SSDPSearchSocketList
	 * 	org.cybergarage.upnp.xml.DeviceData
	 * 	org.cybergarage.upnp.UPnP
	 * 	ContentDirectory -> MediaServer
	 * 	Subscriber ??
	 * 	ResourceNode -> GetNetwork ??
	 */
	
	////////////////////////////////////////////////
	//	Network Interfaces
	////////////////////////////////////////////////
	
	private static String ifAddress = "10.1.1.16";
	private static boolean bFoundAddress = false;
	
	public final static int IPV4_BITMASK =  0x0001;
	public final static int IPV6_BITMASK =  0x0010;
	public final static int LOCAL_BITMASK = 0x0100;

	/*	
	 * 	the interface(s) to use for the device can be passed in
	 * 	from configuration at startup or can can be auto configured
	 * 	through interrogation of the host's interfaces
	 */
	
	public final static void setInterface(String ifaddr)
	{
		if (hasAssignedInterface() == true)
			ifAddress =  getInterface();
			
		try {
			Enumeration nis = NetworkInterface.getNetworkInterfaces();
			while (nis.hasMoreElements() && bFoundAddress == false){
				NetworkInterface ni = (NetworkInterface)nis.nextElement();
				Enumeration addrs = ni.getInetAddresses();
				while (addrs.hasMoreElements()) {
					InetAddress addr = (InetAddress)addrs.nextElement();
					if (addr.getHostAddress() == ifaddr)
						if (isUsableAddress(addr) == true)
						{
							ifAddress = ifaddr;
							bFoundAddress = true;
							break;
						}
				}
			}
		}
		catch(Exception e){};
	}
	
	public final static String getInterface()
	{
		return ifAddress;
	}
	
	private final static boolean hasAssignedInterface()
	{
		return (0 < ifAddress.length()) ? true : false;
	}
	
	////////////////////////////////////////////////
	//	Network Interfaces
	////////////////////////////////////////////////

	// Thanks for Theo Beisch (10/27/04)
	
	private final static boolean isUsableAddress(InetAddress addr)
	{
		if (USE_LOOPBACK_ADDR == false) {
			if (addr.isLoopbackAddress() == true)
				return false;
		}
		if (USE_ONLY_IPV4_ADDR == true) {
			if (addr instanceof Inet6Address)
				return false;
		}
		if (USE_ONLY_IPV6_ADDR == true) {
			if (addr instanceof Inet4Address)
				return false;
		}
		return true;
	}
	
	public final static int getNHostAddresses()
	{
		if (hasAssignedInterface() == true)
			return 1;
			
		
		// work out the number of network addresses on the host
		int nHostAddrs = 0;
		try {
			Enumeration nis = NetworkInterface.getNetworkInterfaces();
			while (nis.hasMoreElements()){
				NetworkInterface ni = (NetworkInterface)nis.nextElement();
				Enumeration addrs = ni.getInetAddresses();
				while (addrs.hasMoreElements()) {
					InetAddress addr = (InetAddress)addrs.nextElement();
					if (isUsableAddress(addr) == false)
						continue;
					nHostAddrs++;
				}
			}
		}
		catch(Exception e){
			Debug.warning(e);
		};
		return nHostAddrs;
	}

	/**
	 * 
	 * @param ipfilter
	 * @param interfaces
	 * @return
	 * @since 1.8.0
	 * @author Stefano "Kismet" Lenzi &lt;kismet.sl@gmail.com&gt;
	 */
	public final static InetAddress[] getInetAddress(int ipfilter,String[] interfaces){
		Enumeration nis;
		if(interfaces!=null){
			Vector iflist = new Vector();
			for (int i = 0; i < interfaces.length; i++) {
				NetworkInterface ni;
				try {
					ni = NetworkInterface.getByName(interfaces[i]);
				} catch (SocketException e) {
					continue;
				}
				if(ni != null) iflist.add(ni);

			}
			nis = iflist.elements();
		}else{
			try {
				nis = NetworkInterface.getNetworkInterfaces();
			} catch (SocketException e) {
				return null;
			}
		}		
		ArrayList addresses = new ArrayList();
		while (nis.hasMoreElements()){
			NetworkInterface ni = (NetworkInterface)nis.nextElement();			
			Enumeration addrs = ni.getInetAddresses();
			while (addrs.hasMoreElements()) {
				InetAddress addr = (InetAddress)addrs.nextElement();
				if(((ipfilter & LOCAL_BITMASK)==0) && addr.isLoopbackAddress())
					continue;
				
				if (((ipfilter & IPV4_BITMASK)!=0) && addr instanceof Inet4Address ) {						
					addresses.add(addr);
				}else if (((ipfilter & IPV6_BITMASK)!=0)&& addr instanceof InetAddress) {
					addresses.add(addr);
				}
			}
		}
		return (InetAddress[]) addresses.toArray(new InetAddress[]{});
	}
	
	
	//this one needs to stay
	public final static String getHostAddress(int n)
	{
		if (hasAssignedInterface() == true)
			return getInterface();
			
		int hostAddrCnt = 0;
		try {
			Enumeration nis = NetworkInterface.getNetworkInterfaces();
			while (nis.hasMoreElements()){
				NetworkInterface ni = (NetworkInterface)nis.nextElement();
				Enumeration addrs = ni.getInetAddresses();
				while (addrs.hasMoreElements()) {
					InetAddress addr = (InetAddress)addrs.nextElement();
					if (isUsableAddress(addr) == false)
						continue;
					if (hostAddrCnt < n) {
						hostAddrCnt++;
						continue;
					}
					String host = addr.getHostAddress();
					//if (addr instanceof Inet6Address)
					//	host = "[" + host + "]";
					return host;
				}
			}
		}
		catch(Exception e){};
		return "";
	}

	////////////////////////////////////////////////
	//	isIPv?Address
	////////////////////////////////////////////////
	
	public final static boolean isIPv6Address(String host)
	{
		try {
			InetAddress addr = InetAddress.getByName(host);
			if (addr instanceof Inet6Address)
				return true;
			return false;
		}
		catch (Exception e) {}
		return false;
	}

	public final static boolean isIPv4Address(String host)
	{
		try {
			InetAddress addr = InetAddress.getByName(host);
			if (addr instanceof Inet4Address)
				return true;
			return false;
		}
		catch (Exception e) {}
		return false;
	}

	////////////////////////////////////////////////
	//	hasIPv?Interfaces
	////////////////////////////////////////////////

	public final static boolean hasIPv4Addresses()
	{
		int addrCnt = getNHostAddresses();
		for (int n=0; n<addrCnt; n++) {
			String addr = getHostAddress(n);
			if (isIPv4Address(addr) == true)
				return true;
		}
		return false;
	}

	public final static boolean hasIPv6Addresses()
	{
		int addrCnt = getNHostAddresses();
		for (int n=0; n<addrCnt; n++) {
			String addr = getHostAddress(n);
			if (isIPv6Address(addr) == true)
				return true;
		}
		return false;
	}

	////////////////////////////////////////////////
	//	hasIPv?Interfaces
	////////////////////////////////////////////////

	public final static String getIPv4Address()
	{
		int addrCnt = getNHostAddresses();
		for (int n=0; n<addrCnt; n++) {
			String addr = getHostAddress(n);
			if (isIPv4Address(addr) == true)
				return addr;
		}
		return "";
	}

	public final static String getIPv6Address()
	{
		int addrCnt = getNHostAddresses();
		for (int n=0; n<addrCnt; n++) {
			String addr = getHostAddress(n);
			if (isIPv6Address(addr) == true)
				return addr;
		}
		return "";
	}

	////////////////////////////////////////////////
	//	getHostURL
	////////////////////////////////////////////////
	
	/*
	 * 	TODO include the following classes' URL aspects in this class as makes sense:
	 *	ItemNode
	 *	ResourceNode
	 *	ContentDirectory
	 *	HTTP
	 *	HTTPRequest
	 *	ControlRequest
	 *	QueryRequest
	 *	NotifyRequest
	 *	Subscriber
	 *	SubscriptionRequest
	 *	ServiceData
	 *	ControlPoint
	 *	Device
	 *	Service
	 *	Parser
	 *	Icon ??
	 *	SOAP ??
	 *	MySQL ??
	 */
	
	
	public final static String getHostURL(String host, int port, String uri)
	{
		String hostAddr = host;
		if (isIPv6Address(host) == true)
			hostAddr = "[" + host + "]";
		return 
			"http://" +
			hostAddr + 
			":" + Integer.toString(port) +
			uri;
	}
	
}
