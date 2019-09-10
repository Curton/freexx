package com.liukedun.freexx.docker.model;

import com.liukedun.freexx.exceptions.InvalidArgumentException;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author CoveyLiu, covey@liukedun.com
 * @date 2019-09-08 01:44
 */

public class PortPool implements Serializable {

    private static final int MIN_PORT_NUMBER = 1000;

    private static final int MAX_PORT_NUMBER = 32768;

    private int startPort = 10000;

    private int endPort = 30000;

    private int currentPointer;

    private int remain;

    private Map<Integer,Boolean> portMap = new LinkedHashMap<>();

    public PortPool() {
        this.currentPointer = startPort;
        this.remain = endPort - startPort + 1;
    }

    public PortPool(int startPort, int endPort) throws InvalidArgumentException {
        if (startPort < MIN_PORT_NUMBER || endPort > MAX_PORT_NUMBER || startPort >= endPort) {
            throw new InvalidArgumentException();
        }
        this.startPort = startPort;
        this.endPort = endPort;
        this.currentPointer = startPort;
        this.remain = endPort - startPort + 1;
    }


    public int getStartPort() {
        return startPort;
    }

    public void setStartPort(int startPort) {
        this.startPort = startPort;
    }

    public int getEndPort() {
        return endPort;
    }

    public void setEndPort(int endPort) {
        this.endPort = endPort;
    }

    public static class Port {

        private boolean isUesd = false;

        private int portNumber;
    }

}
