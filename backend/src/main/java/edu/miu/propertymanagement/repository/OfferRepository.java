package edu.miu.propertymanagement.repository;

import edu.miu.propertymanagement.entity.Offer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends CrudRepository<Offer, Long> {

    List<Offer> findByCustomerId(long id);
    List<Offer> findAll();
}
