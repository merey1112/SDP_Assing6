package com.engine.element;

import com.engine.visitor.ProjectVisitor;
import java.util.ArrayList;
import java.util.List;

public class Directory implements ProjectElement {
    private String name;
    private List<ProjectElement> children = new ArrayList<>();

    public Directory(String name) {
        this.name = name;
    }

    public void addElement(ProjectElement element) {
        children.add(element);
    }

    @Override
    public void accept(ProjectVisitor visitor) {
        visitor.visit(this);
        for (ProjectElement child : children) {
            child.accept(visitor);
        }
    }

    String getName() { return name; }
    public List<ProjectElement> getChildren() { return children; }
}