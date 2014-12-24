package com.worldline.easycukes.example.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.worldline.easycukes.example.data.Person;
import com.worldline.easycukes.example.persistence.PersistenceManager;

@Path("/person")
public class PersonService {

	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Person> list() throws Exception {
		return new PersistenceManager().query("select * from person");

	}

	@POST
	@Path("/new")
	@Consumes(MediaType.APPLICATION_JSON)
	public void add(Person person) throws Exception {
		System.out.println("Adding person " + person.toString());
		new PersistenceManager()
				.update("INSERT INTO person(CODE, NAME, title, nationality) VALUES("
						+ person.getCode()
						+ ", '"
						+ person.getName()
						+ "', "
						+ person.getTitle().trim()
						+ ", "
						+ person.getNationality().trim() + ")");

	}

	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	public Person get(@QueryParam("code") String code) throws Exception {
		System.out.println("Getting person by code = " + code);
		return new PersistenceManager().query(
				"select * from person where code = " + code).get(0);

	}
}
