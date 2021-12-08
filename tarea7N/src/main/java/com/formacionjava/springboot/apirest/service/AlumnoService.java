package com.formacionjava.springboot.apirest.service;

import java.util.List;

import com.formacionjava.springboot.apirest.entity.Alumno;

public interface AlumnoService {

	public List<Alumno> findAll();

	public Alumno findById(Long id);

	public Alumno save(Alumno alumno);

	public void delete(Long id);

}
