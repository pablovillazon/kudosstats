package jkin.be.kudosstats.queue;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jkin.be.kudosstats.model.User;
import jkin.be.kudosstats.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class Subscriber {

    /*
    @Autowired
    KudosController kudosController;
    */
    @Autowired
    private UserRepository userRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(Subscriber.class);

    @RabbitListener(queues = "kudos-queue")
    public void onMessageFromRabbitMQ(final String messageFromRabbitMQ)
    {
        LOGGER.info("{}", messageFromRabbitMQ);

        //Handle Message from Rabbit

        //kudosController.KudosHandler(messageFromRabbitMQ);
        LOGGER.info("{}", messageFromRabbitMQ);
        JsonObject jsonObject = new JsonParser().parse(messageFromRabbitMQ).getAsJsonObject();

        String kudosDestino = jsonObject.get("destino").getAsString();
        String kudosTema = jsonObject.get("tema").getAsString();
        String kudosFuente = jsonObject.get("fuente").getAsString();

        LOGGER.info("Kudos Received: " + kudosDestino + " | " + kudosFuente + " | " + kudosTema);

        //Get User by destination from DB
        try {
            User user = userRepository.findByNickname(kudosDestino);

            if (user != null) {
                Long kudosQty = user.getTotalKudos();
                LOGGER.info("#Kudos Usuario previous: " + user.getTotalKudos() + " User:" + kudosDestino);
                user.setTotalKudos(kudosQty + 1);

                userRepository.save(user);

                LOGGER.info("#Kudos Usuario Updated to: " + user.getTotalKudos());
            }
        }
        catch(Exception ex)
        {
            LOGGER.error(ex.getMessage());
        }
    }
}
