package com.snp.rest;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.snp.models.Favourite;
import com.snp.models.User;
import com.snp.repos.FavouriteRepository;
import com.snp.repos.UserRepository;

@RestController
public class FavouriteManagerRest {

	private FavouriteRepository favRepo;
	private UserRepository userRepo;
	
	@Autowired
	public FavouriteManagerRest(FavouriteRepository favRepo, UserRepository userRepo) {
		this.favRepo = favRepo;
		this.userRepo = userRepo;
	}
	
	@PostMapping("/removeAllFavourites")
	public ResponseEntity<String> removeAllFavourites(HttpSession session) {
		final List<Favourite> favouritesForRemove = new ArrayList<>();
		final User user = (User) session.getAttribute("currentUser");
		if (null == user) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in!!!");
		}
		favouritesForRemove.addAll(favRepo.findByOwner(user));
		if (favouritesForRemove.isEmpty()) {
			return ResponseEntity.ok().body("Nothing to remove");
		}
		final User owner = userRepo.findByEmailAndPassword(user.getEmail(), user.getPassword());
		owner.getFavourites().removeAll(favouritesForRemove);
		userRepo.saveAndFlush(owner);
		favRepo.deleteAll(favouritesForRemove);
		session.setAttribute("currentUser", owner);
		return ResponseEntity.ok().body("Favourites are removed");
	}
	
	@PostMapping("/removeFavourite")
	public ResponseEntity<String> removeFavourite(@RequestParam(name = "id") int id, HttpSession session) {
		final User user = (User) session.getAttribute("currentUser");
		if (null == user) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("");
		}
		final Favourite favouriteForRemove = favRepo.findById(id).orElse(null);
		if (null != favouriteForRemove) {
			if (!user.equals(favouriteForRemove.getOwner())) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			} else {
				final User owner = userRepo.findByEmailAndPassword(user.getEmail(), user.getPassword());
				owner.removeFavourite(favouriteForRemove);
				userRepo.saveAndFlush(owner);
				favRepo.deleteById(id);
				session.setAttribute("currentUser", owner);
				return ResponseEntity.ok().body("Favourite with id: " + id + " is removed");
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Favourite with id: " + id + " is not found");
		}
	}

	@GetMapping("/getFavourites")
	public ResponseEntity<List<Favourite>> getAllFavourites(HttpSession session) {
		final List<Favourite> favourites = new ArrayList<>();
		final User user = (User) session.getAttribute("currentUser");
		if (null == user) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(favourites);
		} else {
			favourites.addAll(favRepo.findByOwner(user));
		}
		return ResponseEntity.ok(favourites);
	}

	@PostMapping("/addFavourite")
	public ResponseEntity<Favourite> addFavourite(
			@RequestParam(name = "url") String url,
			@RequestParam(name = "title") String title, 
			HttpSession session) {

		final User user = (User) session.getAttribute("currentUser");
		if (null == user) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		final Favourite favourite = new Favourite(url, title);
		favourite.setOwner(user);
		user.addFavourites(favourite);
		session.setAttribute("currentUser", userRepo.save(user));

		return ResponseEntity.ok(favourite);
	}
}
