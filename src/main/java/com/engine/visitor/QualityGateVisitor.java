package com.engine.visitor;

public class QualityGateVisitor {
    private boolean passed = true;
    private StringBuilder report = new StringBuilder("=== QUALITY GATE REPORT ===\n");

    public void evaluate(ProjectStatisticsVisitor stats, SecurityAuditVisitor security) {
        // 1. Complexity check (<= 10)
        if (stats.getAverageComplexity() <= 10.0) {
            report.append("Complexity: PASS (Avg: ").append(stats.getAverageComplexity()).append(")\n");
        } else {
            passed = false;
            report.append("Complexity: FAIL (Avg: ").append(stats.getAverageComplexity()).append(")\n");
        }

        // 2. Test/Source ratio check (>= 0.40)
        if (stats.getTestToSourceRatio() >= 0.40) {
            report.append("Tests Ratio: PASS (Ratio: ").append(stats.getTestToSourceRatio()).append(")\n");
        } else {
            passed = false;
            report.append("Tests Ratio: FAIL (Ratio: ").append(stats.getTestToSourceRatio()).append(")\n");
        }

        // 3. Security issues check (== 0)
        if (security.getIssuesFound() == 0) {
            report.append("Security: PASS (0 critical issues)\n");
        } else {
            passed = false;
            report.append("Security: FAIL (Issues found: ").append(security.getIssuesFound()).append(")\n");
        }

        report.append("FINAL RESULT: ").append(passed ? "PASSED [READY FOR RELEASE]" : "FAILED").append("\n");
    }

    public boolean isPassed() {
        return passed;
    }

    public String getReport() {
        return report.toString();
    }
}