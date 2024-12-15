package lt.shopenz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import lt.shopenz.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>
{
}
