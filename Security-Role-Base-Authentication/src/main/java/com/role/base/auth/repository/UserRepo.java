package com.role.base.auth.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.role.base.auth.entity.User;

//import org.springframework.stereotype.Repository;


public interface UserRepo extends CrudRepository<User,Integer>{
	
   @Query("Select u from User u where u.userName= :userName")
	public User getUserByUserName(@Param("userName") String userName );
   
   @Query("UPDATE User u Set u.failedAttemp=?1 where u.userName=?2")
   @Modifying
   public void updateFailedAttemp(Integer failedAttemp, String userName);
}
