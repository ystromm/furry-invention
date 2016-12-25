package com.github.ystromm.furry_invention.twitter_service

import org.springframework.social.twitter.api.SearchMetadata
import org.springframework.social.twitter.api.SearchOperations
import org.springframework.social.twitter.api.SearchResults
import org.springframework.social.twitter.api.Twitter

class HashtagControllerTest extends spock.lang.Specification {
    def twitter = Mock(Twitter)
    def searchOperations = Mock(SearchOperations)
    def hashtagController = new HashtagController(twitter)

    def "should return empty"() {
        given:
        def searchResults = new SearchResults([], new SearchMetadata(1, 1))
        1 * twitter.searchOperations() >> searchOperations
        1 * searchOperations.search(_) >> searchResults
        expect:
        hashtagController.getWordsForHashtag() == []
    }
}
