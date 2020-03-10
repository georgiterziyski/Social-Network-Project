package com.snp.social_network_project.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.snp.social_network_project.models.Favourite;
import com.snp.social_network_project.models.User;


@Repository
public interface FavouriteRepository extends JpaRepository<Favourite, Integer> {

	public List<Favourite> findByOwner(final User owner);
}
