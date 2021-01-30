package com.health.application.controller

import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@CrossOrigin(origins = ["*"])
class LoginController {

    @RequestMapping(value = ["/health/login"], method = [RequestMethod.GET])
    @ResponseBody
    fun currentUserName(principal: Principal): Principal {
        return principal
    }
}