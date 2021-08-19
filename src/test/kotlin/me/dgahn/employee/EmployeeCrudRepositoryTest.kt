package me.dgahn.employee

import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.toList
import me.dgahn.R2bcConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [EmployeeCrudRepository::class, R2bcConfiguration::class])
class EmployeeCrudRepositoryTest(employeeRepo: EmployeeCrudRepository) : FunSpec({

    extension(SpringExtension)

    test("직원을 저장할 수 있다.") {
        val employee = Employee(name = "test")
        val saved = employeeRepo.save(employee)

        saved.name shouldBe employee.name
    }

    test("직원을 조회할 수 있다.") {
        val employee = Employee(name = "test")
        val saved = employeeRepo.save(employee)

        val find = employeeRepo.findById(saved.id!!)
        find shouldBe saved
    }

    test("직원을 업데이트할 수 있다.") {
        val employee = Employee(name = "test")
        val saved = employeeRepo.save(employee)

        val find1 = employeeRepo.findById(saved.id!!)
        val newName = "test1"
        employeeRepo.save(find1!!.copy(name = newName))
        val find2 = employeeRepo.findById(saved.id!!)

        find2!!.name shouldBe newName
        find1 shouldNotBe find2
    }

    test("직원을 이름으로 조회할 수 있다.") {
        val employee = Employee(name = "test")
        val saved = employeeRepo.save(employee)

        val find = employeeRepo.findByName(employee.name)
        find shouldBe saved
    }

    test("직원 목록을 조회할 수 있다.") {
        employeeRepo.saveAll((1..1000).map { Employee(name = "test_$it") }).collect()

        val find = employeeRepo.findAll()
        find.collectIndexed { index, value ->
            println(value.name)
            value.name shouldBe "test_${index + 1}"
        }
    }

    test("test로 시작하는 직원 목록을 조회할 수 있다.") {
        val name = "test"
        employeeRepo.saveAll((1..100).map { Employee(name = "test_$it") }).collect()

        val find = employeeRepo.findAllByNameStartsWith(name)
        find.toList() shouldHaveSize 100
        find.collect { employee -> employee.name.startsWith("test") }
    }

    test("test로 저장하면서 다른 일을 할 수 있다.") {
        employeeRepo.saveAll((1..100).map { Employee(name = "test_$it") })
            .collect { println(it.name) }

        val find = employeeRepo.findAll()
        find.toList() shouldHaveSize 100
    }

})