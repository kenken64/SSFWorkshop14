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
        // redisTemplate.opsForValue().set(ctc.getId(), ctc);
        redisTemplate.opsForList().rightPush(ctc.getId(), ctc.getName());
        redisTemplate.opsForList().rightPush(ctc.getId(), ctc.getEmail());
        redisTemplate.opsForList().rightPush(ctc.getId(), ctc.getPhoneNumber());
    }

    @Override
    public Contact findById(final String contactId) {
        // Contact result = (Contact) redisTemplate.opsForValue().get(contactId);
        String name = (String) redisTemplate.opsForList().index(contactId, 0);
        String email = (String) redisTemplate.opsForList().index(contactId, 1);
        String phoneNumber = (String) redisTemplate.opsForList().index(contactId, 2);
        logger.info(">>> name " + name);
        logger.info(">>> email " + email);
        logger.info(">>> phoneNumber " + phoneNumber);
        

        Contact ct = new Contact();
        ct.setId(contactId);
        ct.setName(name);
        ct.setEmail(email);
        ct.setPhoneNumber(Integer.parseInt(phoneNumber));

        return ct;
    }
}
