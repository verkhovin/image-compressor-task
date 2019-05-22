package ru.verkhovin.imagecompressor.configuration

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class WebServerConfiguration {
    @Bean
    fun webServerFactory(gracefulShutdownConfigurer: GracefulShutdownConfigurer): ConfigurableServletWebServerFactory {
        val factory = TomcatServletWebServerFactory()
        factory.addConnectorCustomizers(gracefulShutdownConfigurer)
        return factory
    }
}
