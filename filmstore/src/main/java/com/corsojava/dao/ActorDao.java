package com.corsojava.dao;

import java.util.List;

import com.corsojava.model.Actor;
import com.corsojava.model.Film;

public interface ActorDao {

	Actor getActor(int id);

	List<Actor> getAllActors();

	int addActor(Actor actor);

	int updateActor(int id, Actor actor);

	int deleteActor(int id);

	List<Actor> findActors(Actor actor);

	List<Actor> findFilmActors(int filmId);

}
