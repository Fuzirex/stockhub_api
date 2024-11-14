package stock.hub.api.controller;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

public class BaseController {

    public <T> ResponseEntity<T> ok() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentLanguage(LocaleContextHolder.getLocale());
        return ResponseEntity.ok().headers(headers).build();
    }

    public <T> ResponseEntity<T> ok(T result) {
        if (Objects.isNull(result)) return ResponseEntity.noContent().build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentLanguage(LocaleContextHolder.getLocale());
        return ResponseEntity.ok().headers(headers).body(result);
    }

    public <T> ResponseEntity<T> okReport(T result, String fileName) {
        if (Objects.isNull(result)) return ResponseEntity.noContent().build();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s", fileName))
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(result);
    }

}
