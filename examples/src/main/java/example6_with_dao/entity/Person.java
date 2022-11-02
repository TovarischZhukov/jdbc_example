package example6_with_dao.entity;

import org.jetbrains.annotations.NotNull;

public final class Person {

  public final int id;
  public final @NotNull String name;

  public Person(int id, @NotNull String name) {
    this.id = id;
    this.name = name;
  }

  @Override
  public @NotNull String toString() {
    return "Person{" +
      "id=" + id +
      ", name='" + name + '\'' +
      '}';
  }
}
