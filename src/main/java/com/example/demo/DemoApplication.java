package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.example.demo.domain.ArticleAuthorEntity;
import com.example.demo.domain.ArticleEntity;
import com.example.demo.domain.AuthorEntity;
import com.example.demo.domain.CategoryEntity;
import com.example.demo.domain.TagEntity;
import com.example.demo.repository.ArticleAuthorRepository;
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
			AuthorRepository authorRepository,
			ArticleAuthorRepository articleAuthorRepository) {

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

			CategoryEntity category1 = categoryRepository.save(categoryPolitics);
			CategoryEntity category2 = categoryRepository.save(categoryInternational);
			CategoryEntity category3 = categoryRepository.save(categoryCulture);

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

			TagEntity tag1 = tagRespository.save(tagLiterature);
			TagEntity tag2 = tagRespository.save(tagBook);
			TagEntity tag3 = tagRespository.save(tagWar);
			TagEntity tag4 = tagRespository.save(tagMayoSauce);
			TagEntity tag5 = tagRespository.save(tagMayor);

			List<TagEntity> tagsHarryPotter = new ArrayList<>();
			tagsHarryPotter.add(tag1);
			tagsHarryPotter.add(tag2);

			List<TagEntity> tagsEwoks = new ArrayList<>();
			tagsEwoks.add(tag3);

			List<TagEntity> tagsMayor = new ArrayList<>();
			tagsMayor.add(tag4);
			tagsMayor.add(tag5);

			List<TagEntity> tagsFood = new ArrayList<>();
			tagsFood.add(tag3);

			ArticleEntity articleHarryPotter = ArticleEntity.builder()
					.title("Harry Potter, good guy or bad guy ?")
					.content("Who really is Harry Potter, the \"boy who lived\" ?")
					.category(category3)
					.tags(tagsHarryPotter)
					.build();

			ArticleEntity articleEwoks = ArticleEntity.builder()
					.title("The raging war between Ewoks and droid.")
					.content(
							"The King of Ewok in the tourment of this recent conflict which divides the opinion among youth.")
					.category(category2)
					.tags(tagsEwoks)
					.build();

			ArticleEntity articleMayor = ArticleEntity.builder()
					.title("Mayor in mayo fury !")
					.content("Mayor Arnold ordered 3000 pounds of hellmann's sauce with state fund. ")
					.category(category1)
					.tags(tagsMayor)
					.build();

			ArticleEntity articleFood = ArticleEntity.builder()
					.title("Great food, but bad wine ?")
					.content("This restaurant was serving great food with bad wine. What the hell is that ?! ")
					.category(category2)
					.tags(tagsFood)
					.build();

			ArticleEntity article1 = articleRepository.save(articleHarryPotter);
			ArticleEntity article2 = articleRepository.save(articleEwoks);
			ArticleEntity article3 = articleRepository.save(articleMayor);
			ArticleEntity article4 = articleRepository.save(articleFood);

			AuthorEntity authorJohn = AuthorEntity.builder()
					.lastname("Doe")
					.firstname("John")
					.build();

			AuthorEntity authorJane = AuthorEntity.builder()
					.lastname("Doe")
					.firstname("Jane")
					.build();

			AuthorEntity author1 = authorRepository.save(authorJohn);
			AuthorEntity author2 = authorRepository.save(authorJane);

			ArticleAuthorEntity articleAuthor1 = ArticleAuthorEntity.builder()
					.article(article1)
					.author(author1)
					.contribution("redactor")
					.build();

			ArticleAuthorEntity articleAuthor2 = ArticleAuthorEntity.builder()
					.article(article1)
					.author(author2)
					.contribution("editor")
					.build();

			ArticleAuthorEntity articleAuthor3 = ArticleAuthorEntity.builder()
					.article(article2)
					.author(author1)
					.contribution("killer")
					.build();

			ArticleAuthorEntity articleAuthor4 = ArticleAuthorEntity.builder()
					.article(article3)
					.author(author2)
					.contribution("stalker")
					.build();

			ArticleAuthorEntity articleAuthor5 = ArticleAuthorEntity.builder()
					.article(article4)
					.author(author2)
					.contribution("writter")
					.build();

			articleAuthorRepository.save(articleAuthor1);
			articleAuthorRepository.save(articleAuthor2);
			articleAuthorRepository.save(articleAuthor3);
			articleAuthorRepository.save(articleAuthor4);
			articleAuthorRepository.save(articleAuthor5);

		};
	}

}
