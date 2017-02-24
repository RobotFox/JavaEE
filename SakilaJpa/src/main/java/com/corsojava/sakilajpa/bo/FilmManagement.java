/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.corsojava.sakilajpa.bo;

import com.corsojava.sakilajpa.dao.FilmDao;
import com.corsojava.sakilajpa.model.Film;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author <a href="mailto:lucio.benfante@gmail.com">Lucio Benfante</a>
 */
@Service
public class FilmManagement {
    
    @Resource
    private FilmDao filmDao;

    @Transactional(readOnly = true)
    public void printAllFilms() {
        List<Film> films = filmDao.getAllFilms();
        for (Film film : films) {
            System.out.println(film.getTitle());
        }
    }
    
    @Transactional(readOnly = true)
    public List<Film> findFilmByTitle(String title) {
        return filmDao.findFilmByTitle(title);
    }
}
