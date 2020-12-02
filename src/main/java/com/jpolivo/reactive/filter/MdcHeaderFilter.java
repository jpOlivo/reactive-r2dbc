package com.jpolivo.reactive.filter;

/*import static com.jpolivo.reactive.filter.LogHelper.getContextMapKey;
import static java.util.stream.Collectors.toMap;

import java.util.Map;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import lombok.NonNull;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

@Component
public class MdcHeaderFilter implements WebFilter {
    @Override
    @NonNull
    public Mono<Void> filter(
        @NonNull ServerWebExchange ex, 
        @NonNull WebFilterChain chain) {

        return chain.filter(ex)
                .contextWrite(
                    ctx -> addRequestHeadersToContext(ex.getRequest(), ctx)
                );
    }
    
    private Context addRequestHeadersToContext(
            final ServerHttpRequest request,
            final Context context) {

        final Map<String, String> contextMap = request
        		.getHeaders().toSingleValueMap().entrySet()
                .stream()
                .filter(x -> x.getKey().startsWith("x-"))
                .collect(
                        toMap(v -> v.getKey(),
                                Map.Entry::getValue
                        )
                );

        return context.put(getContextMapKey(), contextMap);
    }
}*/