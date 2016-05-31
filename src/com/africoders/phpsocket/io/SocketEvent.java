package com.africoders.phpsocket.io;

/**
 * Created by Dr. Anthony Ogundipe on 1/10/2016.
 */
public interface SocketEvent extends Runnable {
    public void run(String params, String sender);
}