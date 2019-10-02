# National Archives
Code test for candidates.

## Instructions
Given a CSV file(text file that uses a comma to separate values) with the following structure:

filename, origin, metadata, hash  
file1, London, "a file about London", e737a6b0734308a08b8586720b3c299548ff77b846e3c9c89db88b63c7ea69b6  
file2, Surrey, "a file about The National Archives", a4bf0d05d8805f8c35b633ee67dc10efd6efe1cb8dfc0ecdba1040b551564967  
file55, Londom, "London was initially incorrectly spelled as Londom", e737a6b0734308a08b8586720b3c299548ff77b846e3c9c89db88b63c7ea69b6  
file4, Penrith, "Lake District National Park info", a4bf0d05d8805f8c35b633ee67dc10efd6efe1cb8dfc0ecdba1040b551564968

Can you please write a piece of code (in Java or Scala) that allows a specified value in a specified column (by title) with a new value.

You should provide a method

```void updateRowValues(String csvFileName, String column, String oldValue, String newValue)```

that would enable changing "Londom" to "London" in the "origin" column in the file with the "csvFileName" path.

Can you please check in the code in a github repository and send us the link to it?

If you have any questions, please send them to us.

## Notes
To exercise the code, use sbt in the root folder that contains the repository.
This solution was implemented in Scala and uses Scalatest and sbt project layout.
It was possible to include alternative 'style' of solution using libraries
eg Apache Spark Dataframe, or other Scala CSV parser / writer libraries. These
choices were not made to keep the solution simple and for a more expedient completion.

### Additional information
The solution should perhaps have 'overwritten' the original CSV file, but this was not 
done in order that the reviewers could simply check the faulty file against the corrected
file. 
The source file and the output file are located relative to the project structure in a 
src/resources folder. This was, again, to simplify and expedite the solution. The classpath
could have been searched for the CSV resource, or the location of the data files could be 
passed into an instance of the application as a parameter. This would be easy to achieve. 

### Supplement
In the interview, it was asked what would be needed to identify duplicate entries in the 
source data file. This was added to the code as a unit test, and a single function implementation. 
The test uses the provided data as having a duplicate entry as identified by the value of the 
hash. For each duplicate, it was required that the names of the files (column 1 in the data), 
be presented by their hash value.