package dev.techdozo.graphql.controller;

import dev.techdozo.graphql.domain.service.BookCatalogService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Configuration
public class GraphQLConfiguration {

  @Bean
  public RuntimeWiringConfigurer runtimeWiringConfigurer(BookCatalogService bookCatalogService) {

    return builder -> {
      builder.type(
          "Books",
          wiring ->
              wiring.dataFetcher("ratings", env -> bookCatalogService.ratings(env.getSource())));
      builder.type(
          "Query",
          wiring ->
              wiring
                  .dataFetcher(
                      "bookById",
                      environment ->
                          bookCatalogService.bookById(
                              Integer.parseInt(environment.getArgument("id"))))
                  .dataFetcher("books", environment -> bookCatalogService.getBooks()));
    };
  }
}
