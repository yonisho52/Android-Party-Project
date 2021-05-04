# Android Party App

Our final project in Android 1 Development class, Holon Institute of Technology. 

The project includes the application itself, which was written in Java in Android Studio, 
and the server, which was written in Node.js to act as the middle man between our application and MongoDB database. 

The server includes POST/GET HTTP requests to support the necessary functions of the application. 

The application includes the following features:

- Login page
- Registration page, with the option to register 3 different user types: Regular user, DJ user, and place owner user.
- After login, a calendar is shown along with a red dot symbol on dates that have event planned on it. 
- A DJ and place owner users can add events via the calendar fragment, and to include basic information about their event such as start and end time, location, what artist is performing at that event, address etc. 
- A general chat room is enabled, where everyone can message. Mostly made for DJs and place owners to promote their events, but also can be a cool place to hang out at. 
- For regular users, theres a fragment that would require a pin code of an event. The pin code itself is given at the entrance for the event, and that way the user can access poll and surveys made by the DJ during the party to get some feedback from the people at the event.
- A rating is provided for DJ and place owner type users, as well as events, all of them can be rated by regular users and the rating is updated after a new rate is provided. Ofcourse, a user can provide only 1 rating for a DJ / event / place. 
- The application uses SharedPreferances to make it easier for the user to come back to the application. 

# Results:

<p align="center">

| Main Page - Login  | Register - Regular User | Register - DJ User | Register - Place Owner User |
| ------------- | ------------- | ------------- | ------------- |
| Main page of the application  | Registration form of a regular user  | Registration form of a DJ type user  | Registration form of a place owner type user  |
| <img src="https://github.com/yonisho52/Android-Party-Project/blob/main/Result%20Images/login.jpg" width="180" >  | <img src="https://github.com/yonisho52/Android-Party-Project/blob/main/Result%20Images/regular-user.jpg" width="180" >  | <img src="https://github.com/yonisho52/Android-Party-Project/blob/main/Result%20Images/dj-user.jpg" width="180" >  | <img src="https://github.com/yonisho52/Android-Party-Project/blob/main/Result%20Images/owner-user.jpg" width="180" >  
</p>

<p align="center">

| Home - Calendar | Calendar - Add New Event | Event Summary | Profile |
| ------------- | ------------- | ------------- | ------------- |
| Similar page to all 3 types of users  | Page relevant for  Place Owners only  | Event registration by a DJ/Place owner type user  | Similar page to all 3 types of users  |
| <img src="https://github.com/yonisho52/Android-Party-Project/blob/main/Result%20Images/calendar.jpg" width="180" >  | <img src="https://github.com/yonisho52/Android-Party-Project/blob/main/Result%20Images/calendar-add-new-event.jpg" width="180" >  | <img src="https://github.com/yonisho52/Android-Party-Project/blob/main/Result%20Images/event summary.jpg" width="180" >  | <img src="https://github.com/yonisho52/Android-Party-Project/blob/main/Result%20Images/profile.jpg" width="180" >  
</p>


<p align="center">

| Advertisement  | Saved Events | Enter Party | Now Event |
| ------------- | ------------- | ------------- | ------------- |
| Promotion page (call it a chat lobby) | Saved events page, for regular users  | Event PIN code for regular users, DJ/Place owner users wont need code  | Current events options provided to regular users  |
| <img src="https://github.com/yonisho52/Android-Party-Project/blob/main/Result%20Images/ads.jpg" width="180" >  | <img src="https://github.com/yonisho52/Android-Party-Project/blob/main/Result%20Images/saved-event-regular.jpg" width="180" >  | <img src="https://github.com/yonisho52/Android-Party-Project/blob/main/Result%20Images/insert-partCode-regular.jpg" width="180" >  | <img src="https://github.com/yonisho52/Android-Party-Project/blob/main/Result%20Images/now-event-regular.jpg" width="180" >  
</p>

<p >

| Now Event - Make Survey | Now Event - Survey Result | Now Event - Voting | Now Event - Voting |
| ------------- | ------------- | ------------- | ------------- |
| Survey creation page  | Survey results page  | Event rating to provide feedback by regular users  | Event rating to provide feedback by regular users  |
| <img src="https://github.com/yonisho52/Android-Party-Project/blob/main/Result%20Images/now-event-dj-survey.jpg" width="150" >  | <img src="https://github.com/yonisho52/Android-Party-Project/blob/main/Result%20Images/now-event-live-survey-result.jpg" width="150" >  | <img src="https://github.com/yonisho52/Android-Party-Project/blob/main/Result%20Images/party-survey-vote.jpg" width="150" >  |  <img src="https://github.com/yonisho52/Android-Party-Project/blob/main/Result%20Images/now-event-rating.jpg" width="150" >  
</p>


