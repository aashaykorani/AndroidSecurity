# Homework 4: Mobile Mess

# **Part 0: Working Repository and Submission**
## 1) Synchronize Your Repository and Acquire the Lab Material
---
Log into GitHub within any web browser and create an empty, **private** repository named ``<NetID>-appsec4``.

Now, using the Ubuntu 20.04.3 LTS course virtual machine (https://drive.google.com/file/d/1rhzwJaiFmKy8SbvQuLIP_EQBsIskDNMz/view?usp=sharing; `nyuappsec` is the password), open the command shell and execute the following commands, replacing ``<YourGitHubHandle>`` with your own GitHub username and ``<NetID>`` with your own NYU NetID:

> If you do not know how to create a personal access token to use with the command line, follow the instructions here: https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/creating-a-personal-access-token

> **⚠ Warning!** Using your personal access token directly within commands, as shown in the laboratory instructions, is not a secure practice. A threat actor in a position to view the command history of the user account on the system may abuse the token to gain unauthorized access and/or make unauthorized modifications to your GitHub account. Use an appropriately secured environment variable, an access-controlled file, or another mitigation technique if you want to avoid leaving your GitHub Personal Access Token in your command history.
```
cd ~
git clone https://github.com/NYUJRA/AppSec4.git AppSec4
cd AppSec4
git remote remove origin
git init
git remote add origin https://<YourGitHubHandle>:<YourPersonalAccessToken>@github.com/<YourGitHubHandle>/<NetID>-appsec4.git
git push -u origin main
```


You should now have a local working directory in ``~/AppSec4`` that is configured to use your remote GitHub repository at ``https://github.com/<YourGitHubHandle>/<NetID>-appsec4`` as a version control system.

## 2) What to Submit
---
Submit your AppSec4 repository along with detailed lab report, with screenshots, to describe the specific actions taken to complete each task. Enough detail should be provide that enable the recipient of your report to blindly reproduce your actions to achieve the same outcomes. Provide explanation to the observations that are interesting or surprising.

All lab tasks should be performed in the provided NYU-AppSec/CSGY9163 Ubuntu 20.04.3LTS virtual machine. Each task is required to include a minimum of 1 screenshot to prove your individual completion of the task. **Every screenshot is required to include the date and time displayed in your virtual machine; otherwise, credit for the particular task will be removed.**

For tasks involving source code or exploit code, include the important code snippets followed by explanation. Simply executing code without explanation will not be eligible for credit.

**Your report _must_ be written in markdown.** Create a folder in your AppSec4 repository, using "Report" as the new folder's name. Within `Report/`, create a sub-folder called "Artifacts" within it. Store all of your screenshots and related laboratory artifacts within the "Artifacts" sub-folder, and include **one** markdown file in the "Report" sub-folder, which will contain your documentation for both, Part 1 and Part 2, of this lab.

Your repository should now include the following file structure:

    - GiftCardSite/
        - ...
    - Report/
        - Artifacts/
            - <NetID>-screenshot1.jpg
            - <NetID>-screenshot2.jpg
        - <NetID>-AppSec-Lab4.md

> **Note.** The GiftCardSite/ source files should be updated with all of the fixes and modifications made throughout the lab. Throughout grading, the updated source files will be reviewed and corroborated with your lab report.

## 3) How to Submit
---

You must submit your assigment in two places.
1. In BrightSpace, submit a link to your assignment repository.
2. Update the "Assignment 4 Report URL" column in the grading allocation spreadsheet (https://docs.google.com/spreadsheets/d/1y57x4X4nI1FvESEGO9lxLw0HgQtfOn0z5aO3yIqoCy8/edit?usp=sharing) with a link to your private AppSec 1 repository. 

    2a. You must invite the professor(s) to collaborate on your private repository
        \
         - Professor Allen's GitHub handle is `NYUJRA`
        \
         - Professor Abdala's GitHub handle is `kurlee`

    2b. You must invite the course assistant(s) to collaborate on your private repository
        \
         - CA Ritik Roongta's GitHub handle is `racro`
         \
         - CA Vignesh Nadar's GitHub handle is `Vignesh-Nadar`
         \
         - CA Xiang Me's GitHub handle is `n132`

Timeliness of your submission will be corroborated with your GitHub commit history. Commits made after the assignment's submission deadline will not be considered for grading.


## Introduction

Your organization has decided that they want to make an Android application
available for students who want to purchase NYU GiftCards. They took the liberty
of hiring a contractor to create the application, but the code came back less
useful than desired. Though your boss never told you which contracting company
was hired, you're pretty sure it as Shoddy Corp's Cut-Rate Contracting. They
also created a back-end for the application to interact with, but that was given
to another member of your team at work to fix.

Like previously, it's up to you to fix the messy code and ensure that the
application works as intended. Luckily, someone has gone through
the code and compiled a list of things that need to change before your company
is ready to ship the application.

## Part 1: Setting up Your Environment

After starting Android studio, you should set up the Android Emulator. To do
this open Android Studio and go to:

```
Tools->AVD Manager
```
This will open a new window. In this window you should see a button that says:

```
+ Create Virtual Device...
```

Click this button. Another window will open asking you to choose a device. We
tested this application on an emulated Pixel 3a, so we suggest selecting that
model, then clicking next. It will then ask you to select a system image. We
recommend downloading the image labeled R, which has an API Level of 30, the
x86 ABI, and a Target of Android 11.0 (Google Play). You may need to download
this image before you can use it by clicking the Download link next to the image
name. This will open a window that will ask you to accept the terms and
conditions. After you accept the image will download.

After downloading the image, the Android Virtual Device Manager will ask you to
name the virtual device and select between Portrait or Landscape. Ensure
Portrait is selected, leave Graphics at Automatic, and ensure Enable Device
Frame is checked. Then click on the button labeled Finish.

Note that the first time you run the Android Emulator it will take some time.
The emulator will set up the device for you, so let it do its work.

After you have created an emulated Android device, it is time to import the
project. Select the following:

```
File->New->Import Project
```

This will spawn a window that allows you to navigate your file system. Navigate
to this folder, then select "GiftCardSite" which should appear with the default
Android symbol next to it. This is the project you will be working on. After you
import it you should be able to explore the code, run the emulator by pressing
the green play button in the top right hand side of Android Studio, and then
proceed to the next step.

## Part 2: It's all about intent

As you may remember from class, Android uses Intents to move in between parts of
an application, or to communicate between applications (thus providing
functionality the app doesn't naively support, like Web browsing).

### Part 2.1: What's the difference?

Intents, when not handled correctly, can cause problems. Take a look at the code
on lines 69 to 73 of SecondFragment.kt and lines 68 to 70 of ThirdFragment.kt.
These are two different ways of handling intents. For this portion of the
assignment, you should create a text file, called difference.txt, which answers
the following questions in 3 sentences or less.

1. What are the two types of Intents?
2. Which of the two types of Intents are more secure?
3. What type of Intent is shown on lines 69 to 73 of SecondFragment.kt?
4. What type of Intent is shown on lines 68 to 70 of ThirdFragment.kt?
5. Which of these two Intents is the proper way to do an Intent?

As the last question above hinted, one of these two Intents is not correct.
Fix the incorrect Intent, then in 3 sentences or less discuss in difference.txt
which file you modified and why.

### Part 2.2: Shutting out the world

It seems that the developers of the application wanted to allow other
applications to use Intents to launch the GiftCard application. However, this
isn't what your company wants. At this moment your company does not anticipate
a need for other applications to use Intents to launch Activities within the
GiftCard application.

For this part, you should remove the possibility of other applications using
Intents to launch activities of your application. To do this, changes will need
to be made to the AndroidManifest.xml file.

## Part 3: Can you read me out there?

Communication of data in transit is especially important. If communications are
not secured in transit, then network adversaries can read confidential data such
as passwords, or modify data in transit without worry. Unfortunately, the
developers of this application did not include any https encryption in calls to
the REST API that it is using in the backend. For this part of the application,
please secure all communication with the REST API using HTTPS. This modification
will require changes to the following files:

1. SecondFragment.kt
2. ThirdFragment.kt
3. CardScrollingActivity.kt
4. ProductScrollingActivity.kt
5. UseCard.kt
6. GetCard.kt
7. CardRecyclerViewAdapter.kt
8. RecyclerViewAdapter.kt

These changes should not be large. If you find yourself including new libraries,
or writing more lines of code instead of just modifying code that already exits
you are likely overthinking the problem.

## Part 4: Oops, was that card yours?

There exists a vulnerability in the REST API that allows users to use GiftCards that
do not belong to them. In a file called BUG.txt, explain why this vulnerability
may be occurring, and how it can be fixed. Your explanation should be no larger
than a paragraph.

You can start looking for this vulnerability in the following files:

1. UseCard.kt
2. CardInterface.kt

Think about how the application is telling the server which card to use, and how
that may be problematic.

## Part 5: Privacy is Important

Many modern Android applications collect large amounts of privacy-invasive
metrics about their users. This is very problematic, since many users carry
their devices at all times, and are unaware of the implications of granting a
permission.

In this section your goal is to remove all privacy invasive code. This is done
by removing all metric collecting code, all areas that needlessly interact with
sensors, and all permissions that are not needed for the basic functionality of
the application (buying, browsing, and using gift cards).

You should remove all necessary code in the following files:

1. AndroidManifest.xml
2. UserInfo.kt
3. CardScrollingActivity.kt
4. ProductScrollingActivity.kt

## Grading

Total points: 100

Part 2 is worth 30 points:

* 10 points for answering the questions.
* 10 points for fixing the correct intent.
* 10 points for closing the application to outside intents.

Part 3 is worth 20 points:

* 2.5 points for each file correctly modified to use HTTPS.

Part 4 is worth 20 points:

* 10 points for identifying the cause of the vulnerability.
* 10 points for describing a potential solution.

Part 5 is worth 30 points:

* 10 points for removing unneeded permissions.
* 10 points for removing metric collection API calls.
* 10 points for removing interaction with sensors.


## Concluding Remarks

Despite the fixes you've made, there are almost certainly still many
bugs lurking in the application, and the overall design of the application could
be better done. With enough changes, this application could serve as a decent
front-end for a REST API, but that API would also have to be audited.
