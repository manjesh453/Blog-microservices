package com.postservice.conf;

import com.postservice.entity.Post;
import com.postservice.repo.PostRepo;
import com.postservice.shared.Category;
import com.postservice.shared.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class BootStrapPost implements CommandLineRunner {
    private final PostRepo postService;

    @Override
    public void run(String... args) throws Exception {
        List<Post> posts = List.of(
                Post.builder().title("FootBall")
                        .content("Football is one of the most popular sports in the world. Millions play and watch it.")
                        .status(Status.ACTIVE).imageName("football.jpg")
                        .categoryId(Category.SPORTS).userId(1L).likes(0L).build(),

                Post.builder().title("MOMO")
                        .content("Momo is a type of South Asian dumpling, native to Tibet, Nepal, and parts of India.")
                        .status(Status.ACTIVE).imageName("momo.jpg")
                        .categoryId(Category.FOOD).userId(1L).likes(0L).build(),

                Post.builder().title("Super Computer")
                        .content("Supercomputers are high-performance systems used for scientific and engineering tasks.")
                        .status(Status.ACTIVE).imageName("super_computer.jpg")
                        .categoryId(Category.TECHNOLOGY).userId(1L).likes(0L).build(),

                Post.builder().title("Trade market")
                        .content("The trade market involves the exchange of goods and services and is a core aspect of modern economics.")
                        .status(Status.ACTIVE).imageName("trade.jpg")
                        .categoryId(Category.BUSINESS).userId(1L).likes(0L).build(),

                Post.builder().title("Late Queen")
                        .content("Queen Elizabeth II served as the Queen of the United Kingdom and other realms for decades.")
                        .status(Status.ACTIVE).imageName("queen.jpg")
                        .categoryId(Category.POLITICS).userId(1L).likes(0L).build(),

                Post.builder().title("Fashion")
                        .content("Fashion is a form of self-expression through clothing, hairstyle, and lifestyle choices.")
                        .status(Status.ACTIVE).imageName("fasion.jpg")
                        .categoryId(Category.OTHERS).userId(1L).likes(0L).build(),

                Post.builder().title("Education")
                        .content("Education empowers individuals with knowledge, skills, and values to succeed in life.")
                        .status(Status.ACTIVE).imageName("Education.jpg")
                        .categoryId(Category.OTHERS).userId(1L).likes(0L).build(),

                Post.builder().title("Chowmin")
                        .content("Chowmin is a popular stir-fried noodle dish originating from Chinese cuisine.")
                        .status(Status.ACTIVE).imageName("chaumin.jpg")
                        .categoryId(Category.FOOD).userId(1L).likes(0L).build(),

                Post.builder().title("Cricket")
                        .content("Cricket is a bat-and-ball game played between two teams and is hugely popular in South Asia.")
                        .status(Status.ACTIVE).imageName("cricket.jpg")
                        .categoryId(Category.SPORTS).userId(1L).likes(0L).build(),

                Post.builder().title("Space X")
                        .content("SpaceX is revolutionizing space technology with the goal of enabling people to live on other planets.")
                        .status(Status.ACTIVE).imageName("spacex.jpg")
                        .categoryId(Category.TECHNOLOGY).userId(1L).likes(0L).build()
        );

        postService.saveAll(posts);

    }
}
