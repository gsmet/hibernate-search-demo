package org.acme.hibernate.search.demo;

import java.util.List;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

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
