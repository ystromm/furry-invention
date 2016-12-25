package com.github.ystromm.furry_invention.twitter_service;

import lombok.NonNull;
import lombok.Value;

@Value(staticConstructor = "wordCount")
public class WordCount {
    @NonNull
    private final String word;
    private final int count;
}
