package com.example.demo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.example.demo.domain.ArticleEntity;
import com.example.demo.domain.CategoryEntity;
import com.example.demo.domain.TagEntity;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.TagRepository;

@SpringBootApplication
@EnableJpaAuditing
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner adminRunner(
			CategoryRepository categoryRepository,
			TagRepository tagRespository,
			ArticleRepository articleRepository) {

		System.out.println("RUNNER");

		return args -> {

			CategoryEntity category = CategoryEntity.builder()
					.name("books")
					.articles(null)
					.build();

			TagEntity tag = TagEntity.builder()
					.name("nicetag")
					.articles(null)
					.build();

			List<TagEntity> tags = new ArrayList<>();
			tags.add(tag);

			ArticleEntity article = ArticleEntity.builder()
					.title("Harry Potter and the Philosopher Stone")
					.content("A nice book for nice people.")
					.category(category)
					.tags(tags)
					.build();

			categoryRepository.save(category);
			tagRespository.save(tag);
			articleRepository.save(article);

		};
	}

}
