package health.app.config


import org.springframework.amqp.core.AmqpTemplate
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.DirectExchange
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitMqConfig {

    @Value("\${jsa.rabbitmq.queue}")
    internal lateinit var queueName: String

    @Value("\${jsa.rabbitmq.exchange}")
    internal lateinit var exchange: String

    @Value("\${jsa.rabbitmq.routingkey}")
    private lateinit var routingkey: String

    @Bean
    internal fun queue(): Queue {
        return Queue(queueName, false)
    }

    @Bean
    internal fun exchange(): DirectExchange {
        return DirectExchange(exchange)
    }

    @Bean
    internal fun binding(queue: Queue, exchange: DirectExchange): Binding {
        return BindingBuilder.bind(queue).to(exchange).with(routingkey)
    }

    @Bean
    fun jsonMessageConverter(): MessageConverter {
        return Jackson2JsonMessageConverter()
    }

    fun rabbitTemplate(connectionFactory: ConnectionFactory): AmqpTemplate {
        val rabbitTemplate = RabbitTemplate(connectionFactory)
        rabbitTemplate.messageConverter = jsonMessageConverter()
        return rabbitTemplate
    }
}