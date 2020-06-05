package com.snp.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.snp.models.Favourite;
import com.snp.models.User;


@Repository
public interface FavouriteRepository extends JpaRepository<Favourite, Integer> {

	public List<Favourite> findByOwner(final User owner);
}
