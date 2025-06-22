# Java Practice Repository

This repository is a personal Java practice space designed to cover **language learning, experimentation, architectural exercises, practical problem-solving, and blog-ready code samples**.

Each directory is categorized by purpose, allowing you to organize and grow your knowledge in a structured yet flexible way.

---

## 🗂 Directory Structure

| Directory | Description |
|-----------|-------------|
| `playground/` | A free space for quick tests and experiments. No structure or polish required—just try things out. |
| `core-concepts/` | Java language fundamentals such as OOP, generics, exception handling, and access modifiers. |
| `api-snippets/` | Practical examples using core Java APIs like Stream, Optional, Collections, etc. |
| `coding-recipes/` | Handy, reusable solutions to common problems (e.g., LRU Cache, JSON parser). Great for interviews or production inspiration. |
| `patterns/` | Design pattern practice: GoF, concurrency patterns, behavioral/creational/structural designs. |
| `architecture/` | Application-level design examples including layered architecture, DDD, and hexagonal architecture. |
| `testing-and-tooling/` | Testing, logging, build tools like JUnit, Mockito, Logback, Lombok, and Gradle. |
| `jdk-exploration/` | Hands-on exploration of new features from Java 8 to 21 (e.g., Records, Virtual Threads). |
| `labs/` | Deeper exploration of JVM internals, GC, class loading, reflection, etc. |
| `benchmarks/` | Benchmarking for performance comparisons across algorithms, data structures, and APIs. |
| `blog-samples/` | Clean, well-documented code examples for blog posts, presentations, or tutorials. |
| `drafts/` | Unpolished ideas, prototypes, and scratch work. Move them to another directory once stabilized. |

---

## 🧪 How to Run Examples

Each directory is set up as a standalone Gradle project. You can run them independently via CLI or within IntelliJ IDEA.

```bash
# Example: Build core-concepts project
cd core-concepts
./gradlew build

# Run tests
./gradlew test
```

---

## 🧰 Development Workflow

1. **Start in `playground/` or `drafts/`** for quick experiments or new ideas.
2. **Promote** the code to a structured directory when it becomes more refined (e.g., `core-concepts/`, `coding-recipes/`, etc.).
3. **Polish the code** by organizing comments, adding README files, and writing tests.
4. **Move to `blog-samples/`** when preparing for external sharing (e.g., blog post or talk).

---

## 🗒️ Development Notes

* Unit tests go under `src/test/java` even for small examples.
* Blog-ready samples should include clear `README.md` files and code comments.
* Experimental results can be documented in `RESULT.md` or `README.md` inside `labs/` or `benchmarks/`.

---

## 📜 License

This project is licensed under the MIT License. Feel free to use or modify it for your own purposes.

---

## 🙋‍♂️ Author

* **polynomeer** – [polynomeer.github.io](https://polynomeer.github.io)
* GitHub: [@polynomeer](https://github.com/polynomeer)
