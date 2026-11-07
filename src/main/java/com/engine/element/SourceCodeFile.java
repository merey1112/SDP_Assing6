package com.engine.element;

import com.engine.visitor.ProjectVisitor;

public class SourceCodeFile implements ProjectElement {
    private String name;
    private int linesOfCode;
    private int complexity;
    private boolean hasHardcodedCredentials;

    public SourceCodeFile(String name, int linesOfCode, int complexity, boolean hasHardcodedCredentials) {
        this.name = name;
        this.linesOfCode = linesOfCode;
        this.complexity = complexity;
        this.hasHardcodedCredentials = hasHardcodedCredentials;
    }

    @Override
    public void accept(ProjectVisitor visitor) {
        visitor.visit(this);
    }

    public String getName() { return name; }
    public int getLinesOfCode() { return linesOfCode; }
    public int getComplexity() { return complexity; }
    public boolean isHasHardcodedCredentials() { return hasHardcodedCredentials; }
}