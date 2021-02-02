package il.ac.idc.cs.sinkhole;

import java.util.Random;


public class rootServers {
    /**
     * contains all root servers in a list. supports random fetching of domain name for one root server
     */

    // a list of all root DNS servers domain names
    final static String[] DNSRootServersDomainNames = new String[] {
                "a.root-servers.net",
                "b.root-servers.net",
                "c.root-servers.net",
                "d.root-servers.net",
                "e.root-servers.net",
                "f.root-servers.net",
                "g.root-servers.net",
                "h.root-servers.net",
                "i.root-servers.net",
                "j.root-servers.net",
                "k.root-servers.net",
                "l.root-servers.net",
                "m.root-servers.net",
    };


    /**
     * @return a random root server domain
     */
    public static String getRandomServer(){
        return DNSRootServersDomainNames[new Random().nextInt(DNSRootServersDomainNames.length)];

    }

}
