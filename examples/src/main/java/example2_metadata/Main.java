package example2_metadata;

import commons.JDBCCredentials;
import org.jetbrains.annotations.NotNull;

import java.sql.DriverManager;
import java.sql.SQLException;

public final class Main {

    private static final @NotNull JDBCCredentials CREDS = JDBCCredentials.DEFAULT;

    public static void main(@NotNull String @NotNull [] args) {
        try (var connection = DriverManager.getConnection(CREDS.url(), CREDS.login(), CREDS.password())) {
            final var metaData = connection.getMetaData();
            final var resultSet = metaData.getTables(
                    "",
                    "public",
                    null,
                    new String[]{"TABLE"}
            );
            while (resultSet.next()) {
                System.out.println(resultSet.getString("TABLE_NAME"));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
