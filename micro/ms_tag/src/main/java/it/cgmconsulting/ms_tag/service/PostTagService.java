package it.cgmconsulting.ms_tag.service;

import it.cgmconsulting.ms_tag.entity.PostTag;
import it.cgmconsulting.ms_tag.entity.PostTagId;
import it.cgmconsulting.ms_tag.entity.Tag;
import it.cgmconsulting.ms_tag.repository.PostTagRepository;
import it.cgmconsulting.ms_tag.repository.TagRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PostTagService {

    private final PostTagRepository postTagRepository;
    private final TagRepository tagRepository;

    public ResponseEntity<?> addTagsToPost(int postId, Set<String> tagNames) {
        Set<Tag> tags = tagRepository.getTags(tagNames);
        List<PostTag> postTags = new ArrayList<>();
        for (Tag t : tags) {
            postTags.add(new PostTag(new PostTagId(postId, t)));
        }
        postTagRepository.saveAll(postTags);
        return ResponseEntity.status(201).body(postTags);
    }
    @Transactional
    public ResponseEntity<?> updateTagsToPost(int postId, Set<String> tagNames) {
        postTagRepository.deleteByPostId(postId);
        Set<PostTag> newPostTags = new HashSet<>();

        if (!tagNames.isEmpty()) {
            Set<Tag> newTags = tagRepository.getTags(tagNames);

            for (Tag t : newTags) {
                newPostTags.add(new PostTag(new PostTagId(postId, t)));
            }
            postTagRepository.saveAll(newPostTags);
        }
        return ResponseEntity.ok(newPostTags);
    }

    public ResponseEntity<?>getTagsByPost(int postId){
        Set<String> tags = postTagRepository.getTagsByPost(postId);
        return ResponseEntity.ok(tags);
    }
}
        /*
        Set<Tag> tagsToAdd = new HashSet<>(newTags);
        tagsToAdd.removeAll(oldTags); // Rimuove i tag esistenti dai nuovi tag

        // Calcola i tag da rimuovere
        Set<Tag> tagsToRemove = new HashSet<>(oldTags);
        tagsToRemove.removeAll(newTags); // Rimuove i nuovi tag dai tag esistenti

        // Rimuove i vecchi tag dal post
        for (Tag t : tagsToRemove) {
            postTagRepository.deleteById(new PostTagId(postId, t));
        }

        // Aggiunge i nuovi tag al post
        List<PostTag> postTags = new ArrayList<>();
        for (Tag t : tagsToAdd) {
            postTags.add(new PostTag(new PostTagId(postId, t)));
        }
        postTagRepository.saveAll(postTags);

        // Restituisce la risposta con i tag aggiornati
        return ResponseEntity.ok(postTags);

        public ResponseEntity<?> updateTagsToPost(int postId, Set<String> tagNames) {    Set<Tag> newTags = tagRepository.getTags(tagNames);
        Set<PostTag> postTags = postTagRepository.getPostTags(postId);    Set<Tag> deletableTags = new HashSet<>();
        for(PostTag pt : postTags){        if (!newTags.contains(pt.getPostTagId().getTag()))
            deletableTags.add(pt.getPostTagId().getTag());    }
        for(Tag t : newTags){
            postTags.add(new PostTag(new PostTagId(postId, t)));    }
        postTagRepository.deleteOldPostTags(deletableTags);    postTagRepository.saveAll(postTags);
        return ResponseEntity.ok(postTags);}
        */


