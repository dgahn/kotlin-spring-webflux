package me.dgahn.employee

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/employees")
class EmployeeController(private val employeeRepo: EmployeeRepository) {

    @GetMapping("/{id}")
    private fun getEmployeeById(@PathVariable id: String) = Mono.just(employeeRepo.findById(id))

}