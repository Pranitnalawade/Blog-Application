package com.springboot.blog.springboot_blog.service.impl;

import com.springboot.blog.springboot_blog.entity.Post;
import com.springboot.blog.springboot_blog.exception.ResourceNotFoundException;
import com.springboot.blog.springboot_blog.payload.PostDto;
import com.springboot.blog.springboot_blog.repository.PostRepository;
import com.springboot.blog.springboot_blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;

    //@Autowired //from spring 4.3 onword when we config class as bean it has only one constructor then we can ommit this annotation
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        // convert dto to entity

//        Post post = new Post();
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());

        Post post = mapToEntity(postDto);
        Post newPost = postRepository.save(post);

        PostDto postResponce = mapToDTO(newPost);
//        PostDto postResponce = new PostDto();

//        postResponce.setId(newPost.getId());
//        postResponce.setTitle(newPost.getTitle());
//        postResponce.setDescription(newPost.getDescription());
//        postResponce.setContent(newPost.getContent());

        return postResponce;
    }

    @Override
    public List<PostDto> getAllPost(int pageNo, int pageSize) {
        // Create Pageble instance
        Pageable pageable =PageRequest.of(pageNo,pageSize);
        Page<Post> posts = postRepository.findAll(pageable);

        // get content for page object
        //Page class have getContent method, so we need to get content from page object
        List<Post> listOfPosts = posts.getContent();

        return listOfPosts.stream()
                .map(post -> mapToDTO(post))
                .collect(Collectors.toList());
    }

    //convert Entity in DTO
    private PostDto mapToDTO(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());

        return postDto;
    }

    // convert dto to entity
    private Post mapToEntity(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return post;
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return mapToDTO(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        //get post by id from database
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());


        Post updatedPost = postRepository.save(post);
        return mapToDTO(updatedPost);
    }

    @Override
    public void deletePostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);
    }

}
