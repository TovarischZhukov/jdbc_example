package example4_callable_statement;

import commons.JDBCCredentials;
import org.jetbrains.annotations.NotNull;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;

public final class Main {

    private static final @NotNull JDBCCredentials CREDS = JDBCCredentials.DEFAULT;

    public static void main(@NotNull String @NotNull [] args) {
        try (var connection = DriverManager.getConnection(CREDS.url(), CREDS.login(), CREDS.password())) {
            final var upperProc = connection.prepareCall("{ ? = call upper( ? ) }");
            upperProc.registerOutParameter(1, Types.VARCHAR);
            upperProc.setString(2, "lowercase to uppercase");
            upperProc.execute();
            final var upperCased = upperProc.getString(1);
            upperProc.close();
            System.out.println(upperCased);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
