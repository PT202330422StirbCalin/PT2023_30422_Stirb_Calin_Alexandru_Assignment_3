package org.example;

import java.io.IOException;

/**

 The DatabaseDump class provides a utility for creating a database dump using the pg_dump command-line tool.
 It allows users to create a dump file of a PostgreSQL database.
 */
public class DatabaseDump {
    public static void createDump(){
        String databaseName = "postgres";
        String username = "postgres";
        String password = "password";
        String dumpFilePath = "C:\\Users\\stirb\\IdeaProjects\\Assignment3\\dump_file.sql";
        try{
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "pg_dump", "-U", username, "-Fc", "-f", dumpFilePath, databaseName);
            processBuilder.environment().put("PGPASSWORD",password);
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Database dump created successfully.");
            } else {
                System.out.println("Failed to create the database dump.");
            }
        }catch(IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
