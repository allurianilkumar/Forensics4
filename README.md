In this email you will be working with email dataset. Email messages can be very important and useful
source of information. Moreover, it can be the only source of evidence a forensics specialist has. From
our class about email forensics, several aspects are important in the investigation. These are, technology
used, location of storing messages, content of the messages, source and distention, and time and date
stamps. Technology wise, the focus is around the protocols and platforms used on the server and client
sides. Content, source and distention, and time stamps can be found in the message itself as we have
seen in the class when we explored the “hidden” header. We have seen how to access that using SAU
and Gmail email systems.
Objectives

1- Learn the importance of email messages for forensics work
2- Learn the different parts of an email message
3- Learn how to extract tracing information from a message
4- Learn to extract time stamps of the message journey
5- Learn to analyze the content of an email message
6- Learn to create some statistical data based on email messages content
7- Learn to use geo-location to locate an IP address that an email message passed by

In this work, you will be provided with an email dataset of 1000 emails. This dataset is taken from the
CSDMC2010 SPAM corpus, which is one of the datasets used for a competition associated with ICONIP
2010. The emails have the header data which is very important for a forensics task.
In this work you are asked to develop a tool that does the following:
Word search:
In this part, the email messages containing a defined text will be retrieved.
The goal is to find email messages that are relevant to the context defined by the search term.
1- For simplicity, the user enters only one word for the search
2- The tool should output a list of email files where this word appears
Find contacts:
In this part, you are asked to find the email addresses exchanging emails with a given email address.
The goal of this part is to track emails that communicated with an email of interest.
1- The user gives an email address.
2- The tool will show a list of email addresses that communicated with the given address. Either
sending or receiving.


Track an email message:
In this part, you are asked to list the IP addresses given the name of an email message file.
The goal of this part is to track the journey of an email message using the IP addresses appearing in that
message header.
1- The user in this part enters the name of the email message file
2- The output will be a list of IP addresses with the time stamps associated with the IP addresses.
Locate an IP address:
In this part your tool should accept an IP address and then report the IP address geo-location.
The goal in this part is to be able to use web services provided by a website, given in the slides, to find
the approximate address that was used to originate the message:
1- The user enter the IP address
2- The tool should show: country, city, longitude and latitude for the given IP address
Content Analysis: (bonus part)
In this part, you are asked to find word frequency/word count for the top 5 most frequent words.
The goal of this work is to have a closer look of the theme of the email messages to be analyzed.
1- You are required to do this based on the content part only. Therefore, you have to exclude the
header parts while implementing this task.
2- Notice that you should ignore the so-called stop words of the English language.
a. Check the section of Long Stopword List found in : http://www.ranks.nl/stopwords

Notes and Hints

1- When you run your tool, a list of options will be displayed to choose from, similar to:

2- The search, in the case of looking for IP addresses, can be done using regular expression, please
check this link https://pymotw.com/2/re/
3- You may think of creating a data structure that stores information about/of each email message
and then use this data structure to perform some/all of the tasks in this project; it is up to you.
a. For this data structure, I suggest you look into the entire assignment first and see how
you will design and use the data structure.

# Contact me for more details kumarallurianil@gmail.com
