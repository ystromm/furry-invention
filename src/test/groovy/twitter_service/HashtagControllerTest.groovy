package com.github.ystromm.furry_invention.twitter_service

import org.springframework.social.twitter.api.SearchMetadata
import org.springframework.social.twitter.api.SearchOperations
import org.springframework.social.twitter.api.SearchResults
import org.springframework.social.twitter.api.Tweet
import org.springframework.social.twitter.api.Twitter

class HashtagControllerTest extends spock.lang.Specification {
    def twitter = Mock(Twitter)
    def searchOperations = Mock(SearchOperations)
    def hashtagController = new HashtagController(twitter)

    def setup() {
        1 * twitter.searchOperations() >> searchOperations
    }

    def "should search"() {
        when:
        hashtagController.getWordsForHashtag("query")
        then:
        1 * searchOperations.search("query") >> searchResults([]);
    }

    def "should return empty"() {
        given:
        1 * searchOperations.search(_) >> searchResults([]);
        expect:
        hashtagController.getWordsForHashtag("#kaka") == []
    }

    def "should return a word"() {
        given:
        1 * searchOperations.search(_) >> searchResults([tweet("apa")])
        expect:
        hashtagController.getWordsForHashtag("#kaka") == [WordCount.of("apa", 1)]
    }

    def "should return words"() {
        given:
        searchOperations.search(_) >> searchResults([tweet("apa kaka")])
        expect:
        hashtagController.getWordsForHashtag("#kaka") == [WordCount.of("apa", 1), WordCount.of("kaka", 1)]
    }

    def "should return same word with count"() {
        given:
        searchOperations.search(_) >> searchResults([tweet("apa apa")])
        expect:
        hashtagController.getWordsForHashtag("#orungatang") == [WordCount.of("apa", 2)]
    }

    def "should return words with counts"() {
        given:
        searchOperations.search(_) >> searchResults([tweet("apa kaka apa kaka")])
        expect:
        hashtagController.getWordsForHashtag("#orungatang") == [WordCount.of("apa", 2), WordCount.of("kaka", 2)]
    }

    def "should strip punctuation"() {
        given:
        searchOperations.search(_) >> searchResults([tweet(text)])
        expect:
        hashtagController.getWordsForHashtag("#orungatang") == [WordCount.of(word, count)]
        where:
        text |  word | count
        "apa!" | "apa" | 1
        "apa." | "apa" | 1
        "apa!" | "apa" | 1
    }


    private SearchResults searchResults(tweets) {
        new SearchResults(tweets, new SearchMetadata(1, 1))
    }

    private Tweet tweet(String text) {
        new Tweet(1l, text, new Date(), "fromUser", "profileImageUrl", 2l, 3l, "languageCode", "source")
    }
}
