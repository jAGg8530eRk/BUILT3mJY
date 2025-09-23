// 代码生成时间: 2025-09-24 06:57:19
package com.example.security;

import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
# NOTE: 重要实现细节
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.exceptions.HttpStatusException;
# NOTE: 重要实现细节
import io.micronaut.security.annotation.Secured;
# TODO: 优化性能
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.transaction.annotation.ReadOnly;
import javax.inject.Inject;
# 添加错误处理
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Controller("/api")
@Requires(property = "app.feature.security")
public class SqlInjectionPreventionService {
# 改进用户体验

    private final DataSource dataSource;
# NOTE: 重要实现细节

    @Inject
    public SqlInjectionPreventionService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Get("/users")
# 改进用户体验
    @ReadOnly
    @Secured(SecurityRule.IS_AUTHENTICATED)
    public HttpResponse<String> getUsers(@QueryValue(value = "name", defaultValue = "") String name) {
        try {
            return HttpResponse.ok(getUsersQuery(name));
        } catch (Exception e) {
            // Log the error and return a 500 status code with a generic error message
# FIXME: 处理边界情况
            return HttpResponse.serverError("Internal Server Error: " + e.getMessage());
        }
# TODO: 优化性能
    }

    private String getUsersQuery(String name) throws SQLException {
        String sql = "SELECT * FROM users WHERE name LIKE ?";
        try (var connection = dataSource.getConnection();
# 添加错误处理
             var statement = connection.prepareStatement(sql)) {

            statement.setString(1, name + "%"); // Using prepared statements to prevent SQL injection
# TODO: 优化性能

            try (ResultSet rs = statement.executeQuery()) {
                StringBuilder builder = new StringBuilder();
                while (rs.next()) {
                    builder.append("Name: ").append(rs.getString("name")).append(", ");
                }
                return builder.toString();
            }
        }
    }
}
