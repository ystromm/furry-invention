package com.github.ystromm.furry_invention.twitter_service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.twitter.api.SearchOperations;
import org.springframework.social.twitter.api.SearchParameters;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.stream;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class HashtagController {
    private final Twitter twitter;
    private final long limit;

    @Inject
    public HashtagController(Twitter twitter) {
        this(twitter, 100);
    }

    HashtagController(Twitter twitter, long limit) {
        this.twitter = twitter;
        this.limit = limit;
    }

    @RequestMapping(path = "/hashtag")
    public Collection<WordCount> getWordsForHashtag(String query) {
        final SearchResults searchResults = twitter.searchOperations().search(query);
        return searchResults.getTweets()
                .stream().map(tweet -> tweet.getText())
                .flatMap(text -> stream(text.split("\\W")))
                .collect(Collectors.groupingBy(identity(), counting()))
                .entrySet().stream()
                .map(entry -> WordCount.of(entry.getKey(), entry.getValue())).sorted(new Comparator<WordCount>() {
                    @Override
                    public int compare(WordCount o1, WordCount o2) {
                        if (o1.getCount() == o2.getCount()) {
                            return String.CASE_INSENSITIVE_ORDER.compare(o1.getWord(), o2.getWord());
                        }
                        else {
                            return Long.compare(o2.getCount(), o1.getCount());
                        }
                    }
                }).limit(limit).collect(toList());
    }
}
