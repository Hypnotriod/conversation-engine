package org.hypnotriod.conversationengine.engine.contants;

import java.util.regex.Pattern;

/**
 *
 * @author Ilya Pikin
 */
public class TextManipulationConstants {
    public static final String SPOKEN_QUERY_DATA_REGEX = "[[:alpha:] ]+";
    public static final String UTTERANCE_DATA_PATTERN_REGEX = "\\[[a-z0-9]+:(\\?|[a-z0-9]+\\.[a-z0-9]+)\\]";
    public static final Pattern UTTERANCE_DATA_PATTERN = Pattern.compile(UTTERANCE_DATA_PATTERN_REGEX);
}
