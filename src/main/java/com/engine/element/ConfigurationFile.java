package com.engine.element;

import com.engine.visitor.ProjectVisitor;

public class ConfigurationFile implements ProjectElement {
    private String name;
    private boolean insecure;

    public ConfigurationFile(String name, boolean insecure) {
        this.name = name;
        this.insecure = insecure;
    }

    @Override
    public void accept(ProjectVisitor visitor) {
        visitor.visit(this);
    }

    public String getName() { return name; }
    public boolean isInsecure() { return insecure; }
}