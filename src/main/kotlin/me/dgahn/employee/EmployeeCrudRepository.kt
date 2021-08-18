package me.dgahn.employee

import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface EmployeeCrudRepository : CoroutineCrudRepository<Employee, String> {

    suspend fun findByName(name: String): Employee

    fun findAllByNameStartsWith(name: String): Flow<Employee>

}
