# NationalArchives
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
