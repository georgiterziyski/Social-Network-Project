package com.snp.social_network_project.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.snp.social_network_project.models.Post;
import com.snp.social_network_project.models.User;


@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

	public List<Post> findByOwner(final User owner);
}
