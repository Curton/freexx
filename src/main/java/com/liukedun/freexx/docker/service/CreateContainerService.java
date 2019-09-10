package com.liukedun.freexx.docker.service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.model.*;
import com.liukedun.freexx.docker.DockerClientList;
import com.liukedun.freexx.exceptions.RequiredImageNotFoundException;
import com.liukedun.freexx.exceptions.StartNewClientFailedException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CoveyLiu, covey@liukedun.com
 * @date 2019-09-07 23:33
 */

@Log4j2
@Service
public class CreateContainerService {

    private static final int SS_CONTAINER_EXPOSE_PORT = 8388;

    /**
     * default container memory : 128MB
     */
    private static final long DEFAULT_CONTAINER_MEMORY = 1024 * 1024 * 128;

    private DockerClientList dockerClients;

    private String dockerNamePrefix;

    public CreateContainerService(DockerClientList dockerClientList) {
        this.dockerClients = dockerClientList;
    }

    private static String getContainerIdByName(DockerClient dockerClient, String dockerName) {
        List<Container> containers = dockerClient.listContainersCmd().withShowAll(true).exec();
        boolean clientExist = false;
        for (Container container : containers) {
            for (String s : container.getNames()) {
                if (s.substring(1).equals(dockerName)) {
                    clientExist = true;
                }
            }
            if (clientExist) {
                return container.getId();
            }
        }
        return null;
    }

    public boolean CreateDefautClient() {
        return false;
    }

    public String create(String dockerName, int tcpPort, int udpPort, String password, String method) {
        for (DockerClient dockerClient : dockerClients) {
            try {

                // get image id
                String targetImageId = null;
                List<Image> imageList = dockerClient.listImagesCmd().withShowAll(true).exec();
                boolean doFlag = false;
                for (Image image : imageList) {
                    for (String string : image.getRepoTags()) {
                        if (string.contains("shadowsocks/shadowsocks-libev")) {
                            doFlag = true;
                            break;
                        }
                    }
                    if (doFlag) {
                        targetImageId = image.getId();
                        break;
                    }
                }

                // cannot find targetImageId
                if (targetImageId == null) {
                    throw new RequiredImageNotFoundException();
                }

                // set user name
                // String email = "liukedun@qq.com";
                // String dockerName = "ss_client_" + email.replace('@', '-').replace('.', '-');

                // check if user name exist
                String existClientId = getContainerIdByName(dockerClient, dockerName);

                // stop and remove existing container
                if (existClientId != null) {
                    dockerClient.stopContainerCmd(existClientId).withTimeout(1000);
                    dockerClient.removeContainerCmd(existClientId).withForce(true).exec();
                    log.warn("stop and remove container : " + existClientId + " (" + dockerName + ")");
                }

                // HostConfig
                HostConfig hostConfig = new HostConfig()
                        .withRestartPolicy(RestartPolicy.alwaysRestart())
                        .withCpuShares(256)
                        .withMemory(DEFAULT_CONTAINER_MEMORY);
                List<PortBinding> portBindingList = new ArrayList<>(2);
                portBindingList.add(new PortBinding(new Ports.Binding("", ""), new ExposedPort(SS_CONTAINER_EXPOSE_PORT, InternetProtocol.TCP)));
                portBindingList.add(new PortBinding(new Ports.Binding("", ""), new ExposedPort(SS_CONTAINER_EXPOSE_PORT, InternetProtocol.UDP)));
                hostConfig.withPortBindings(portBindingList);

                // Create container
                CreateContainerResponse container = dockerClient
                        .createContainerCmd(targetImageId)
                        .withName(dockerName)
                        .withEnv("PASSWORD=cstprivatess", "METHOD=rc4-md5")
                        .withHostConfig(hostConfig)
                        .withExposedPorts(ExposedPort.tcp(SS_CONTAINER_EXPOSE_PORT), ExposedPort.udp(SS_CONTAINER_EXPOSE_PORT))
                        .exec();

                // start docker client
                dockerClient.startContainerCmd(container.getId()).exec();

                // get new docker client info
                String newClientId = getContainerIdByName(dockerClient, dockerName);
                log.info("newClientId : " + newClientId);

                // check
                if (newClientId == null) {
                    throw new StartNewClientFailedException();
                }

                // inspect new client
                InspectContainerResponse inspectContainerResponse = dockerClient
                        .inspectContainerCmd(newClientId)
                        .exec();
                return inspectContainerResponse.getId();

            } catch (Exception e) {
                log.warn(e.getMessage());
            }
        }
        return null;
    }
}
