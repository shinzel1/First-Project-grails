package com.twentyonecceducation

import com.twentyonecceducation.security.SecRole
import com.twentyonecceducation.security.SecUser
import grails.plugin.springsecurity.annotation.Secured

@Secured([SecRole.ROLE_USER])
class HomeController {

    def springSecurityService

    def index() {
        SecUser user = springSecurityService.currentUser

        [user: user]
    }
}
