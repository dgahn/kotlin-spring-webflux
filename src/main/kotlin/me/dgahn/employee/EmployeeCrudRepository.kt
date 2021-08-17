package me.dgahn.employee

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface EmployeeCrudRepository : CoroutineCrudRepository<Employee, String>
