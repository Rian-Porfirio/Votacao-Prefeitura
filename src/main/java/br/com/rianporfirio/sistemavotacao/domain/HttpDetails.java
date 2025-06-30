package br.com.rianporfirio.sistemavotacao.domain;

import jakarta.persistence.Embeddable;
import lombok.Data;
import org.springframework.http.HttpMethod;

@Data
@Embeddable
public class HttpDetails {
    private int statusCode;
    private HttpMethod httpMethod;
    private String errorTrack;
    private String endpoint;
}
