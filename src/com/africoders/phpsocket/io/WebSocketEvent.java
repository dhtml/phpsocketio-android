package com.africoders.phpsocket.io;

import java.io.Serializable;

/**
 * Created by Dr. Anthony Ogundipe on 1/10/2016.
 */
public class WebSocketEvent implements Serializable {
    public String ename;
    public SocketEvent ehandle;

    public WebSocketEvent(String ename, SocketEvent ehandle) {
        super();
        this.ename= ename;
        this.ehandle= ehandle;
    }

}
