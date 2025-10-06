// 代码生成时间: 2025-10-07 02:53:21
package com.example.rank;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RankService {

    // A thread-safe map to store rankings
    private Map<String, Integer> rankings = new ConcurrentHashMap<>();

    /**<ol>
     * Adds or updates a ranking for the given key.
     *
     * @param key The unique identifier for the ranking entry.
     * @param score The score associated with the ranking.
     */
    public void updateRank(String key, int score) {
        rankings.put(key, score);
    }

    /**<ol>
     * Retrieves the top rankings based on the score.
     *
     * @param limit The number of top rankings to retrieve.
     * @return A list of rankings in descending order of score.
     */
    public List<Map.Entry<String, Integer>> getTopRanks(int limit) {
        return rankings.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }

    /**<ol>
     * Removes a ranking entry from the rankings.
     *
     * @param key The unique identifier for the ranking entry to remove.
     */
    public void removeRank(String key) {
        rankings.remove(key);
    }

    /**<ol>
     * Clears all rankings.
     */
    public void clearRanks() {
        rankings.clear();
    }
}
