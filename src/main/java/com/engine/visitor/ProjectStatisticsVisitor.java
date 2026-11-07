package com.engine.visitor;

import com.engine.element.*;

public class ProjectStatisticsVisitor implements ProjectVisitor {
    private int totalFiles = 0;
    private int totalLinesOfCode = 0;
    private int totalTests = 0;
    private int totalComplexity = 0;
    private int sourceFilesCount = 0;

    @Override
    public void visit(SourceCodeFile file) {
        totalFiles++;
        sourceFilesCount++;
        totalLinesOfCode += file.getLinesOfCode();
        totalComplexity += file.getComplexity();
    }

    @Override
    public void visit(TestFile file) {
        totalFiles++;
        totalLinesOfCode += file.getLinesOfCode();
        totalTests += file.getTestCasesCount();
    }

    @Override
    public void visit(ConfigurationFile file) {
        totalFiles++;
    }

    @Override
    public void visit(DependencyFile file) {
        totalFiles++;
    }

    @Override
    public void visit(Directory directory) {
        // Папку как отдельный файл не считаем
    }

    public double getAverageComplexity() {
        return sourceFilesCount == 0 ? 0 : (double) totalComplexity / sourceFilesCount;
    }

    public double getTestToSourceRatio() {
        return sourceFilesCount == 0 ? 0 : (double) totalTests / sourceFilesCount;
    }

    public String getReport() {
        return "=== PROJECT STATISTICS ===\n" +
                "Total Files: " + totalFiles + "\n" +
                "Total Lines of Code: " + totalLinesOfCode + "\n" +
                "Total Test Cases: " + totalTests + "\n" +
                "Average Complexity: " + getAverageComplexity() + "\n" +
                "Test/Source Ratio: " + getTestToSourceRatio() + "\n";
    }
}