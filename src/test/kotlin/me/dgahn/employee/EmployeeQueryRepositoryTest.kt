package me.dgahn.employee

import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import me.dgahn.R2bcConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [EmployeeCrudRepository::class, EmployeeQueryRepository::class, R2bcConfiguration::class])
class EmployeeQueryRepositoryTest(
    employeeCrudRepository: EmployeeCrudRepository,
    employeeQueryRepository: EmployeeQueryRepository
) : FunSpec({

    extension(SpringExtension)

    test("직원을 조회할 수 있다.") {
        val employee = Employee(name = "test")
        val saved = employeeCrudRepository.save(employee)
        val find = employeeQueryRepository.findById(saved.id!!)

        find!!.name shouldBe employee.name
    }

    test("직원을 저장할 수 있다.") {
        val employee = Employee(name = "test")
        val saved = employeeQueryRepository.save(employee)
        val find = employeeQueryRepository.findById(saved!!)

        find!!.name shouldBe employee.name
    }

    test("직원을 목록으로 저장할 수 있다.") {
        val saved = employeeQueryRepository.saveAll((1..100).map { Employee(name = "test_$it") })
            .map { it }
            .toList()

        saved shouldHaveSize 100
    }

    test("직원을 목록으로 조회할 수 있다.") {
        employeeQueryRepository.saveAll((1..100).map { Employee(name = "test_$it") }).collect()
        val find = employeeQueryRepository.findAll().toList()

        find shouldHaveSize 100
    }

})