package com.formacionjava.springboot.apirest.dao;

import org.springframework.data.repository.CrudRepository;

import com.formacionjava.springboot.apirest.entity.Alumno;

public interface AlumnoDao extends CrudRepository<Alumno, Long> {

}
