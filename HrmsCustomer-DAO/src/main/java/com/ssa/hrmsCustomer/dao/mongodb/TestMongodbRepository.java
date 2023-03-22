package com.ssa.hrmsCustomer.dao.mongodb;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ssa.hrmsCustomer.dto.mongodbentities.TestMongodb;

@Repository
public interface TestMongodbRepository extends MongoRepository<TestMongodb, Integer> {

}
