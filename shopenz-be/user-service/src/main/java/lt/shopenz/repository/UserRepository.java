package lt.shopenz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import lt.shopenz.model.User;

public interface UserRepository extends JpaRepository<User, Long>
{
}
