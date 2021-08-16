package me.dgahn.employee

import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.spring.SpringExtension
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.test.web.reactive.server.WebTestClient


@WebFluxTest(EmployeeController::class, EmployeeRepository::class)
class EmployeeControllerTest(private val webTestClient: WebTestClient) : FunSpec({

    extension(SpringExtension)

    test("employee를 식별자로 조회할 수 있다.") {
        webTestClient.get()
            .uri("/employees/test_1")
            .exchange()
            .expectStatus()
            .isOk
    }

})
