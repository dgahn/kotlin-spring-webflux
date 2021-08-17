package me.dgahn.employee

import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import me.dgahn.R2bcConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [EmployeeCrudRepository::class, R2bcConfiguration::class])
class EmployeeRepositoryTest(employeeRepo: EmployeeCrudRepository) : FunSpec({

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

})