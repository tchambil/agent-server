package dcc.agent.server.service.siebog.core;

/**
 * Created by teo on 30/04/15.
 */

import java.util.ArrayList;
import java.util.List;

public class AID {
    public static final boolean ISGUID = true;
    public static final boolean ISLOCALNAME = false;
    private static String platformID;
    private static String atHAP = null;
    private String name = new String();
    private List addresses = new ArrayList();
    private List resolvers = new ArrayList();

    public AID() {
        this("", ISGUID);
    }

    public AID(String guid) {
        this(guid, ISGUID);
    }

    public AID(String name, boolean isGUID) {
        if (atHAP == null)
            atHAP = "@" + getPlatformID();
        if (isGUID)
            setName(name);
        else
            setLocalName(name);
    }

    public AID(String name, String platform) {
        // initialize the static variable atHAP, if not yet initialized
        if (atHAP == null)
            atHAP = "@" + platform;
        setName(name);
    }

    static final String getPlatformID() {
        return platformID;
    }

    static final void setPlatformID(String id) {
        platformID = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String n) {
        name = n.trim();
    }

    public void addAddresses(String url) {
        if (!addresses.contains(url)) {
            addresses.add(url);
        }
    }

    public boolean removeAddresses(String url) {
        return addresses.remove(url);
    }

    public void clearAllAddresses() {
        addresses.clear();
    }

    public void addResolvers(AID aid) {
        resolvers.add(aid);
    }

    public boolean removeResolvers(AID aid) {
        return resolvers.remove(aid);
    }

    public void clearAllResolvers() {
        resolvers.clear();
    }

    public String[] getAddressesArray() {
        Object[] objs = addresses.toArray();
        String[] result = new String[objs.length];
        System.arraycopy(objs, 0, result, 0, objs.length);
        return result;
    }

    public AID[] getResolversArray() {
        Object[] objs = resolvers.toArray();
        AID[] result = new AID[objs.length];
        System.arraycopy(objs, 0, result, 0, objs.length);
        return result;
    }

    public String toString() {
        StringBuffer s = new StringBuffer("( agent-identifier ");
        if ((name != null) && (name.length() > 0)) {
            s.append(" :name ");
            s.append(name);
        }
        if (addresses.size() > 0)
            s.append(" :addresses (sequence ");
        for (int i = 0; i < addresses.size(); i++)
            try {
                s.append((String) addresses.get(i));
                s.append(" ");
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        if (addresses.size() > 0)
            s.append(")");
        if (resolvers.size() > 0)
            s.append(" :resolvers (sequence ");
        for (int i = 0; i < resolvers.size(); i++) {
            try {
                s.append(resolvers.get(i).toString());
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
            s.append(" ");
        }
        if (resolvers.size() > 0)
            s.append(")");
        s.append(")");
        return s.toString();
    }

    public int compareTo(Object o) {
        AID id = (AID) o;
        return name.toLowerCase().toUpperCase().compareTo(id.name.toLowerCase().toUpperCase());
    }

    public String getLocalName() {
        int atPos = name.lastIndexOf('@');
        if (atPos == -1)
            return name;
        else
            return name.substring(0, atPos);
    }

    public void setLocalName(String n) {
        name = n.trim();
        if ((name != null) && (!name.toLowerCase().endsWith(atHAP.toLowerCase())))
            name = name.concat(atHAP);
    }

    String getHap() {
        int atPos = name.lastIndexOf('@');
        if (atPos == -1)
            return name;
        else
            return name.substring(atPos + 1);
    }

}

