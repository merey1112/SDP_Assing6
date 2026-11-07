package com.engine;

import com.engine.element.*;
import com.engine.visitor.*;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== STARTING SMART CODE INSPECTION ENGINE ===");

        // 1. Создаем структуру проекта как в задании (fintech-project)
        Directory root = new Directory("fintech-project");

        Directory srcDir = new Directory("src");
        srcDir.addElement(new SourceCodeFile("PaymentService.java", 120, 8, false));
        srcDir.addElement(new SourceCodeFile("UserService.java", 90, 12, true)); // КРЕДЫ!

        Directory testsDir = new Directory("tests");
        testsDir.addElement(new TestFile("PaymentServiceTest.java", 60, 3));

        Directory configDir = new Directory("config");
        configDir.addElement(new ConfigurationFile("application.properties", true)); // НЕБЕЗОПАСНО!

        DependencyFile pomFile = new DependencyFile("pom.xml", List.of("spring-boot", "junit"), false);

        root.addElement(srcDir);
        root.addElement(testsDir);
        root.addElement(configDir);
        root.addElement(pomFile);

        // 2. Создаем посетителей
        SecurityAuditVisitor securityVisitor = new SecurityAuditVisitor();
        ProjectStatisticsVisitor statsVisitor = new ProjectStatisticsVisitor();
        ComplianceVisitor complianceVisitor = new ComplianceVisitor();

        // 3. Запускаем обход (Visitor pattern)
        root.accept(securityVisitor);
        root.accept(statsVisitor);
        root.accept(complianceVisitor);

        // 4. Оцениваем через Quality Gate
        QualityGateVisitor qualityGate = new QualityGateVisitor();
        qualityGate.evaluate(statsVisitor, securityVisitor);

        // 5. Выводим отчеты
        System.out.println("\n" + securityVisitor.getReport());
        System.out.println(statsVisitor.getReport());
        System.out.println(complianceVisitor.getReport());
        System.out.println(qualityGate.getReport());
    }
}