package com.github.ystromm.twitter_service;

import com.google.common.collect.Lists;
import org.springframework.social.connect.ConnectionRepository;
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

    private ConnectionRepository connectionRepository;

    @Inject
    public HashtagController(Twitter twitter, ConnectionRepository connectionRepository) {
        this.twitter = twitter;
        this.connectionRepository = connectionRepository;
    }


    @RequestMapping(path = "/hashtag")
    public Collection<String> getHashtag() {
        SearchParameters searchParameters = new SearchParameters("#brexit");
        final SearchResults searchResults = twitter.searchOperations().search(searchParameters);
        return Lists.transform(searchResults.getTweets(), tweet -> tweet.getText());
    }
}
