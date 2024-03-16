package pt.com.equadis.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "customer response dto", name = "Customer DTO")
public record CustomerDto(Long id, String name) {
}
