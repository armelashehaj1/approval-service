package net.threeisolutions.approvals.api.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubjectDto {
    @Size(max = 100)
    private String type;

    @Size(max = 200)
    private String id;

    @Size(max = 500)
    private String title;

    @Size(max = 2000)
    private String url;

    @Builder.Default
    private Map<String, Object> attributes = new HashMap<>();
}
