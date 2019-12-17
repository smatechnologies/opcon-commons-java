package com.smatechnologies.opcon.commons.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


/**
 * @author Pierre PINON
 */
public class VersionComparator implements Comparator<String> {

    private List<Character> separators = Collections.singletonList('.');

    public List<Character> getSeparators() {
        return separators;
    }

    public void setSeparators(List<Character> separators) {
        this.separators = separators;
    }

    @Override
    public int compare(String version, String version2) {
        if (version == null && version2 == null) {
            return 0;
        } else if (version == null) {
            return -1;
        } else if (version2 == null) {
            return 1;
        }

        List<String> versionParts = getParts(version);
        List<String> versionParts2 = getParts(version2);

        int minPartSize = Math.min(versionParts.size(), versionParts2.size());
        for (int i = 0; i < minPartSize; i++) {
            String versionPart = versionParts.get(i);
            String versionPart2 = versionParts2.get(i);

            String[] versionPartWithPaddingBoth = appendPadding(versionPart, versionPart2);
            String versionPartWithPadding = versionPartWithPaddingBoth[0];
            String versionPartWithPadding2 = versionPartWithPaddingBoth[1];

            int compareValue = versionPartWithPadding.compareTo(versionPartWithPadding2);
            if (compareValue != 0) {
                return compareValue;
            }
        }

        if (versionParts.size() < versionParts2.size()) {
            return -1;
        } else if (versionParts.size() > versionParts2.size()) {
            return 1;
        }

        return 0;
    }

    private List<String> getParts(String version) {
        if (version == null) {
            return null;
        }

        return Arrays.asList(version.split("[" + Pattern.quote(separators.stream().map(Object::toString).collect(Collectors.joining())) + "]"));
    }

    private String[] appendPadding(String part, String part2) {
        part = StringUtils.leftPad(part, part2.length(), '\0');
        part2 = StringUtils.leftPad(part2, part.length(), '\0');

        return new String[] { part, part2 };
    }
}
