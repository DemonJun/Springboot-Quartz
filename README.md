# Springboot-Quartz-Demo
1. The IDE needs to install the plugin lombok.
2. The database connection pool uses Druid to replace the Quartz underlying database connection pool C3P0 as Druid, separating the business database and the Quartz database.
3. The Quartz database script is in the resources/quartz/schema folder. You need to create the database Quartz, execute the SQL script, and initialize the database.
4. Unit test method: startJob starts the task, resumeJob continues to execute the task, and TestJob class defines a test task.
