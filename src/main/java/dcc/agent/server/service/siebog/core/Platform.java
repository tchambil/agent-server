package dcc.agent.server.service.siebog.core;

/**
 * Created by teo on 30/04/15.
 */

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Platform {
   public boolean Debug, Kiwi, Verbose;
    private Dispatcher dispatcher;
    private String idPlatform;
    private AMS ams;

    public Platform(boolean Debug, boolean Kiwi, boolean Verbose) {
        this.Debug = Debug;
        this.Kiwi = Kiwi;
        this.Verbose = Verbose;
        ams = new AMS(Debug, Verbose);
        dispatcher = new Dispatcher(Debug, Verbose);

        try {
            InetAddress addr = InetAddress.getLocalHost();
           byte[] ipAddr = addr.getAddress();
            this.idPlatform = addr.getHostName();
        } catch (UnknownHostException e) {
            System.out.println("no se ha obtenido la direccion de la maquina");
        }

    }

    public Dispatcher getDispatcher() {
        return dispatcher;
    }

    public AMS getAMS() {
        return ams;
    }

    public String getidPlatform() {
        return idPlatform;
    }

}

