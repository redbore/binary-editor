package ru.editor.binaryeditor.core.services;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import lombok.RequiredArgsConstructor;
import ru.editor.binaryeditor.core.domain.BinaryFile;
import ru.editor.binaryeditor.core.domain.Field;
import ru.editor.binaryeditor.core.domain.Instance;
import ru.editor.binaryeditor.core.domain.Type;
import ru.editor.binaryeditor.core.domain.XmlField;
import ru.editor.binaryeditor.core.domain.XmlFile;
import ru.editor.binaryeditor.core.domain.XmlSegment;

@RequiredArgsConstructor
public class BinaryFileWriter {

  private final SettingsService settingsService;
  private final XmlFileReader xmlFileReader;

  private ByteBuffer byteBuffer;

  public BinaryFile write(BinaryFile binaryFile) throws Exception {
    XmlFile xmlFile = xmlFileReader.read();
    byteBuffer = ByteBuffer.allocate(binaryFile.getSize(xmlFile));

    binaryFile.getTypes()
        .forEach(type -> writeType(type, xmlFile.findXmlSegment(type.getName())));

    Files.write(settingsService.getBinaryFilePath(), byteBuffer.array());

    byteBuffer = null;
    return binaryFile;
  }

  private void writeType(Type type, XmlSegment xmlSegment) {
    type.getInstances()
        .forEach(instance -> writeInstance(instance, xmlSegment));
  }

  private void writeInstance(Instance instance, XmlSegment xmlSegment) {
    instance.getFields()
        .forEach(field -> writeField(field, xmlSegment.findXmlField(field.getName())));
  }

  private void writeField(Field field, XmlField xmlField) {
    Object value = field.getValue();
    switch (xmlField.getType()) {
      case "int":
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN)
            .putInt(toInt(value));
        break;
      case "float":
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN)
            .putFloat(toFloat(value));
        break;
      case "string":
        byteBuffer.order(ByteOrder.BIG_ENDIAN)
            .put(fixSizeString(toString(value), xmlField.getLength()).getBytes());
        break;
      default:
        throw new RuntimeException();
    }
  }

  private static Integer toInt(Object value) {
    return value instanceof Integer ? (Integer) value : 0;
  }

  private static Float toFloat(Object value) {
    return value instanceof Float ? (Float) value : 0f;
  }

  private static String toString(Object value) {
    return value instanceof String ? (String) value : "";
  }

  private static String fixSizeString(String value, Integer length) {
    StringBuilder stringBuilder = new StringBuilder(value);
    if (value.length() < length) {
      int needSize = length - value.length();
      for (int i = 0; i < needSize; i++) {
        stringBuilder.append("\u0000");
      }
    }
    return stringBuilder.toString();
  }
}
