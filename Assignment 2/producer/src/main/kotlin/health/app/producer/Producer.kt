package health.app.producer

import health.app.model.Activity
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.AmqpTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class Producer {

    companion object {
        val LOGGER = LoggerFactory.getLogger(Producer.javaClass)
    }

    @Autowired
    private val amqpTemplate: AmqpTemplate? = null

    @Value("\${jsa.rabbitmq.exchange}")
    private lateinit var exchange: String

    @Value("\${jsa.rabbitmq.routingkey}")
    private lateinit var routingkey: String

    fun produce(activity: Activity) {
        amqpTemplate!!.convertAndSend(exchange, routingkey, activity)
        LOGGER.info("Send msg = {}", activity)
    }
}