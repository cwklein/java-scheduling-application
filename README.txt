Title: C195 Project - Scheduling App
Purpose: A GUI-based Java Application that facilitates the viewing and augmentation of company data. This application allows a user to more readily view, add, modify or delete customer/ appointment information from the provided SQL Database.

-------------------------------------------------------------------------------------------

Author: Colby Klein - WGU ID:001198444
Contact Information: cklei41@wgu.edu
Student Application Version: PRFA - QAM2
Date: 8/11/23

-------------------------------------------------------------------------------------------

IDE: IntelliJ Community 2021.1.3
JDK: Java SE 17.0.1
JavaFX: JavaFX-SDK-17.0.1

-------------------------------------------------------------------------------------------

Directions: Hopefully the UI is self-explanatory, but for the sake of being thorough the user should do as follows:

  L)From 'login':
      Enter their desired username and password in the corresponding fields and press submit, if they are correct then the application will move to 'appointments' as the default screen.

  User can navigate to:
      - 'customers' (C) by pressing "View Customer List"
      - 'appointments' (A) by pressing "View Appointments"
      - 'reports' (R) by pressing "View Report List"

  C)From 'customers' the user can:
      C.1)Add a new customer by selecting "Add".
      C.2)Modify an existing customer by clicking on the desired row in the tableview and then selecting "Modify".
      C.3)Delete an existing customer by clicking on the desired row in the tableview and then selecting "Delete" (This option will confirm that the user also intends to delete any assoicated appointments for the given customer to maintain database integrity).

  A)From 'appointments' the user can:
      A.1)Add a new appointment by selecting "Add".
      A.2)Modify an existing appointment by clicking on the desired row in the tableview and then selecting "Modify".
      A.3)Delete an existing appointment by clicking on the desired row in the tableview and then selecting "Delete".
      
  R)From 'reports' the user can:
      R.1)Generate a 'Monthly View by Type' Report by selecting an appointment type and month and then pressing submit in the corresponding box.
      R.2)Generate a 'Contact Schedule' Report by selecting a contact and then pressing submit in the corresponding box.
      R.3)Generate a 'Customer Schedule' Report by selecting a customer and then pressing submit in the corresponding box.

-------------------------------------------------------------------------------------------

A3F - Additional Report: The third report 'Customer Schedule' creates a report regarding the selected customer's schedule. This report includes both past and upcoming appointments for the selected customer and generates both a "Result Count" for the number of appointments and a table view displaying all of the selected customer's appointments.

-------------------------------------------------------------------------------------------

MySQL Connector: mysql-connector-java-8.0.27
