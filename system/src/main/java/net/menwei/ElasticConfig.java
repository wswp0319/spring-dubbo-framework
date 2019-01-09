package net.menwei;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@EnableAutoConfiguration
@EnableElasticsearchRepositories(basePackages = "net.menwei.repositories")
public class ElasticConfig {

}
