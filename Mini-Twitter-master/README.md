# Mini-Twitter
Backend for a mini messaging service, inspired by Twitter

Mini Twitter REST endpoints.

### Instructions to run the application

In windows download gardle and configure its path and environment variables in the control panel

Download the zip file and navigate to the challenge folder within the project from the terminal 
Then follow these steps:

run `gradle build`

`gradlew.bat build`

to Launch the server run `java -jar build/libs/challenge-0.0.1-SNAPSHOT.jar`

In Mac

Download the zip file and navigate to the challenge folder within the project from the terminal 
Then follow these steps:

run `gradle build`

`gradle wrapper`

`./gradlew build`

to Launch the server run `java -jar build/libs/challenge-0.0.1-SNAPSHOT.jar`


#### HTTP basic authentication
Username :  handle from people table

Password :  pwd from people table 

Example : Username :  neha  , Password : neha1

#### Messages
An endpoint to read the message list for the current user (as identified by their HTTP Basic authentication credentials). Include messages they have sent and messages sent by users they follow. 

Request Method : GET 

URL : http://localhost:8080/message

#### Messages with Search

An endpoint to read the message list for the current user (as identified by their HTTP Basic authentication credentials). Include messages they have sent and messages sent by users they follow.Support a “search=” parameter that can be used to further filter messages based on keyword.

Request Method : GET 

URL : http://localhost:8080/message/search={keyword}

Path Variable: search - The keyword to filter messages

Example : http://localhost:8080/message/search=right

#### Following
Endpoints to get the list of people the user is following.

Request Method : GET

List of people user is following -  http://localhost:8080/people/following

#### Followers
Endpoints to get the list of people who are the followers of the user.

Request Method : GET

List of followers of the user  -  http://localhost:8080/people/followers

#### Follow
An endpoint to start following another user.

Request Method : POST

URL: http://localhost:8080/people/follow/{handle}

Path Variable:  The handle of the person you want to follow

Example : http://localhost:8080/people/follow/sara


#### Unfollow
An endpoint to unfollow another user.

Request Method : POST

URL : http://localhost:8080/people/unfollow/{handle}

Path Variable:  The handle of the person you want to unfollow

Example : http://localhost:8080/people/unfollow/sara

#### Popular Follower
An endpoint that returns a list ofall users, paired with their most "popular" follower. The more followers someone has, the more "popular" they are. 

Request Method : GET

URL: http://localhost:8080/people/popular

