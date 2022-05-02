# Part 1: The Only Part
## Task 1: Getting Intentional

### **Task 1a: What's the difference?**
1) What are the two types of Intents?</br>
A - There are two types of intents in android - Implicit and Explicit intents.

2) Which of the two types of Intents is more secure?</br>
A - Explicit intent is more secure because here a target component/activity is specified in the code. Therefore, only that component/activity will be invoked. Whereas, an implicit intent specifies an action that can invoke any app on the device able to perform the action.

3) What type of Intent is shown on lines 69 to 73 of SecondFragment.kt?</br>
A - Implicit intent because it does not specify the specific activity to be invoked.

4) What type of Intent is shown on lines 68 to 70 of ThirdFragment.kt?</br>
A - Explicit intent because it specifically invokes the ProductScrollingActivity.

5) Between #4 and #5, which incorporates the more secure technique for implementing an Intent?</br>
A - ThirdFragment.kt because it implements an explicit intent.

### **Task 1b: Switching Intents**

Since the SecondFragment.kt file uses implicit intent, we made changes to that file and changed the intent from implicit to explicit. In order to do so, we commented out the lines 69 to 73 and added the lines highlighted in the below image. These lines specifically call the ProductScrollingActivity from our own app to browse the cards. This way, no other app on the phone will be invoked in order to perform this function, thus securing our application.

![](./Artifacts/task1-b.png)

### **Task 1c: Shutting Out The World**

In order to stop other apps from using intents to launch the activities of our app we can use the permission tag in the AndroidManifest.xml file. It declares a security permission that can be used to limit access to specific components or features of applications.

In the image below, the first line defines the protection level. It indicates the procedure the system should follow when determining whether or not to grant the permission to an application requesting it. In our case, we set the protection level to "signature". This tells the system to grant permission to the activities only if requesting application is signed with the same certificate as the application that declared the permission. If the certificate matches the system will automatically grant the permission. In the second line we give a name to our permission that will be specific to our own app. In the third line, we specify that our app uses the permission mentioned above.

![](./Artifacts/task1-c1.png)

In the below two images, we add the android:permission tag to all the activities in the AndroidManifest.xml except the MainActivity. By declaring this, only our app will be able to call these activities. No other app will be able to do so because that app will not be using the same permission as our app. 

Here, we leave out the MainActivity because if we add permission to that activity, our launcher will not be able to open the home screen of our app. Hence we do not add the permission tag to the main activity.

![](./Artifacts/task1-c2.png)

![](./Artifacts/task1-c3.png)

## Task 2: Stoppin' the Eavesdroppin'

In order to make the communication secure, it is mandatory we use the HTTPS protocol in our applications. Hence, we modify all the mentioned files in change all the places where `http://nyuappsec.com` was being called to `https://nyuappsec.com`

After making the changes and proxying the traffic we can see that our app now uses the https protocol to communicate with the server.

![](./Artifacts/task2-1.png)

![](./Artifacts/task2-2.png)

## Task 3: Oops! Was that card yours?

**Task 3a**

Here, in order to find out the vulnerable end point, we take the following steps:
- Setup our device to proxy traffic through Burp.
- Buy 1 giftcard and use it.
- The 1st image shows the call application makes to /api/use/44 end point.
- The number 44 looks like some sort of identification number for the giftcard.
- We then send our intercepted request to the repeater and try changing the number at the end of the api call and see what happens.
- After we forward our intercepted request, our user is left with 0 cards.
- However, in the 2nd image we can see that on changing the call to '/api/use/1' and sending the request, we can see that it succeeds.
- This shows that we even when our user did not have a card, we were able to use a card that did not belong to our user.

![](./Artifacts/task3-1.png)

![](./Artifacts/task3-2.png)