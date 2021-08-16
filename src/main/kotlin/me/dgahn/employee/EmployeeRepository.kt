package me.dgahn.employee

import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
class EmployeeRepository {

    val employeeIdMap = (1..100).associate { "test_$it" to Employee(id = "test_$it", name = "test_$it") }

    fun findById(id: String): Mono<Employee> = try {
        Mono.just(employeeIdMap.getValue(id))
    } catch (e: NoSuchElementException) {
        throw e
    }

}
