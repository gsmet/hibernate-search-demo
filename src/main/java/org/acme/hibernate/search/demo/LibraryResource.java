package org.acme.hibernate.search.demo;

import java.util.List;

import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import org.acme.hibernate.search.demo.model.Author;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.hibernate.search.mapper.orm.session.SearchSession;

import io.quarkus.runtime.StartupEvent;

@Path("/library")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LibraryResource {

	@Inject
	SearchSession searchSession;

	@ConfigProperty(name = "org.acme.hibernate.search.force-reindex", defaultValue = "true")
	boolean forceReindex;

	@Transactional
	public void reindex(@Observes StartupEvent event) throws InterruptedException {
		if (forceReindex) {
			searchSession.massIndexer()
					.startAndWait();
		}
	}

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
		return searchSession
				.search(Author.class)
				.where(f -> pattern == null || pattern.isEmpty() ?
						f.matchAll() :
						f.simpleQueryString().fields("firstName", "lastName", "books.title").matching(pattern))
				.sort(s -> s.field("lastName_sort").then().field("firstName_sort"))
				.fetchAllHits();
	}
}
