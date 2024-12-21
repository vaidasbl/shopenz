package lt.shopenz.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lt.shopenz.dto.UserRegisterDto;
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

    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(KafkaTemplate<String, Long> pTemplate, UserRepository pRepository, final BCryptPasswordEncoder pEncoder)
    {
        kafkaTemplate = pTemplate;
        userRepository = pRepository;
        passwordEncoder = pEncoder;
    }

    @Transactional
    public void registerUser(UserRegisterDto userRegisterDto)
    {
        if (!userRegisterDto.isPasswordsMatching())
        {
            throw new IllegalStateException("Passwords do not match");
        }

        if (getUserByEmail(userRegisterDto.getEmail()).isPresent())
        {
            throw new IllegalStateException("Email already exists");
        }

        String hashedPassword = passwordEncoder.encode(userRegisterDto.getPassword());

        User user = new User(userRegisterDto.getEmail(), hashedPassword);

        User createdUser = userRepository.save(user);

        kafkaTemplate.send(userCreatedTopic, "1", createdUser.getId());
    }

    @Transactional
    public void deleteUserById(long userId)
    {
        userRepository.deleteById(userId);

        kafkaTemplate.send(userDeletedTopic, "1", userId);
    }

    public Optional<User> getUserByEmail(String email)
    {
        return userRepository.findByEmail(email);
    }

}
