package com.denizbyrk.movietheater.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String date, seance, movieName, userName;
	
	@Column(nullable = false)
	private Long saloon, seat, userID;
	
	@JsonBackReference
	@ManyToOne
	private User user;
	
	@JsonBackReference
	@ManyToOne
	private Movie movie;
	
	public Ticket() { }
	
	public Ticket(String date, String seance, String movieName, String username, Long seat, Long saloon, Long userID, User user, Movie movie) {

		this.date = date;
		this.seance = seance;
		this.movieName = movieName;
		this.userName = username;
		this.seat = seat;
		this.saloon = saloon;
		this.userID = userID;
		this.user = user;
		this.movie = movie;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getSeance() {
		return seance;
	}

	public void setSeance(String seance) {
		this.seance = seance;
	}

	public Long getSeat() {
		return seat;
	}

	public void setSeat(Long seat) {
		this.seat = seat;
	}
	
	public Long getSaloon() {
		return saloon;
	}

	public void setSaloon(Long saloon) {
		this.saloon = saloon;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}	
}