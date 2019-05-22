package ru.verkhovin.imagecompressor.configuration

import org.apache.catalina.connector.Connector
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextClosedEvent
import org.springframework.stereotype.Component
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

private const val TIMEOUT = 100

@Component
class GracefulShutdownConfigurer : TomcatConnectorCustomizer, ApplicationListener<ContextClosedEvent> {
    @Volatile
    private var connector: Connector? = null

    override fun customize(connector: Connector) {
        this.connector = connector
    }

    override fun onApplicationEvent(event: ContextClosedEvent) {
        connector ?: return
        connector!!.pause()
        val executor = this.connector!!.protocolHandler.executor
        if (executor is ThreadPoolExecutor) {
            try {
                executor.shutdown()
                if (!executor.awaitTermination(TIMEOUT.toLong(), TimeUnit.SECONDS)) {
                    executor.shutdownNow() //force
                }
            } catch (ex: InterruptedException) {
                Thread.currentThread().interrupt()
            }
        }
    }
}
