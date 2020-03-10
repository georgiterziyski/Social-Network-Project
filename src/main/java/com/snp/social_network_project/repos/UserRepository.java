package com.snp.social_network_project.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.snp.social_network_project.models.User;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	User findByEmailAndPassword(final String email, final String password);

	User findByEmail(String email);
}
