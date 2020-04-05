package org.hypnotriod.conversationengine.engine.contants;

import java.util.regex.Pattern;

/**
 *
 * @author Ilya Pikin
 */
public class TextManipulationConstants {

    public static final String REGEX_ONLY_FULL_WORDS_MATCH = "(^| )%s( |$)";
    public static final String REGEX_DIALOG_QUERY_DATA = "[[:alpha:] ]+";
    public static final String REGEX_UTTERANCE_DATA = "\\[[a-z0-9]+:(\\?|[a-z0-9]+\\.[a-z0-9]+)\\]";

    public static final Pattern PATTERN_UTTERANCE_DATA = Pattern.compile(REGEX_UTTERANCE_DATA);
}
