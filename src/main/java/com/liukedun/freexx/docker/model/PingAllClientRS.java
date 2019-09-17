package com.liukedun.freexx.docker.model;

import lombok.Data;

/**
 * @author Covey Liu, covey@liukedun.com
 * Date: 9/16/2019 5:34 PM
 */
@Data
public class PingAllClientRS {

    String clientName;

    String ipAddress;

    double latencyMillisecond;

}
