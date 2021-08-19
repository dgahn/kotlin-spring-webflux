package me.dgahn.employee

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.flow
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux


@Repository
class EmployeeQueryRepository(private val client: DatabaseClient) {

    suspend fun findById(id: String): Employee? = client
        .sql("SELECT * FROM employee WHERE id = $1")
        .bind(0, id)
        .map { row, _ ->
            Employee(
                id = row["id", String::class.java],
                name = row["name", String::class.java]
            )
        }
        .one()
        .awaitFirstOrNull()

    suspend fun save(employee: Employee) = this.client.sql("INSERT INTO  employee (name) VALUES (:name)")
        .filter { statement, _ -> statement.returnGeneratedValues("id").execute() }
        .bind("name", employee.name)
        .fetch()
        .first()
        .map { r -> r["id"] as String }
        .awaitFirstOrNull()

    fun saveAll(data: List<Employee>): Flow<String> = client.inConnectionMany { connection ->
        val statement =
            connection.createStatement("INSERT INTO  employee (name) VALUES ($1)")
                .returnGeneratedValues("id")
        for (p in data) {
            statement.bind(0, p.name).add()
        }
        Flux.from(statement.execute())
            .flatMap { r -> r.map { row, _ -> row.get("id", String::class.java) } }
    }.asFlow()

    fun findAll() = client
        .sql("SELECT * FROM employee")
        .filter { statement, _ -> statement.fetchSize(10).execute() }
        .map { row, _ ->
            Employee(
                id = row["id", String::class.java],
                name = row["name", String::class.java]
            )
        }
        .flow()

}
