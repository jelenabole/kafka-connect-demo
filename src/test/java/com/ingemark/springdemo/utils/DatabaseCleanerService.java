package com.ingemark.springdemo.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.hibernate.MappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

@Component
public class DatabaseCleanerService {

    private static final List<String> tableNamesToIgnore = List.of("flyway_schema_history", "shedlock");

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private DataSource dataSource;

    @Transactional
    public void deleteAll() throws SQLException {
        getTablesToClean().forEach(t -> {
            var queryString = format("TRUNCATE %s RESTART IDENTITY CASCADE", t);
            entityManager.createNativeQuery(queryString).executeUpdate();
        });
    }

    private List<String> getTablesToClean() throws MappingException, SQLException {
        var tablesToClean = new ArrayList<String>();

        try (var connection = dataSource.getConnection()) {
            var resultSet = connection.getMetaData()
                    .getTables(connection.getCatalog(), null, null, new String[]{"TABLE"});

            while (resultSet.next()) {
                var tableName = resultSet.getString("TABLE_NAME");
                if (tableNamesToIgnore.contains(tableName)) {
                    continue;
                }
                var schemaName = resultSet.getString("TABLE_SCHEM");
                tablesToClean.add(format("%s.%s", schemaName, tableName));
            }
        }

        return tablesToClean;
    }
}
