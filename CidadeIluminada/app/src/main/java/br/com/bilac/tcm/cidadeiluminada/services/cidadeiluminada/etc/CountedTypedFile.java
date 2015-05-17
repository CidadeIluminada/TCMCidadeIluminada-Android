package br.com.bilac.tcm.cidadeiluminada.services.cidadeiluminada.etc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import br.com.bilac.tcm.cidadeiluminada.services.cidadeiluminada.listeners.ProgressListener;
import retrofit.mime.TypedFile;

/**
 * Created by arthur on 16/05/15.
 */
public class CountedTypedFile extends TypedFile {
    public static final int BUFFER_SIZE = 4096;

    private final ProgressListener listener;

    public CountedTypedFile(String mimeType, File file, ProgressListener listener) {
        super(mimeType, file);
        this.listener = listener;
    }

    @Override public void writeTo(OutputStream out) throws IOException {
        byte[] buffer = new byte[BUFFER_SIZE];
        long total = 0;
        try (FileInputStream in = new FileInputStream(super.file())) {
            int read;
            while ((read = in.read(buffer)) != -1) {
                total += read;
                this.listener.transferred(total);
                out.write(buffer, 0, read);
            }
        }
    }
}
