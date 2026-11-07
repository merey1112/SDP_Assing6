# Smart Code Inspection & Compliance Engine (Visitor Pattern)

## Project Overview
This project implements a Smart Code Inspection & Compliance Engine in **Java 17+** using the **Visitor Design Pattern** and **Composite Pattern** for directory structures. It analyzes project components (source files, tests, configurations, and dependencies) across multiple operations (security audits, statistics, quality gates, compliance, and technical debt analysis).

---

## Answers to Assignment Questions

### 1. What problem does Visitor solve in your project?
Visitor separates data structures (project elements like files and directories) from the operations performed on them (audit, statistics, compliance), avoiding cluttering element classes with evaluation logic.

### 2. Where are Element and Visitor?
* **Element hierarchy:** Located in `com.engine.element` (`ProjectElement`, `SourceCodeFile`, `TestFile`, `ConfigurationFile`, `DependencyFile`, `Directory`).
* **Visitor interface & implementations:** Located in `com.engine.visitor` (`ProjectVisitor`, `SecurityAuditVisitor`, `ProjectStatisticsVisitor`, `QualityGateVisitor`, `ComplianceVisitor`, `TechnicalDebtVisitor`).

### 3. What is double dispatch?
Double dispatch is a mechanism where the operation executed depends on two factors: the runtime type of the visitor and the runtime type of the element. In Java, this is achieved by calling `element.accept(visitor)` (dispatch 1: determines element type) which immediately calls `visitor.visit(this)` (dispatch 2: determines visitor type based on specific element overload).

### 4. Why not put all operations directly inside the Element classes?
Putting operations directly inside elements violates the **Open-Closed Principle (OCP)**. Every time a new audit or report type is added, we would have to modify every single element class.

### 5. Is it easier to add a new Visitor or a new Element? Why?
* **Adding a new Visitor is easy:** You simply create a new class implementing `ProjectVisitor` without touching existing element classes.
* **Adding a new Element is hard:** If you add a new element (e.g., `DatabaseMigrationFile`), you must modify the `ProjectVisitor` interface and **every single concrete visitor** to implement a `visit(...)` method for that new element.

### 6. When would Visitor be a bad choice?
Visitor is a bad choice when the element class hierarchy is volatile and changes frequently (e.g., adding new types of files often), because every addition forces changes across all existing visitors.

### 7. How is Visitor different from Strategy?
* **Strategy** encapsulates a single algorithm for a single object or component.
* **Visitor** operates on a heterogeneous object structure (like a tree/composite of various nodes) and allows applying multiple distinct operations across the entire structure.

### 8. Which SOLID principles can you identify?
* **Single Responsibility Principle (SRP):** Each visitor has one specific job.
* **Open-Closed Principle (OCP):** The element hierarchy is closed for modification when adding new operations.
* **Interface Segregation Principle (ISP):** Clients depend on cleanly separated interfaces (`ProjectElement` and `ProjectVisitor`).