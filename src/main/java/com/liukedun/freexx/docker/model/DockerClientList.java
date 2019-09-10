package com.liukedun.freexx.docker.model;

import com.github.dockerjava.api.DockerClient;
import lombok.Data;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author CoveyLiu, covey@liukedun.com
 * @date 2019-09-02 21:02
 */

@Data
public class DockerClientList implements Iterable<DockerClient> {

    Map<String, DockerClient> dockerClients = new LinkedHashMap<>();

    public void add(String dockerName, DockerClient dockerClient) {
        this.dockerClients.put(dockerName, dockerClient);
    }

    public void remove(String dockerName) {
        this.dockerClients.remove(dockerName);
    }

    @Override
    public Iterator<DockerClient> iterator() {
        return this.dockerClients.values().iterator();
    }

    @Override
    public void forEach(Consumer<? super DockerClient> action) {
        this.dockerClients.values().forEach(action);
    }
}
