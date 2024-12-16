package lt.shopenz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lt.shopenz.model.User;
import lt.shopenz.repository.UserRepository;

@Service
public class UserService
{
    @Value("${kafka.topic.user-created}")
    private String userCreatedTopic;

    @Value("${kafka.topic.user-deleted}")
    private String userDeletedTopic;

    private final KafkaTemplate<String, Long> kafkaTemplate;

    private final UserRepository userRepository;

    public UserService(KafkaTemplate<String, Long> pTemplate, UserRepository pRepository)
    {
        kafkaTemplate = pTemplate;
        userRepository = pRepository;
    }

    public List<User> getAllUsers()
    {
        return userRepository.findAll();
    }

    @Transactional
    public User createUser(User user)
    {
        User createdUser = userRepository.save(user);

        kafkaTemplate.send(userCreatedTopic, "1", createdUser.getId());

        return createdUser;
    }

    @Transactional
    public void deleteUserById(long userId)
    {
        userRepository.deleteById(userId);

        kafkaTemplate.send(userDeletedTopic, "1", userId);
    }
}
