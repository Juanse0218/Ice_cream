package com.example.IceCream_SpringBoot.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.example.IceCream_SpringBoot.model.Cliente;

@Repository
public interface ClienteRepository extends MongoRepository<Cliente, String> {
    boolean existsByCedula(String cedula);
    boolean existsByNombre(String nombre);
    Cliente findByCedula(String cedula);
    Cliente findByNombre(String nombre);
}