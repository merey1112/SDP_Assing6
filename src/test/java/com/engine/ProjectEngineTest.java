package com.engine;

import com.engine.element.*;
import com.engine.visitor.*;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectEngineTest {

    @Test
    void testSourceCodeFileAcceptAndGetters() {
        SourceCodeFile file = new SourceCodeFile("Auth.java", 100, 5, false);
        assertEquals("Auth.java", file.getName());
        assertEquals(100, file.getLinesOfCode());
        assertEquals(5, file.getComplexity());
        assertFalse(file.isHasHardcodedCredentials());
    }

    @Test
    void testTestFileAcceptAndGetters() {
        TestFile file = new TestFile("AuthTest.java", 50, 4);
        assertEquals("AuthTest.java", file.getName());
        assertEquals(50, file.getLinesOfCode());
        assertEquals(4, file.getTestCasesCount());
    }

    @Test
    void testConfigurationFileAcceptAndGetters() {
        ConfigurationFile file = new ConfigurationFile("config.properties", true);
        assertEquals("config.properties", file.getName());
        assertTrue(file.isInsecure());
    }

    @Test
    void testDependencyFileAcceptAndGetters() {
        DependencyFile file = new DependencyFile("pom.xml", List.of("junit"), false);
        assertEquals("pom.xml", file.getName());
        assertEquals(1, file.getDependencies().size());
        assertFalse(file.isHasVulnerabilities());
    }

    @Test
    void testDirectoryAndCompositeAccept() {
        Directory dir = new Directory("root");
        SourceCodeFile file = new SourceCodeFile("Main.java", 10, 2, false);
        dir.addElement(file);

        assertEquals(1, dir.getChildren().size());

        ProjectStatisticsVisitor stats = new ProjectStatisticsVisitor();
        dir.accept(stats);
        assertEquals(1, stats.getReport().contains("Total Files: 1") ? 1 : 0);
    }

    @Test
    void testSecurityAuditVisitorWithIssues() {
        SourceCodeFile badFile = new SourceCodeFile("Hack.java", 20, 15, true);
        SecurityAuditVisitor visitor = new SecurityAuditVisitor();

        badFile.accept(visitor);
        assertEquals(1, visitor.getIssuesFound());
        assertTrue(visitor.getReport().contains("Hardcoded credentials"));
    }

    @Test
    void testSecurityAuditVisitorNoIssues() {
        SourceCodeFile goodFile = new SourceCodeFile("Safe.java", 20, 2, false);
        SecurityAuditVisitor visitor = new SecurityAuditVisitor();

        goodFile.accept(visitor);
        assertEquals(0, visitor.getIssuesFound());
    }

    @Test
    void testProjectStatisticsVisitorCalculations() {
        SourceCodeFile src = new SourceCodeFile("A.java", 100, 10, false);
        TestFile test = new TestFile("ATest.java", 50, 5);

        ProjectStatisticsVisitor stats = new ProjectStatisticsVisitor();
        src.accept(stats);
        test.accept(stats);

        assertTrue(stats.getReport().contains("Total Files: 2"));
        assertTrue(stats.getReport().contains("Total Lines of Code: 150"));
        assertEquals(10.0, stats.getAverageComplexity());
        assertEquals(5.0, stats.getTestToSourceRatio());
    }

    @Test
    void testQualityGatePass() {
        ProjectStatisticsVisitor stats = new ProjectStatisticsVisitor();
        SourceCodeFile src = new SourceCodeFile("A.java", 100, 5, false);
        TestFile test = new TestFile("ATest.java", 50, 2);
        SecurityAuditVisitor security = new SecurityAuditVisitor();

        src.accept(stats);
        test.accept(stats);
        src.accept(security);

        QualityGateVisitor qg = new QualityGateVisitor();
        qg.evaluate(stats, security);

        assertTrue(qg.isPassed());
    }

    @Test
    void testQualityGateFail() {
        ProjectStatisticsVisitor stats = new ProjectStatisticsVisitor();
        SourceCodeFile badSrc = new SourceCodeFile("Bad.java", 100, 15, true);
        SecurityAuditVisitor security = new SecurityAuditVisitor();

        badSrc.accept(stats);
        badSrc.accept(security);

        QualityGateVisitor qg = new QualityGateVisitor();
        qg.evaluate(stats, security);

        assertFalse(qg.isPassed());
    }

    @Test
    void testComplianceVisitorViolations() {
        ConfigurationFile badConfig = new ConfigurationFile("app.yml", true);
        ComplianceVisitor compliance = new ComplianceVisitor();

        badConfig.accept(compliance);
        assertTrue(compliance.getReport().contains("FAILED"));
    }

    @Test
    void testEmptyDirectoryEdgeCase() {
        Directory emptyDir = new Directory("empty");
        ProjectStatisticsVisitor stats = new ProjectStatisticsVisitor();

        emptyDir.accept(stats);
        assertEquals(0, stats.getAverageComplexity());
        assertEquals(0.0, stats.getTestToSourceRatio());
    }

    @Test
    void testTechnicalDebtVisitor() {
        SourceCodeFile file = new SourceCodeFile("Complex.java", 120, 15, false);
        TechnicalDebtVisitor debtVisitor = new TechnicalDebtVisitor();

        file.accept(debtVisitor);
        assertTrue(debtVisitor.getTechnicalDebtScore() > 0);
        assertTrue(debtVisitor.getReport().contains("TECHNICAL DEBT REPORT"));
    }
}