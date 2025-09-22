// 代码生成时间: 2025-09-23 01:30:35
package com.example.micronaut;

import io.micronaut.context.annotation.Requires;
import io.micronaut.core.convert.ArgumentConversionException;
import io.micronaut.core.convert.ConversionService;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.server.exceptions.InternalServerException;
import io.micronaut.jackson.serialize.JacksonObjectSerializer;
import javax.inject.Inject;

@Controller("/api/response")
@Requires(property = "api.response.formatter.enabled", value = "true")
public class ApiResponseFormatter {

    @Inject
    private ConversionService<?> conversionService;

    @Inject
    private JacksonObjectSerializer jacksonObjectSerializer;

    /**
     * Handles the API response formatting.
     *
     * @param request The HTTP request.
     * @param payload The payload to be formatted.
     * @return A formatted API response.
     */
    @Post("/{payload}")
    public String formatApiResponse(HttpRequest request, String payload) {
        try {
            // Attempt to convert the payload to an object using the ConversionService
            Object object = conversionService.convert(payload, Object.class)
                    .orElseThrow(() -> new ArgumentConversionException(payload, Object.class));

            // Serialize the object to JSON using the JacksonObjectSerializer
            return jacksonObjectSerializer.serialize(object, request.getCharset().orElse(null));
        } catch (ArgumentConversionException ex) {
            // Handle payload conversion errors
            return handleConversionError(ex);
        } catch (Exception ex) {
            // Handle any other exceptions
            throw new InternalServerException("Error formatting API response", ex);
        }
    }

    /**
     * Handles conversion errors by returning a formatted error message.
     *
     * @param ex The conversion exception.
     * @return A formatted error message.
     */
    private String handleConversionError(ArgumentConversionException ex) {
        // Create a simple error response
        return "{\
  \"status\": \"error\",\
  \"message\": \"Payload conversion error: 