package com.github.ystromm.furry_invention.twitter_service;

import com.google.common.collect.Lists;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.twitter.api.SearchOperations;
import org.springframework.social.twitter.api.SearchParameters;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.Collection;

@RestController
@RequestMapping("/api")
public class HashtagController {
    private Twitter twitter;

    @Inject
    public HashtagController(Twitter twitter) {
        this.twitter = twitter;
    }


    @RequestMapping(path = "/hashtag")
    public Collection<String> getWordsForHashtag() {
        final SearchParameters searchParameters = new SearchParameters("#brexit");
        final SearchOperations searchOperations = twitter.searchOperations();
        final SearchResults searchResults = searchOperations.search(searchParameters);
        return Lists.transform(searchResults.getTweets(), tweet -> tweet.getText());
    }
}
