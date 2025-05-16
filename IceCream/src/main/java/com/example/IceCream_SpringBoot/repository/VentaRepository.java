package com.example.IceCream_SpringBoot.repository;

import com.example.IceCream_SpringBoot.model.VentaDocument;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VentaRepository extends MongoRepository<VentaDocument, String> {
    List<VentaDocument> findByMetodoPago(String metodoPago);
}
