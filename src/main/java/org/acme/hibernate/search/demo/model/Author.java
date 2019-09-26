package org.acme.hibernate.search.demo.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.hibernate.search.engine.backend.types.Sortable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
@Indexed
public class Author extends PanacheEntity {

	@FullTextField(analyzer = "standard")
	@KeywordField(name = "firstName_sort", sortable = Sortable.YES)
	public String firstName;

	@FullTextField(analyzer = "standard")
	@KeywordField(name = "lastName_sort", sortable = Sortable.YES)
	public String lastName;

	@OneToMany(mappedBy = "author", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@IndexedEmbedded
	public List<Book> books;
}
