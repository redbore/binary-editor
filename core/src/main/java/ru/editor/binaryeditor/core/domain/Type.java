package ru.editor.binaryeditor.core.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
@Accessors(fluent = true)
public class Type {

    @NonNull
    private final UUID uuid;

    @NonNull
    private final String name;

    @NonNull
    private final List<Instance> instances;

    public Instance findInstance(UUID instanceId) {
        return instances.stream()
                .filter(instance -> instance.uuid().equals(instanceId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Instance not found"));
    }
}
