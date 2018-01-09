package com.github.rsaestrela.prometheus

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@SpringBootApplication
@CrossOrigin
class SocialQuestionnaireApp

fun main(args: Array<String>) {
    SpringApplication.run(SocialQuestionnaireApp::class.java, *args)
}

data class QuestionnaireRequest(var useSocialNetworkSites: Boolean,
                                var socialSitesMemberOf: ArrayList<SocialSites>,
                                var manyHoursSpendSocialSitesAWeek: Int,
                                var informationIncludedSocialSites: ArrayList<String>)


enum class SocialSites {
    FACEBOOK,
    INSTAGRAM,
    HI5,
    LINKEDIN,
    PINTEREST,
}

enum class InformationIncludedSocialSites {
    EMAIL_ADDRESS,
    HOME_TOWN_CITY,
    IM_ADDRESS,
    PHONE_NUMBER,
    PHOTOS_OTHERS,
    PHOTOS_YOURSELF,
    POLITICAL_VIEW,
    REAL_NAME,
    RELATIONSHIP_STATUS,
    SEXUAL_ORIENTATION
}

enum class ResponseCode(private val response: String) {

    SUCCESS("success"),
    BAD_REQUEST("invalid request");

    override fun toString(): String {
        return response
    }

}

@Controller
@RequestMapping("/")
class SocialQuestionnaireController(@Autowired val service: SocialQuestionnaireService) {

    @RequestMapping(method = [(RequestMethod.POST)])
    fun indexPost(@RequestBody request: QuestionnaireRequest): ResponseEntity<ResponseCode> {

        val validationResult = service.validRequest(request)
        validationResult.first.not().apply {
            return ResponseEntity.badRequest().body(validationResult.second)
        }

        return ResponseEntity.ok(validationResult.second)
    }
}

@Service
class SocialQuestionnaireService {

    fun validRequest(request: QuestionnaireRequest): Pair<Boolean, ResponseCode> {
        return when (request.useSocialNetworkSites &&
                (request.socialSitesMemberOf.isEmpty() ||
                        request.manyHoursSpendSocialSitesAWeek == 0 ||
                        request.informationIncludedSocialSites.isEmpty())) {
            true -> Pair(false, ResponseCode.BAD_REQUEST)
            false -> Pair(true, ResponseCode.SUCCESS)
        }
    }

}

@Service
class PrometheusMetrics {


}
