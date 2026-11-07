package com.engine.visitor;

import com.engine.element.*;

public class SecurityAuditVisitor implements ProjectVisitor {
    private int issuesFound = 0;
    private StringBuilder report = new StringBuilder("=== SECURITY AUDIT REPORT ===\n");

    @Override
    public void visit(SourceCodeFile file) {
        if (file.isHasHardcodedCredentials()) {
            issuesFound++;
            report.append("[HIGH] Hardcoded credentials found in: ").append(file.getName()).append("\n");
        }
    }

    @Override
    public void visit(TestFile file) {
        // Тесты обычно не проверяют на безопасность
    }

    @Override
    public void visit(ConfigurationFile file) {
        if (file.isInsecure()) {
            issuesFound++;
            report.append("[MEDIUM] Insecure configuration in: ").append(file.getName()).append("\n");
        }
    }

    @Override
    public void visit(DependencyFile file) {
        if (file.isHasVulnerabilities()) {
            issuesFound++;
            report.append("[HIGH] Vulnerable dependencies in: ").append(file.getName()).append("\n");
        }
    }

    @Override
    public void visit(Directory directory) {
        // Папка сама по себе не содержит уязвимостей, обходим её детей через accept
    }

    public int getIssuesFound() {
        return issuesFound;
    }

    public String getReport() {
        if (issuesFound == 0) {
            return report.toString() + "No security issues found. PASS!\n";
        }
        return report.toString() + "Total issues found: " + issuesFound + "\n";
    }
}