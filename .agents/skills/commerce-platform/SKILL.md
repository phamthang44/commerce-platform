```markdown
# commerce-platform Development Patterns

> Auto-generated skill from repository analysis

## Overview
This skill teaches the core development patterns and conventions used in the `commerce-platform` Java repository. It covers file naming, import/export styles, commit message formatting, and testing patterns. While no specific frameworks or automated workflows were detected, this guide provides best practices and commands to streamline your development process.

## Coding Conventions

### File Naming
- Use **PascalCase** for all file names.
  - **Example:**  
    `OrderService.java`, `ProductRepository.java`

### Import Style
- Use **relative imports** for referencing classes within the project.
  - **Example:**
    ```java
    import com.mycompany.commerceplatform.services.OrderService;
    ```

### Export Style
- Use **named exports** (public classes and methods).
  - **Example:**
    ```java
    public class OrderService {
        public void processOrder(Order order) { ... }
    }
    ```

### Commit Messages
- Follow the **Conventional Commits** specification.
- Use the `feat` prefix for new features.
- Keep commit messages concise (average 81 characters).
  - **Example:**
    ```
    feat: add payment gateway integration to checkout process
    ```

## Workflows

### Creating a New Feature
**Trigger:** When adding new functionality to the platform  
**Command:** `/new-feature`

1. Create a new Java file using PascalCase for the class name.
2. Implement the feature using relative imports for dependencies.
3. Export the class or methods as `public`.
4. Write corresponding test cases in a `*.test.*` file.
5. Commit changes using the `feat` prefix and a concise description.

### Writing and Running Tests
**Trigger:** When validating new or existing code  
**Command:** `/run-tests`

1. Create or update test files matching the `*.test.*` pattern.
2. Use your preferred Java testing framework (e.g., JUnit).
3. Run the tests using your build tool or IDE.
4. Review and fix any failing tests before committing.

## Testing Patterns

- Test files follow the `*.test.*` naming convention.
  - **Example:**  
    `OrderService.test.java`
- The specific testing framework is not enforced, but standard Java testing practices apply.
- Place test files alongside or in a dedicated test directory as per project structure.

**Sample Test File:**
```java
import org.junit.Test;
import static org.junit.Assert.*;

public class OrderServiceTest {
    @Test
    public void testProcessOrder() {
        OrderService service = new OrderService();
        // ...setup and assertions...
    }
}
```

## Commands
| Command        | Purpose                                        |
|----------------|------------------------------------------------|
| /new-feature   | Scaffold and commit a new feature              |
| /run-tests     | Run all test files in the repository           |
```
