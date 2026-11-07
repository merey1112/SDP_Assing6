package com.engine.element;

import com.engine.visitor.ProjectVisitor;

public class TestFile implements ProjectElement {
    private String name;
    private int linesOfCode;
    private int testCasesCount;

    public TestFile(String name, int linesOfCode, int testCasesCount) {
        this.name = name;
        this.linesOfCode = linesOfCode;
        this.testCasesCount = testCasesCount;
    }

    @Override
    public void accept(ProjectVisitor visitor) {
        visitor.visit(this);
    }

    public String getName() { return name; }
    public int getLinesOfCode() { return linesOfCode; }
    public int getTestCasesCount() { return testCasesCount; }
}