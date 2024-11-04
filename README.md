
# Documentation


## TASK 3.3.2

GET http://localhost:7000/api/trips/

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 09:58:16 GMT
Content-Type: application/json
Content-Length: 962

[
{
"id": 1,
"name": "Mountain Hike",
"startTime": [
2023,
5,
12,
9,
0
],
"endTime": [
2023,
5,
12,
17,
0
],
"latitude": 34.0564,
"longitude": -118.2468,
"price": 99.99,
"category": "BEACH"
},
{
"id": 2,
"name": "City Tour",
"startTime": [
2023,
6,
15,
10,
0
],
"endTime": [
2023,
6,
15,
13,
0
],
"latitude": 40.7128,
"longitude": -74.006,
"price": 49.99,
"category": "CITY"
},
{
"id": 3,
"name": "Beach Day",
"startTime": [
2023,
7,
20,
8,
0
],
"endTime": [
2023,
7,
20,
18,
0
],
"latitude": 36.7783,
"longitude": -119.4179,
"price": 59.99,
"category": "FOREST"
},
{
"id": 4,
"name": "Wine Tasting",
"startTime": [
2023,
8,
18,
11,
0
],
"endTime": [
2023,
8,
18,
15,
0
],
"latitude": 34.0522,
"longitude": -118.2437,
"price": 129.99,
"category": "LAKE"
},
{
"id": 5,
"name": "Desert Safari",
"startTime": [
2023,
9,
25,
16,
0
],
"endTime": [
2023,
9,
25,
20,
0
],
"latitude": 25.276987,
"longitude": 55.296249,
"price": 149.99,
"category": "SEA"
},
{
"id": 6,
"name": "Historical Walk",
"startTime": [
2023,
10,
30,
14,
0
],
"endTime": [
2023,
10,
30,
17,
0
],
"latitude": 51.5074,
"longitude": -0.1278,
"price": 39.99,
"category": "SNOW"
}
]

__________________________________________________

GET http://localhost:7000/api/trips/2

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 10:10:24 GMT
Content-Type: application/json
Content-Length: 449

{
"id": 2,
"name": "City Tour",
"startTime": [
2023,
6,
15,
10,
0
],
"endTime": [
2023,
6,
15,
13,
0
],
"latitude": 40.7128,
"longitude": -74.006,
"price": 49.99,
"category": "CITY",
"guide": {
"id": 2,
"firstName": "Jane",
"lastName": "Smith",
"email": "janesmith@example.com",
"phone": "+987654321",
"yearsOfExperience": 8,
"trips": [
{
"id": 2,
"name": "City Tour",
"startTime": [
2023,
6,
15,
10,
0
],
"endTime": [
2023,
6,
15,
13,
0
],
"latitude": 40.7128,
"longitude": -74.006,
"price": 49.99,
"category": "CITY"
}
]
}
}

________________________________________________


POST http://localhost:7000/api/trips/

HTTP/1.1 201 Created
Date: Mon, 04 Nov 2024 10:13:28 GMT
Content-Type: application/json
Content-Length: 166

{
"id": 7,
"name": " Lang tur",
"startTime": [
2023,
7,
21,
9,
1
],
"endTime": [
2023,
9,
15,
13,
0
],
"latitude": 40.7128,
"longitude": -74.006,
"price": 49.99,
"category": "CITY",
"guide": null
}

_______________________________________

PUT http://localhost:7000/api/trips/3

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 10:16:18 GMT
Content-Type: application/json
Content-Length: 460

{
"id": 3,
"name": "Beach Day",
"startTime": [
2023,
7,
20,
8,
0
],
"endTime": [
2023,
7,
20,
18,
0
],
"latitude": 36.7783,
"longitude": -119.4179,
"price": 59.99,
"category": "BEACH",
"guide": {
"id": 3,
"firstName": "Michael",
"lastName": "Brown",
"email": "michaelbrown@example.com",
"phone": "+456123789",
"yearsOfExperience": 10,
"trips": [
{
"id": 3,
"name": "Beach Day",
"startTime": [
2023,
7,
20,
8,
0
],
"endTime": [
2023,
7,
20,
18,
0
],
"latitude": 36.7783,
"longitude": -119.4179,
"price": 59.99,
"category": "BEACH"
}
]
}
}

______________________________________________--


DELETE http://localhost:7000/api/trips/2

HTTP/1.1 204 No Content
Date: Mon, 04 Nov 2024 10:20:45 GMT
Content-Type: text/plain

<Response body is empty>

Response code: 204 (No Content); Time: 117ms (117 ms); Content length: 0 bytes (0 B)


_______________________________________________


PUT http://localhost:7000/api/trips/3/guides/1

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 10:26:58 GMT
Content-Type: text/plain
Content-Length: 0

<Response body is empty>

Response code: 200 (OK); Time: 22ms (22 ms); Content length: 0 bytes (0 B)


_______________________________________________________________________________________________________________________________--


## TASK 3.3.5

PUT method are used to update and POST is used to create. 
And we assume trip already exist, thats why we use PUT. 


_______________________________________________________________________________________

## TASK 8.3

It would add following to my test:
"private static String userToken, adminToken;"

add following in BeforeEach:
"UserDTO verifiedUser1 = securityDAO.getVerifiedUser(user1.getUsername(), user1.getPassword());
UserDTO verifiedAdmin = securityDAO.getVerifiedUser(admin.getUsername(), admin.getPassword());
userToken = "Bearer " + securityController.createToken(verifiedUser1);
adminToken = "Bearer " + securityController.createToken(verifiedAdmin);"

and each time I test a method I will make sure that header has token as Authorization:
header("Authorization", userToken)





