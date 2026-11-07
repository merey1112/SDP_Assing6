package com.engine.visitor;

import com.engine.element.*;

public class ComplianceVisitor implements ProjectVisitor {
    private boolean compliant = true;
    private StringBuilder report = new StringBuilder("=== COMPLIANCE REPORT ===\n");

    @Override
    public void visit(SourceCodeFile file) {
        if (file.isHasHardcodedCredentials()) {
            compliant = false;
            report.append("[VIOLATION] Source file ").append(file.getName()).append(" contains hardcoded credentials.\n");
        }
    }

    @Override
    public void visit(TestFile file) {}

    @Override
    public void visit(ConfigurationFile file) {
        if (file.isInsecure()) {
            compliant = false;
            report.append("[VIOLATION] Config file ").append(file.getName()).append(" is insecure.\n");
        }
    }

    @Override
    public void visit(DependencyFile file) {
        if (file.isHasVulnerabilities()) {
            compliant = false;
            report.append("[VIOLATION] Dependency file ").append(file.getName()).append(" has vulnerable libraries.\n");
        }
    }

    @Override
    public void visit(Directory directory) {}

    public String getReport() {
        if (compliant) {
            return report.toString() + "Compliance Check: PASSED\n";
        }
        return report.toString() + "Compliance Check: FAILED\n";
    }
}