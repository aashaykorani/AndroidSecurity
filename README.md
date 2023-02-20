# **Android Security**

# Final Report

Click [here](https://github.com/aashaykorani/ank8821-appsec4/blob/master/Report/ank8821-Appsec4-Lab4.md) to view my final report for this project.

<!---
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

For tasks involving source code or exploit code, include the important code snippets followed by explanation. Simply executing or modifying code without explanation will not be eligible for credit.

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
--->

# **Part 1: The Only Part**
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


## Task 0 (0pts): Getting Started
---
> **NOTICE!** Consider allocating 8GB of RAM to the lab VM as well as any additional CPUs, if available. Android Studio documentation suggests 8GB of RAM is needed. Otherwise, your user experience may be impacted.

Using the following command, open the Android Studio Integrated Developer Environment (IDE):
```
android-studio ~/AppSec4/GiftCardSite
```

It may take several minutes for `Gradle sync` to complete. Once complete, verify that you can run the application on the Android virtual device configured for the lab. At the top of Android Studio, click the green "play" arrow icon next to the `app` and `AppSec4 Device` dropdown selections.

The Android virtual device should launch, the Android operating system should boot, and your application should be automatically executed within the virtual device. If all goes well, you have successfully built and launched the `GiftCardSite` mobile application for Android OS. Now, you can interact with the application in the emulated device (right side of screen), and you can also navigate, manage, update, and rebuild the source files for the application (left and middle of screen).

If you receive an error, such as `The emulator process for Appsec4 Device avd has terminated.` then you may need to free disk space by deleted previous assignment files or other unnecessary files that you may have installed into the virtual machine during previous labs.

> **Note:** If you have issues with the specific virtual device provided, you may delete it and create your own. Please ensure to use an ABI of x86_64 and a relatively recent version of the Android API, no matter the hardware device chosen to emulate.

Now, you are ready to make this Android application more secure!

## Task 1 (30pts): Getting Intentional
---
As you may remember from class, Android uses Intents to facilitate inter-process communications (IPC) between activities and applications.

**Task 1a (10pts): What's the difference?**

Intents, when not handled correctly, can cause problems. Take a look at the code
on lines 69 to 73 of `SecondFragment.kt` and lines 68 to 70 of `ThirdFragment.kt`.
These are two different ways of handling intents. For this portion of the
assignment, you should create a text file, called difference.txt, which answers
the following questions in 3 sentences or less.

1. What are the two types of Intents?
2. Which of the two types of Intents is more secure?
3. What type of Intent is shown on lines 69 to 73 of [SecondFragment.kt](GiftCardSite/app/src/main/java/com/example/giftcardsite/SecondFragment.kt)?
4. What type of Intent is shown on lines 68 to 70 of [ThirdFragment.kt](GiftCardSite/app/src/main/java/com/example/giftcardsite/ThirdFragment.kt)?
5. Between #4 and #5, which incorporates the more secure technique for implementing an Intent?

**Task 1b (10pts): Shutting Out The World**

As the last question above hinted, one of these two Intents is not ideal.
Fix the less secure Intent. Explain which file you modified, which lines of code you modified, and reason your modifications.

**Task 1c (10pts): Shutting Out The World**

It seems that the developers of the application wanted to allow other
applications to use Intents to launch the GiftCard application. However, this
isn't what your company wants. At this moment your company does not anticipate
a need for other applications to use Intents to launch Activities within the
GiftCard application.

For this part, you should remove the possibility of other applications - including other applications written by the same developer - to use us Intents to launch activities of your application. To do this, changes will need
to be made to the [AndroidManifest.xml](GiftCardSite/app/src/main/AndroidManifest.xml) file. 

## Task 2 (10pts): Stoppin' the Eavesdroppin'
---
Communication of data in transit is especially important. If communications are
not secured in transit, then network adversaries can read confidential data such
as passwords, or modify data in transit without worry. Unfortunately, the
developers of this application did not include any https encryption in calls to
the REST API that it is using in the backend. For this part of the application,
please secure all communication with the REST API using HTTPS. This modification
will require changes to the following files:

1. [SecondFragment.kt](GiftCardSite/app/src/main/java/com/example/giftcardsite/SecondFragment.kt)
2. [ThirdFragment.kt](GiftCardSite/app/src/main/java/com/example/giftcardsite/ThirdFragment.kt)
3. [CardScrollingActivity.kt](GiftCardSite/app/src/main/java/com/example/giftcardsite/CardScrollingActivity.kt)
4. [ProductScrollingActivity.kt](GiftCardSite/app/src/main/java/com/example/giftcardsite/ProductScrollingActivity.kt)
5. [UseCard.kt](GiftCardSite/app/src/main/java/com/example/giftcardsite/ProductScrollingActivity.kt)
6. [GetCard.kt](GiftCardSite/app/src/main/java/com/example/giftcardsite/GetCard.kt)
7. [CardRecyclerViewAdapter.kt](GiftCardSite/app/src/main/java/com/example/giftcardsite/api/model/CardRecyclerViewAdapter.kt)
8. [RecyclerViewAdapter.kt](GiftCardSite/app/src/main/java/com/example/giftcardsite/api/model/RecyclerViewAdapter.kt)

These changes should not be large. If you find yourself including new libraries,
or writing more lines of code instead of just modifying code that already exits
you are likely overthinking the problem.

## Task 3 (30pts): Oops! Was that card yours?
---

There exists a vulnerability in the REST API that allows users to use GiftCards that do not belong to them.

**Task 3a (20pts).** Review the API endpoints called by the application, and exploit this authorization issue. Explain the underlying vulnerability with as much details as you find appropriate. Document the process and explain the risk(s) in the context of the application's purpose and audience.

You can start looking for this vulnerability in the following files:

1. [UseCard.kt](GiftCardSite/app/src/main/java/com/example/giftcardsite/ProductScrollingActivity.kt)
2. [CardInterface.kt](GiftCardSite/app/src/main/java/com/example/giftcardsite/api/service/CardInterface.kt)

> *Hint:* It may be useful to proxy your Android traffic through an HTTP proxy, such as Burp Suite, to help identify the vulnerable API endpoint, narrowing down from those found in the [CardInterface.kt](GiftCardSite/app/src/main/java/com/example/giftcardsite/api/service/CardInterface.kt).

**Task 3b (10pts).** Think about how the application is telling the server which card to use and how that may be an issue. Without actually doing so, describe how you would recommend to go about fixing this issue, with as much detail as you find necessary to convey your recommendation(s). Ensure to distinguish your recommendation(s) as applicable to either, the Android mobile application source code or the remote REST API server and/or source code.

## Task 3 (30pts): Please Hold... Your Privacy Is Very Important To Us!
---

Many modern Android applications collect large amounts of privacy-invasive
metrics about their users. This is very problematic, since many users carry
their devices at all times, and are unaware of the implications of granting a
permission.

In this section your goal is to remove all privacy invasive code. This is done
by removing all metric collecting code, all areas that needlessly interact with
sensors, and all permissions that are not needed for the basic functionality of
the application (buying, browsing, and using gift cards).

For each sub-task, describe the exact source code modified/removed, and explain your reason for the modification.

**Task 3a.** Remove all unnecessary permissions from the mobile application, leaving only those necessary for the application to function and serve its purpose. For each, explain which files were affected, giving a snippet of the offending source code, along with a reason for the removal of each distinct code block.
> *Hint:* The Android Manifest is not the only place an application can request permissions.

**Task 3b.** Remove all unnecessary collections of metrics from the mobile application, leaving only those necessary for the application to function and serve its purpose. For each, explain which files were affected, giving a snippet of the offending source code, along with a reason for the removal of each distinct code block. If you left any, explain how they each necessarily support the application.
> *Hint:* It may be useful to proxy your Android traffic through an HTTP proxy, such as Burp Suite, to help identify the metrics being collected by the mobile app and correlate back to the offending source code.

**Task 3b.** Remove all unnecessary sensor interactions from the mobile application, leaving only those necessary for the application to function and serve its purpose. For each, explain which files were affected, giving a snippet of the offending source code, along with a reason for the removal of each distinct code block. If you left any, explain how they each necessarily support the application.


1. [AndroidManifest.xml](GiftCardSite/app/src/main/AndroidManifest.xml)
2. [UserInfo.kt](GiftCardSite/app/src/main/java/com/example/giftcardsite/api/service/UserInfo.kt)
3. [CardScrollingActivity.kt](GiftCardSite/app/src/main/java/com/example/giftcardsite/CardScrollingActivity.kt)
4. [ProductScrollingActivity.kt](GiftCardSite/app/src/main/java/com/example/giftcardsite/ProductScrollingActivity.kt)


## Concluding Remarks
---
Despite the fixes you've made, there are almost certainly still many
bugs lurking in the application, and the overall design of the application could
be better done. With enough changes, this application could serve as a decent
front-end for a REST API, but that API would also have to be audited.
