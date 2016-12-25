package com.github.ystromm.furry_invention.twitter_service;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

@Value(staticConstructor = "of")
public class WordCount {
    @NonNull
    private final String word;
    private final long count;
}
