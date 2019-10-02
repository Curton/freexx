package com.liukedun.freexx.web.repository;

import com.liukedun.freexx.web.entity.AssignedPort;
import org.springframework.data.repository.CrudRepository;

import java.net.Inet4Address;

/**
 * @author Covey Liu, covey@liukedun.com
 * Date: 10/2/2019 5:12 PM
 */
public interface AssignedPortRepository extends CrudRepository<AssignedPort, Integer> {

}
