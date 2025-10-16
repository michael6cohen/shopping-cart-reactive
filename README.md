# Shopping Cart Reactive Application

A **Spring Boot Reactive** application implementing a **GraphQL API** for managing a shopping cart stored in a **Neo4j**
database.
The app demonstrates reactive programming using **Spring WebFlux**, **Spring Data Neo4j (Reactive)**, and **Spring
GraphQL**, running Neo4j inside **Docker**.

## Features

- Reactive stack using Spring WebFlux

- **GraphQL API** with:

    - **`addItemToCart`** — Mutation to add or update an item in a cart

    - **`reduceItemInCart`** — Mutation to reduce or remove an item from a cart

    - **`clearCart`** — Mutation to clear all items from a cart

    - **`getCart`** — Query to get all items in a cart

- **Neo4j graph model** with nodes:

    - **`Cart`**

    - **`Item`**

- Relationship `CONTAINS` (`count`, `price`)

- **Reactive Neo4j Repository** for persistence

- **Centralized logging** using SLF4J (`got a new item`, etc.)

- **CORS enabled**

- **GraphiQL** UI for testing queries

## Requirements

- Java 17
- Maven
- Docker & Docker Compose

## Setup

1. Start Neo4j in Docker:
   ```bash
   docker-compose up
   ```

By default, Neo4j runs on:

- Browser: http://localhost:7474

- Bolt: bolt://localhost:7687

- Credentials: neo4j / secret123

2. Run the Spring Boot Application:
    ```bash
    ./mvnw spring-boot:run
     ```

Or build a JAR:
```bash
./mvnw clean package
java -jar target/shopping-cart-reactive-0.0.1-SNAPSHOT.jar
```

The application will start on: http://localhost:8080

## GraphQL API

**Add an item to cart**

```graphql
mutation {
  addItemToCart(cartId: "cart-1", name: "Apple", count: 3, price: 2.5) {
    id
    items {
      name
      count
      price
    }
  }
}
```

**Reduce an item in cart**

```graphql
mutation {
  reduceItemInCart(cartId: "cart-1", name: "Apple", count: 1) {
    id
    items {
      name
      count
      price
    }
  }
}
```

**Clear the cart**

```graphql
mutation {
  clearCart(cartId: "cart-1") {
    id
    items {
      name
    }
  }
}
```

**Get all items in a cart**

```graphql
query {
  getCart(cartId: "cart-1") {
    id
    items {
      name
      count
      price
    }
  }
}
```

## Logging

Each API call is logged through a reactive WebFilter.
Examples:

```pgsql
INFO  Incoming request: POST /graphql
DEBUG GraphQL request body: {
 "query": "query { getCart(cartId: 1235) { id items { name count price } } }"
}
INFO  got a new item: Apple (count=3, price=2.5)
INFO  Completed: POST /graphql -> 200 (35 ms)
```
