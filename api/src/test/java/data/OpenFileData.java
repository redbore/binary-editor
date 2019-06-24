package data;

import com.google.common.io.ByteStreams;
import org.springframework.core.io.Resource;
import rest.File;
import rest.OpenFile;

import java.io.IOException;

import static rest.ApiConverter.toIntArray;

public final class OpenFileData {

    public static OpenFile openFile(Resource binaryFile, Resource specification) throws IOException {
        byte[] binaryFileBody = ByteStreams.toByteArray(binaryFile.getInputStream());
        byte[] specificationBody = ByteStreams.toByteArray(specification.getInputStream());

        return OpenFile.builder()
                .binaryFile(File.builder()
                        .fileName(binaryFile.getFilename())
                        .fileBody(toIntArray(binaryFileBody))
                        .build()
                )
                .specification(File.builder()
                        .fileName(specification.getFilename())
                        .fileBody(toIntArray(specificationBody))
                        .build())
                .build();
    }
}
