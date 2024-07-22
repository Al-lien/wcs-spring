package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.example.demo.domain.ArticleEntity;
import com.example.demo.domain.AuthorEntity;
import com.example.demo.domain.CategoryEntity;
import com.example.demo.domain.TagEntity;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.AuthorRepository;
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
			ArticleRepository articleRepository,
			AuthorRepository authorRepository) {

		System.out.println("RUNNER");

		return args -> {

			CategoryEntity categoryPolitics = CategoryEntity.builder()
					.name("Politics")
					.articles(null)
					.build();

			CategoryEntity categoryInternational = CategoryEntity.builder()
					.name("International")
					.articles(null)
					.build();

			CategoryEntity categoryCulture = CategoryEntity.builder()
					.name("Culture")
					.articles(null)
					.build();

			TagEntity tagLiterature = TagEntity.builder()
					.name("Literature")
					.articles(null)
					.build();

			TagEntity tagBook = TagEntity.builder()
					.name("Book")
					.articles(null)
					.build();

			TagEntity tagWar = TagEntity.builder()
					.name("War")
					.articles(null)
					.build();

			TagEntity tagMayoSauce = TagEntity.builder()
					.name("Mayonnaise")
					.articles(null)
					.build();

			TagEntity tagMayor = TagEntity.builder()
					.name("Mayor")
					.articles(null)
					.build();

			List<TagEntity> tagsHarryPotter = new ArrayList<>();
			tagsHarryPotter.add(tagLiterature);
			tagsHarryPotter.add(tagBook);

			List<TagEntity> tagsEwoks = new ArrayList<>();
			tagsEwoks.add(tagWar);

			List<TagEntity> tagsMayor = new ArrayList<>();
			tagsMayor.add(tagMayoSauce);
			tagsMayor.add(tagMayor);

			ArticleEntity articleHarryPotter = ArticleEntity.builder()
					.title("Harry Potter, good guy or bad guy ?")
					.content("Who really is Harry Potter, the \"boy who lived\" ?")
					.category(categoryCulture)
					.tags(tagsHarryPotter)
					.build();

			ArticleEntity articleEwoks = ArticleEntity.builder()
					.title("The raging war between Ewoks and droid.")
					.content(
							"The King of Ewok in the tourment of this recent conflict which divides the opinion among youth.")
					.category(categoryInternational)
					.tags(tagsEwoks)
					.build();

			ArticleEntity articleMayor = ArticleEntity.builder()
					.title("Mayor in mayo fury !")
					.content("Mayor Arnold ordered 3000 pounds of hellmann's sauce with state fund. ")
					.category(categoryPolitics)
					.tags(tagsMayor)
					.build();

			AuthorEntity authorJohn = AuthorEntity.builder()
					.lastname("Doe")
					.firstname("John")
					.build();

			AuthorEntity authorJane = AuthorEntity.builder()
					.lastname("Doe")
					.firstname("Jane")
					.build();

			categoryRepository.save(categoryPolitics);
			categoryRepository.save(categoryInternational);
			categoryRepository.save(categoryCulture);

			tagRespository.save(tagLiterature);
			tagRespository.save(tagBook);
			tagRespository.save(tagWar);
			tagRespository.save(tagMayoSauce);
			tagRespository.save(tagMayor);

			articleRepository.save(articleHarryPotter);
			articleRepository.save(articleEwoks);
			articleRepository.save(articleMayor);

			authorRepository.save(authorJohn);
			authorRepository.save(authorJane);

		};
	}

}
