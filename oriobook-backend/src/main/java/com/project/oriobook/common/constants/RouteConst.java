package com.project.oriobook.common.constants;

import lombok.Getter;
import org.springframework.data.util.Pair;

import java.util.Arrays;
import java.util.List;

@Getter
public class RouteConst {
    private final String apiPrefix;

    private List<Pair<String, String>> bypassRoutes;

    public RouteConst(String apiPrefix) {
        this.apiPrefix = apiPrefix;
        init();
    }

    public void init() {
        bypassRoutes = Arrays.asList(
                Pair.of(String.format("%s/products**", apiPrefix), "GET"),
                Pair.of(String.format("%s/categories**", apiPrefix), "GET"),
                Pair.of(String.format("%s/auth/sign-up", apiPrefix), "POST"),
                Pair.of(String.format("%s/auth/login", apiPrefix), "POST"),
                Pair.of(String.format("%s/auth/refresh-token", apiPrefix), "POST"),

                Pair.of("/api-docs", "GET"),
                Pair.of("/api-docs/**", "GET"),
                Pair.of("/swagger-resources", "GET"),
                Pair.of("/swagger-resources/**", "GET"),
                Pair.of("/configuration/ui", "GET"),
                Pair.of("/configuration/security", "GET"),
                Pair.of("/swagger-ui/**", "GET"),
                Pair.of("/swagger-ui.html", "GET"),
                Pair.of("/swagger-ui/index.html", "GET")
        );
    }
}
