package org.acme.hibernate.search.demo;

import java.util.List;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.acme.hibernate.search.demo.model.Author;

@Path("/library")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LibraryResource {

	@PUT
	@Path("author")
	@Transactional
	public void addAuthor(Author author) {
		author.persist();
	}

	@GET
	@Path("author/search")
	@Transactional
	public List<Author> searchAuthor(@QueryParam("pattern") String pattern) {
		return Author.listAll();
	}
}
