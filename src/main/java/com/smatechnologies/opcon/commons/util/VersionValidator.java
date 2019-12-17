package com.smatechnologies.opcon.commons.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;


/**
 * This class allows to compare 2 versions to known if valid
 *
 * @author Pierre PINON
 */
public class VersionValidator {

    public static final String FORMAT_2_GROUPS_REGEX = "([^.]*)\\.([^.]*)";
    public static final String FORMAT_3_GROUPS_REGEX = "([^.]*)\\.([^.]*)\\.([^.]*)";
    public static final String FORMAT_4_GROUPS_REGEX = "([^.]*)\\.([^.]*)\\.([^.]*)\\.([^.]*)";
    public static final String FORMAT_5_GROUPS_REGEX = "([^.]*)\\.([^.]*)\\.([^.]*)\\.([^.]*)\\.([^.]*)";

    private Pattern pattern;
    private Rule checkRules[];

    public VersionValidator(String captureRegex) {
        setCaptureRegex(captureRegex);
    }

    public String getCaptureRegex() {
        return pattern == null ? null : pattern.pattern();
    }

    /**
     * Set capture Regex that allows to extract Version parts
     * For instance "([^.]*)\.([^.]*)\.([^.]*)" for version of this form "1.2.3"
     *
     * @param captureRegex
     * @throws NullPointerException
     *         if captureRegex is null
     * @throws PatternSyntaxException
     *         if captureRegex is not valid
     */
    public void setCaptureRegex(String captureRegex) throws NullPointerException, PatternSyntaxException {
        pattern = Pattern.compile(Objects.requireNonNull(captureRegex, "CaptureRegex cannot be null"));
    }

    public Rule[] getCheckRules() {
        return checkRules;
    }

    /**
     * Set the rules to apply on different Version parts
     *
     * @param checkRules
     */
    public void setCheckRules(Rule... checkRules) {
        this.checkRules = checkRules;
    }

    /**
     * @param version1
     * @param version2
     * @return if versions respect given checkRules
     */
    public boolean isValid(String version1, String version2) {
        List<String> versionParts1 = getParts(pattern, version1);
        List<String> versionParts2 = getParts(pattern, version2);

        //Not valid if not match with the pattern
        if (versionParts1 == null || versionParts2 == null) {
            return false;
        }

        if (checkRules != null) {
            for (int i = 0; i < checkRules.length; i++) {

                if (versionParts1.size() <= i || versionParts2.size() <= i) {
                    return false;
                }

                String versionPart1 = versionParts1.get(i);
                String versionPart2 = versionParts2.get(i);

                switch (checkRules[i]) {
                    case MATCH:
                        if (!versionPart1.equals(versionPart2)) {
                            return false;
                        }
                        break;
                    case EQUAL:
                        try {
                            if (!Integer.valueOf(versionPart1).equals(Integer.valueOf(versionPart2))) {
                                return false;
                            }
                        } catch (NumberFormatException e) {
                            return false;
                        }
                        break;
                    case GREATER:
                        try {
                            return Integer.valueOf(versionPart1) > Integer.valueOf(versionPart2);
                        } catch (NumberFormatException e) {
                            return false;
                        }
                    case GREATER_OR_EQUAL:
                        try {
                            if (Integer.valueOf(versionPart1) > Integer.valueOf(versionPart2)) {
                                return true;
                            } else if (Integer.valueOf(versionPart1) < Integer.valueOf(versionPart2)) {
                                return false;
                            }
                        } catch (NumberFormatException e) {
                            return false;
                        }
                        break;
                }
            }

        }

        return true;
    }

    /**
     * Use pattern to extract Version part
     *
     * @param pattern
     * @param version
     * @return Parts or null if does not match with pattern
     */
    private static List<String> getParts(Pattern pattern, String version) {
        Matcher matcher = pattern.matcher(version);

        if (matcher.find()) {
            List<String> parts = new ArrayList<>();

            for (int i = 1; i <= matcher.groupCount(); i++) {
                parts.add(matcher.group(i));
            }

            return parts;
        } else {
            return null;
        }
    }

    public enum Rule {
        NONE,
        MATCH,
        EQUAL,
        GREATER,
        GREATER_OR_EQUAL,
    }
}
