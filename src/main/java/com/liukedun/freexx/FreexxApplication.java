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
     * create docker client manually
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
            log.info("Start.");
        };
    }
}














