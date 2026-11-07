package com.engine.element;

import com.engine.visitor.ProjectVisitor;
import java.util.List;

public class DependencyFile implements ProjectElement {
    private String name;
    private List<String> dependencies;
    private boolean hasVulnerabilities;

    public DependencyFile(String name, List<String> dependencies, boolean hasVulnerabilities) {
        this.name = name;
        this.dependencies = dependencies;
        this.hasVulnerabilities = hasVulnerabilities;
    }

    @Override
    public void accept(ProjectVisitor visitor) {
        visitor.visit(this);
    }

    public String getName() { return name; }
    public List<String> getDependencies() { return dependencies; }
    public boolean isHasVulnerabilities() { return hasVulnerabilities; }
}