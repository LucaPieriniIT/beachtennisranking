package xyz.pierini.rankings.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import com.mongodb.client.MongoClient;

import xyz.pierini.rankings.model.converter.PlayerConverter.PlayerRankingEnumConverter;
import xyz.pierini.rankings.model.converter.PlayerConverter.YearEnumConverter;

@Configuration
public class MongoDbConfig {

	@Autowired
    private MongoProperties mongoProperties;

    @Autowired
    private MongoClient mongoClient;


    @Bean
    public MongoTemplate mongoTemplate() {
        MongoTemplate mongoTemplate = new MongoTemplate(mongoClient, mongoProperties.getDatabase());
        MappingMongoConverter mongoMapping = (MappingMongoConverter) mongoTemplate.getConverter();
        mongoMapping.setCustomConversions(customConversions()); // tell mongodb to use the custom converters
        mongoMapping.afterPropertiesSet();
        return mongoTemplate;
    }

    public MongoCustomConversions customConversions() {
        List<Converter<?, ?>> converters = new ArrayList<>();
        converters.add(PlayerRankingEnumConverter.INSTANCE);
        converters.add(YearEnumConverter.INSTANCE);
        return new MongoCustomConversions(converters);
    }
	
}
