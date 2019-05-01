package ru.editor.binaryeditor.core.services;

import ru.editor.binaryeditor.core.domain.Paths;

public interface SettingsService {

    void savePaths(Paths paths);

    Paths paths();
}
