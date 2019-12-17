package com.smatechnologies.opcon.commons.opcon.util;

import com.smatechnologies.opcon.commons.constants.SystemConstants;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Pierre PINON
 */
public class ExpressionUtil {

    public static final char EVALUATION_EXPRESSION_OPEN = SystemConstants.BRACKET_OPEN.charAt(0);
    public static final char EVALUATION_EXPRESSION_CLOSE = SystemConstants.BRACKET_CLOSE.charAt(0);

    private ExpressionUtil() {}

    /**
     * Split a value by a separator character but ignore separator when character is include in expression
     *
     * @param value
     * @param separator
     * @return list of value parts
     */
    public static List<String> splitStringWithExpressions(String value, char separator) {
        if (value == null) {
            return null;
        }
        List<String> valueParts = new ArrayList<>();

        List<ExpressionPosition> expressionPositions = getExpressionPositions(value);
        Iterator<ExpressionPosition> expressionPositionsIterator = null;
        if (expressionPositions != null && ! expressionPositions.isEmpty()) {
            expressionPositionsIterator = expressionPositions.iterator();
        }

        ExpressionPosition currentExpressionPosition = null;
        int stringPartIndex = 0;
        for (int i = 0; i < value.length(); i++) {
            char currentChar = value.charAt(i);

            if (expressionPositionsIterator != null) {
                while ((currentExpressionPosition == null || currentExpressionPosition.getEnd() < i) && expressionPositionsIterator.hasNext()) {
                    currentExpressionPosition = expressionPositionsIterator.next();
                }
            }

            if (currentChar == separator && (currentExpressionPosition == null || i < currentExpressionPosition.getStart() || i > currentExpressionPosition.getEnd())) {
                valueParts.add(value.substring(stringPartIndex, i));
                stringPartIndex = i + 1;
            }
            if (i == value.length() - 1) {
                valueParts.add(value.substring(stringPartIndex, i + 1));
            }
        }

        return valueParts;
    }

    /**
     * Remove expression of value
     *
     * @param value
     * @return the value without any expression
     */
    public static String removeExpressions(String value) {
        if (value == null || value.isEmpty()) {
            return value;
        }

        List<ExpressionPosition> expressionPositions = getExpressionPositions(value);
        if (expressionPositions == null || expressionPositions.isEmpty()) {
            return value;
        } else {
            String valueWithoutExpression = "";

            ExpressionPosition oldExpressionPosition = null;
            for (ExpressionPosition expressionPosition : expressionPositions) {
                if (oldExpressionPosition == null) {
                    valueWithoutExpression += value.substring(0, expressionPosition.getStart());
                } else {
                    valueWithoutExpression += value.substring(oldExpressionPosition.getEnd() + 1, expressionPosition.getStart());
                }

                oldExpressionPosition = expressionPosition;
            }
            valueWithoutExpression += value.substring(oldExpressionPosition.getEnd() + 1);

            return valueWithoutExpression;
        }
    }

    /**
     * Get the positions of all expressions in a value
     *
     * @param value
     * @return the list of ExpressionPosition
     */
    private static List<ExpressionPosition> getExpressionPositions(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        int currentLevel = 0;
        List<Integer> startIndexsExpression = new ArrayList<>();
        Integer endIndexExpression = null;
        Character myOldChar = null;
        for (int index = 0; index < value.length(); index++) {
            char myChar = value.charAt(index);

            if (myOldChar != null) {
                if (myOldChar == EVALUATION_EXPRESSION_OPEN && myChar == EVALUATION_EXPRESSION_OPEN) {
                    currentLevel++;

                    startIndexsExpression.add(index - 1);

                    myOldChar = null;
                    continue;
                } else if (currentLevel > 0 && myOldChar == EVALUATION_EXPRESSION_CLOSE && myChar == EVALUATION_EXPRESSION_CLOSE) {
                    currentLevel--;

                    endIndexExpression = index;

                    if (currentLevel == 0) {
                        break;
                    }

                    myOldChar = null;
                    continue;
                }
            }
            myOldChar = myChar;
        }

        if (endIndexExpression == null) {
            return null;
        } else {
            List<ExpressionPosition> expressionPositions = null;
            int indexOfSubParsing;
            if (currentLevel == 0) {
                expressionPositions = new ArrayList<>();

                ExpressionPosition expressionPosition = new ExpressionPosition(startIndexsExpression.get(0), endIndexExpression);
                expressionPositions.add(expressionPosition);

                indexOfSubParsing = endIndexExpression + 1;
            } else {
                indexOfSubParsing = startIndexsExpression.get(currentLevel);
            }
            List<ExpressionPosition> expressionPositionsOfSubParsing = getExpressionPositions(value.substring(indexOfSubParsing));
            if (expressionPositionsOfSubParsing != null) {
                for (ExpressionPosition expressionPositionOfSubParsing : expressionPositionsOfSubParsing) {
                    expressionPositionOfSubParsing.setStart(expressionPositionOfSubParsing.getStart() + indexOfSubParsing);
                    expressionPositionOfSubParsing.setEnd(expressionPositionOfSubParsing.getEnd() + indexOfSubParsing);
                }
                if (expressionPositions == null) {
                    expressionPositions = new ArrayList<>();
                }
                expressionPositions.addAll(expressionPositionsOfSubParsing);
            }

            return expressionPositions;
        }
    }

    /**
     * This class represent the position of an expression in a string
     *
     * Start is the index of first expression delimiter character (inclusive)
     * End is the index of last expression delimiter character (inclusive)
     */
    private static class ExpressionPosition {
        private int start;
        private int end;

        public ExpressionPosition(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public int getStart() {
            return start;
        }

        public void setStart(int start) {
            this.start = start;
        }

        public int getEnd() {
            return end;
        }

        public void setEnd(int end) {
            this.end = end;
        }
    }
}
