package com.snp.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.snp.models.Post;
import com.snp.models.User;


@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

	public List<Post> findByOwner(final User owner);
}
