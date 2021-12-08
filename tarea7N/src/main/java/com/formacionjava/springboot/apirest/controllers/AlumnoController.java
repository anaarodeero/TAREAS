package com.formacionjava.springboot.apirest.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.formacionjava.springboot.apirest.entity.Alumno;
import com.formacionjava.springboot.apirest.service.AlumnoService;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.DELETE, RequestMethod.POST,
		RequestMethod.PUT })
public class AlumnoController {

	@Autowired
	private AlumnoService alumnoService;

	// Listar todos los alumnos
	@GetMapping("/alumnos")
	public List<Alumno> mostrarTodos() {
		return alumnoService.findAll();
	}

	// Listar alumonos por ID
	@GetMapping("/alumnos/{id}")
	public ResponseEntity<?> mostrarId(@PathVariable Long id) {
		Alumno alumno = null;
		Map<String, Object> response = new HashMap<>();

		try {
			alumno = alumnoService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (alumno == null) {
			response.put("mensaje", "El alumno ID: ".concat(id.toString().concat(" no existe en la bbdd")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Alumno>(alumno, HttpStatus.OK);
	}

	// Crear alumno
	@PostMapping("/alumnos")
	public ResponseEntity<?> guardar(@RequestBody Alumno alumno) {
		Map<String, Object> response = new HashMap<>();

		try {
			alumnoService.save(alumno);

		} catch (DataAccessException e) {
			response.put("Mensaje", "No se puede guardar el alumno");
			response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	// Actualizar datos de un alumno
	@PutMapping("/alumnos/{id}")
	public ResponseEntity<?> actualizar(@RequestBody Alumno alumno, @PathVariable Long id) {

		Alumno alumnoActual = alumnoService.findById(id);
		Alumno alumnoUpdated = null;

		Map<String, Object> response = new HashMap<>();

		if (alumnoActual == null) {
			response.put("mensaje", "El alumno nº ".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			alumnoActual.setNombre(alumno.getNombre());
			alumnoActual.setApellido(alumno.getApellido());
			alumnoActual.setDni(alumno.getDni());
			alumnoActual.setEmail(alumno.getEmail());
			alumnoActual.setTelefono(alumno.getTelefono());
			alumnoActual.setDireccion(alumno.getDireccion());
			alumnoActual.setCodigoPostal(alumno.getCodigoPostal());

			alumnoUpdated = alumnoService.save(alumnoActual);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta a la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El Alumno ha sido actualizado");
		response.put("alumno", alumnoUpdated);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	// Borrar alumno
	@DeleteMapping("/alumnos/{id}")
	public ResponseEntity<?> borrar(@PathVariable Long id) {

		Map<String, Object> response = new HashMap<>();

		try {
			alumnoService.delete(id);
		} catch (DataAccessException e) {
			response.put("Mensaje", "No se ha podido borrar el alumno");
			response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Map<String, Object>>(HttpStatus.OK);
	}

}
