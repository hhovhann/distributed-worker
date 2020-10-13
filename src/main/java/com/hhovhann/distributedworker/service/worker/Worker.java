package com.hhovhann.distributedworker.service.worker;

import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.*;

@Slf4j
public class Worker implements Callable<Integer> {
    private final String url;
    private static final HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).connectTimeout(Duration.ofSeconds(10)).build();

    public Worker(String url) {
        this.url = url;
    }

    @Override
    public Integer call() {
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url)).setHeader("User-Agent", "Distributed worker").build();
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        int result = -1;
        try {
            result = response.thenApply(HttpResponse::statusCode).get(5, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException ex) {
            log.error("", ex);
        }

        return result;
    }
}
