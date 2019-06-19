package org.acme.hibernate.search.demo.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Book extends PanacheEntity {

	public String title;

	@ManyToOne
	@JsonIgnore
	public Author author;
}
