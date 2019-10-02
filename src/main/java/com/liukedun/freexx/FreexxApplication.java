package com.liukedun.freexx;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DockerClientBuilder;
import com.liukedun.freexx.docker.model.DockerClientList;
import com.liukedun.freexx.web.entity.AssignedPort;
import com.liukedun.freexx.web.entity.User;
import com.liukedun.freexx.web.repository.AssignedPortRepository;
import com.liukedun.freexx.web.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

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
    public static CommandLineRunner commandLineRunner(UserRepository userRepository, AssignedPortRepository assignedPortRepository) {
        return args -> {
            log.info("Start.");
            User user = new User();
            user.setEmail("liukedun@qq.com");
            user.setName("liukedun");
            user.setPassword("88327598");
            user.setDate(new Date());
            userRepository.save(user);

            AssignedPort assignedPort = new AssignedPort();
            assignedPort.setPort(123);
            assignedPort.setUserId(user.getId());
            assignedPortRepository.save(assignedPort);

            Iterable<User> userIterable = userRepository.findAll();
            userIterable.forEach(System.out::println);
        };
    }
}














