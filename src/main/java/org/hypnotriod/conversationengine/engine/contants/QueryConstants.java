package org.hypnotriod.conversationengine.engine.contants;

/**
 *
 * @author Ilya Pikin
 */
public class QueryConstants {

    public static final String QUERY_CUSTOM_DATA_ID_VALUE_MATCHES = "SELECT id, %s FROM %s WHERE %s ~ ?1";
    public static final String QUERY_DIALOG_QUERY_ALL_PATTERN_MATCHES
            = "SELECT * FROM dialog_query dq "
            + "LEFT JOIN dialog_query_dialog_context dqdc "
            + "ON dq.id = dqdc.dialog_query_id "
            + "LEFT JOIN dialog_context dc "
            + "ON dc.id = dqdc.dialog_context_id "
            + "WHERE dc.name = ?2 "
            + "AND ?1 ~ dq.regexp";
}
