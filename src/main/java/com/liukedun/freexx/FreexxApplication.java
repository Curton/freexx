package com.liukedun.freexx;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.*;
import com.github.dockerjava.core.DockerClientBuilder;
import com.liukedun.freexx.docker.DockerClientList;
import com.liukedun.freexx.exceptions.CannotGetImageIdException;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Random;

@SpringBootApplication
@Log4j2
public class FreexxApplication {

    public static void main(String[] args) {
        SpringApplication.run(FreexxApplication.class, args);
    }

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
            for (DockerClient dockerClient : dockerClients) {
                try {
                    Info exec = dockerClient.infoCmd().exec();

                    String targetImageId = null;
                    List<Image> imageList = dockerClient.listImagesCmd().withShowAll(true).exec();

                    for (Image image : imageList) {
                        boolean doFlag = false;
                        for (String string : image.getRepoTags()) {
                            if(string.contains("shadowsocks/shadowsocks-libev")) {
                                doFlag = true;
                                break;
                            }
                        }
                        if(doFlag) {
                            targetImageId = image.getId();
                            break;
                        }
                    }

                    // cannot find targetImageId
                    if(targetImageId == null) {
                        throw new CannotGetImageIdException();
                    }

                    log.error("test to start a container");

                    // always restart
                    RestartPolicy restartPolicy = RestartPolicy.alwaysRestart();
                    // HostConfig
                    HostConfig hostConfig = new HostConfig().withRestartPolicy(restartPolicy).withCpuShares(256);
                    hostConfig.withPortBindings(new PortBinding(new Ports.Binding("", ""), new ExposedPort(8388, InternetProtocol.TCP)));
                    hostConfig.withPortBindings(new PortBinding(new Ports.Binding("", ""), new ExposedPort(8388, InternetProtocol.UDP)));
                    // Create container
                    CreateContainerResponse container = dockerClient
                            .createContainerCmd(targetImageId)
                            .withName("test_ss_cointainer_" + new Random().nextInt(1000))
                            .withEnv("PASSWORD=cstprivatess", "METHOD=rc4-md5")
                            .withHostConfig(hostConfig)
                            .withExposedPorts(ExposedPort.tcp(8388), ExposedPort.udp(8388))
                            .exec();

                    // start docker client
                    dockerClient.startContainerCmd(container.getId()).exec();

                    log.error("test end");

                } catch (Exception e) {
                    log.warn(e.getMessage());
                }

            }
        };
    }

}























