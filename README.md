# ListApp
An app created to help create, prioritize, and manage busy lives through simple to do lists.

# Overview of Code Structure
We will be utilizing the model, view, controller design pattern. This will allow us to work simultaneously on separate modules without worrying about breaking one another's logic.
- All UI elements (JavaFX) will be maintained in the view directory. 
- All data elements have been created within the model directory. This models the data as it is in the database and converts the tables and fields into Java data structures. These are static classes. They do not "do" anything except provide the model of the tables
- All classes that "do things" will be maintained in the controller directory. This directory holds the DataAccess classes that define classes to Create, Update, and Delete elements from the tables.