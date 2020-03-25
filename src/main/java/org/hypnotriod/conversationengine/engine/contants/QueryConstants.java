package org.hypnotriod.conversationengine.engine.contants;

/**
 *
 * @author Ilya Pikin
 */
public class QueryConstants {

    public static final String QUERY_CUSTOM_DATA_ID_VALUE_MATCHES = "SELECT id, %s FROM %s WHERE %s ~ ?1";
    public static final String QUERY_SPOKEN_QUERY_ALL_PATTERN_MATCHES
            = "SELECT * FROM spoken_query sq "
            + "LEFT JOIN spoken_query_spoken_context sqsc "
            + "ON sq.id = sqsc.spoken_query_id "
            + "LEFT JOIN spoken_context sc "
            + "ON sc.id = sqsc.spoken_context_id "
            + "WHERE sc.name = ?2 "
            + "AND ?1 ~ sq.regexp";
}
