package com.liukedun.freexx.web.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.net.Inet4Address;

/**
 * @author Covey Liu, covey@liukedun.com
 * Date: 10/2/2019 5:06 PM
 */
@Entity
@Data
public class AssignedPort {
    @Id
    Integer port;

    /** A user can be assigned with multiple ports */
    Integer userId;
}
