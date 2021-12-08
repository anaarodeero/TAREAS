package com.formacionjava.springboot.apirest.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.formacionjava.springboot.apirest.entity.Usuario;

public interface UsuarioDao extends CrudRepository<Usuario, Long> {

	public Usuario findByUsername(String username);

	@Query("Select usr from Usuario usr where usr.username=?1")
	public Usuario findByUsername2(String username);
}