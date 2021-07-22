[![Build](https://github.com/applibgroup/amplify/actions/workflows/main.yml/badge.svg)](https://github.com/applibgroup/amplify/actions/workflows/main.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=applibgroup_amplify&metric=alert_status)](https://sonarcloud.io/dashboard?id=applibgroup_amplify)

# Amplify
Amplify library for Harmony OS source code
Amplify library is used to prompt users for feedback at the right times. Based on ruled defined by us library determines the right time to ask for feedback and based on their reaction, users are given choice to leave a quick rating/review on the Huawei App gallery 


![image](https://user-images.githubusercontent.com/48115293/126625787-5f1625da-414e-46d7-91b3-62b0ea72a931.png)


# Source
Inspired from android library https://github.com/stkent/amplify

## Features
We can define several rules to determine the right time to prompt for feedback which takes into account the number of days since the app is installed, last asked for feedback, update in the app etc. Library tracks significant events and prompts only when all defined rules allow to do so

In addition to this we can design the layout of the feedback prompt. Users after submitting their feedback are given choice to leave a quick rating/review on the Huawei App gallery 

If User gives positive feedback


![image](https://user-images.githubusercontent.com/48115293/126626270-db79face-6a95-4879-bd9c-962ed39b8048.png)


If we get critical feedback


![image](https://user-images.githubusercontent.com/48115293/126626380-e8e5cb2a-28d5-4fab-8562-341aa5610c99.png)




## Usage
1. Initialize the shared Amplify instance in the MyApplication class and supply feedback collectors that determine where positive and critical feedback should be directed
 
![image](https://user-images.githubusercontent.com/48115293/126281583-0f160b50-4c73-4111-a819-eb0073720dff.png)


By default, the HuaweiAppgalleryFeedbackCollector will search their stores for the app whose package name matches the running application. If your app's build variants do not all share a single package name, amplify will fail to load the appropriate App Gallery page in non-release builds. To fix this, pass your release build package name to the
FeedbackCollectors during initialization.

![image](https://user-images.githubusercontent.com/48115293/126281432-60628c4a-093a-4b18-b447-bca2b41f0e9f.png)


2. Now we have to add Prompt instance to XML layouts where you may want to prompt the user for their feedback and get the shared Amplify instance in the MainAbilitySlice class and call promptIfReady method and pass the Prompt instance 

We have different options to do so, we can have defaultlayout prompt view or custom layout prompt view and can go with default settings or customize according to our need.

a) Default promptview layout with all default settings

![image](https://user-images.githubusercontent.com/48115293/126280140-53139238-d286-4213-8a97-ce6578679792.png)
![image](https://user-images.githubusercontent.com/48115293/126280520-e3803618-bd8f-47e4-817d-b7030714d9ab.png)


b) Default promptview layout with code config

![image](https://user-images.githubusercontent.com/48115293/126280172-7d8b9a3a-57bc-4a3b-913b-26b6182ee927.png)
![image](https://user-images.githubusercontent.com/48115293/126280366-f622cb12-1e74-4791-a133-5156525466c7.png)


c) Default promptview layout with xml config

![default_xml_config_xml](https://user-images.githubusercontent.com/48115293/126279563-70d6ca43-5fbf-4fb6-a81c-d7e8f6e02aff.png)
![default_xml_config_code](https://user-images.githubusercontent.com/48115293/126279513-4d2147c6-4a09-4793-9cc0-8295b07ef635.png)


d) Custom promptview layout with code config

   Here we have to define question view and thanks view xml layout as well
   
   ![image](https://user-images.githubusercontent.com/48115293/126280089-c39d2711-87bb-4717-8efb-099a6bea3fe4.png)
   ![image](https://user-images.githubusercontent.com/48115293/126280258-261c2b15-369f-42b3-bff8-69f898f45fff.png)

   Custom question view
   
  ![custom_question_xml](https://user-images.githubusercontent.com/48115293/126280806-150743b5-88b7-4774-b609-7fdeec41fdb8.png)
  
  Custom thanks view
  
  ![custom_thanks_xml](https://user-images.githubusercontent.com/48115293/126280882-1dd7b22d-dbcb-4f40-9089-f5e68fa638c1.png)

## Limitations
1. currently the feature to open email client from the app is not there, so for time being they are also given choice to review on App gallery
and those with critical feedback are given choice to send a complaint email that will automatically include pertinent app and device information
2. Option for adding border for buttons is not there, can include border width and border color attribute
## Future Work
1. Opening email client when user gives critical feedback with subject & device information in the text already filled, currently opening app gallery for both positive and critical feedback

## License

Copyright 2015 Stuart Kent

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.





