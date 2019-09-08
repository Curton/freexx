package com.liukedun.freexx.docker.service;

import com.liukedun.freexx.docker.DockerClientList;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

/**
 * @author CoveyLiu, covey@liukedun.com
 * @date 2019-09-07 23:33
 */

@Log4j2
@Service
public class CreateContainerService {
    private DockerClientList dockerClientList;


    public boolean CreateDefautClient() {
        return false;
    }

    public CreateContainerService(DockerClientList dockerClientList) {
        this.dockerClientList = dockerClientList;
    }
}
