package com.dietrich.psiuapikt

import com.dietrich.psiuapikt.controller.org.req.OrgUpdateRequest
import com.dietrich.psiuapikt.model.org.Org
import com.dietrich.psiuapikt.service.util.MergeService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class PsiuApiKtApplicationTests(
    @Autowired val mergeService: MergeService
) {

    @Test
    fun contextLoads() {
    }

    @Test
    fun test() {
        val org = Org(
            "name",
            "description"
        )

        val req = OrgUpdateRequest(
            "name updated",
            "description updated"
        )

        mergeService.merge(req, org)

        assertEquals("name updated", org.name)
        assertEquals("description updated", org.description)
    }
}
