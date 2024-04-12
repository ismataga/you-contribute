package com.example.youcontribute.service;

import com.example.youcontribute.config.GithubProperties;
import com.example.youcontribute.service.models.GithubIssueResponse;


import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;


import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.BDDAssertions.then;

@ContextConfiguration(initializers = GithubClientServiceTests.Initializer.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
@EnableConfigurationProperties(value = GithubProperties.class)
@WireMockTest
class GithubClientServiceTests {

    @Autowired
    private WireMockServer wireMockServer;

    @Autowired
    private GithubClient githubClient;

    @Test
    public void it_should_list_issues() {
        //given
        wireMockServer.stubFor(get(urlPathEqualTo("/repos/octocat/Hello-World/issues"))
                .withQueryParam("since", equalTo("2024-01-01"))
                .withHeader("Authorization", equalTo("token ssshhhhhhh"))
                .willReturn(aResponse().withBodyFile("github/issues.json")
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE).withStatus(200)
                )
        );

        //when
        GithubIssueResponse[] response = this.githubClient.listIssues("octocat", "Hello-World", LocalDate.parse("2024-01-01"));

        //then
        then(response).isNotNull();
        then(response.length).isEqualTo(30);
        GithubIssueResponse githubIssueResponse = response[0];
        then(githubIssueResponse.getTitle()).isEqualTo("Jamey");

    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            WireMockServer wireMockServer = new WireMockServer(new WireMockConfiguration().dynamicPort());
            wireMockServer.start();

            applicationContext.getBeanFactory().registerSingleton("wireMockServer", wireMockServer);

            applicationContext.addApplicationListener(applicationEvent -> {
                if (applicationEvent instanceof ContextClosedEvent) {
                    wireMockServer.stop();
                }
            });
            TestPropertyValues.of("github.api-url=" + wireMockServer.baseUrl(),
                            "github.token=ssshhhhhhh"
                    )
                    .applyTo(applicationContext.getEnvironment());
        }

    }

}
