package sg.edu.nus.iss.Workshop14.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.Workshop14.model.Contact;

@Service
public class ContactsRedis implements ContactsRepo {
    private static final Logger logger = LoggerFactory.getLogger(ContactsRedis.class);

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Override
    public void save(final Contact ctc) {
        redisTemplate.opsForHash().put(ctc.getId(), "name", ctc.getName());
        redisTemplate.opsForHash().put(ctc.getId(), "email", ctc.getEmail());
        redisTemplate.opsForHash().put(ctc.getId(), "phoneNumber", ctc.getPhoneNumber());
    }

    @Override
    public Contact findById(final String contactId) {
        String name = (String) redisTemplate.opsForHash().get(contactId, "name");
        String email = (String) redisTemplate.opsForHash().get(contactId, "email");
        Integer phoneNumber = (Integer) redisTemplate.opsForHash().get(contactId, "phoneNumber");
        logger.info(">>> name " + name);
        logger.info(">>> email " + email);
        logger.info(">>> phoneNumber " + phoneNumber);

        Contact ct = new Contact();
        ct.setId(contactId);
        ct.setName(name);
        ct.setEmail(email);
        ct.setPhoneNumber(phoneNumber.intValue());

        return ct;
    }
}
