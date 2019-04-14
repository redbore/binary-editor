package ru.editor.binaryeditor.core.domain;

import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
public class Type {

  @NonNull
  private final UUID uuid;

  @NonNull
  private final String name;

  @NonNull
  private final List<Instance> instances;

  public Instance findInstance(UUID instanceId) {
    return instances.stream()
        .filter(instance -> instance.getUuid().equals(instanceId))
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Instance not found"));
  }
}
