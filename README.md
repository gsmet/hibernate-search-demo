# Quarkus + Hibernate Search + Elasticsearch demo

You have several tags for the different steps:

 * `step1` - create the empty Quarkus project
 * `step2` - add the necessary extensions
 * `step3` - implement the model and a few services
 * `step4` - implement the full text search

## Start your PostgreSQL instance

```
docker run --ulimit memlock=-1:-1 -it --rm=true --memory-swappiness=0 --name postgresql_quarkus_test -e POSTGRES_USER=quarkus_test -e POSTGRES_PASSWORD=quarkus_test -e POSTGRES_DB=quarkus_test -p 5432:5432 postgres:11.3
```

## Start your Elasticsearch cluster

```
docker run -it --rm=true --name elasticsearch_quarkus_test -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" docker.elastic.co/elasticsearch/elasticsearch:7.0.1
```
