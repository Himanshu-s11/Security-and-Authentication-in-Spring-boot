package com.role.base.auth.userTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.role.base.auth.entity.User;
import com.role.base.auth.repository.UserRepo;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserTest {

	@Autowired
	private UserRepo repo;

	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void testUpdateFailedAttemp() {
		String userName = "UserMe@gmail.com";
		Integer failedAttemp = 2;
		Integer id = 2;

		repo.updateFailedAttemp(failedAttemp, userName);
		User user = entityManager.find(User.class, id);
		assertEquals(failedAttemp,user.getFailedAttemp());
	}
}
