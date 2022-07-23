package dev.techdozo.graphql.config;

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
          "Query",
          wiring ->
              wiring
                  .dataFetcher("books", environment -> bookCatalogService.getBooks())
                  .dataFetcher(
                      "bookById",
                      env -> bookCatalogService.bookById(Integer.parseInt(env.getArgument("id")))));
      builder.type(
          "Book",
          wiring ->
              wiring.dataFetcher("ratings", env -> bookCatalogService.ratings(env.getSource())));
    };
  }
}
