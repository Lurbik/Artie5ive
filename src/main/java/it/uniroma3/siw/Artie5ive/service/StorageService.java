package it.uniroma3.siw.Artie5ive.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class StorageService {

    private final Path uploadDir = Paths.get("uploads");

    public StorageService() {
        try {
            Files.createDirectories(uploadDir);
        } catch (IOException e) {
            throw new RuntimeException("Impossibile creare la cartella uploads", e);
        }
    }

    public String salva(MultipartFile file) {
        if (file == null || file.isEmpty())
            return null;

        String nomeOriginale = file.getOriginalFilename();
        String estensione = nomeOriginale != null && nomeOriginale.contains(".")
                ? nomeOriginale.substring(nomeOriginale.lastIndexOf("."))
                : ".jpg";

        String nomeFile = UUID.randomUUID().toString() + estensione;

        try {
            Files.copy(file.getInputStream(),
                    uploadDir.resolve(nomeFile),
                    StandardCopyOption.REPLACE_EXISTING);
            return nomeFile;
        } catch (IOException e) {
            throw new RuntimeException("Errore nel salvataggio del file", e);
        }
    }

    public void elimina(String nomeFile) {
        if (nomeFile == null)
            return;
        try {
            Files.deleteIfExists(uploadDir.resolve(nomeFile));
        } catch (IOException e) {
            // log ma non bloccare
        }
    }
}