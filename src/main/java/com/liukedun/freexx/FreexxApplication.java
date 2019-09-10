package com.liukedun.freexx;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DockerClientBuilder;
import com.liukedun.freexx.docker.model.DockerClientList;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author Covey Liu
 */

@SpringBootApplication
@Log4j2
public class FreexxApplication {

    public static void main(String[] args) {
        SpringApplication.run(FreexxApplication.class, args);
    }

    /**
     * docker client
     */
    @Bean
    public static DockerClientList dockerClients() {
        DockerClientList dockerClients = new DockerClientList();
        // get docker client instance
        DockerClient dockerClient = DockerClientBuilder.getInstance("tcp://hk.liukedun.com:2375").build();
        dockerClients.add("hk.liukedun", dockerClient);
        return dockerClients;
    }

    @Bean
    public static CommandLineRunner commandLineRunner(DockerClientList dockerClients) {
        return args -> {
            /*for (DockerClient dockerClient : dockerClients) {
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
                    String email = "liukedun@qq.com";
                    String dockerName = "ss_client_" + email.replace('@', '-').replace('.', '-');

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
                            .withMemory((long) 1024 * 1024 * 128);
                    List<PortBinding> portBindingList = new ArrayList<>(2);
                    portBindingList.add(new PortBinding(new Ports.Binding("", ""), new ExposedPort(8388, InternetProtocol.TCP)));
                    portBindingList.add(new PortBinding(new Ports.Binding("", ""), new ExposedPort(8388, InternetProtocol.UDP)));
                    hostConfig.withPortBindings(portBindingList);

                    // Create container
                    CreateContainerResponse container = dockerClient
                            .createContainerCmd(targetImageId)
                            .withName(dockerName)
                            .withEnv("PASSWORD=cstprivatess", "METHOD=rc4-md5")
                            .withHostConfig(hostConfig)
                            .withExposedPorts(ExposedPort.tcp(8388), ExposedPort.udp(8388))
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

                } catch (Exception e) {
                    log.warn(e.getMessage());
                }

            }*/
        };
    }
}














