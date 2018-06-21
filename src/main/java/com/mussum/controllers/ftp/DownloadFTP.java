package com.mussum.controllers.ftp;

import com.mussum.controllers.ftp.utils.FTPcontrol;
import com.mussum.controllers.ftp.utils.Convert;
import com.mussum.util.S;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import java.io.InputStream;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
public class DownloadFTP {

    private final FTPcontrol ftp = new FTPcontrol();

    @GetMapping("/api/download")
    public ResponseEntity getFile(
            @RequestHeader("professor") String prof,
            @RequestHeader("dir") String dir,
            @RequestHeader("fileName") String fileName) {
        try {

            S.out("requesting FILE: " + dir + fileName, this);

            ftp.connect();
            ftp.getFtp().setSoTimeout(3000);
            InputStream file = ftp.getFile(dir + "/", fileName);
            //ftp.getFtp().completePendingCommand();
            ftp.disconnect();
            byte[] bytes = StreamUtils.copyToByteArray(file);
            //file.close();
            if (file == null) {
                S.out("ERRO: File not found", this);
                return new ResponseEntity("ERRO: File not found", HttpStatus.NOT_FOUND);
            }

            S.out("file served.", this);
            return new ResponseEntity(bytes, HttpStatus.OK);
        } catch (Exception ex) {
            ftp.disconnect();
            S.out("ERRO: " + ex.getMessage(), this);
            return new ResponseEntity("Erro: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/api/photo")
    public ResponseEntity getProfessorPhoto(@RequestHeader("professor") String prof) {
        try {
            S.out("requesting user PHOTO: " + prof, this);

            ftp.connect();
            ftp.getFtp().setSoTimeout(60);
            String[] fotosPerfil = ftp.getContentFrom("\\_res\\perfil_img\\");
            String profPhotoName = null;
            for (String foto : fotosPerfil) {
                S.out("FOTO: " + foto, this);
                if (foto.startsWith(prof)) {
                    profPhotoName = foto;
                    break;
                }
            }

            InputStream img = ftp.getFile("\\_res\\perfil_img\\", profPhotoName);
            if (img == null) {
                ftp.disconnect();
                S.out("ERRO: photo " + profPhotoName + " not found", this);
                return new ResponseEntity("ERRO: photo not found", HttpStatus.NOT_FOUND);
            }
            ftp.disconnect();

            String base64 = Convert.inputStreamToBASE64(img);
            S.out("user photo served.", this);
            return new ResponseEntity(base64, HttpStatus.OK);

        } catch (Exception ex) {
            ftp.disconnect();
            S.out("ERRO: " + ex.getMessage(), this);
            return new ResponseEntity("ERRO: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

}
