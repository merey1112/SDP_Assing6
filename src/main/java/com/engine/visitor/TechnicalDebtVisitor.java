package com.engine.visitor;

import com.engine.element.*;

public class TechnicalDebtVisitor implements ProjectVisitor {
    private int technicalDebtScore = 0;
    private StringBuilder report = new StringBuilder("=== TECHNICAL DEBT REPORT ===\n");

    @Override
    public void visit(SourceCodeFile file) {
        if (file.getComplexity() > 10) {
            technicalDebtScore += file.getComplexity() * 2;
            report.append("[DEBT] High complexity in source file: ").append(file.getName())
                    .append(" (Complexity: ").append(file.getComplexity()).append(")\n");
        }
        if (file.getLinesOfCode() > 100) {
            technicalDebtScore += 5;
            report.append("[DEBT] Large file size (LOC > 100): ").append(file.getName()).append("\n");
        }
    }

    @Override
    public void visit(TestFile file) {
        // Тесты тоже могут вносить свой вклад в тех. долг, если они слишком короткие
    }

    @Override
    public void visit(ConfigurationFile file) {
        if (file.isInsecure()) {
            technicalDebtScore += 10;
            report.append("[DEBT] Insecure configuration adds debt: ").append(file.getName()).append("\n");
        }
    }

    @Override
    public void visit(DependencyFile file) {
        if (file.isHasVulnerabilities()) {
            technicalDebtScore += 15;
            report.append("[DEBT] Vulnerable dependencies: ").append(file.getName()).append("\n");
        }
    }

    @Override
    public void visit(Directory directory) {
        // Директории не влияют напрямую на тех. долг кода
    }

    public int getTechnicalDebtScore() {
        return technicalDebtScore;
    }

    public String getReport() {
        report.append("Total Technical Debt Score: ").append(technicalDebtScore).append("\n");
        return report.toString();
    }
}