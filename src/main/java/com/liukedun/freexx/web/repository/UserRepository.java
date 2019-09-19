package com.liukedun.freexx.web.repository;

import com.liukedun.freexx.web.entity.User;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Covey Liu, covey@liukedun.com
 * Date: 9/17/2019 5:53 PM
 */
public interface UserRepository extends CrudRepository<User, Integer> {

}
