package com.loganalyzer.ingestion.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;

@Configuration
@EnableMongoAuditing // Ye annotation automatic timestamping enable karti hai
public class MongoConfig {

    /**
     * Transaction Manager: Agar aap chahte hain ki ek saath multiple 
     * collections mein data save ho (ya kahin fail ho to rollback ho jaye), 
     * toh ye bean zaroori hai.
     */
    @Bean
    MongoTransactionManager transactionManager(MongoDatabaseFactory dbFactory) {
        return new MongoTransactionManager(dbFactory);
    }
}