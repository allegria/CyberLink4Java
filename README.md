[![GitHub version](https://badge.fury.io/gh/peterwatts%2Fallegria-Cyberlink4Java.png)](http://badge.fury.io/gh/peterwatts%2Fallegria-Cyberlink4Java)

CyberLink4Java
==============

CyberLink for Java is a development package for UPnP™ developers.

CyberLink controls these protocols automatically, and supports to create your devices and control points quickly.


###Forums
[Open Discussion](http://sourceforge.net/forum/forum.php?forum_id=258158)  
[Help](http://sourceforge.net/forum/forum.php?forum_id=258159)  
[Developer](http://sourceforge.net/forum/forum.php?forum_id=258160)  
  		
###Developers  

**Satoshi Konno**  
CyberGarage  
https://github.com/cybergarage  
skonno@users.sourceforge.net  
http://www.cybergarage.org/blog/skonnoblog.html  

**Stefano Lenzi**
kismet-sl@users.sourceforge.net
  

[LICENSE](./LICENSE.txt)

**TODO**  

* reorganise to use gradle build and so looks like allegria's CmisDlnaConnector stack diagram  
* add cmis directory to org.cybergarage.upnp.std.av.server 
* see if CyberGarage will take ideas upstream 
* expand types of av files that can be processed
* standardise to reference architectures based on UPnP Architecture version 1.1 and MediaServer:4 (or 5) - 
* rebuild the out-of-band transfer protocol to also use [vlc](http://www.videolan.org/projects/) rtsp    
* cherry pick cool code snippets from other projects: 
	* [Cling](https://github.com/4thline/cling) - e.g. [DIDL Parser](http://4thline.org/projects/cling/support/xref/org/teleal/cling/support/contentdirectory/DIDLParser.html)  
	* [pDLNA](https://github.com/geuma/pDLNA) - e.g. [config](https://github.com/geuma/pDLNA/blob/master/etc/pdlna.conf)
	* [Coherence](https://github.com/coherence-project/) - e.g. [Mirabeau UPnP over XMPP bridge](http://coherence.beebits.net/wiki/MirabeauHowTo?redirectedfrom=Mirabeau)  
* rebuild classes using CyberGarage's [Apache Felix OSGI](http://felix.apache.org/site/apache-felix-upnp.html) version    

Cool Supporting Development Tools:  

* [Developer Tools for UPnP Technologies](http://opentools.homeip.net/dev-tools-for-upnp)  
* [Apache CMIS Workbench](https://chemistry.apache.org/java/developing/tools/dev-tools-workbench.html)  