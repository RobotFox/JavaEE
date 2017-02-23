package com.corsojava.filmstorejpa.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.corsojava.filmstorejpa.model.Actor;

public interface ActorDao {

	List<Actor> listActors();
}
