package org.ranking_app.repository.review;

public interface ReviewItemStatsProjection {
    Double getPriceMin();
    Double getPriceMax();
    Double getRankingAvg();
}
