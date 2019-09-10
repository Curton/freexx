package com.liukedun.freexx.docker.service;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author CoveyLiu, covey@liukedun.com
 * @date 2019-09-08 17:21
 */

public class ContainerConfigService {
    @Value("cpus")
    private String cpus;
    private int cpuShares;
    private long memory;



}
