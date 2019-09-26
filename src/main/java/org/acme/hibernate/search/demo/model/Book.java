package org.acme.hibernate.search.demo.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Book extends PanacheEntity {

	@FullTextField(analyzer = "standard")
	public String title;

	@ManyToOne
	@JsonIgnore
	public Author author;
}
