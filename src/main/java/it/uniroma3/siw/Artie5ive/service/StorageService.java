package it.uniroma3.siw.Artie5ive.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.Map;
import java.util.UUID;

@Service
public class StorageService {

    @Autowired(required = false)
    private Cloudinary cloudinary;

    @Value("${cloudinary.cloud-name:#{null}}")
    private String cloudName;

    private final Path uploadDir = Paths.get("uploads");

    public StorageService() {
        try {
            Files.createDirectories(Paths.get("uploads"));
        } catch (IOException e) {
            // ignora se già esiste
        }
    }

    public String salva(MultipartFile file) {
        if (file == null || file.isEmpty()) return null;

        // Se Cloudinary è configurato, usa quello
        if (cloudName != null && cloudinary != null) {
            try {
                Map result = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap("folder", "artie5ive")
                );
                return (String) result.get("secure_url");
            } catch (IOException e) {
                throw new RuntimeException("Errore upload Cloudinary", e);
            }
        }

        // Altrimenti salva in locale come prima
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

    public void elimina(String riferimento) {
        if (riferimento == null) return;

        // Se è un URL Cloudinary
        if (riferimento.startsWith("http")) {
            try {
                // Estrai public_id dall'URL
                String publicId = estraiPublicId(riferimento);
                cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            } catch (IOException e) {
                // log ma non bloccare
            }
            return;
        }

        // Altrimenti elimina dal filesystem locale
        try {
            Files.deleteIfExists(uploadDir.resolve(riferimento));
        } catch (IOException e) {
            // log ma non bloccare
        }
    }

    private String estraiPublicId(String url) {
        // URL tipo: https://res.cloudinary.com/cloudname/image/upload/v123/artie5ive/nomefile.jpg
        // Public ID = artie5ive/nomefile (senza estensione)
        String[] parts = url.split("/upload/");
        if (parts.length < 2) return url;
        String afterUpload = parts[1];
        // Rimuovi versione (v123/)
        if (afterUpload.startsWith("v")) {
            afterUpload = afterUpload.substring(afterUpload.indexOf("/") + 1);
        }
        // Rimuovi estensione
        int lastDot = afterUpload.lastIndexOf(".");
        if (lastDot > 0) afterUpload = afterUpload.substring(0, lastDot);
        return afterUpload;
    }
}