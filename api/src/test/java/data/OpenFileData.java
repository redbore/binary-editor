package data;

import com.google.common.io.ByteStreams;
import org.springframework.core.io.Resource;
import rest.File;
import rest.OpenFile;
import rest.OpenFile.OpenFileBuilder;

import java.io.IOException;

import static rest.ApiConverter.toIntArray;

public final class OpenFileData {

    public static OpenFile openFile(Resource binaryFile, Resource specification) throws IOException {
        return anOpenFile(binaryFile, specification).build();
    }

    public static OpenFileBuilder anOpenFile(Resource binaryFile, Resource specification) throws IOException {
        byte[] binaryFileBody = ByteStreams.toByteArray(binaryFile.getInputStream());
        byte[] specificationBody = ByteStreams.toByteArray(specification.getInputStream());

        return OpenFile.builder()
                .binaryFile(File.builder()
                        .name(binaryFile.getFilename())
                        .body(toIntArray(binaryFileBody))
                        .build()
                )
                .specification(File.builder()
                        .name(specification.getFilename())
                        .body(toIntArray(specificationBody))
                        .build());
    }
}
